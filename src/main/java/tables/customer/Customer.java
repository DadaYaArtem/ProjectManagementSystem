package tables.customer;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private long id;
    private String first_name;
    private String last_name;
    private String phone_number;

}
