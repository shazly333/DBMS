package query;

import data.IRecord;

/**
 * Created by khaled on 11/22/17.
 */
public interface ICondition {
    boolean validate(IRecord record);
}
