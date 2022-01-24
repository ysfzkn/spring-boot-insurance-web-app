package net.javaguides.springboot.model;


import org.springframework.format.annotation.DateTimeFormat;


public class Application  {

	
	private long id;
	
	private String place;
	
	@DateTimeFormat(pattern = "yyyy-dd-MM")
	private String start;
	
	@DateTimeFormat(pattern = "yyyy-dd-MM")
	private String end;
	
	private String name;
	
	private String surname;
	
	private String identity;

	private String tel;

	private String mail;

	private String offer;
	
	 public Application() 
	 {
		 
	 }
	  
	@Override
	public String toString() {
		return "Application [id=" + id + ", place=" + place + ", start=" + start + ", end=" + end + ", name=" + name
				+ ", surname=" + surname + ", identity=" + identity + ", tel=" + tel + ", mail=" + mail + ", offer="
				+ offer + "]";
	}
	public Application(long id, String place, String start, String end, String name, String surname,
			String identity, String tel, String mail, String offer) {
		super();
		this.id = id;
		this.place = place;
		this.start = start;
		this.end = end;
		this.name = name;
		this.surname = surname;
		this.identity = identity;
		this.tel = tel;
		this.mail = mail;
		this.offer = offer;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
}
