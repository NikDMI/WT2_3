package by.bsuir.lab3.client.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import by.bsuir.lab3.client.bean.network.NetworkMessageConverter;
import by.bsuir.lab3.client.bean.network.messages.BasicMessage;
import by.bsuir.lab3.client.services.exception.ServiceException;
import by.bsuir.lab3.client.services.interf.*;

public class ClientTcp implements ClientModel {

	
	public void connectToServer(SocketAddress serverAddress) throws ServiceException {
		clientSocket = new Socket();
		try {
			clientSocket.connect(serverAddress);
			inputStream = clientSocket.getInputStream();
			outputStream = clientSocket.getOutputStream();
		}
		catch (Exception e) {
			throw new ServiceException("Can't connect to server", e);
		}
		networkMessageConverter = new NetworkMessageConverter(inputStream, outputStream);
	}
	
	
	/**
	 * Finalize server work
	 */
	public void shutdownConnection() throws ServiceException {
		try {
			clientSocket.close();
		}
		catch (Exception e) {
			throw new ServiceException("Can't close connection", e);
		}
	}
	
	
	/***
	 * Sends message to the server
	 * @param message
	 */
	public void sendMessage(BasicMessage message) throws ServiceException {
		try {
			NetworkMessageConverter.sendMessage(outputStream, message);
		}
		catch (Exception e) {
			throw new ServiceException("Can't send message", e);
		}
		
	}
	
	
	/***
	 * Check, if server send message to the client
	 * @return null if no message sending
	 */
	public BasicMessage tryRecieveMessage() throws ServiceException{
		try {
			if (inputStream.available() == 0) {
				return null;
			}
			return networkMessageConverter.readNextMessage();
		}
		catch (Exception e) {
			throw new ServiceException("Can't recieve the message", e);
		}
	}
	
	
	private Socket clientSocket;
	private InputStream inputStream;
	private OutputStream outputStream;
	NetworkMessageConverter networkMessageConverter;
}
