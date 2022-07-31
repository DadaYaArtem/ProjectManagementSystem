package tables;

import tables.developer.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevelopersSkillsDao {
    private PreparedStatement devsByBranch;
    private PreparedStatement devsBySkill;

    public DevelopersSkillsDao(Connection connection) throws SQLException {
        devsByBranch = connection.prepareStatement("SELECT DEVELOPERS.ID, first_name, last_name, age, salary FROM DEVELOPERS JOIN SKILLS_DEVELOPERS ON DEVELOPERS.ID = SKILLS_DEVELOPERS.DEVELOPER_ID " +
                "JOIN SKILLS ON SKILLS.ID = SKILLS_DEVELOPERS.SKILL_ID WHERE BRANCH = ?");

        devsBySkill = connection.prepareStatement("SELECT DEVELOPERS.ID, first_name, last_name, age, salary FROM DEVELOPERS JOIN SKILLS_DEVELOPERS ON DEVELOPERS.ID = SKILLS_DEVELOPERS.DEVELOPER_ID " +
                "JOIN SKILLS ON SKILLS.ID = SKILLS_DEVELOPERS.SKILL_ID WHERE SKILL = ?");
    }

    public List<Developer> getDeveloperByBranch(String branch) throws SQLException {
        devsByBranch.setString(1, branch);

        try (ResultSet rs = devsByBranch.executeQuery()){
            List<Developer> result = new ArrayList<>();

            while (rs.next()){
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirst_name(rs.getString("first_name"));
                developer.setLast_name(rs.getString("last_name"));
                developer.setAge(rs.getInt("age"));
                developer.setSalary(rs.getInt("salary"));

                result.add(developer);
            }

            return result;
        }
    }

    public List<Developer> getDeveloperBySkill(String skill) throws SQLException {
        devsBySkill.setString(1, skill);

        try (ResultSet rs = devsBySkill.executeQuery()){
            List<Developer> result = new ArrayList<>();

            while (rs.next()){
                Developer developer = new Developer();
                developer.setId(rs.getLong("id"));
                developer.setFirst_name(rs.getString("first_name"));
                developer.setLast_name(rs.getString("last_name"));
                developer.setAge(rs.getInt("age"));
                developer.setSalary(rs.getInt("salary"));

                result.add(developer);
            }

            return result;
        }
    }
}
