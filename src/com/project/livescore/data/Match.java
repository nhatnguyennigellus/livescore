package com.project.livescore.data;

public class Match {
	private int id;
	private String League;
	private String Round;
	private String TeamA;
	private String TeamB;
	private int GoalA;
	private int GoalB;
	private String ScorerListA;
	private String ScorerListB;
	private int PSO_A;
	private int PSO_B;
	public String getLeague() {
		return League;
	}
	public void setLeague(String league) {
		League = league;
	}
	public String getRound() {
		return Round;
	}
	public void setRound(String round) {
		Round = round;
	}
	public String getTeamA() {
		return TeamA;
	}
	public void setTeamA(String teamA) {
		TeamA = teamA;
	}
	public String getTeamB() {
		return TeamB;
	}
	public void setTeamB(String teamB) {
		TeamB = teamB;
	}
	public int getGoalA() {
		return GoalA;
	}
	public void setGoalA(int goalA) {
		GoalA = goalA;
	}
	public int getGoalB() {
		return GoalB;
	}
	public void setGoalB(int goalB) {
		GoalB = goalB;
	}
	public String getScorerListA() {
		return ScorerListA;
	}
	public void setScorerListA(String scorerListA) {
		ScorerListA = scorerListA;
	}
	public String getScorerListB() {
		return ScorerListB;
	}
	public void setScorerListB(String scorerListB) {
		ScorerListB = scorerListB;
	}
	public int getPSO_A() {
		return PSO_A;
	}
	public void setPSO_A(int pSO_A) {
		PSO_A = pSO_A;
	}
	public int getPSO_B() {
		return PSO_B;
	}
	public void setPSO_B(int pSO_B) {
		PSO_B = pSO_B;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
