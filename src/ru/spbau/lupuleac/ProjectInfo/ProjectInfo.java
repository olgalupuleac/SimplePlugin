package ru.spbau.lupuleac.ProjectInfo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;

import java.util.HashMap;
import java.util.Map;

public class ProjectInfo {
    private Project project;
    private int totalNumberOfClasses;
    private Map<PsiClass, ClassInfo> classInfoMap;

    public ProjectInfo(Project project){
        this.project = project;
        classInfoMap = new HashMap<>();
    }

    public String getProjectName(){
        return project.getName();
    }

    private void projectJavaClasses(){
        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();
        for (VirtualFile file : vFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
            if(psiFile instanceof PsiJavaFile){
                PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile;
                final PsiClass[] classes = psiJavaFile.getClasses();
                totalNumberOfClasses += classes.length;
                for(PsiClass psiClass : classes){
                    psiClass.getAllMethods();
                }
            }

        }
    }

    private void collectInfoAboutMethods(PsiClass psiClass){
        if(!classInfoMap.containsKey(psiClass)){
            classInfoMap.put(psiClass, new ClassInfo());
        }
        //the methods from the superclass are not included
        PsiMethod[] methods = psiClass.getMethods();
        ClassInfo classInfo = classInfoMap.get(psiClass);
        classInfo.setNumberOfMethods(methods.length);
        for(PsiMethod method : methods){
            PsiCodeBlock methodBody = method.getBody();
            if(methodBody != null){
                classInfo.increaseTotalMethodsLength(methodBody.getText().length());
            }
        }
    }

}
