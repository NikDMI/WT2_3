package by.bsuir.lab3.client.bean.network.messages;

import java.io.WriteAbortedException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Represents basic class of message that can be transmitted
 * @author nikmi
 *
 */
public class BasicMessage {
	
	public enum MessageTypes {LOG_IN};
	
	public static final int MAX_MESSAGE_SIZE = 254;
	
	public BasicMessage(byte[] data, int dataLength, int dataOffset) throws Exception {
		if (dataLength < 0 || dataLength > MAX_MESSAGE_SIZE) {
			throw new Exception("Invalid data size");
		}
		for(int i = 0; i < dataLength; ++i) {
			this.data[i]= data[i + dataOffset]; 
		}
		this.dataLength = dataLength;
	}
	
	
	public BasicMessage() throws Exception {
		this(null, 0, 0);
	}
	
	/***
	 * Get message type
	 * @return
	 */
	public MessageTypes getMessageType() {
		return messageType;
	}
	
	
	/**
	 * 
	 * @param dataLength length of array
	 * @return data
	 */
	public byte[] getMessageData(Integer[] dataLength) {
		dataLength[0] = this.dataLength;
		return data;
	}
	
	
	/***
	 * Clears array of data
	 */
	protected void clearData() {
		dataLength = 0;
	}
	
	
	/***
	 * Add new string to data array
	 * @param string
	 */
	protected void addNextString(String string) throws IllegalArgumentException{
		byte[] stringEncoding = string.getBytes(StandardCharsets.UTF_8);
		if (stringEncoding.length > MAX_MESSAGE_SIZE - dataLength - 1) {
			throw new IllegalArgumentException("Large string");
		}
		data[dataLength++] = (byte)stringEncoding.length;
		for (byte b: stringEncoding) {
			data[dataLength++] = b;
		}
	}
	
	
	/***
	 * Returns next string
	 * @param offset - in\out return next offset after reading
	 * @return
	 */
	protected String getNextString(Integer[] offset) {
		String resultString = "";
		byte stringLength = data[offset[0]++];
		byte[] stringEncoding = new byte[stringLength];
		for (int i = 0 ; i < stringLength; ++i) {
			stringEncoding[i]= data[offset[0] + i]; 
		}
		resultString = new String(stringEncoding,StandardCharsets.UTF_8);
		offset[0] = offset[0] + stringLength;
		return resultString;
	}
	
	
	protected MessageTypes messageType;
	private byte[] data = new byte[MAX_MESSAGE_SIZE];
	private int dataLength = 0;
}
