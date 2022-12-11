package by.bsuir.lab3.server.controller.impl;

import by.bsuir.lab3.server.bean.structures.UserInfo;
import by.bsuir.lab3.server.controller.exception.ControllerException;
import by.bsuir.lab3.server.controller.interf.Users;

public class UserController implements Users{

	public UserController() {
		
	}
	
	
	/***
	 * Check user with this authorization data
	 * @param userLogin
	 * @param userPassword
	 * @return
	 */
	public UserInfo.UserRole getUserRole(String userLogin, String userPassword) throws ControllerException {
		return UserInfo.UserRole.NONE;
	}
	
	
	/***
	 * Add new user to DB
	 * @param userLogin
	 * @param userPassword
	 */
	public void addNewUser(String userLogin, String userPassword, UserInfo userInfo) throws ControllerException {
		
	}
	
	
	/***
	 * Gets info about corresponding user
	 * @param userLogin
	 * @return
	 */
	public UserInfo getUserInfo(String userLogin) {
		return null;
	}
}
