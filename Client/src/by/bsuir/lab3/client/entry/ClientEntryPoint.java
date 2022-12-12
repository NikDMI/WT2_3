package by.bsuir.lab3.client.entry;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

import by.bsuir.lab3.client.bean.network.messages.BasicMessage;
import by.bsuir.lab3.client.bean.network.messages.LoginMessage;
import by.bsuir.lab3.client.services.ServiceFactory;
import by.bsuir.lab3.client.services.ServiceFactory.ClientType;
import by.bsuir.lab3.client.services.interf.ClientModel;

public class ClientEntryPoint {

	public static void main(String[] args) {
		try {
			clientModel =  ServiceFactory.getClientModel(ClientType.TCP);
			SocketAddress serverAddress = new InetSocketAddress(SERVER_DOMAIN, SERVER_PORT);
			System.out.println("Connection...");
			clientModel.connectToServer(serverAddress);
			System.out.println("Connect succsessfully");
			int command = 0;
			scanner = new Scanner(System.in);
			while (command != 1) {
				System.out.println("Команды\n 1 - выход\n 2 - авторизация");
				command = scanner.nextInt();
				switch(command) {
				case 2:
					LogIn();
				}
			}
			LoginMessage loginMessage = new LoginMessage("ha", "1234");
			clientModel.sendMessage(loginMessage);
			clientModel.shutdownConnection();
		}
		catch (Exception e) {
			System.out.println("Ooops: " + e.getMessage());
		}
		System.out.println("Clinet ends work");
	}
	
	
	private static void LogIn() {
		System.out.println("Логин: ");
		String loginString = scanner.next();
		System.out.println("Пароль: ");
		String passwordString = scanner.next();
		try {
			LoginMessage message = new LoginMessage(loginString, passwordString);
			clientModel.sendMessage(message);
			BasicMessage messageFromServer = null;
			while (messageFromServer == null) {
				messageFromServer = clientModel.tryRecieveMessage();
			}
			message = (LoginMessage)messageFromServer;
			if (message.userLogin.equals(loginString)) {
				System.out.println("Успешная авториация");
			} else {
				System.out.println("Ошибка аторизации");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
	}
	
	private static final String SERVER_DOMAIN = "127.0.0.1";
	private static final int SERVER_PORT = 5000;
	
	private static ClientModel clientModel;
	private static Scanner scanner;
}
