package tables.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement clearSt;

    public CompanyDaoService(Connection connection) throws SQLException {
        getAllSt = connection.prepareStatement("SELECT * FROM companies");
        getByIdSt = connection.prepareStatement("SELECT * FROM companies WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxID FROM companies");
        updateSt = connection.prepareStatement("UPDATE companies SET name =?, ceo=? WHERE id=?");
        createSt = connection.prepareStatement("INSERT INTO companies (name, ceo) VALUES (?, ?)");
        deleteByIdSt = connection.prepareStatement("DELETE FROM companies WHERE id = ?");
        clearSt = connection.prepareStatement("DELETE FROM companies");
    }

    public long create(Company company) throws SQLException {
        createSt.setString(1, company.getName());
        createSt.setString(2, company.getCeo());
        createSt.executeUpdate();

        long id;

        try (ResultSet resultSet = selectMaxIdSt.executeQuery()) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;
    }

    public Company getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet resultSet = getByIdSt.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            Company result = new Company();
            result.setId(id);
            result.setName(resultSet.getString("name"));
            result.setCeo(resultSet.getString("ceo"));
            return result;
        }
    }

    public List<Company> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Company> result = new ArrayList<>();

            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getLong("id"));
                company.setName(rs.getString("name"));
                company.setCeo(rs.getString("ceo"));

                result.add(company);
            }

            return result;
        }
    }

    public void update(Company company) throws SQLException {
        updateSt.setString(1, company.getName());
        updateSt.setString(2, company.getCeo());
        updateSt.setLong(3, company.getId());

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