package Actions;

import gui.MainFrame;
import lombok.SneakyThrows;
import resource.data.Row;
import resource.implementation.Entity;
import tree.TreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExportAction extends AbstractAction{

        public ExportAction() {
            putValue(NAME,"Export");
            putValue(SHORT_DESCRIPTION,"Export");
        }

        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            JTree tree = MainFrame.getInstance().getJTree();
            TreeItem node = (TreeItem) tree.getLastSelectedPathComponent();
            /* if nothing is selected */
            if (node == null || !(node.getDbNode() instanceof Entity)) return;

            List<Row> listaRedova = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(node.getName());
            int brKolona = listaRedova.get(0).getBrojKolona();
            String path = null;

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(MainFrame.getInstance());
            if(returnVal == JFileChooser.APPROVE_OPTION) {
               // System.out.println("Usaooooo");
                path = chooser.getSelectedFile().getAbsolutePath()+ "\\"+ node.getName() + ".csv";


                File file = new File(path);
                file.createNewFile();
            }



            FileWriter myWriter = new FileWriter(path);

            Set<String> zaglavnja = listaRedova.get(0).getFields().keySet();

            int k=0;
            for(String s: zaglavnja){
                k++;
                myWriter.write(s);
                if(k!=brKolona){
                    myWriter.write(",");
                }
            }
            myWriter.write("\n");

            for(Row red : listaRedova){
                k=0;
                Map<String, Object> polje = red.getFields();

                for(String key: zaglavnja){
                    myWriter.write((String) polje.get(key));
                    if(k!=brKolona-1){
                        myWriter.write(",");
                    }
                    k++;
                }
                myWriter.write("\n");
            }


            myWriter.close();
        }


}
