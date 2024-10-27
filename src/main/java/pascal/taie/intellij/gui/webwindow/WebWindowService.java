package pascal.taie.intellij.gui.webwindow;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.ui.jcef.JBCefApp;

import javax.swing.JComponent;
import javax.swing.JLabel;


@Service(Service.Level.PROJECT)
public final class WebWindowService {

    private final WebWindow webWindow;

    private final JComponent component;

    private String url = "index.html";
    private String queryContent = "Hello world";

    public WebWindowService(Project project) {
        if (JBCefApp.isSupported()) {
            webWindow = new WebWindow(project, this);
            component = webWindow.getComponent();
        } else {
            webWindow = null;
            component = new JLabel("CEF is not supported on this platform");
        }
    }

    public JComponent getComponent() {
        return component;
    }

    public synchronized void reload(String url, String queryContent) {
        this.url = url;
        this.queryContent = queryContent;
        webWindow.reload();
    }

    public String getUrl() {
        return url;
    }

    public String getQueryContent() {
        return queryContent;
    }
}
