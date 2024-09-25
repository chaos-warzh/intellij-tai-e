package pascal.taie.intellij.common;

import java.io.File;

public record TaiePluginConstants() {

    public static final String PLUGIN_NAME = "Intellij Tai-e";
    public static final String TOOL_WINDOW_ID = "Tai-e";
    public static final String DEFAULT_EXPORT_DIR = System.getProperty("user.home") + File.separatorChar + TOOL_WINDOW_ID;

    // For left-hand-side toolbar view
    public static final String ACTION_GROUP_CUSTOMIZED_LEFT = "pascal.taie.intellij.customized.left";
    public static final String ACTION_GROUP_TAINT_LEFT = "pascal.taie.intellij.taint.left";

}
