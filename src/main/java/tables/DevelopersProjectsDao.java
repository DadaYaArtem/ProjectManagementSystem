package tables;

import lombok.Data;
import tables.developer.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevelopersProjectsDao {
    private PreparedStatement sumOfSalariesForExactProject;
    private PreparedStatement listDevelopersFromExactProject;
    private PreparedStatement projectsWithDevCount;

    public DevelopersProjectsDao(Connection connection) throws SQLException {
        sumOfSalariesForExactProject = connection.prepareStatement("SELECT sum(salary) AS sum FROM DEVELOPERS JOIN DEVELOPERS_PROJECTS ON DEVELOPERS_PROJECTS.DEVELOPER_ID = DEVELOPERS.ID " +
                "JOIN PROJECTS ON DEVELOPERS_PROJECTS.PROJECT_ID = PROJECTS.ID " +
                "WHERE PROJECTS.ID = ? GROUP BY PROJECTS.NAME ");

        listDevelopersFromExactProject = connection.prepareStatement("SELECT DEVELOPERS.id, first_name, last_name, age, salary FROM DEVELOPERS " +
                "JOIN DEVELOPERS_PROJECTS ON DEVELOPERS_PROJECTS.DEVELOPER_ID = DEVELOPERS.ID " +
                "JOIN PROJECTS ON DEVELOPERS_PROJECTS.PROJECT_ID = PROJECTS.ID WHERE PROJECTS.ID = ?");

        projectsWithDevCount = connection.prepareStatement("SELECT FOUNDATION_DATE, NAME, COUNT(DEVELOPERS.ID) AS DEVCOUNT FROM DEVELOPERS_PROJECTS " +
                "JOIN DEVELOPERS ON DEVELOPERS.ID = DEVELOPERS_PROJECTS.DEVELOPER_ID " +
                "JOIN PROJECTS ON DEVELOPERS_PROJECTS.PROJECT_ID = PROJECTS.ID GROUP BY NAME");
    }

    public long getSalary(long id) throws SQLException {
        sumOfSalariesForExactProject.setLong(1, id);
        try (ResultSet resultSet = sumOfSalariesForExactProject.executeQuery()) {
            resultSet.next();
            return resultSet.getLong("sum");
        }
    }

    public List<Developer> listOfDevelopersOnProject(long id) throws SQLException {
        listDevelopersFromExactProject.setLong(1, id);

        try (ResultSet rs = listDevelopersFromExactProject.executeQuery()) {
            List<Developer> result = new ArrayList<>();

            while (rs.next()) {
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


    @Data
    public static class DevelopersProjects {
        private String name;
        private LocalDate foundation_date;
        private int DEVCOUNT;
    }

    public List<DevelopersProjects> listOfProjectsWithDevCount() throws SQLException {
        try (ResultSet rs = projectsWithDevCount.executeQuery()){
            List<DevelopersProjects> myData = new ArrayList<>();

            while (rs.next()){
                DevelopersProjects dp = new DevelopersProjects();
                dp.setName(rs.getString("name"));
                dp.setFoundation_date(LocalDate.parse(rs.getString("foundation_date")));
                dp.setDEVCOUNT(rs.getInt("DEVCOUNT"));

                myData.add(dp);
            }

            return myData;
        }
    }
}
