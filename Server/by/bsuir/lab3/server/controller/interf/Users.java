package by.bsuir.lab3.server.controller.interf;

import by.bsuir.lab3.server.bean.structures.UserInfo;
import by.bsuir.lab3.server.controller.exception.ControllerException;

public interface Users {

	/***
	 * Check user with this authorization data
	 * @param userLogin
	 * @param userPassword
	 * @return
	 */
	public UserInfo.UserRole getUserRole(String userLogin, String userPassword) throws ControllerException;
	
	
	/***
	 * Add new user to DB
	 * @param userLogin
	 * @param userPassword
	 */
	public void addNewUser(String userLogin, String userPassword, UserInfo userInfo) throws ControllerException;
	
	
	/***
	 * Gets info about corresponding user
	 * @param userLogin
	 * @return
	 */
	public UserInfo getUserInfo(String userLogin);
	
}
