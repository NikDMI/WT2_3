package by.bsuir.lab3.server.services.impl;

import java.net.SocketAddress;
import java.net.ServerSocket;
import java.net.*;
import java.util.concurrent.*;

import by.bsuir.lab3.server.services.interf.*;
import by.bsuir.lab3.server.dao.DaoFactory;
import by.bsuir.lab3.server.dao.DaoFactory.StorageType;
import by.bsuir.lab3.server.dao.interf.UsersDao;
import by.bsuir.lab3.server.services.exception.*;

public class ServerTcp implements ServerModel {
	
	public ServerTcp() throws ServiceException{
		try {
			serverSocket = new ServerSocket();
			threadPool = Executors.newFixedThreadPool(10);
		} catch (Exception e) {
			throw new ServiceException("Can't create socket", e);
		}
		
	}

	/***
	 * Initialize server
	 * @param bindingAddress address on which server hosts
	 */
	public void startServer(SocketAddress bindingAddress) throws ServiceException {
		try {
			serverSocket.bind(bindingAddress);
			UsersDao usersDao = DaoFactory.getDao(StorageType.XML);
			while(true) {
				//Accept client connections
				Socket clientSocket = serverSocket.accept();
				threadPool.execute(new TcpClientCallback(clientSocket, usersDao));
			}
		} catch (Exception e) {
			throw new ServiceException("Can't start server", e);
		}
	}
	
	
	/**
	 * Finalize server work
	 */
	public void stopServer() throws ServiceException {
		try {
			serverSocket.close();
		} catch (Exception e) {
			throw new ServiceException("Can't stop server", e);
		}
	}
	
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
}
