package eg.edu.alexu.csd.oop.db.cs15;

import data.DatabaseManager;
import data.IDatabaseManager;
import eg.edu.alexu.csd.oop.db.Database;
import parsers.*;

import java.sql.SQLException;

public class DatabaseImp implements Database {
    DatabaseManager databaseManager;
    public DatabaseImp(){
        databaseManager = DatabaseManager.getInstance();
    }
    @Override
    public String createDatabase(String databaseName, boolean dropIfExists) {
        if(dropIfExists){
            try {
                executeStructureQuery("DROP "+databaseName);
            } catch (SQLException e) { }
            finally {
                try {
                    executeStructureQuery("CREATE "+databaseName);
                } catch (SQLException e) {}
            }
        }
        else {
            try {
                executeStructureQuery("CREATE "+databaseName);
            } catch (SQLException e) {}
        }
        return databaseManager.databasePath(databaseName);
    }

    @Override
    public boolean executeStructureQuery(String query) throws SQLException {
        String modifiedQuery = query.trim().toLowerCase();
        IBooleanParser booleanParser = null;
        if(modifiedQuery.startsWith("create table")) {
            booleanParser = new CreateTableParser();
        }
        else if(modifiedQuery.startsWith("create")) {
            booleanParser = new CreateDatabaseParser();
        }
        else if(modifiedQuery.startsWith("drop table")) {
            booleanParser = new DropTableParser();
        }else if(modifiedQuery.startsWith("create")) {
            booleanParser = new DropDatabaseParser();
        }
        else {
            throw new SQLException();
        }
        return booleanParser.parse(query);
    }

    @Override
    public Object[][] executeQuery(String query) throws SQLException {
        ICollectionParser collectionParser = null;
        String modifiedQuery = query.trim().toLowerCase();
        if(modifiedQuery.startsWith("select"))
            collectionParser = new SelectParser();
        else
            throw new SQLException();
        return collectionParser.parse(query);
    }

    @Override
    public int executeUpdateQuery(String query) throws SQLException {
        IIntegerParser integerParser = null;
        String modifiedQuery = query.trim().toLowerCase();
        if(modifiedQuery.startsWith("insert")) {
            integerParser = new InsertParser();
        }
        else if(modifiedQuery.startsWith("update")) {
            integerParser = new UpdateParser();
        }
        else if(modifiedQuery.startsWith("delete")) {
            integerParser = new DeleteParser();
        }
        else
            throw new SQLException();
        return integerParser.parse(query);
    }
}
