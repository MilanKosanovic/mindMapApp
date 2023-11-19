package Actions;

import gui.MainFrame;
import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import resource.enums.KeyWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Locale;

public class PrettyAction extends AbstractAction implements Publisher {
    public PrettyAction() {
        putValue(NAME,"Pretty");
        putValue(SHORT_DESCRIPTION,"Pretty");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    String output = MainFrame.getInstance().getTextPane().getText();
    String niz[] = output.split("\\s+");

    MainFrame.getInstance().getTextPane().setText("");

    HashSet<String> hashSet = new HashSet<>();
        for (KeyWord k : KeyWord.values()) {
            hashSet.add(k.toString());
        }
        int flag =0,iteracija = 0;

        for(String s: niz){

            flag=0;

            for(String key: hashSet){
                if((s.toUpperCase(Locale.ROOT).equals(key))){
                    flag = 1;
                    if(iteracija == 1){
                        MainFrame.getInstance().getTextPane().appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.BLACK);
                        MainFrame.getInstance().getTextPane().appendToPane(MainFrame.getInstance().getTextPane(), "\n", Color.BLACK);
                    }
                    MainFrame.getInstance().getTextPane().appendToPane(MainFrame.getInstance().getTextPane(), s.toUpperCase(Locale.ROOT), Color.CYAN);
                    iteracija = 1;
                }
            }
                if(flag == 0) {
                    MainFrame.getInstance().getTextPane().appendToPane(MainFrame.getInstance().getTextPane(), " ", Color.BLACK);
                    MainFrame.getInstance().getTextPane().appendToPane(MainFrame.getInstance().getTextPane(), s, Color.BLACK);
                }
        }
    }

    @Override
    public void addSubscriber(Subscriber sub) {

    }

    @Override
    public void removeSubscriber(Subscriber sub) {

    }

    @Override
    public void notifySubscribers(Notification notification) {

    }
}
