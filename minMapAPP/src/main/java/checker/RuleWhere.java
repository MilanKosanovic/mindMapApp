package checker;

import resource.enums.RulesSQL;

import java.util.Stack;

public class RuleWhere extends Rule {
    public RuleWhere(String name, String description, String suggestion, RulesSQL rule) {
        super(name, description, suggestion, rule);
    }

    @Override
    public String checkRule(String sqlQuery) {
        return super.checkRule(sqlQuery);
    }
}