package by.bsuir.lab3.server.services.interf;

import java.net.*;

import by.bsuir.lab3.server.services.exception.ServiceException;

/***
 * Represents server
 * @author nikmi
 *
 */
public interface ServerModel {
	/***
	 * Initialize server
	 * @param bindingAddress address on which server hosts
	 */
	public void startServer(SocketAddress bindingAddress) throws ServiceException;
	
	
	/**
	 * Finalize server work
	 */
	public void stopServer() throws ServiceException;
}
