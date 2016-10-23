package controllers;

import play.*;
import play.mvc.*;
import java.util.*;

import models.*;
import org.apache.commons.codec.binary.Base64;
import java.text.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
//import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

public class Application extends Controller {

    public static void index() {
        render();
    }
	 public static void form() {
        render();
    }
	 public static void gettag() 
	 {
		 List<tag> tags = tag.all().fetch();
		 renderJSON(tags);
     }
	 public static void getcontact() 
	 {
		 String tag = params.get("tag");
		 List<contact> contacts = contact.find("tag_name", tag ).fetch();
		 renderJSON(contacts);
     }
	
	 public static void get() throws IOException, ParserConfigurationException, SAXException, ParseException 
    {
    	String token = params.get("token");
    	String tag	 = params.get("tag");
		String url = tag+".highrisehq.com/";
		String username =params.get("password");
		String password = params.get("password");
		String xmlSource = getAuth(token, url, username,password);
		List arraydata = getContent(xmlSource);
		List output1 = savetagdata(arraydata);
		List output2 = savepersondata(arraydata);
		render();
    }
	  private static List savepersondata(List arraydatas) throws ParseException {
		  for (int i=0; i<arraydatas.size(); i++){
		        List groupcontact = (List) arraydatas.get(i);
		        	contact.saveperson(groupcontact);      
		    }
		return null;
	}
	private static List savetagdata(List arraydata) 
	{
		   
		  for (int i=0; i<arraydata.size(); i++){
		        List groupcontact = (List) arraydata.get(i);
		        	tag.savetag(groupcontact);      
		    }
	return null;
	}
	private static String getAuth(String token, String url, String username, String password) throws IOException 
	  {
		  String newUrl = "https://" + url;
			ClientConfig clientConfig = new ClientConfig();
		    HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(token, password);
		    clientConfig.register( feature) ;
		    Client client = ClientBuilder.newClient( clientConfig );
		    WebTarget webTarget = client.target(newUrl);
		    String xmlSource=webTarget.path("people.xml")
		    .request()
		    .accept(MediaType.APPLICATION_XML)
		    .get(String.class);
		    
		return xmlSource;
			
	  }
	  private static List getContent(String xmlSource) throws IOException, ParserConfigurationException, SAXException 
	  {
		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource(new StringReader(xmlSource)));
		    
			        NodeList nList = doc.getElementsByTagName("person");
			       
			        List GroupArrayContact = new ArrayList();
			        for (int ntemp = 0; ntemp < nList.getLength(); ntemp++) {
			        	List ArrayContact = new ArrayList();
			    		Node nNode = nList.item(ntemp);
			    			Element eElement = (Element) nNode;
			    			ArrayContact.add("AuthorID<>" + eElement.getElementsByTagName("author-id").item(0).getTextContent()); 
			    			ArrayContact.add("CompanyID<>" + eElement.getElementsByTagName("company-id").item(0).getTextContent());
			    			ArrayContact.add("CreatedAt<>" + eElement.getElementsByTagName("created-at").item(0).getTextContent());
			    			ArrayContact.add("ID<>" + eElement.getElementsByTagName("id").item(0).getTextContent());
			    			ArrayContact.add("Firstname<>" + eElement.getElementsByTagName("first-name").item(0).getTextContent());
			    			ArrayContact.add("Lastname<>" + eElement.getElementsByTagName("last-name").item(0).getTextContent());
			    			ArrayContact.add("Title<>" + eElement.getElementsByTagName("title").item(0).getTextContent());
			    			ArrayContact.add("UpdatedAt<>" + eElement.getElementsByTagName("updated-at").item(0).getTextContent());
							ArrayContact.add("groupID<>" + eElement.getElementsByTagName("group-id").item(0).getTextContent());
							ArrayContact.add("OwnerID<>" + eElement.getElementsByTagName("owner-id").item(0).getTextContent());
							ArrayContact.add("VisibleTo<>" + eElement.getElementsByTagName("visible-to").item(0).getTextContent());
							ArrayContact.add("Company<>" + eElement.getElementsByTagName("company-name").item(0).getTextContent());
							ArrayContact.add("LinkedinURL<>" + eElement.getElementsByTagName("linkedin-url").item(0).getTextContent());
							NodeList tList = eElement.getElementsByTagName("tags");
			    			 for (int ttemp = 0; ttemp < tList.getLength(); ttemp++) 
			    			 {
			    				 Node tNode = tList.item(ttemp);
			    				 Element tElement = (Element) tNode;
								 NodeList tgList = tElement.getElementsByTagName("tag");
								 String tag_name = "";
										for (int tgtemp = 0; tgtemp < tgList.getLength(); tgtemp++) 
										 {
											if (tgtemp!=0)
											{
												tag_name += ";";
											}
												Node tgNode = tgList.item(tgtemp);
												Element tgElement = (Element) tgNode;
												tag_name += eElement.getElementsByTagName("name").item(tgtemp).getTextContent();
												ArrayContact.add("Tags<>" + eElement.getElementsByTagName("id").item(tgtemp).getTextContent()+"<>" + eElement.getElementsByTagName("name").item(tgtemp).getTextContent());	
										 }
										if( tag_name == "")
										{}
										else
										{
											ArrayContact.add("TagIDs<>"+ tag_name);
										}
							 }
			    			 GroupArrayContact.add(ArrayContact);
			        }
			return GroupArrayContact;
	  }
}