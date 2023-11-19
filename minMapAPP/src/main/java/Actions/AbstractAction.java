package Actions;

import javax.swing.*;
import java.net.URL;

public abstract class AbstractAction extends javax.swing.AbstractAction {

    public Icon loadIcon(String fileName){
        URL imageURl = getClass().getResource(fileName);
        Icon icon = null;

        if(imageURl != null){
            icon = new ImageIcon(imageURl);
        }else{
            System.out.println("Image icon not found" + fileName);
        }
        return icon;
    }
}
