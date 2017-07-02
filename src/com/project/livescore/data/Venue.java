package com.project.livescore.data;

public class Venue {
	private String id;
	private String city;
	private String country;
	private String league;
	
	public Venue() {
	}

	public Venue(String id, String city, String league, String country) {
		super();
		this.id = id;
		this.city = city;
		this.league = league;
		this.country = country;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Venue [id=" + id + ", city=" + city + ", league=" + league + "]";
	}
	
	
}
