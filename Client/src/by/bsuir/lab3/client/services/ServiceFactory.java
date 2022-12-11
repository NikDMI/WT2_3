package by.bsuir.lab3.client.services;

import by.bsuir.lab3.client.services.impl.ClientTcp;
import by.bsuir.lab3.client.services.interf.ClientModel;
import by.bsuir.lab3.client.services.exception.*;


public class ServiceFactory {
	
	public enum ClientType {TCP};
	
	public static ClientModel getClientModel(ClientType serverType) throws ServiceException {
		switch(serverType) {
		case TCP:
			return new ClientTcp();
		}
		return null;
	}
	
	private ServiceFactory() {
		
	}
}
