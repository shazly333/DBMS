package data;

/**
 * Created by khaled on 11/22/17.
 */
public interface IField {
    void setDefault(Object defaultValue);
    Object getDefault();
    void setName(String name);
    String getName();
    Class getType();
}
