package by.bsuir.lab3.server.services.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

import by.bsuir.lab3.server.bean.network.NetworkMessageConverter;
import by.bsuir.lab3.server.bean.network.messages.BasicMessage;

/**
 * Represents callback to work with connected client
 * @author nikmi
 *
 */
public class TcpClientCallback implements Runnable {

	public TcpClientCallback(Socket clientSocket) throws Exception {
		this.clientSocket = clientSocket;
		networkMessageConverter = new NetworkMessageConverter(clientSocket.getInputStream(),
				clientSocket.getOutputStream());
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
					break;
				}
				
			}
		} catch (Exception e) {
			
		}
	}
	
	private Socket clientSocket;
	private NetworkMessageConverter networkMessageConverter;
}
