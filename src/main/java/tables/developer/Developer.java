package tables.developer;

import lombok.Data;

@Data
public class Developer {
    private long id;
    private String first_name;
    private String last_name;
    private int age;
    private int salary;
}