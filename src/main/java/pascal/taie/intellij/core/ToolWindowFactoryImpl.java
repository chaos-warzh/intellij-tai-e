package pascal.taie.intellij.core;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.gui.toolwindow.view.TaintToolWindowPanel;

public final class ToolWindowFactoryImpl implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        // for taint analysis:
        final TaintToolWindowPanel taintToolWin = new TaintToolWindowPanel(project, toolWindow);
        toolWindow.getContentManager().addContent(ApplicationManager.getApplication().getService(ContentFactory.class)
                .createContent(taintToolWin.getRootPanel(), "", false));
    }
}
