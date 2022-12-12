package by.bsuir.lab3.server.dao;

import by.bsuir.lab3.server.dao.exception.DaoException;
import by.bsuir.lab3.server.dao.impl.UsersDaoXml;
import by.bsuir.lab3.server.dao.interf.UsersDao;

public class DaoFactory {

	public enum StorageType {XML};
	
	public static UsersDao getDao(StorageType type) throws DaoException {
		switch (type) {
		case XML:
			return new UsersDaoXml("D:\\БГУИР 3 КУРС\\ВТ\\LabWorks\\LW_3\\DB");
		}
		return null;
	}
	
	private DaoFactory() {
		
	}
}
