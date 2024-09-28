package pascal.taie.intellij.gui.toolwindow;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import pascal.taie.intellij.gui.toolwindow.view.TaieToolWindowPanel;

public final class ToolWindowFactoryImpl implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        final TaieToolWindowPanel toolWin = new TaieToolWindowPanel(project);
        final ContentFactory contentFactory = ApplicationManager.getApplication().getService(ContentFactory.class);
        final Content content = contentFactory.createContent(toolWin.getRootPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
