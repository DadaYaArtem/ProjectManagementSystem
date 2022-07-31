import prefs.Prefs;
import storage.DatabaseConnection;
import storage.DatabaseInitService;
import tables.DevelopersProjectsDao;
import tables.DevelopersSkillsDao;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String connectionUrl = new Prefs().getString(Prefs.URL);
        new DatabaseInitService().initDB(connectionUrl);


        DevelopersSkillsDao developersSkillsDao = new DevelopersSkillsDao(connection);
        DevelopersProjectsDao developersProjectsDao = new DevelopersProjectsDao(connection);

        System.out.println("Salary for all developers of project: " + developersProjectsDao.getSalary(1));
        System.out.println("List of all developers on project: " + developersProjectsDao.listOfDevelopersOnProject(1));
        System.out.println("List of all developers by branch: " + developersSkillsDao.getDeveloperByBranch("Java"));
        System.out.println("List of all developers by skill: " + developersSkillsDao.getDeveloperBySkill("Senior"));
        System.out.println("List of all projects with developers amount: " + developersProjectsDao.listOfProjectsWithDevCount());
    }
}
