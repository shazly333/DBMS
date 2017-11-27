package parsers;

import java.sql.SQLException;

/**
 * Created by khaled on 11/22/17.
 */
public interface IBooleanParser {
    boolean parse(String query) throws SQLException, IllegalAccessException, InstantiationException;
}
