package checker;

import com.mysql.cj.x.protobuf.MysqlxExpect;
import resource.enums.KeyWord;
import resource.enums.RulesSQL;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RuleQueryOrder extends Rule{

    List<String> wordOrder;

    public RuleQueryOrder(String name, String description, String suggestion, RulesSQL rule) {
        super(name, description, suggestion, rule);
        wordOrder = Arrays.asList("SELECT", "FROM", "INNER_OUTER_CROSS", "JOIN", "ON", "WHERE", "GROUP",
                "BY", "ORDER", "BY", "DESC_ASC");
    }

    @Override
    public String checkRule(String sqlQuery) {
        List<Integer> indeksi = new ArrayList<>();
        List<String> keyWordFromQuery = new ArrayList<>();
        List<String> keyWords = Stream.of(KeyWord.values())
                .map(KeyWord::name)
                .collect(Collectors.toList());
        String split[] = sqlQuery.split("[\\n\\s]");

        for(String s : split){
            if(keyWords.contains(s.toUpperCase())){
                keyWordFromQuery.add(s.toUpperCase());        //sve kljucne reci iz kverija
            }
        }

        for(String s : keyWordFromQuery){
              indeksi.add(wordOrder.indexOf(s));
            System.out.println(wordOrder.indexOf(s));
        }

        if(!isSequential(indeksi)){
            return this.getName() + " " + this.getDescription() + " " +this.getSuggestion();
        }

        return "";
    }

    private boolean isSequential(List<Integer> list1){

        int i=0,j=1;
        for(i = 0 ; i < list1.size()-1 ;i++,j++ ){
            if(list1.get(i).compareTo(list1.get(j)) != -1){
                return false;
            }
        }
        return true;
    }

}
