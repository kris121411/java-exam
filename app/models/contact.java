package models;
 

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class contact extends Model {

	public Integer author_id;
	public Integer contact_id;
	@Column(nullable = true)
    public Integer company_id;	
    public String firstname;
    public String lastname;
    @Column(nullable = true)
    public String title;
    @Column(nullable = true)
    public String company;
    @Column(nullable = true)
    public Date created;
    @Column(nullable = true)
    public Date updated;
//    @Column(nullable = false)
//    public int owner_id;
    @Column(nullable = true)
    public String visible;
    @Column(nullable = true)
    public String linkedinurl;
    @Column(nullable = true)
    public String tag_name;
    
    
    
	public contact(Integer authorID, Integer companyID, Date createdAt,
			Integer iD, String firstname2, String lastname2, String title2,
			Date updatedAt, Integer ownerID, String visibleTo, String company2,
			String linkedinURL2, String i) 
	{
		this.author_id = authorID;
		 this.contact_id = iD;
		 this.company_id = companyID;
		 this.firstname = firstname2;
		 this.lastname = lastname2;
		 this.title = title2;
		 this.company = company2;
		 this.created = createdAt;
		 this.updated = updatedAt;
//		 this.owner_id = ownerID;
		 this.visible = visibleTo;
		 this.linkedinurl = linkedinURL2;
		 this.tag_name = i;
	}







	public static void saveperson(List groupcontact) throws ParseException 
	{
		Integer AuthorID = null ;Integer CompanyID = null ;Date CreatedAt = null;Integer ID = null;String Firstname = null ;String Lastname = null;
		String Title = null;Date UpdatedAt = null;Integer OwnerID = null;String VisibleTo = null;String Company = null;String LinkedinURL = null;String TagIDs = null;
		for (int x=0; x<groupcontact.size(); x++){
	    	String contact_info = (String) groupcontact.get(x);
	    	String[] contact_split = contact_info.split("<>");
	    	if(contact_split.length>1){
	    		if(contact_split[0].equals("AuthorID")){
	    			AuthorID =  Integer.parseInt(contact_split[1]);
	    		}
		    	else if(contact_split[0].equals("CompanyID")){
		    		CompanyID =  Integer.parseInt(contact_split[1]);
		    	}
		    	else if(contact_split[0].equals("CreatedAt")){
		    		
		    		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		    		CreatedAt = format.parse(contact_split[1]);
		    	}
		    	else if(contact_split[0].equals("ID")){
		    		ID =  Integer.parseInt(contact_split[1]);
		    	}
		    	else if(contact_split[0].equals("Firstname")){
		    		Firstname =  contact_split[1];
		    	}
		    	else if(contact_split[0].equals("Lastname")){
		    		Lastname =  contact_split[1];
		    	}else if(contact_split[0].equals("Title")){
		    		Title =  contact_split[1];
		    	}
		    	else if(contact_split[0].equals("UpdatedAt")){
		    		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		    		UpdatedAt = format.parse(contact_split[1]);
		    	}
		    	else if(contact_split[0].equals("OwnerID")){
		    		OwnerID =  Integer.parseInt(contact_split[1]);
		    	}
		    	else if(contact_split[0].equals("VisibleTo")){
		    		VisibleTo =  contact_split[1];
		    	}
		    	else if(contact_split[0].equals("Company")){
		    		Company =  contact_split[1];
		    	}
		    	else if(contact_split[0].equals("LinkedinURL")){
		    		LinkedinURL =  contact_split[1];
		    	}
		    	else if(contact_split[0].equals("TagIDs")){
		    		TagIDs =  contact_split[1];
		    	}
		    } 
	    	else
	    	{
	    		if(contact_split[0].equals("AuthorID"))
		    	{
	    			AuthorID =  null;

		    	}
		    	else if(contact_split[0].equals("CompanyID"))
		    	{
		    		CompanyID =  null;
		    	}
		    	else if(contact_split[0].equals("CreatedAt"))
		    	{
		    		CreatedAt =  null;
		    	}
		    	else if(contact_split[0].equals("ID"))
		    	{
		    		ID =  null;
		    	}
		    	else if(contact_split[0].equals("Firstname"))
		    	{
		    		Firstname =  null;
		    	}
		    	else if(contact_split[0].equals("Lastname"))
		    	{
		    		Lastname =  null;
		    	}else if(contact_split[0].equals("Title"))
		    	{
		    		Title =  null;
		    	}
		    	else if(contact_split[0].equals("UpdatedAt"))
		    	{
		    		UpdatedAt =  null;
		    	}
		    	else if(contact_split[0].equals("OwnerID"))
		    	{
		    		OwnerID =  null;
		    	}
		    	else if(contact_split[0].equals("VisibleTo"))
		    	{
		    		VisibleTo =  null;
		    	}
		    	else if(contact_split[0].equals("Company"))
		    	{
		    		Company =  null;
		    	}
		    	else if(contact_split[0].equals("LinkedinURL"))
		    	{
		    		LinkedinURL =  null;
		    	}
		    	else if(contact_split[0].equals("TagIDs"))
		    	{
		    		TagIDs =  null;
		    	}
	    	}
	    	}
			String[] TagIDs_split = TagIDs.split(";");
				int count = 0;
				for(int i =1 ; i <=TagIDs_split.length; i++)
				{
					String query = "Select contact FROM contact contact where contact.tag_name =   "+"'"+TagIDs_split[count]+"'"+" and contact_id=  "+ ID;
					contact contacts = contact.find(query).first();
					if(contacts==null)
					{
						contact contact = new contact(AuthorID,CompanyID,CreatedAt,ID,Firstname,Lastname,Title,
						UpdatedAt,OwnerID,VisibleTo,Company,LinkedinURL,TagIDs_split[count]).save();
						count++;
					//	System.out.println("+++++++++++");
					}
					else
					{
					//	System.out.println("xxxxxxxxxxx");
					}

				}
				
			//	    List<tag> bobPostComments = contact.find("firstname", "Lovern").fetch();
			//    System.out.println(bobPostComments);
			
		
	}

    
    




 
}