package DataManagers.UserData;

import Models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserDataMapper {

	static void userDomainToDB (User user, PreparedStatement st) {
		try {
			st.setString(1, user.getId());
			st.setString(2, user.getFirstName());
			st.setString(3, user.getLastName());
			st.setString(4, user.getJobTitle());
			st.setString(5, user.getProfilePictureURL());
			st.setString(6, user.getBio());
			st.setString(7, user.getUserName());
			st.setString(8, user.getPassword());
			if (user.isLoggedIn()) {
				st.setInt(9, 1);
			} else
				st.setInt(9, 0);
			st.setString(10, user.getToken());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static User userDBtoDomain (ResultSet rs) {
		User user = new User();
		try {
			user.setId(rs.getString(1));
			user.setFirstName(rs.getString(2));
			user.setLastName(rs.getString(3));
			user.setJobTitle(rs.getString(4));
			user.setProfilePictureURL(rs.getString(5));
			user.setBio(rs.getString(6));
			user.setUserName(rs.getString(7));
			user.setPassword(rs.getString(8));
			if (rs.getInt(9) == 1) {
				user.setLoggedIn(true);
				user.setToken(rs.getString(10));
			} else
				user.setLoggedIn(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
