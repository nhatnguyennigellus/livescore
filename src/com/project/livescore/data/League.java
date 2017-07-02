package com.project.livescore.data;

public class League {
	private int id;
	private String name;
	
	public League() {
		// TODO Auto-generated constructor stub
	}

	public League(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "League [id=" + id + ", name=" + name + "]";
	}
	
	
}
