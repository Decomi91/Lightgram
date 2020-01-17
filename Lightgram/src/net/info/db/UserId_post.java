package net.info.db;

import java.sql.Date;

public class UserId_post {
	private String id;
	private int itemNum;
	private int level;
	private String pic_url;
	private String dataText;
	private Date posted_time;
	private Date alter_time;
	private String location;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getDataText() {
		return dataText;
	}
	public void setDataText(String dataText) {
		this.dataText = dataText;
	}
	public Date getPosted_time() {
		return posted_time;
	}
	public void setPosted_time(Date posted_time) {
		this.posted_time = posted_time;
	}
	public Date getAlter_time() {
		return alter_time;
	}
	public void setAlter_time(Date alter_time) {
		this.alter_time = alter_time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
