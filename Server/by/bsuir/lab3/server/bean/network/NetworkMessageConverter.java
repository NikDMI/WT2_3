package by.bsuir.lab3.server.bean.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.management.BadAttributeValueExpException;

import by.bsuir.lab3.server.bean.network.messages.*;

/**
 * This class helps client and server sides to work with 
 * communication detail more easily
 * @author nikmi
 *
 */
public class NetworkMessageConverter {
	
	
	public NetworkMessageConverter(InputStream inputStream, OutputStream outputStream) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		dataBuffer = new byte[MAX_BUFFER_LENGTH];
	}

	
	/**
	 * Try get next message from stream
	 * @param stream
	 * @return null - if error
	 */
	public BasicMessage readNextMessage() throws IOException, Exception {
		byte packetLength = 0;
		int readedBytesCount = 0;
		bufferLength = 0;
		do {
			readedBytesCount = inputStream.read(dataBuffer, bufferLength, MAX_BUFFER_LENGTH - bufferLength);
			bufferLength += readedBytesCount;
			if (packetLength == 0) {//Get the packet length
				packetLength = dataBuffer[0];
			}
		} while (readedBytesCount > 0 && (bufferLength < packetLength));
		//Get the type of the packet
		BasicMessage.MessageTypes messageType = BasicMessage.MessageTypes.values()[dataBuffer[1]];
		switch (messageType) {
		case LOG_IN:
			return new LoginMessage(dataBuffer, bufferLength, 0);
		}
		return null;
	}
	
	
	/**
	 * Send message to the stream
	 * @param stream
	 * @param message
	 */
	public static void sendMessage(OutputStream stream, BasicMessage message) throws IOException {
		Integer dataLength = new Integer(0);
		byte[] messageData = message.getMessageData(dataLength);
		//Create packet
		int packetLength = dataLength + 2;
		byte[] packet = new byte[packetLength];
		packet[0] = (byte)packetLength;
		packet[1] = (byte)message.getMessageType().ordinal();
		for (int i = 0; i < dataLength; ++i) {
			packet[2+i] = messageData[i];
		}
		stream.write(packet, 0, packetLength);
	}
	
	
	private static final int MAX_BUFFER_LENGTH = 256;
	private InputStream inputStream;
	private OutputStream outputStream;
	private final byte[] dataBuffer;
	private int bufferLength = 0;
}
