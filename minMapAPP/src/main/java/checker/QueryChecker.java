package checker;

import java.util.Stack;

public interface QueryChecker {

        public Stack<String> check(String sqlQuery);

        String checkCSV(String csvFile);
}
