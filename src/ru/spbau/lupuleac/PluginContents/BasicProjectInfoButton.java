package ru.spbau.lupuleac.PluginContents;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import ru.spbau.lupuleac.ProjectInfo.ClassInfo;
import ru.spbau.lupuleac.ProjectInfo.ProjectInfo;

import java.util.Map;

public class BasicProjectInfoButton extends AnAction {
    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.
    BasicProjectInfoButton() {
        // Set the menu item name.
        super("Basic Project Information");
        // Set the menu item name, description and icon.
        // super("Text _Boxes","Item description",IconLoader.getIcon("/Mypackage/icon.png"));
    }

    private String getMeassage(ProjectInfo projectInfo){
        StringBuilder message = new StringBuilder();
        message.append("Total number of classes: ");
        message.append(Integer.toString(projectInfo.getTotalNumberOfClasses()));
        message.append("\nTotal number of methods: ");
        message.append(Integer.toString(projectInfo.getTotalNumberOfMethods()));
        message.append("\nAverage method length in project: ");
        message.append(Double.toString(projectInfo.getAverageMethodLength()));
        message.append("\nAverage number of fields in classes: ");
        message.append(Double.toString(projectInfo.getAverageNumberOfFields()));
        message.append('\n');
        for(Map.Entry<PsiClass, ClassInfo> entry :  projectInfo.getClassInfoMap ().entrySet()){
            message.append(entry.getKey().getName());
            message.append(":\n    average method length: ");
            message.append(entry.getValue().averageMethodLength());
            message.append("\n    average length of field names: ");
            message.append(entry.getValue().averageFieldsNamesLength());
            message.append('\n');
        }
        return message.toString();
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        ProjectInfo projectInfo = new ProjectInfo(project);
        Messages.showInfoMessage(getMeassage(projectInfo),
                "Basic Project Info");
    }
}
