package ru.spbau.lupuleac.PluginContents;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;
import ru.spbau.lupuleac.ProjectInfo.ClassInfo;
import ru.spbau.lupuleac.ProjectInfo.ProjectInfo;

import java.util.Map;

public class BasicProjectInfoButton extends AnAction {
    public BasicProjectInfoButton() {
        // Set the menu item name.
        super("Basic Project Information");
    }

    /**
     * Retrieves the information for the message box.
     *
     * @param projectInfo is an object of ProjectInfo class
     * @return a string containing whole required information
     */
    @NotNull
    private String getMessage(@NotNull ProjectInfo projectInfo) {
        StringBuilder message = new StringBuilder();
        message.append("Total number of classes: ");
        message.append(Integer.toString(projectInfo.getTotalNumberOfClasses()));
        message.append("\nTotal number of methods: ");
        message.append(Integer.toString(projectInfo.getTotalNumberOfMethods()));
        message.append("\nAverage method length in project: ");
        message.append(String.format("%.2f", projectInfo.getAverageMethodLength()));
        message.append("\nAverage number of fields in classes: ");
        message.append(String.format("%.2f", projectInfo.getAverageNumberOfFields()));
        message.append('\n');
        for (Map.Entry<PsiClass, ClassInfo> entry : projectInfo.getClassInfoMap().entrySet()) {
            message.append(entry.getKey().getQualifiedName());
            message.append(":\n    average method length: ");
            message.append(String.format("%.2f", entry.getValue().averageMethodLength()));
            message.append("\n    average length of field names: ");
            message.append(String.format("%.2f", entry.getValue().averageFieldsNamesLength()));
            message.append('\n');
        }
        return message.toString();
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        ProjectInfo projectInfo = new ProjectInfo(project);
        Messages.showInfoMessage(getMessage(projectInfo),
                "Basic Project Info");
    }
}
