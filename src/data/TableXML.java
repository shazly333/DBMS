package data;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import query.ICondition;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by khaled on 11/25/17.
 */
public class TableXML implements ITable {
    String name;
    String databaseName;

    public TableXML(String databaseName, String name) {
        this.databaseName = databaseName;
        this.name = name;
//        initializeTableFiles();
    }
    public String getName() {
        return this.name;
    }
    @Override
    public int insert(IRecord record) {
        List<IField> fields = getFields();
        /*for (int i = 0; i < fields.size(); i++){
            Object value = record.getAttribute(fields.get(i).getName());
            String data = String.valueOf(value);
            if(data.matches("^-?\\d+$")&&fields.get(i).getType().getSimpleName().equals("IntField")) {
            }
            else if (!data.matches("^-?\\d+$")&&fields.get(i).getType().getSimpleName().equals("VarcharField")) {
            }
            else
                return  false;
        }*/
        List<IRecord> allRecords = getRecords();
        allRecords.add(record);
        setRecords(allRecords);
        return 1;
    }

    @Override
    public List<IRecord> select(ICondition condition) {
        List<IRecord> records = this.getRecords();
        List<IRecord> result = new ArrayList<>();
        for(int i = 0; i < records.size(); i++) {
            if (condition.validate(records.get(i))) {
                result.add(records.get(i));
            }
        }

        return result;
    }

    @Override
    public int delete(ICondition condition) {
        List<IRecord> records = getRecords();
        int count = 0;

        for (int i = 0; i < records.size(); i++) {
            if (condition.validate(records.get(i))) {
                records.remove(i);
                count++;
            }

        }
        setRecords(records);
        return count;
    }

    @Override
    public int update(ICondition condition, List<String> fieldNames, List<String> values) {
        List<IRecord> records = getRecords();
        int count = 0;

        for (IRecord record : records) {
            if (condition.validate(record)) {
                count++;
                for (int i = 0; i < fieldNames.size(); i++) {
                    record.setAttribute(fieldNames.get(i), values.get(i));
                }
            }
        }
        setRecords(records);
        return count;
    }

    @Override
    public void addField(IField field) {
        if (!fieldExists(field)) {
            List<IField> newFields = getFields();
            newFields.add(field);
            setFields(newFields);
        }
    }

    @Override
    public List<IField> getFields() {
        List<IField> fields = new ArrayList<>();

        try {

            InputStream inputStream;
            Reader reader = null;
            try {
                inputStream = new FileInputStream(xmlPath());
                try {
                    reader = new InputStreamReader(inputStream, "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            InputSource is = new InputSource(reader);
            is.setEncoding("ISO-8859-1");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList fieldElements = doc.getElementsByTagName("field");

            for (int i = 0; i < fieldElements.getLength(); i++) {
                Node fieldElement = fieldElements.item(i);

                if (fieldElement.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) fieldElement;
                    String fieldClassName = eElement.getAttribute("class");
                    String fieldName = eElement.getAttribute("name");
                    fields.add(FieldsFactory.create(fieldClassName, fieldName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fields;
    }

    @Override
    public void setFields(List<IField> fields) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element tableElement = doc.createElement("table");
            doc.appendChild(tableElement);

            for (IField field : fields) {
                Element fieldElement = doc.createElement("field");
                tableElement.appendChild(fieldElement);

                Attr nameAttribute = doc.createAttribute("name");
                nameAttribute.setValue(field.getName());
                fieldElement.setAttributeNode(nameAttribute);

                Attr classAttribute = doc.createAttribute("class");
                classAttribute.setValue(field.getClass().getSimpleName());
                fieldElement.setAttributeNode(classAttribute);
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            DOMSource source = new DOMSource(doc);
            String path = schemaPath();
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean fieldExists(IField field) {
        for (IField currentField : getFields()) {
            if (field.getName().equals(currentField.getName())) return true;
        }
        return false;
    }

    @Override
    public void createXMLFile() {
        setRecords(new ArrayList<IRecord>());
    }

    private void initializeTableFiles() {
        setRecords(new ArrayList<IRecord>());
        setFields(new ArrayList<IField>());
    }

    public List<IRecord> getRecords() {
        List<IRecord> records = new ArrayList<>();

        try {
            InputStream inputStream;
            Reader reader = null;
            try {
                inputStream = new FileInputStream(xmlPath());
                try {
                    reader = new InputStreamReader(inputStream, "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            InputSource is = new InputSource(reader);
            is.setEncoding("ISO-8859-1");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            doc.getDocumentElement().normalize();
            NodeList recordList = doc.getElementsByTagName("record");

            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordElement = recordList.item(i);

                if (recordElement.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) recordElement;

                    NodeList fieldList = eElement.getElementsByTagName("field");
                    List<String> fieldNames = new ArrayList<>();
                    List<Object> values = new ArrayList<>();

                    for (int count = 0; count < fieldList.getLength(); count++) {
                        Node fieldElement = fieldList.item(count);

                        if (fieldElement.getNodeType() == fieldElement.ELEMENT_NODE) {
                            Element field = (Element) fieldElement;
                            fieldNames.add(field.getAttribute("name"));

//                            String value = values.add(field.getTextContent());
                            Object value = field.getTextContent();
                            if (field.getAttribute("class").equals("IntField")) {
                                values.add(Integer.parseInt(value.toString()));
                            } else {
                                values.add(value);
                            }
                        }
                    }

                    IRecord record = new Record(this, fieldNames, values);
                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return records;
    }

    private void setRecords(List<IRecord> records) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element tableElement = doc.createElement("table");
            doc.appendChild(tableElement);

            Attr tableNameAttribute = doc.createAttribute("name");
            tableNameAttribute.setValue(this.name);
            tableElement.setAttributeNode(tableNameAttribute);

            for (IRecord record : records) {
                Element recordElement = doc.createElement("record");
                tableElement.appendChild(recordElement);


                for (IField field : getFields()) {

                    Object cell = record.getAttribute(field.getName());

                    Element fieldElement = doc.createElement("field");
                    recordElement.appendChild(fieldElement);

                    Attr nameAttribute = doc.createAttribute("name");
                    nameAttribute.setValue(field.getName());
                    fieldElement.setAttributeNode(nameAttribute);

                    Attr classAttribute = doc.createAttribute("class");
                    classAttribute.setValue(field.getClass().getSimpleName());
                    fieldElement.setAttributeNode(classAttribute);
                    fieldElement.appendChild(doc.createTextNode(cell.toString()));
                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            DOMSource source = new DOMSource(doc);
            String path = xmlPath();
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String schemaPath() {
        return DatabaseManager.getInstance().databasePath(databaseName) + System.getProperty("file.separator") + name + ".xsc";
    }

    private String xmlPath() {
        return DatabaseManager.getInstance().databasePath(databaseName) + System.getProperty("file.separator") + name + ".xml";
    }
}
