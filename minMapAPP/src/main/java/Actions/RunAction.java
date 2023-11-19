package Actions;

import checker.QueryChecker;
import checker.QueryCheckerImplementation;
import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Stack;

public class RunAction extends AbstractAction{
    public RunAction() {
        putValue(NAME,"R u n");
        putValue(SHORT_DESCRIPTION,"R u n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String poruka = "";
        Stack<String> stack = new Stack<>();
        stack = MainFrame.getInstance().getAppCore().getDatabase().check(MainFrame.getInstance().getQuerry());
        if(!stack.empty()){
            while(!stack.empty()){
                poruka+=stack.pop();
                poruka+="\n";

            }

            JOptionPane.showMessageDialog(MainFrame.getInstance(), poruka,"Obavestenje", JOptionPane.ERROR_MESSAGE);
        }
        else{
            MainFrame.getInstance().getAppCore().executeUpit(MainFrame.getInstance().getQuerry());
            //MainFrame.getInstance().getAppCore().getDatabase().checkCSV("asda");
        }

    }
}
