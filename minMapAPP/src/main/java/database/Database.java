package database;

import checker.QueryChecker;
import resource.DBNode;
import resource.data.Row;

import java.sql.SQLException;
import java.util.List;
import java.util.Stack;

public interface Database{

    DBNode loadResource();

    void insertDataToTable(List<String> redovi,String imeTabele) throws SQLException;

    List<Row> readDataFromTable(String tableName);

    public Stack<String> check(java.lang.String sqlQuery);

    List<Row> executeQuerry(String querry);

    String checkCSV(String zaglavlje);
}
