package database;

import database.settings.Settings;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class MYSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MYSQLrepository(Settings settings) {
        this.settings = settings;
    }



    private void initConnection() throws SQLException, ClassNotFoundException{
        String ip = (String) settings.getParameter("mysql_ip");
        String database = (String) settings.getParameter("mysql_database");
        String username = (String) settings.getParameter("mysql_username");
        String password = (String) settings.getParameter("mysql_password");
        //Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database,username,password);


    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }

    @Override
    public void insert(List<String> redovi,String imeTabele) {

        String temp = "`";
        temp = temp + imeTabele + "`";

        String zaglavlje = "";
        int j = 0;
        String redsplit1[] = redovi.get(0).split(",");
        for(String s : redsplit1){
            zaglavlje=zaglavlje +"`" + s +"`";
            if(j != redsplit1.length-1){
                zaglavlje+=",";
            }
            j++;
        }
            try {
                initConnection();
                int br=0;
                for(String red : redovi){

                    String red_spreman = "";
                    int i = 0;
                    String redsplit[] = red.split(",");
                    for(String s : redsplit){
                        red_spreman = red_spreman +"'" + s +"'";
                        if(i != redsplit.length-1){
                            red_spreman+=",";
                        }
                        i++;
                    }
                    if(br != 0) {
                        String query = "INSERT INTO " + temp + " (" + zaglavlje + ") VALUES" + " (" + red_spreman + ")";
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        System.out.println(query);
                        boolean rs = preparedStatement.execute();
                    }
                    br++;
                }
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            catch (ClassNotFoundException e2){ e2.printStackTrace();}
            finally {
                this.closeConnection();
            }
    }

    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("RAF_BP_Primer");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()){

                String tableName = tables.getString("TABLE_NAME");
                if(tableName.contains("trace"))continue;
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                //Koje atribute imaja ova tabela?

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()){

                    // COLUMN_NAME TYPE_NAME COLUMN_SIZE ....

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                    System.out.println(columnType);

                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));

//                    ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
//
//                    while (pkeys.next()){
//                        String pkColumnName = pkeys.getString("COLUMN_NAME");
//                    }


                    Attribute attribute = new Attribute(columnName, newTable,
                            AttributeType.valueOf(
                                    Arrays.stream(columnType.toUpperCase().split(" "))
                                    .collect(Collectors.joining("_"))),
                            columnSize);
                    newTable.addChild(attribute);

                }



            }


            //TODO Ogranicenja nad kolonama? Relacije?


            return ir;
            //String isNullable = columns.getString("IS_NULLABLE");
            // ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, table.getName());
            // ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, table.getName());

        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        catch (ClassNotFoundException e2){ e2.printStackTrace();}
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }

    @Override
    public List<Row> execute(String querry) {
        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();


            PreparedStatement preparedStatement = connection.prepareStatement(querry);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()){

                Row row = new Row();
                row.setName("nasQuerry");

                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;

    }
}
