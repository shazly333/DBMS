package query;

import data.ICell;
import data.IRecord;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConditionsTester {
    @Test
    public void trueConditionTest(){
        ICondition condition = new TrueCondition();
        IRecord record1 = new TestingRecord();
        assertTrue(condition.validate(record1));

        ICell cell1 = new TestingCell("ss");
        record1.setAttribute("name",cell1);
        assertTrue(condition.validate(record1));

        IRecord record2 = new TestingRecord();
        assertTrue(condition.validate(record2));
        ICell cell2 = new TestingCell(11);
        record2.setAttribute("Number",cell2);
        assertTrue(condition.validate(record2));
    }
    @Test
    public void falseConditionTest(){
        ICondition condition = new FalseCondition();
        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell1 = new TestingCell("ss");
        record1.setAttribute("name",cell1);
        assertFalse(condition.validate(record1));

        IRecord record2 = new TestingRecord();
        assertFalse(condition.validate(record2));
        ICell cell2 = new TestingCell(11);
        record2.setAttribute("Number",cell2);
        assertFalse(condition.validate(record2));
    }
    @Test
    public void equalConditionTest(){
        ICondition condition = new EqualCondition("name","ss");

        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell2 = new TestingCell("ss");
        IRecord record2 = new TestingRecord();
        record2.setAttribute("name",cell2);
        assertTrue(condition.validate(record2));

        ICell cell3 = new TestingCell("ss");
        IRecord record3 = new TestingRecord();
        record3.setAttribute("secondName",cell3);
        assertFalse(condition.validate(record3));

        ICondition condition2 = new EqualCondition("age",11);

        IRecord record4 = new TestingRecord();
        assertFalse(condition2.validate(record4));

        ICell cell5 = new TestingCell(11);
        IRecord record5 = new TestingRecord();
        record5.setAttribute("age",cell5);
        assertTrue(condition2.validate(record5));

        ICell cell6 = new TestingCell(11);
        IRecord record6 = new TestingRecord();
        record6.setAttribute("secondName",cell6);
        assertFalse(condition2.validate(record6));

        ICell cell7 = new TestingCell(new Integer(11));
        IRecord record7 = new TestingRecord();
        record7.setAttribute("age",cell7);
        assertTrue(condition2.validate(record7));
    }
    @Test
    public void biggerThanConditionTest(){
        ICondition condition = new BiggerThanCondition("name","ss");

        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell2 = new TestingCell("ss");
        IRecord record2 = new TestingRecord();
        record2.setAttribute("name",cell2);
        assertFalse(condition.validate(record2));

        ICell cell3 = new TestingCell("ss");
        IRecord record3 = new TestingRecord();
        record3.setAttribute("secondName",cell3);
        assertFalse(condition.validate(record3));

        ICondition condition2 = new BiggerThanCondition("age",11);

        IRecord record4 = new TestingRecord();
        assertFalse(condition2.validate(record4));

        ICell cell5 = new TestingCell(12);
        IRecord record5 = new TestingRecord();
        record5.setAttribute("age",cell5);
        assertTrue(condition2.validate(record5));

        ICell cell6 = new TestingCell(11);
        IRecord record6 = new TestingRecord();
        record6.setAttribute("age",cell6);
        assertFalse(condition2.validate(record6));

        ICell cell7 = new TestingCell(new Integer(10));
        IRecord record7 = new TestingRecord();
        record7.setAttribute("age",cell7);
        assertFalse(condition2.validate(record7));

        ICell cell8 = new TestingCell(12);
        IRecord record8 = new TestingRecord();
        record8.setAttribute("Age",cell8);
        assertFalse(condition2.validate(record8));
    }
    @Test
    public void smallerThanConditionTest(){
        ICondition condition = new SamllerThanCondition("name","ss");

        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell2 = new TestingCell("ss");
        IRecord record2 = new TestingRecord();
        record2.setAttribute("name",cell2);
        assertFalse(condition.validate(record2));

        ICell cell3 = new TestingCell("ss");
        IRecord record3 = new TestingRecord();
        record3.setAttribute("secondName",cell3);
        assertFalse(condition.validate(record3));

        ICondition condition2 = new SamllerThanCondition("age",11);

        IRecord record4 = new TestingRecord();
        assertFalse(condition2.validate(record4));

        ICell cell5 = new TestingCell(12);
        IRecord record5 = new TestingRecord();
        record5.setAttribute("age",cell5);
        assertFalse(condition2.validate(record5));

        ICell cell6 = new TestingCell(11);
        IRecord record6 = new TestingRecord();
        record6.setAttribute("age",cell6);
        assertFalse(condition2.validate(record6));

        ICell cell7 = new TestingCell(new Integer(10));
        IRecord record7 = new TestingRecord();
        record7.setAttribute("age",cell7);
        assertTrue(condition2.validate(record7));

        ICell cell8 = new TestingCell(10);
        IRecord record8 = new TestingRecord();
        record8.setAttribute("Age",cell8);
        assertFalse(condition2.validate(record8));
    }
    @Test
    public void biggerThanOrEqualConditionTest(){
        ICondition condition = new BiggerThanOrEqualCondition("name","ss");

        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell2 = new TestingCell("ss");
        IRecord record2 = new TestingRecord();
        record2.setAttribute("name",cell2);
        assertTrue(condition.validate(record2));

        ICell cell3 = new TestingCell("ss");
        IRecord record3 = new TestingRecord();
        record3.setAttribute("secondName",cell3);
        assertFalse(condition.validate(record3));

        ICondition condition2 = new BiggerThanOrEqualCondition("age",11);

        IRecord record4 = new TestingRecord();
        assertFalse(condition2.validate(record4));

        ICell cell5 = new TestingCell(12);
        IRecord record5 = new TestingRecord();
        record5.setAttribute("age",cell5);
        assertTrue(condition2.validate(record5));

        ICell cell6 = new TestingCell(11);
        IRecord record6 = new TestingRecord();
        record6.setAttribute("age",cell6);
        assertTrue(condition2.validate(record6));

        ICell cell7 = new TestingCell(new Integer(10));
        IRecord record7 = new TestingRecord();
        record7.setAttribute("age",cell7);
        assertFalse(condition2.validate(record7));

        ICell cell8 = new TestingCell(12);
        IRecord record8 = new TestingRecord();
        record8.setAttribute("Age",cell8);
        assertFalse(condition2.validate(record8));
    }
    @Test
    public void smallerThanOrEqualConditionTest(){
        ICondition condition = new SamllerThanOrEqualCondition("name","ss");

        IRecord record1 = new TestingRecord();
        assertFalse(condition.validate(record1));

        ICell cell2 = new TestingCell("ss");
        IRecord record2 = new TestingRecord();
        record2.setAttribute("name",cell2);
        assertTrue(condition.validate(record2));

        ICell cell3 = new TestingCell("ss");
        IRecord record3 = new TestingRecord();
        record3.setAttribute("secondName",cell3);
        assertFalse(condition.validate(record3));

        ICondition condition2 = new SamllerThanOrEqualCondition("age",11);

        IRecord record4 = new TestingRecord();
        assertFalse(condition2.validate(record4));

        ICell cell5 = new TestingCell(12);
        IRecord record5 = new TestingRecord();
        record5.setAttribute("age",cell5);
        assertFalse(condition2.validate(record5));

        ICell cell6 = new TestingCell(11);
        IRecord record6 = new TestingRecord();
        record6.setAttribute("age",cell6);
        assertTrue(condition2.validate(record6));

        ICell cell7 = new TestingCell(new Integer(10));
        IRecord record7 = new TestingRecord();
        record7.setAttribute("age",cell7);
        assertTrue(condition2.validate(record7));

        ICell cell8 = new TestingCell(10);
        IRecord record8 = new TestingRecord();
        record8.setAttribute("Age",cell8);
        assertFalse(condition2.validate(record8));
    }
    private class TestingRecord implements IRecord {
        HashMap<String,ICell> map = new HashMap<>();
        @Override
        public ICell getAttribute(String fieldName) {
            return map.get(fieldName);
        }

        @Override
        public void setAttribute(String fieldName, ICell data) {
            map.put(fieldName,data);
        }
    }
    private class TestingCell implements ICell{
        private Object data;
        @Override
        public Object getData() throws TypeNotPresentException {
            return data;
        }

        @Override
        public void setData(Object data) {
            this.data = data;
        }

        public TestingCell(Object data)
        {
            setData(data);
        }
    }
}