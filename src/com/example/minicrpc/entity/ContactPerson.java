package com.example.minicrpc.entity;

import java.util.List;

import android.graphics.Bitmap;

/**
 * ͨѶ¼�л�ȡ��ʵ��
 * @author Administrator
 *
 */
public class ContactPerson {

	/** ��ϵ�˵�id�� */
	private long id;
	/** ��ϵ�˵����� */
	private String name;
	/** ��ϵ�˵绰 */
	private List<String> allphones;
	/** ͷ��  */
	private Bitmap protrait;
	
	
	public ContactPerson(long id, String name, List<String> allphones,
			Bitmap protrait) {
		super();
		this.id = id;
		this.name = name;
		this.allphones = allphones;
		this.protrait = protrait;
	}
	
	
	
	
	
	public ContactPerson() {
		super();
		// TODO Auto-generated constructor stub
	}





	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAllphones() {
		return allphones;
	}
	public void setAllphones(List<String> allphones) {
		this.allphones = allphones;
	}
	public Bitmap getProtrait() {
		return protrait;
	}
	public void setProtrait(Bitmap protrait) {
		this.protrait = protrait;
	}



	@Override
	public String toString() {
		return "ContactPerson [id=" + id + ", name=" + name + ", allphones="
				+ allphones + ", protrait=" + (protrait==null ? "false" : "true") + "]";
	}
	
	
	
}
