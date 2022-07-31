package tables.project;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ProjectDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement clearSt;

    public ProjectDaoService(Connection connection) throws SQLException {
        getAllSt = connection.prepareStatement("SELECT * FROM projects");
        getByIdSt = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxID FROM projects");
        updateSt = connection.prepareStatement("UPDATE customers SET name =?, cost=?, foundation_date=? WHERE id=?");
        createSt = connection.prepareStatement("INSERT INTO projects (name, cost, foundation_date) VALUES (?, ?, ?)");
        deleteByIdSt = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
        clearSt = connection.prepareStatement("DELETE FROM projects");
    }

    public long create(Project project) throws SQLException {
        createSt.setString(1, project.getName());
        createSt.setInt(2, project.getCost());
        createSt.setString(3, project.getFoundation_date().toString());
        createSt.executeUpdate();

        long id;

        try (ResultSet resultSet = selectMaxIdSt.executeQuery();) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;
    }

    public Project getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet resultSet = getByIdSt.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            Project result = new Project();
            result.setId(id);
            result.setName(resultSet.getString("name"));
            result.setCost(resultSet.getInt("cost"));
            result.setFoundation_date(LocalDate.parse(resultSet.getString("foundation_date")));
            return result;
        }
    }

    public List<Project> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Project> result = new ArrayList<>();

            while (rs.next()) {
                Project project = new Project();
                project.setId(rs.getLong("id"));
                project.setName(rs.getString("first_name"));
                project.setCost(rs.getInt("cost"));
                project.setFoundation_date(LocalDate.parse(rs.getString("foundation_date")));

                result.add(project);
            }

            return result;
        }
    }

    public void update(Project project) throws SQLException {
        updateSt.setString(1, project.getName());
        updateSt.setInt(2, project.getCost());
        updateSt.setString(3, project.getFoundation_date().toString());
        updateSt.setLong(4, project.getId());

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