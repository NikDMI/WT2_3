package by.bsuir.lab3.server.services.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

import by.bsuir.lab3.server.bean.network.NetworkMessageConverter;
import by.bsuir.lab3.server.bean.network.messages.BasicMessage;
import by.bsuir.lab3.server.bean.network.messages.LoginMessage;
import by.bsuir.lab3.server.bean.structures.UserInfo;
import by.bsuir.lab3.server.dao.interf.UsersDao;

/**
 * Represents callback to work with connected client
 * @author nikmi
 *
 */
public class TcpClientCallback implements Runnable {

	public TcpClientCallback(Socket clientSocket, UsersDao usersDao) throws Exception {
		this.clientSocket = clientSocket;
		networkMessageConverter = new NetworkMessageConverter(clientSocket.getInputStream(),
				clientSocket.getOutputStream());
		this.usersDao = usersDao;
	}
	
	@Override
	public void run() {
		try {
			InputStream inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();
			while (clientSocket.isConnected()) {
				//recieve client request
				BasicMessage message = networkMessageConverter.readNextMessage();
				switch (message.getMessageType()) {
				case LOG_IN:
					LoginMessage loginMessage = (LoginMessage)message;
					try {
						UserInfo userInfo = usersDao.getUserInfo(loginMessage.userLogin);
						if (userInfo != null && userInfo.userPassword.equals(loginMessage.userPassword)) {
							isUserAuthorized = true;
						} else {
							loginMessage = new LoginMessage("-bad-", "");
						}
					}
					catch (Exception e) {
						loginMessage = new LoginMessage("-bad-", "");
					}
					NetworkMessageConverter.sendMessage(outputStream, loginMessage);
					break;
				}
				
			}
		} catch (Exception e) {
			
		}
	}
	
	private Socket clientSocket;
	private NetworkMessageConverter networkMessageConverter;
	private UsersDao usersDao;
	private boolean isUserAuthorized = false;
}
