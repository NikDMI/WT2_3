package by.bsuir.lab3.server.services;

import by.bsuir.lab3.server.services.interf.*;
import by.bsuir.lab3.server.services.exception.ServiceException;
import by.bsuir.lab3.server.services.impl.*;

public class ServiceFactory {
	
	public enum ServerType {TCP};
	
	public static ServerModel getServerModel(ServerType serverType) throws ServiceException {
		switch(serverType) {
		case TCP:
			return new ServerTcp();
		}
		return null;
	}
	
	private ServiceFactory() {
		
	}
}
