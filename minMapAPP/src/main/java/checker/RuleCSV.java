package checker;

import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.data.Row;
import resource.enums.RulesSQL;
import tree.TreeItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RuleCSV extends Rule{

    public RuleCSV(String name, String description, String suggestion, RulesSQL rule) {
        super(name, description, suggestion, rule);
    }

    @Override
    public String checkRule(String sqlQuery) {
        JTree tree = MainFrame.getInstance().getJTree();
        TreeItem node = (TreeItem) tree.getLastSelectedPathComponent();

        List<Row> redoviSvi = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(node.getName());
        List<String> kolone = new ArrayList<>();

        for(String s : redoviSvi.get(0).getFields().keySet()){
            kolone.add(s);
        }

        String split[] = sqlQuery.split(",");
        for(String s : split){
            if(!kolone.contains(s)){
                return this.getName() + " " + this.getDescription() + " " + this.getSuggestion();
            }
        }

        return "";
    }
}
