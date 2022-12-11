package by.bsuir.lab3.server.services.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * Represents callback to work with connected client
 * @author nikmi
 *
 */
public class TcpClientCallback implements Runnable {

	public TcpClientCallback(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try {
			InputStream inputStream = clientSocket.getInputStream();
			OutputStream outputStream = clientSocket.getOutputStream();
			while (clientSocket.isConnected()) {
				//recieve client request
			}
		} catch (Exception e) {
			
		}
	}
	
	private Socket clientSocket;
}
