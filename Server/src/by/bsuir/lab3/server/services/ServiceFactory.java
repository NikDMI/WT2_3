package by.bsuir.lab3.server.services;

import by.bsuir.lab3.server.services.interf.*;
import by.bsuir.lab3.server.services.impl.*;

public class ServiceFactory {
	
	enum ServerType {TCP};
	
	public ServerModel getServerModel(ServerType serverType) {
		switch(serverType) {
		case TCP:
			//return 
		}
		return null;
	}
	
	private ServiceFactory() {
		
	}
}
