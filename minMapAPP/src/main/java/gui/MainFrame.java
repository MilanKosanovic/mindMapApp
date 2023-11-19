package gui;

import Actions.ActionManager;
import app.AppCore;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import tree.implementation.SelectionListener;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Data
public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jsp;
    private JTree jTree;
    private JPanel left;
    private JToolBar jToolBar;
    private ActionManager actionManager;
    private TextPaneNas textPane;

    private MainFrame() {

    }

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.setSize(screenSize.width/2, screenSize.height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.actionManager = new ActionManager();

        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(1500, 500));
        jTable.setFillsViewportHeight(true);

        jToolBar = new Toolbar();
        this.add(jToolBar,BorderLayout.NORTH);

        textPane = new TextPaneNas();
        this.add(textPane,BorderLayout.CENTER);


        this.add(new JScrollPane(jTable),BorderLayout.SOUTH);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }

    private void initialiseTree() {
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jsp = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        left = new JPanel(new BorderLayout());
        left.add(jsp, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        pack();
    }


    @Override
    public void update(Notification notification) {


    }

    public String getQuerry(){

        return this.getTextPane().getText();
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public TextPaneNas getTextPane() {
        return textPane;
    }

    public AppCore getAppCore() {
        return appCore;
    }
}
