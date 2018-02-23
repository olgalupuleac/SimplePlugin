package ru.spbau.lupuleac.PluginContents;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;
import ru.spbau.lupuleac.PluginContents.TextBoxes;

public class MyPluginRegistration implements ApplicationComponent {
    // Returns the component name (any unique string value).
    @NotNull
    public String getComponentName() {
        return "MyPlugin";
    }


    // If you register the ru.spbau.lupuleac.MyPlugin.MyPluginRegistration class in the <application-components> section of
    // the plugin.xml file, this method is called on IDEA start-up.
    public void initComponent() {
        ActionManager am = ActionManager.getInstance();
        TextBoxes action = new TextBoxes();

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