package by.bsuir.lab3.server.entry;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import by.bsuir.lab3.server.services.*;
import by.bsuir.lab3.server.services.ServiceFactory.ServerType;
import by.bsuir.lab3.server.services.exception.ServiceException;
import by.bsuir.lab3.server.services.interf.ServerModel;

/***
 * 
 * @author nikmi
 *Represent entry point of the server side
 */
public class StartPoint {
	
	public static void main(String[] args) {
		try {
			final ServerModel serverModel = ServiceFactory.getServerModel(ServerType.TCP);
			
			Thread serverThread = new Thread(new Runnable() {
			
				@Override
				public void run() {
					try {
						SocketAddress serverAddress = new InetSocketAddress(SERVER_DOMAIN, SERVER_PORT);
						serverModel.startServer(serverAddress);
					} catch (Exception e) {
						
					}
				}
			});
			serverThread.run();
			System.in.read();
			serverModel.stopServer();
		} catch(Exception e) {
			
		}
		
	}
	
	private static final String SERVER_DOMAIN = "127.0.0.1";
	private static final int SERVER_PORT = 5000;
}
