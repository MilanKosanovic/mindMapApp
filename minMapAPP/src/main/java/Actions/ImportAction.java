package Actions;

import app.Main;
import gui.MainFrame;
import lombok.SneakyThrows;
import resource.implementation.Entity;
import tree.TreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ImportAction extends AbstractAction{
    public ImportAction() {
        putValue(NAME,"Import");
        putValue(SHORT_DESCRIPTION,"Import");
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        JTree tree = MainFrame.getInstance().getJTree();
        TreeItem node = (TreeItem) tree.getLastSelectedPathComponent();
        /* if nothing is selected */
        if (node == null || !(node.getDbNode() instanceof Entity)){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Izaberite tabelu za izvoz!","Obavestenje", JOptionPane.ERROR_MESSAGE);
            return;}

        String imeTabele = node.getName();
        String path;
        File file = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = chooser.showOpenDialog(MainFrame.getInstance());
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
            file = new File(path);
            System.out.println(path);
        }
        if(file == null){
            JOptionPane.showMessageDialog(MainFrame.getInstance(), "Nije moguce otvoriti fajl","Obavestenje", JOptionPane.ERROR_MESSAGE);
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> redovi = new ArrayList<>();
        String red = bufferedReader.readLine();
        while(red != null){
            redovi.add(red);
            red = bufferedReader.readLine();
        }
        bufferedReader.close();
        for(String s : redovi){
            System.out.println(s);
        }
        String poruka = MainFrame.getInstance().getAppCore().getDatabase().checkCSV(redovi.get(0));
        if(poruka.equals("")){
            MainFrame.getInstance().getAppCore().getDatabase().insertDataToTable(redovi,imeTabele);
            MainFrame.getInstance().getAppCore().readDataFromTable(node.getName());
        }else{
            JOptionPane.showMessageDialog(MainFrame.getInstance(), poruka,"Obavestenje", JOptionPane.ERROR_MESSAGE);
        }


    }
}
