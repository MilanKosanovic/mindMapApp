package checker;

import lombok.Getter;
import resource.enums.RulesSQL;

import java.util.Stack;


public class Rule {

    private String name;
    private String description;
    private String suggestion;
    private RulesSQL rule;


    public Rule(String name, String description, String suggestion, RulesSQL rule) {
        this.name = name;
        this.description = description;
        this.suggestion = suggestion;
        this.rule = rule;
    }

    public String checkRule(String sqlQuery){
        return "";
    }


    ///geteri i seteri

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
