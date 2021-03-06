package parsers;

import operations.CreateDatabase;
import operations.IBooleanOperation;

import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Created by khaled on 11/22/17.
 */
public class CreateDatabaseParser implements IBooleanParser {

    @Override
    public boolean parse(String query) throws SQLException {
        if (isValidateQuery(query)) {
            IBooleanOperation create = new CreateDatabase(getDataBaseName(query));
            return create.execute();
        } else {
            throw new SQLException("invalid query");
        }
    }

    public String getDataBaseName(String query) {
        query = query.trim();
        String dataBaseName = query.split("\\s+")[2];
        if(dataBaseName.contains(";")) {
            dataBaseName = dataBaseName.substring(0, dataBaseName.lastIndexOf(";"));
        }
        return dataBaseName.toLowerCase();
    }

    private boolean isValidateQuery(String query) {
        if (Pattern.matches("(?i)\\s*(CREATE)\\s+(DATABASE)\\s+\\w+\\s*(;)?\\s*", query)) {
            return true;
        }
        return false;
    }

}
