package by.bsuir.lab3.client.services.interf;

import java.net.SocketAddress;

import by.bsuir.lab3.client.bean.network.messages.BasicMessage;
import by.bsuir.lab3.client.services.exception.ServiceException;

public interface ClientModel {
	/***
	 * Connects to server
	 * @param bindingAddress address on which server hosts
	 */
	public void connectToServer(SocketAddress serverAddress) throws ServiceException;
	
	
	/**
	 * Finalize client work
	 */
	public void shutdownConnection() throws ServiceException;
	
	
	/***
	 * Sends message to the server
	 * @param message
	 */
	public void sendMessage(BasicMessage message) throws ServiceException;
	
	
	/***
	 * Check, if server send message to the client
	 * @return null if no message sending
	 */
	public BasicMessage tryRecieveMessage() throws ServiceException;
}
