package epaw.lab4.model;

import java.sql.Timestamp;

public class Tweet implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	 private int id;
	 private int uid;
	 private String uname;
	 private String picture;
	 private Timestamp postDateTime;
	 private String content;
	 private boolean featured;

	 public Tweet() {
	 }

	 public Integer getId() {
		 return this.id;
	 }
	 
	 public void setId(Integer id) {
		 this.id = id;
	 }

	 public int getUid() {
		 return this.uid;
	 }
	 
	 public void setUid(int uid) {
		 this.uid = uid;
	 }
	 
	 public String getUname() {
		 return this.uname;
	 }
	 
	 public void setUname(String uname) {
		 this.uname = uname;
	 }

	 public String getPicture() {
		 return this.picture;
	 }

	 public void setPicture(String picture) {
		 this.picture = picture;
	 }
	 
	 public Timestamp getPostDateTime() {
		 return this.postDateTime;
	 }
	 public void setPostDateTime(Timestamp postDateTime) {
		 this.postDateTime = postDateTime;
	 }
	 
	 public String getContent() {
		 return this.content;
	 }
	 public void setContent(String content) {
		 this.content = content;
	 }

	public boolean isFeatured() {
		return this.featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

}
