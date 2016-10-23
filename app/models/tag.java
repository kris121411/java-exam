package models;
 
import java.util.*;

import javax.persistence.*;
 
import play.db.jpa.*;
import models.*;
 
@Entity
public class tag extends Model 
{
  public String tag_name;
  public Integer tag_id;   
  public tag(String tag_name, Integer tag_id)
  {
      this.tag_name = tag_name;	 
      this.tag_id = tag_id;
  }
public static void savetag(List groupcontact) 
{
    for (int x=0; x<groupcontact.size(); x++)
    {
    	String contact_info = (String) groupcontact.get(x);
    	String[] contact_split = contact_info.split("<>");
    	if(contact_split[0].equals("Tags"))
    	{
    		int tagID = Integer.parseInt(contact_split[1]);
    		String tagName =  contact_split[2];
    		tag tags = tag.find("tag_name", tagName).first();
    	        if(tags == null) 
    	        {
    	            tag tag = new tag(tagName,tagID).save();
    	        }
    	        else
    	        {
    	        }
    	}
    	else
    	{
    	}
    } 
   // List<tag> bobPostComments = tag.find("tag_name", "SuperUSer").fetch();
    //System.out.println(bobPostComments);
}


  
}