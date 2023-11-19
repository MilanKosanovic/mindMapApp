package checker;

import  org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import resource.enums.RulesSQL;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class QueryCheckerImplementation implements QueryChecker{

    List<Rule> rules;
    Stack<String> stack;
    private int brojac=-1;
    Rule ruleCsv;



    public QueryCheckerImplementation() {
        rules = new ArrayList<>();
        stack = new Stack<>();

        init();
    }

    private void init(){
        String is = null;
        List<String> pravila = new ArrayList<>();


        try{
           Object obj = new JSONParser().parse(new FileReader("src/main/java/checker/rules.txt"));
           JSONObject jsonObject = (JSONObject) obj;

           JSONArray jsonArray = (JSONArray) jsonObject.get("rules");
           for(int i=0;i<8;i++){
               JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
               pravila.add(jsonObject1.get("ruleName").toString());
               pravila.add(jsonObject1.get("description").toString());
               pravila.add(jsonObject1.get("suggestion").toString());
           }
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            if(pravila.isEmpty()){
                System.out.println("Inicijalizacija checkera nije uspela");
                return;
            }
        }
        rules.add(new RuleTable(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.NON_EXISTING_TABLE));
        rules.add(new RuleColumn(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.NON_EXISTING_COLUMN));
        rules.add(new RuleQueryValid(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.QUERY_NOT_VALID));
        rules.add(new RuleQueryOrder(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.QUERY_WRONG_ORDER));
        rules.add(new RuleAlias(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.ALIAS_DOUBLE_QUOTES));
        rules.add(new RuleGroup(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.FUNCTION_MISSING_GROUP_BY));
        rules.add(new RuleWhere(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.WHERE_WRONG_FUNCTION));
        this.ruleCsv = new RuleCSV(pravila.get(++brojac),pravila.get(++brojac),pravila.get(++brojac), RulesSQL.CSV_FILE_NOT_VALID);
    }

    public Stack<String> check(String sqlQuery){
        while(!stack.empty()){
            stack.pop();
        }

        for(Rule r : rules){
            String s = r.checkRule(sqlQuery);
            if(!s.equals("")){
                stack.push(s);
            }
        }

        return stack;
    }

    @Override
    public String checkCSV(String zaglavlja) {

        return this.ruleCsv.checkRule(zaglavlja);
    }
}
