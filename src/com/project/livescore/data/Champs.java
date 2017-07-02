package com.project.livescore.data;

public class Champs {
	private String name;
	private boolean on;
	
	public Champs() {
	}

	public Champs(String name, boolean on) {
		super();
		this.name = name;
		this.on = on;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}
	
	
}
