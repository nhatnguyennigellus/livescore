package com.project.livescore.data;

public class Player {
	private String id;
	private String firstname;
	private String lastname;
	private int kitNo;
	private String team;
	
	public Player() {
	}

	public Player(String id, String firstname, String lastname, int kitNo, String team) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.kitNo = kitNo;
		this.team = team;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public String toString() {
		return "Player [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", kitNo=" + kitNo
				+ ", team=" + team + "]";
	}
	
}
