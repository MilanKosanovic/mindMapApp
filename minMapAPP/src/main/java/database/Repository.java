package database;

import resource.DBNode;
import resource.data.Row;

import java.sql.SQLException;
import java.util.List;

public interface Repository {

    DBNode getSchema();

    void insert(List<String> redovi,String imeTabele);

    List<Row> get(String from);

    List<Row>execute(String querry);
}
