package by.bsuir.lab3.server.dao.interf;

import by.bsuir.lab3.server.bean.structures.UserInfo;
import by.bsuir.lab3.server.controller.exception.ControllerException;
import by.bsuir.lab3.server.dao.exception.DaoException;

public interface UsersDao {
	
	public void addNewUser(String userLogin, String userPassword, UserInfo userInfo) throws DaoException;
	
	
	/***
	 * Gets info about corresponding user
	 * @param userLogin
	 * @return
	 */
	public UserInfo getUserInfo(String userLogin) throws DaoException;
}
