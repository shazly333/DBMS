package data;

import java.util.List;

/**
 * Created by khaled on 11/22/17.
 */
public interface ITable {
    boolean insert(IRecord record);
    List<IRecord> select(ICondition condition);
    int delete(ICondition condition);
    int update(ICondition condition, IRecord record);
    void addField(IField field);
    List<IField> getFields();
    void setFields(List<IField> fields);
    boolean fieldExists(IField field);


}