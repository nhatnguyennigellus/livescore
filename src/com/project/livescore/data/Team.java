package com.project.livescore.data;

public class Team {
	private int id;
	private String name;
	private String country;
	private String liga;
	
	public Team() {
	}

	public Team(int id, String name, String country, String liga) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
		this.liga = liga;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLiga() {
		return liga;
	}

	public void setLiga(String liga) {
		this.liga = liga;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", country=" + country + "]";
	}
	
	
}
