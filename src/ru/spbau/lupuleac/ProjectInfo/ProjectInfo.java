package ru.spbau.lupuleac.ProjectInfo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;

public class ProjectInfo {
    public static String projectModules(Project project){
        String projectName = project.getName();
        StringBuilder sourceRootsList = new StringBuilder();
        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();
        for (VirtualFile file : vFiles) {
            sourceRootsList.append(file.getUrl()).append("\n");
        }

       return "Source roots for the " + projectName + " plugin:\n" + sourceRootsList;
    }
}
