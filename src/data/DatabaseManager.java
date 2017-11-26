package data;

import data.Exceptions.DatabaseNotFoundException;

import javax.xml.crypto.Data;
import java.io.File;

/**
 * Created by khaled on 11/26/17.
 */
public class DatabaseManager implements IDatabaseManager {
    private Database databaseInUse;

    @Override
    public void createDatabase(String databaseName) {
        //TODO create database folder
        databaseInUse = new Database(databaseName);
    }

    @Override
    public void dropDatabase(String databaseName) {
        //TODO delete database folder
    }

    @Override
    public Database getDatabaseInUse(String databaseName) {
        return databaseInUse;
    }

    @Override
    public void setDatabaseInUse(String databaseName) throws DatabaseNotFoundException {
        databaseInUse = new Database(databaseName);
    }
}