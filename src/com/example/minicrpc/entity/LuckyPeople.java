package com.example.minicrpc.entity;

import java.util.Date;

/**
 * ���յ��˵�ʵ����Ϣ
 * @author michael.chen
 *
 */
public class LuckyPeople {
	
	
	public static final int MAN = 0;
	public static final int FEMAN = 1;
	
	/**
	 * ���ݿ��е�id
	 */
	private long id;
	
	/**
	 * ͷ���ļ���·��
	 */
	private String portraitPath;
	
	private String name;
	
	/**
	 * �Ա���Ϊ0�� ŮΪ1
	 */
	private int sex;
	
	private Date birthdayDate;
	/**
	 * �绰
	 */
	private String tel;
	/**��ע */
	private String note;
	
	
	
	public LuckyPeople() {}



	

	public LuckyPeople(long id, String portraitPath, String name, int sex,
			Date birthdayDate, String tel, String note) {
		super();
		this.id = id;
		this.portraitPath = portraitPath;
		this.name = name;
		this.sex = sex;
		this.birthdayDate = birthdayDate;
		this.tel = tel;
		this.note = note;
	}



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getPortraitPath() {
		return portraitPath;
	}



	public void setPortraitPath(String portraitPath) {
		this.portraitPath = portraitPath;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getSex() {
		return sex;
	}



	public void setSex(int sex) {
		this.sex = sex;
	}



	public Date getBirthdayDate() {
		return birthdayDate;
	}



	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	@Override
	public String toString() {
		return "LuckyPeople [portraitPath=" + portraitPath + ", name=" + name
				+ ", sex=" + sex + ", birthdayDate=" + birthdayDate + ", tel="
				+ tel + ", note=" + note + "]";
	}
	
	
}
