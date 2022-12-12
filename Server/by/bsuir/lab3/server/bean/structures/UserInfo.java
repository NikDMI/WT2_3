package by.bsuir.lab3.server.bean.structures;

public class UserInfo {
	
	public enum UserRole {ADMIN, USER, NONE};

	public UserRole userRole = UserRole.USER;
	public String userName = "none";
	public String userGroup = "none";
	public Integer userAge = 0;
	
	public String userPassword;
	
}


