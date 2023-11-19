package database;

import checker.QueryChecker;
import checker.QueryCheckerImplementation;
import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.sql.SQLException;
import java.util.List;
import java.util.Stack;

@Data

public class DatabaseImplementation implements Database {

    private Repository repository;
    private QueryChecker queryChecker;


    public DatabaseImplementation(Repository repository) {
        this.repository = repository;
        this.queryChecker = new QueryCheckerImplementation();
        init();
    }

    private void init(){

    }

    @Override
    public void insertDataToTable(List<String> redovi,String imeTabele){
            repository.insert(redovi,imeTabele);
    }

    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

    public Stack<String> check(String sqlQuery) {
        return queryChecker.check(sqlQuery);
    }

    @Override
    public List<Row> executeQuerry(String querry) {



        return this.repository.execute(querry);
    }

    @Override
    public String checkCSV(String zaglavlje) {
        return this.queryChecker.checkCSV(zaglavlje);
    }
}
