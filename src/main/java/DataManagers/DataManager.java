package DataManagers;

import DataManagers.DBConnectionPool.DataBaseConnector;
import DataManagers.ProjectData.ProjectDataHandler;
import DataManagers.SkillData.SkillDataHandler;
import DataManagers.UserData.UserDataHandler;
import Extras.IOReader;
import Models.*;
import Repositories.*;
import Static.Configs;
import org.json.JSONException;
import java.sql.*;
import java.util.List;

public class DataManager {

	public static void init () throws Exception {
		DataBaseConnector.init();
		DataManager.addSkills(IOReader.getHTML(Configs.SERVICE_URL + "/skill"));
		DataManager.addUsers();
	}

	private static void addProjects (String data, List<User> users) throws JSONException {
		ProjectDataHandler.init();
		ProjectDataHandler.addProjects(ProjectRepository.setProjects(data), users);
	}

	private static void addSkills (String data) throws JSONException {
		SkillDataHandler.init();
		SkillDataHandler.addSkills(SkillRepository.setSkills(data, "FROM_IO"));
	}

	private static void addUsers () throws Exception {
		UserDataHandler.init();
		List<User> users = UserRepository.setUsers(Configs.USER_DATA);
		UserRepository.setLoggedInUser(users.get(0));
		UserDataHandler.addUsers(users);
		DataManager.addProjects(IOReader.getHTML(Configs.SERVICE_URL + "/project"), users);
	}

	public static List<User> getUsers () {
		return UserDataHandler.getUsers();
	}

	public static List<Project> getProjects (String pageNum) {
		return ProjectDataHandler.getProjects(pageNum);
	}

	public static List<Skill> getSkills () {
		return SkillDataHandler.getSkills();
	}

	public static void dropExistingTable (String tableName) {
		Connection con = DataBaseConnector.getConnection();
		try {
			Statement stmt = con.createStatement();
			String    sql  = "SET FOREIGN_KEY_CHECKS = 0";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE IF EXISTS " + tableName;
			stmt.executeUpdate(sql);
			sql = "SET FOREIGN_KEY_CHECKS = 1";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DataBaseConnector.releaseConnection(con);
	}

	public static User findUserWithID (String ID) {
		return UserDataHandler.getUser(ID);
	}

	public static void removeUserSkill (String skillName, String userID) {
		UserDataHandler.removeUserSkill(skillName, userID);
	}

	public static void addProjectToDB (Project project) {
		ProjectDataHandler.addProjectToDB(project);
	}

	public static List<User> getUserWithName (String name) {
		return UserDataHandler.getUserWithName(name);
	}

	public static List<Project> getProjectsWithTitle (String title, String username) {
		return ProjectDataHandler.getProjectWithTitle(title, username);
	}

	public static List<Project> getProjectsWithDesc (String desc, String username) {
		return ProjectDataHandler.getProjectsWithDesc(desc, username);
	}

	public static int getProjectsNum() {
		return ProjectDataHandler.getProjectsNum();
	}

	public static int getProjectsNum(String username) {
		return ProjectDataHandler.getProjectsNum(username);
	}

	public static List<Project> getAuctionableProjects() {return ProjectDataHandler.getAuctionableProjects(); }

	public static void addBidWinner(String userID, String projectID, int bidAmount) {
		ProjectDataHandler.addBidWinner(userID, projectID, bidAmount);
	}

	public static void deleteProjectRecords(String projectID) {
		ProjectDataHandler.deleteProjectRecords(projectID);
	}

	public static User findUserWithUsername(String userName) {
		return UserDataHandler.findUserWithUsername(userName);
	}

	public static void addUserToDB(User user) {
		UserDataHandler.addUserToDB(user);
	}

	public static String getNextValidUserID() {
		return UserDataHandler.getNextValidUserID();
	}

	public static boolean checkPasswordCorrectness(String userName, String password) {
		return UserDataHandler.checkPasswordCorrectness(userName, password);
	}

	public static void userLogin(String userName) {
		UserDataHandler.userLogin(userName);
	}
}
