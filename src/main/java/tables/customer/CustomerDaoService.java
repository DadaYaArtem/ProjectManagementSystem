package tables.customer;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CustomerDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement clearSt;

    public CustomerDaoService(Connection connection) throws SQLException {
        getAllSt = connection.prepareStatement("SELECT * FROM customers");
        getByIdSt = connection.prepareStatement("SELECT * FROM customers WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxID FROM customers");
        updateSt = connection.prepareStatement("UPDATE customers SET first_name =?, last_name=?, phone_number=? WHERE id=?");
        createSt = connection.prepareStatement("INSERT INTO customers (first_name, last_name, phone_number) VALUES (?, ?, ?)");
        deleteByIdSt = connection.prepareStatement("DELETE FROM customers WHERE id = ?");
        clearSt = connection.prepareStatement("DELETE FROM customers");
    }

    public long create(Customer customer) throws SQLException {
        createSt.setString(1, customer.getFirst_name());
        createSt.setString(2, customer.getLast_name());
        createSt.setString(3, customer.getPhone_number());
        createSt.executeUpdate();

        long id;

        try (ResultSet resultSet = selectMaxIdSt.executeQuery();) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;
    }

    public Customer getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet resultSet = getByIdSt.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            Customer result = new Customer();
            result.setId(id);
            result.setFirst_name(resultSet.getString("first_name"));
            result.setLast_name(resultSet.getString("last_name"));
            result.setPhone_number(resultSet.getString("phone_number"));
            return result;
        }
    }

    public List<Customer> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Customer> result = new ArrayList<>();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("second_name"));
                customer.setPhone_number(rs.getString("phone_number"));

                result.add(customer);
            }

            return result;
        }
    }

    public void update(Customer customer) throws SQLException {
        updateSt.setString(1, customer.getFirst_name());
        updateSt.setString(2, customer.getLast_name());
        updateSt.setString(3, customer.getPhone_number());
        updateSt.setLong(4, customer.getId());

        updateSt.executeUpdate();
    }

    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.executeUpdate();
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }
}