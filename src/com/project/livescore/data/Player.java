package com.project.livescore.data;

public class Player {
	private int id;
	private String firstname;
	private String lastname;
	private int kitNo;
	private String team;
	private String country;
	private String league;
	private int lineUp;
	private String position;
	
	public Player() {
	}

	public Player(String firstname, String lastname, int kitNo, String team, String country, String league, int lineUp, String position) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.kitNo = kitNo;
		this.team = team;
		this.country = country;
		this.league = league;
		this.lineUp = lineUp;
		this.position = position;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getKitNo() {
		return kitNo;
	}

	public void setKitNo(int kitNo) {
		this.kitNo = kitNo;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public int getLineUp() {
		return lineUp;
	}

	public void setLineUp(int lineUp) {
		this.lineUp = lineUp;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", kitNo=" + kitNo
				+ ", team=" + team + "]";
	}

}
