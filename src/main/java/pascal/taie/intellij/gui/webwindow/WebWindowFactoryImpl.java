package pascal.taie.intellij.gui.webwindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public class WebWindowFactoryImpl implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        WebWindowService service = project.getService(WebWindowService.class);
        Content content = ContentFactory.getInstance().createContent(service.getComponent(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
