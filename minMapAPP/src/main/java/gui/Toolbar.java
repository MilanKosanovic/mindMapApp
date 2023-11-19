package gui;

import javax.swing.*;

public class Toolbar extends JToolBar {

    private JMenu jMenuImport;
    private JMenu jMenuExport;
    private JMenu jMenuPretty;
    private JMenu jMenuQChecker;

    public Toolbar(){
        init();
    }
    private void init(){


        this.add(MainFrame.getInstance().getActionManager().getImportAction());
        this.add(MainFrame.getInstance().getActionManager().getExportAction());
        this.add(MainFrame.getInstance().getActionManager().getPrettyAction());
        this.add(MainFrame.getInstance().getActionManager().getRunAction());
    }

}
