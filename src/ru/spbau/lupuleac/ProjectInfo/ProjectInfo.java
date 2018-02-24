package ru.spbau.lupuleac.ProjectInfo;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.ProjectScope;
import com.intellij.util.indexing.FileBasedIndex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectInfo {
    private Project project;
    private int totalNumberOfClasses;
    private Map<PsiClass, ClassInfo> classInfoMap;

    public ProjectInfo(Project project) {
        this.project = project;
        classInfoMap = new HashMap<>();
        collectInfo();
    }

    public int getTotalNumberOfClasses() {
        return totalNumberOfClasses;
    }

    public int getTotalNumberOfMethods() {
        int totalNumberOfMethods = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalNumberOfMethods += classInfo.getNumberOfMethods();
        }
        return totalNumberOfMethods;
    }

    public double getAverageNumberOfFields() {
        double totalNumberOfFields = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalNumberOfFields += classInfo.getNumberOfFields();
        }
        return totalNumberOfFields / totalNumberOfClasses;
    }

    public double getAverageMethodLength() {
        double totalMethodLength = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalMethodLength += classInfo.getTotalMethodsLength();
        }
        return totalMethodLength / getTotalNumberOfMethods();
    }

    public Map<PsiClass, ClassInfo> getClassInfoMap() {
        return classInfoMap;
    }

    private void collectInfo() {
        //VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();
        Collection<VirtualFile> files = FileBasedIndex.getInstance()
                .getContainingFiles(
                        FileTypeIndex.NAME,
                        JavaFileType.INSTANCE,
                        ProjectScope.getProjectScope(project));
        for (VirtualFile file : files) {
            System.out.println(file.getUrl());
            PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
            if (psiFile instanceof PsiJavaFile) {
                PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
                final PsiClass[] classes = psiJavaFile.getClasses();
                totalNumberOfClasses += classes.length;
                for (PsiClass psiClass : classes) {
                    collectInfoAboutMethods(psiClass);
                    collectInfoAboutFields(psiClass);
                }
            }
        }

    }

    private void collectInfoAboutMethods(PsiClass psiClass) {
        if (!classInfoMap.containsKey(psiClass)) {
            classInfoMap.put(psiClass, new ClassInfo());
        }
        //the methods from the superclass are not included
        PsiMethod[] methods = psiClass.getMethods();
        ClassInfo classInfo = classInfoMap.get(psiClass);
        classInfo.setNumberOfMethods(methods.length);
        for (PsiMethod method : methods) {
            PsiCodeBlock methodBody = method.getBody();
            if (methodBody != null) {
                classInfo.increaseTotalMethodsLength(methodBody.getText().length());
            }
        }
    }

    private void collectInfoAboutFields(PsiClass psiClass) {
        if (!classInfoMap.containsKey(psiClass)) {
            classInfoMap.put(psiClass, new ClassInfo());
        }
        PsiField[] fields = psiClass.getFields();
        ClassInfo classInfo = classInfoMap.get(psiClass);
        classInfo.setNumberOfFields(fields.length);
        for (PsiField field : fields) {
            classInfo.increaseTotalFieldsNamesLength(field.getName().length());
        }
    }
}
