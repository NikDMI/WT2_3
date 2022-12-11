package by.bsuir.lab3.server.dao.impl;

import java.io.File;
import java.io.InputStream;
import java.lang.constant.Constable;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import by.bsuir.lab3.server.bean.structures.UserInfo;
import by.bsuir.lab3.server.bean.structures.UserInfo.UserRole;
import by.bsuir.lab3.server.dao.exception.DaoException;
import by.bsuir.lab3.server.dao.interf.UsersDao;

public class UsersDaoXml implements UsersDao{

	
	public UsersDaoXml(String directoryName) throws DaoException{
		this.directoryName = directoryName + "\\";
		authorizationFile = new File(this.directoryName + AUTHORIZATION_FILE_NAME);
		try {
			authorizationFile.createNewFile();
		} catch (Exception e) {
			throw new DaoException("Can't create db file", e);
		}
		lockObject = new ReentrantLock();
		//authInputStream = Files.newInputStream(directoryName + AUTHORIZATION_FILE_NAME, StandardOpenOption)
	}
	
	
	public UserInfo.UserRole getUserRole(String userLogin, String userPassword) throws DaoException{
		try {
			XMLStreamReader xmlStreamReader = XMLInputFactory.newDefaultFactory().createXMLStreamReader
					(Files.newInputStream(authorizationFile.toPath(), StandardOpenOption.READ));
			while (xmlStreamReader.hasNext()) {
				xmlStreamReader.nextTag();
				//Get tags with user auth info
				if(xmlStreamReader.getName().getLocalPart() == USER_AUTH_INFO_TAG) {
					xmlStreamReader.nextTag();
					String login = xmlStreamReader.getElementText();
					xmlStreamReader.nextTag();
					String password = xmlStreamReader.getElementText();
				}
			}
		} catch (Exception e) {
			throw new DaoException("Can't read file", e);
		}
		 return UserInfo.UserRole.NONE;
	}
	
	
	public void addNewUser(String userLogin, String userPassword, UserInfo userInfo) throws DaoException{
		try {
			 //load xml
			 Document document = null;
			 Element rootElement = null;
			 if (authorizationFile.length() == 0) {
				 document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
				 rootElement = document.createElement("root");
				 document.appendChild(rootElement);
			 } else {
				 document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(authorizationFile);
				 rootElement = document.getDocumentElement();
			 }
			 if (getUserInfo(userLogin) != null) {
				 return;
			 }
			 //Add new user
			 Node newUser = document.createElement(USER_AUTH_INFO_TAG);
			 Node loginNode = document.createElement(USER_AUTH_INFO_LOGIN_TAG); loginNode.setTextContent(userLogin);
			 Node passwordNode = document.createElement(USER_AUTH_INFO_PASSWORD_TAG); passwordNode.setTextContent(userPassword);
			 newUser.appendChild(loginNode);
			 newUser.appendChild(passwordNode);
			 appendUserInfo(document, newUser, userInfo);
			 rootElement.appendChild(newUser);
			 //Update xml
			 Transformer transformer = TransformerFactory.newInstance().newTransformer();
	         Source source = new DOMSource(document);
	         Result result = new StreamResult(Files.newOutputStream(authorizationFile.toPath(), 
	        		 StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE, StandardOpenOption.CREATE));
	         transformer.transform(source, result);
		} catch (Exception e) {
			throw new DaoException("Can't read file", e);
		}
	}
	
	
	/***
	 * Add new user info to UserNode
	 * @param document
	 * @param node
	 * @param userInfo
	 */
	private void appendUserInfo(Document document, Node node, UserInfo userInfo) {
		Node userInfoNode = document.createElement(USER_INFO_TAG);
		//
		Node userNameNode = document.createElement(USER_INFO_NAME_TAG);
		userNameNode.setTextContent(userInfo.userName);
		Node userGroupNode = document.createElement(USER_INFO_GROUP_TAG);
		userGroupNode.setTextContent(userInfo.userGroup);
		Node userAgeNode = document.createElement(USER_INFO_AGE_TAG);
		userAgeNode.setTextContent(userInfo.userAge.toString());
		Node userRoleNode = document.createElement(USER_INFO_ROLE_TAG);
		userRoleNode.setTextContent(userInfo.userRole.name());
		//Add nodes
		userInfoNode.appendChild(userNameNode);
		userInfoNode.appendChild(userGroupNode);
		userInfoNode.appendChild(userAgeNode);
		userInfoNode.appendChild(userRoleNode);
		node.appendChild(userInfoNode);
	}
	
	
	/***
	 * Gets info about corresponding user
	 * @param userLogin
	 * @return
	 */
	public UserInfo getUserInfo(String userLogin) throws DaoException{
		try {
			 //load xml
			 Document document = null;
			 Element rootElement = null;
			 if (authorizationFile.length() == 0) {
				 return null;
			 } else {
				 document = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(authorizationFile);
				 rootElement = document.getDocumentElement();
			 }
			 //Add new user
			 NodeList usersInfo = rootElement.getChildNodes();
			 for (int i = 0; i < usersInfo.getLength(); ++i) {
				 Node userNode = usersInfo.item(i);
				 Node userLoginNode = userNode.getFirstChild();
				 if (userLoginNode.getTextContent().equals(userLogin)) {
					 UserInfo userInfo = new UserInfo();
					 Node userInfoNode = userNode.getLastChild();
					 NodeList userInfoListNodes = userInfoNode.getChildNodes();
					 userInfo.userName = userInfoListNodes.item(0).getTextContent();
					 userInfo.userGroup = userInfoListNodes.item(1).getTextContent();
					 userInfo.userAge = Integer.parseInt(userInfoListNodes.item(2).getTextContent());
					 userInfo.userRole = UserRole.valueOf(userInfoListNodes.item(3).getTextContent());
					 return userInfo;
				 }
			 }
			 
		} catch (Exception e) {
			throw new DaoException("Can't read file", e);
		}
		return null;
	}
	
	
	private String directoryName;
	private static final String AUTHORIZATION_FILE_NAME = "Authorization.xml";
	private File authorizationFile;
	
	Lock lockObject;
	
	//XML tags
	private static final String USER_AUTH_INFO_TAG = "user";
	private static final String USER_AUTH_INFO_LOGIN_TAG = "login";
	private static final String USER_AUTH_INFO_PASSWORD_TAG = "password";
	private static final String USER_INFO_TAG = "userInfo";
	private static final String USER_INFO_NAME_TAG = "name";
	private static final String USER_INFO_GROUP_TAG = "group";
	private static final String USER_INFO_AGE_TAG = "age";
	private static final String USER_INFO_ROLE_TAG = "role";
	
}
