package operations;

import data.*;

public class DeleteFromTable implements IIntegerOperation {
    private query.ICondition myCondition;
    private String tableName;

    public DeleteFromTable(String tableName, query.ICondition condition) {
        this.tableName = tableName;
        myCondition = condition;
    }


    @Override
    public int execute() {
        IDatabaseManager manager = new DatabaseManager();
        IDatabase database = manager.getDatabaseInUse();
        TableXML table = new TableXML(database.getName(), tableName);
        return table.delete(myCondition);
    }
}
