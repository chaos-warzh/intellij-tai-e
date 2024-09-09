package pascal.taie.intellij.gui.toolwindow.view;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaintToolWindowPanel extends JComponent implements Disposable {

    private JPanel rootPanel;

    private JTextField searchCtt;

    private JTable taintTable;

    private JButton taintConfigButton;

    private JButton analyzeButton;

    private JButton stopButton;

    private JButton searchButton;

    private static final String[] HEAD = {"No.", "Taint Flow"};

    private static final DefaultTableModel model = new DefaultTableModel(null, HEAD);

    public TaintToolWindowPanel(final Project project, final ToolWindow toolWindow) {
        // tbd
        init();
    }

    private void init() {
        initTable();
        initButton();
    }

    private void initTable() {
        taintTable.setModel(model);
        taintTable.setEnabled(true);
    }

    private void initButton() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        taintConfigButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input =
                        Messages.showInputDialog("Please input the taint config file path.", "Taint Config", Messages.getQuestionIcon());
            }
        });
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void dispose() {
        // tbd
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public static DefaultTableModel getTableModel() {
        return model;
    }
}
