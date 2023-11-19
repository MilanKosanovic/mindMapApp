package checker;

import gui.MainFrame;
import resource.DBNode;
import resource.data.Row;
import resource.enums.RulesSQL;
import utils.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

public class RuleColumn extends Rule{
    public RuleColumn(String name, String description, String suggestion, RulesSQL rule) {
        super(name, description, suggestion, rule);
    }

    @Override
    public String checkRule(String sqlQuery) {

        String imeBaze;
        imeBaze = Constants.MYSQL_DATABASE;

        List<String> tabeleSaBazom = new ArrayList<>();
        List<String> tabele = new ArrayList<>();
        List<String> koloneSaBazom = new ArrayList<>();
        List<String> kolone = new ArrayList<>();

        String split[] = sqlQuery.split(" ");

        for (String s : split) {
            if (s.contains(imeBaze))
                tabeleSaBazom.add(s);
        }

        for (int i = 0; i < tabeleSaBazom.size(); i++) {
            tabele.add(tabeleSaBazom.get(i).split("\\.")[1]);
        }

        for (String s : split) {
            for (int i = 0; i < tabele.size(); i++) {
                if (s.contains(tabele.get(i)))
                    koloneSaBazom.add(s);
            }
        }

        for (int i = 0; i < koloneSaBazom.size(); i++) {
            kolone.add(koloneSaBazom.get(i).split("\\.")[1]);
        }
        for (String s : tabele) {
            if (kolone.contains(s)) {
                kolone.remove(s);
            }
        }

        List<DBNode> tabeleBaze = MainFrame.getInstance().getAppCore().getIr().getChildren();
        List<String> imenaTabela = new ArrayList<>();

        for (DBNode db : tabeleBaze) {
            imenaTabela.add(db.getName());
        }

        int flag = tabele.size();
        for (String s : tabele) {
            if (imenaTabela.contains(s.toUpperCase(Locale.ROOT))) {
                flag--;
            } else {
                System.out.println("NE");
            }
        }

        if (flag != 0) {
            return this.getName() + " " + this.getDescription() + " " + this.getSuggestion();
        }

        List<Row> redoviSvi = null;
        List<Row> redovi2 = null;

        if (!tabele.isEmpty()) {

            if (tabele.size() == 1) {
                redoviSvi = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(tabele.get(0).toUpperCase(Locale.ROOT));
            }

            if (tabele.size() == 2) {
                redoviSvi = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(tabele.get(0).toUpperCase(Locale.ROOT));
                redovi2 = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(tabele.get(1).toUpperCase(Locale.ROOT));
            }
        }

        //System.out.println(tabele);
        //System.out.println();

        List<String> zaglavlja = new ArrayList<>();

        if (!tabele.isEmpty()) {

            for (String s : redoviSvi.get(0).getFields().keySet()) {
                zaglavlja.add(s);
            }

            if (tabele.size() == 2) {
                for (String s : redovi2.get(0).getFields().keySet()) {
                    zaglavlja.add(s);
                }
            }
        }

        for (String s : zaglavlja) {
            //System.out.println(s);
        }

        //String split2[] = zaCsv.split(",");

        int flag2 = kolone.size();

        for (String s : kolone) {
            //System.out.println(s.toUpperCase(Locale.ROOT));
        }

        for (String s : zaglavlja) {
            //System.out.println(s.toUpperCase(Locale.ROOT));
        }

        for (int i = 0; i < zaglavlja.size(); i++) {

            for (int j = 0; j < kolone.size(); j++) {
                if (zaglavlja.get(i).equals(kolone.get(j).toUpperCase(Locale.ROOT))) {
                    flag2--;
                }
            }
        }
        System.out.println("flag2: " + flag2 + "flag: " + flag);


        if (flag2 != 0) {

            return this.getName() + " " + this.getDescription() + " " + this.getSuggestion();
        }

        return "";
    }
}
