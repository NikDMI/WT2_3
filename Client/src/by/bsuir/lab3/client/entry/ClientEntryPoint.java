package by.bsuir.lab3.client.entry;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import by.bsuir.lab3.client.bean.network.messages.LoginMessage;
import by.bsuir.lab3.client.services.ServiceFactory;
import by.bsuir.lab3.client.services.ServiceFactory.ClientType;
import by.bsuir.lab3.client.services.interf.ClientModel;

public class ClientEntryPoint {

	public static void main(String[] args) {
		try {
			ClientModel clientModel =  ServiceFactory.getClientModel(ClientType.TCP);
			SocketAddress serverAddress = new InetSocketAddress(SERVER_DOMAIN, SERVER_PORT);
			System.out.println("Connection...");
			clientModel.connectToServer(serverAddress);
			System.out.println("Connect succsessfully");
			LoginMessage loginMessage = new LoginMessage("ha", "1234");
			clientModel.sendMessage(loginMessage);
			clientModel.shutdownConnection();
		}
		catch (Exception e) {
			System.out.println("Ooops: " + e.getMessage());
		}
		System.out.println("Clinet ends work");
	}
	
	private static final String SERVER_DOMAIN = "127.0.0.1";
	private static final int SERVER_PORT = 5000;
}
