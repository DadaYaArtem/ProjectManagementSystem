package tables.skill;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SkillDaoService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement selectMaxIdSt;
    private PreparedStatement getAllSt;
    private PreparedStatement updateSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement existsSt;
    private PreparedStatement clearSt;
    private PreparedStatement searchSt;

    public SkillDaoService(Connection connection) throws SQLException {
        getAllSt = connection.prepareStatement("SELECT * FROM skills");
        getByIdSt = connection.prepareStatement("SELECT * FROM skills WHERE id = ?");
        selectMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxID FROM skills");
        updateSt = connection.prepareStatement("UPDATE skills SET branch =?, skill=? WHERE id=?");
        createSt = connection.prepareStatement("INSERT INTO skills (branch, skill) VALUES (?, ?)");
        deleteByIdSt = connection.prepareStatement("DELETE FROM skills WHERE id = ?");
        clearSt = connection.prepareStatement("DELETE FROM skills");

//        existsSt = connection.prepareStatement("SELECT count(*) > 0 AS humanExists FROM human WHERE id = ?");
//        searchSt = connection.prepareStatement("SELECT id, name, birthday, gender FROM human WHERE name LIKE ?");
    }

    public long create(Skill skill) throws SQLException {
        createSt.setString(1, skill.getBranch().toString());
        createSt.setString(2, skill.getSkill().toString());
        createSt.executeUpdate();

        long id;

        try (ResultSet resultSet = selectMaxIdSt.executeQuery();) {
            resultSet.next();
            id = resultSet.getLong("maxId");
        }

        return id;
    }

    public Skill getById(long id) throws SQLException {
        getByIdSt.setLong(1, id);
        try (ResultSet resultSet = getByIdSt.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }
            Skill result = new Skill();
            result.setId(id);
            result.setBranch(Skill.Branch.valueOf(resultSet.getString("branch")));
            result.setSkill(Skill.Skillset.valueOf(resultSet.getString("skill")));
            return result;
        }
    }

    public List<Skill> getAll() throws SQLException {
        try(ResultSet rs = getAllSt.executeQuery()) {
            List<Skill> result = new ArrayList<>();

            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getLong("id"));
                skill.setBranch(Skill.Branch.valueOf(rs.getString("branch")));
                skill.setSkill(Skill.Skillset.valueOf(rs.getString("skill")));

                result.add(skill);
            }

            return result;
        }
    }

    public void update(Skill customer) throws SQLException {
        updateSt.setString(1, customer.getBranch().toString());
        updateSt.setString(2, customer.getSkill().toString());
        updateSt.setLong(3, customer.getId());

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