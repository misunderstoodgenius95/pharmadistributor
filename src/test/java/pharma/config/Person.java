package pharma.config;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private String name;
    private String email;
    private  String id;
    public Person(String name, String email,String id) {
        this.name = name;
        this.email = email;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
