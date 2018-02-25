package ru.spbau.lupuleac.PluginContents;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;


public class MyPluginRegistration implements ApplicationComponent {
    // Returns the component name (any unique string value).
    @NotNull
    public String getComponentName() {
        return "MyPlugin";
    }

    public void initComponent() {
        ActionManager am = ActionManager.getInstance();
        BasicProjectInfoButton action = new BasicProjectInfoButton();

        // Passes an instance of your custom ru.spbau.lupuleac.MyPlugin.TextBoxes class to the registerAction method of the ActionManager class.
        am.registerAction("MyPluginAction", action);

        // Gets an instance of the WindowMenu action group.
        DefaultActionGroup windowM = (DefaultActionGroup) am.getAction("WindowMenu");

        // Adds a separator and a new menu command to the WindowMenu group on the main menu.
        windowM.addSeparator();
        windowM.add(action);
    }

    // Disposes system resources.
    public void disposeComponent() {
    }
}