package ru.spbau.lupuleac.ProjectInfo;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.ProjectScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which collects the information about project:
 * total number of classes, total number of methods,
 * average length of method in project,
 * average number of fields in classes
 * and provides a Map with the information about each class.
 */
public class ProjectInfo {
    private Project project;
    private int totalNumberOfClasses;
    private Map<PsiClass, ClassInfo> classInfoMap;

    public ProjectInfo(Project project) {
        this.project = project;
        classInfoMap = new HashMap<>();
        collectInfo();
    }

    /**
     * @return total number of classes in the project
     */
    public int getTotalNumberOfClasses() {
        return totalNumberOfClasses;
    }

    /**
     * @return total number of methods in the project
     */
    public int getTotalNumberOfMethods() {
        int totalNumberOfMethods = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalNumberOfMethods += classInfo.getNumberOfMethods();
        }
        return totalNumberOfMethods;
    }

    /**
     * @return average number of fields in class for this project
     */
    public double getAverageNumberOfFields() {
        double totalNumberOfFields = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalNumberOfFields += classInfo.getNumberOfFields();
        }
        return totalNumberOfFields / totalNumberOfClasses;
    }

    /**
     * @return average method length in the project
     */
    public double getAverageMethodLength() {
        double totalMethodLength = 0;
        for (ClassInfo classInfo : classInfoMap.values()) {
            totalMethodLength += classInfo.getTotalMethodsLength();
        }
        return totalMethodLength / getTotalNumberOfMethods();
    }

    @NotNull
    public Map<PsiClass, ClassInfo> getClassInfoMap() {
        return classInfoMap;
    }

    /**
     * Collects the information from the project.
     */
    private void collectInfo() {
        Collection<VirtualFile> files = FileBasedIndex.getInstance()
                .getContainingFiles(
                        FileTypeIndex.NAME,
                        JavaFileType.INSTANCE,
                        ProjectScope.getProjectScope(project));
        for (VirtualFile file : files) {
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

    /**
     * Gets all methods from class and calculates their number and total body length.
     *
     * @param psiClass is psi class which methods are to be explored
     */
    private void collectInfoAboutMethods(@NotNull PsiClass psiClass) {
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

    /**
     * Gets all fields from class and calculates their number and total name's length.
     *
     * @param psiClass is psi class which fields are to be explored
     */
    private void collectInfoAboutFields(@NotNull PsiClass psiClass) {
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
