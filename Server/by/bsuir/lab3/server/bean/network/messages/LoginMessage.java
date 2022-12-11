package by.bsuir.lab3.server.bean.network.messages;

public class LoginMessage extends BasicMessage {

	/***
	 * Read data from buffer
	 * @param data
	 * @param dataLength
	 * @param dataOffset
	 * @throws Exception
	 */
	public LoginMessage(byte[] data, int dataLength, int dataOffset) throws Exception{
		super(data, dataLength, dataOffset);
		messageType = BasicMessage.MessageTypes.LOG_IN;
		Integer offset = 0;
		userLogin = getNextString(offset);
		userPassword = getNextString(offset);
	}
	
	
	public LoginMessage(String userLogin, String userPassword) throws Exception{
		super();
		messageType = BasicMessage.MessageTypes.LOG_IN;
		this.userLogin = userLogin;
		this.userPassword = userPassword;
		//Add data to buffer
		addNextString(userLogin);
		addNextString(userPassword);
	}
	
	
	private String userLogin;
	private String userPassword;
}
