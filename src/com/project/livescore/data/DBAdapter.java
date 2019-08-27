package com.project.livescore.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

@SuppressLint("SdCardPath")
public class DBAdapter {
	public static final String KEY_ID = "id";
	public static final String KEY_LEAGUE = "League";
	public static final String KEY_ROUND = "Round";
	public static final String KEY_TEAM_A = "TeamA";
	public static final String KEY_TEAM_B = "TeamB";
	public static final String KEY_GOAL_A = "GoalA";
	public static final String KEY_GOAL_B = "GoalB";
	public static final String KEY_SCORER_A = "ScorerListA";
	public static final String KEY_SCORER_B = "ScorerListB";
	public static final String KEY_PSO_A = "PSOA";
	public static final String KEY_PSO_B = "PSOB";

	public static final String KEY_LIGA_NAME = "Name";
	public static final String KEY_TEAM_NAME = "TeamName";
	public static final String KEY_VENUE_NAME = "City";
	public static final String KEY_CHAMP_NAME = "Name";
	public static final String KEY_COUNTRY = "Country";
	public static final String KEY_ON = "Status";
	
	public static final String KEY_FIRSTNAME = "Firstname";
	public static final String KEY_LASTNAME = "Lastname";
	public static final String KEY_KIT_NO = "KitNo";
	public static final String KEY_TEAM_PLAYER = "Team";
	public static final String KEY_COUNTRY_PLAYER = "Country";
	public static final String KEY_LEAGUE_PLAYER = "League";
	public static final String KEY_LINE_UP = "LineUp";
	public static final String KEY_POSITION = "Position";

	private static final String DATABASE_NAME = "LivescoreDB";
	private static final String DB_TBL_MATCH = "Match";
	private static final String DB_TBL_LEAGUE = "League";
	private static final String DB_TBL_TEAM = "Team";
	private static final String DB_TBL_VENUE = "Venue";
	private static final String DB_TBL_CHAMP = "Champ";
	private static final String DB_TBL_PLAYER = "Player";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;

	private static final String DB_CR_MATCH = "create table if not exists Match ("
			+ KEY_ID + " integer primary key autoincrement, " + KEY_LEAGUE
			+ " text not null, " + KEY_ROUND + " text not null, " + KEY_TEAM_A
			+ " text not null, " + KEY_TEAM_B + " text not null, " + KEY_GOAL_A
			+ " integer not null, " + KEY_GOAL_B + " integer not null, "
			+ KEY_SCORER_A + " text not null, " + KEY_SCORER_B
			+ " text not null, " + KEY_PSO_A + " integer null, " + KEY_PSO_B
			+ " integer null);";

	private static final String DB_CR_LEAGUE = "create table if not exists League ("
			+ KEY_ID + " integer primary key autoincrement, " 
			+ KEY_LIGA_NAME + " text not null);";
	
	private static final String DB_CR_TEAM = "create table if not exists Team ("
			+ KEY_ID + " integer primary key autoincrement, " 
			+ KEY_TEAM_NAME + " text not null,"
			+ KEY_COUNTRY + " text not null,"
			+ KEY_LIGA_NAME + " text not null"
			+ ");";
	
	private static final String DB_CR_VENUE = "create table if not exists Venue ("
			+ KEY_ID + " integer primary key autoincrement, " 
			+ KEY_VENUE_NAME + " text not null,"
			+ KEY_COUNTRY + " text not null,"
			+ KEY_LEAGUE + " text not null"
			+ ");";
	
	private static final String DB_CR_CHAMPS = "create table if not exists "
			+ DB_TBL_CHAMP + " ("
			+ KEY_LIGA_NAME + " text primary key, " 
			+ KEY_ON + " integer not null);";
	
	private static final String DB_CR_PLAYER = "create table if not exists " 
			+ DB_TBL_PLAYER + " ("
			+ KEY_ID + " integer primary key autoincrement, " 
			+ KEY_FIRSTNAME + " text,"
			+ KEY_LASTNAME + " text not null,"
			+ KEY_KIT_NO + " integer not null,"
			+ KEY_COUNTRY_PLAYER + " text not null,"
			+ KEY_TEAM_PLAYER + " text not null,"
			+ KEY_LEAGUE_PLAYER + " text not null,"
			+ KEY_LINE_UP + " integer not null,"
			+ KEY_POSITION + " text not null"
			+ ");";
	
	private static final int DATABASE_VERSION = 5;

	private final Context mContext;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CR_MATCH);
			db.execSQL(DB_CR_LEAGUE);
			db.execSQL(DB_CR_TEAM);
			db.execSQL(DB_CR_VENUE);
			db.execSQL(DB_CR_CHAMPS);
			db.execSQL(DB_CR_PLAYER);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/*db.execSQL("DROP TABLE IF EXISTS Match");
			db.execSQL("DROP TABLE IF EXISTS League");
			db.execSQL("DROP TABLE IF EXISTS Team");
			db.execSQL("DROP TABLE IF EXISTS Venue");
			db.execSQL("DROP TABLE IF EXISTS Champ");
			db.execSQL("DROP TABLE IF EXISTS Player");*/
			// If you need to add a new column
		    if (newVersion > oldVersion) {
		        db.execSQL("ALTER TABLE " + DB_TBL_PLAYER 
		        		+ " ADD COLUMN " + KEY_POSITION + " TEXT DEFAULT 'Forward'");
		    }
			onCreate(db);
		}

	}

	public DBAdapter(Context ctx) {
		this.mContext = ctx;
	}

	public DBAdapter open() {
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null,
				DATABASE_VERSION);
		mDB = mDbHelper.getWritableDatabase();
		mDB.execSQL(DB_CR_MATCH);
		mDB.execSQL(DB_CR_LEAGUE);
		mDB.execSQL(DB_CR_TEAM);
		mDB.execSQL(DB_CR_VENUE);
		mDB.execSQL(DB_CR_CHAMPS);
		mDB.execSQL(DB_CR_PLAYER);
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/*
	 * Match
	 */
	public void addMatch(String league, String round, String teamA,
			String teamB, int goalA, int goalB, String scorerListA,
			String scorerListB, int penA, int penB) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LEAGUE, league);
		initialValues.put(KEY_ROUND, round);
		initialValues.put(KEY_TEAM_A, teamA);
		initialValues.put(KEY_TEAM_B, teamB);
		initialValues.put(KEY_GOAL_A, goalA);
		initialValues.put(KEY_GOAL_B, goalB);
		initialValues.put(KEY_SCORER_A, scorerListA);
		initialValues.put(KEY_SCORER_B, scorerListB);
		initialValues.put(KEY_PSO_A, penA);
		initialValues.put(KEY_PSO_B, penB);
		mDB.insertOrThrow(DB_TBL_MATCH, null, initialValues);
	}

	public void deleteAll() {
		mDB.delete(DB_TBL_MATCH, null, null);
	}

	public Cursor getAllMatches() {
		return mDB.query(DB_TBL_MATCH, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, "id" + "="
				+ KEY_ID, null, null, null, null);
	}

	public Cursor getMatchByLeague(String league) {
		Cursor mCursor = mDB.query(DB_TBL_MATCH, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, KEY_LEAGUE + " LIKE '%"
				+ league + "%'", null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}
	
	public Cursor getMatchByTeam(String team) {
		Cursor mCursor = mDB.query(DB_TBL_MATCH, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, KEY_TEAM_A + " LIKE '%"
				+ team + "%' OR " + KEY_TEAM_B + " LIKE '%"	+ team + "%'", null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public void removeMatch(int id) {
		mDB.delete(DB_TBL_MATCH, KEY_ID + "=" + id, null);
	}

	public int countRows() {
		Cursor c;

		c = mDB.rawQuery("SELECT * FROM Match", null);
		return c.getCount();
	}
	
	
	/*
	 * League
	 */
	public void addLeague(League league) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LIGA_NAME, league.getName());
		mDB.insertOrThrow(DB_TBL_LEAGUE, null, initialValues);
	}

	public void deleteAllLeague() {
		mDB.delete(DB_TBL_LEAGUE, null, null);
	}
	
	public void deleteLeague(int id) {
		mDB.delete(DB_TBL_LEAGUE, KEY_ID + " = " + id, null);
	}

	public Cursor getAllLeagues() {
		return mDB.query(DB_TBL_LEAGUE, new String[] { KEY_ID, KEY_LIGA_NAME}, "id ="
				+ KEY_ID, null, null, null, null);
	}
	
	/*
	 * Team
	 */
	public void addTeam(Team team) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TEAM_NAME, team.getName());
		initialValues.put(KEY_COUNTRY, team.getCountry());
		initialValues.put(KEY_LIGA_NAME, team.getLiga());
		mDB.insertOrThrow(DB_TBL_TEAM, null, initialValues);
	}

	public void deleteAllTeam() {
		mDB.delete(DB_TBL_TEAM, null, null);
	}
	
	public void deleteTeam(int id) {
		mDB.delete(DB_TBL_TEAM, KEY_ID + " = " + id, null);
	}

	public Cursor getTeamByLeague(String liga) {
		return mDB.query(DB_TBL_TEAM, 
				new String[] { KEY_ID, KEY_TEAM_NAME, KEY_COUNTRY, KEY_LIGA_NAME}, 
				KEY_LIGA_NAME + "= '" + liga + "'", null, null, null, null);
	}
	
	/*
	 * Player
	 */
	public void addPlayer(Player player) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIRSTNAME, player.getFirstname());
		initialValues.put(KEY_LASTNAME, player.getLastname());
		initialValues.put(KEY_KIT_NO, player.getKitNo());
		initialValues.put(KEY_LEAGUE_PLAYER, player.getLeague());
		initialValues.put(KEY_COUNTRY_PLAYER, player.getCountry());
		initialValues.put(KEY_TEAM_PLAYER, player.getTeam());
		initialValues.put(KEY_LINE_UP, player.getLineUp());
		initialValues.put(KEY_POSITION, player.getPosition());
		mDB.insertOrThrow(DB_TBL_PLAYER, null, initialValues);
	}

	public void deleteAllPlayer() {
		mDB.delete(DB_TBL_PLAYER, null, null);
	}
	
	public void deletePlayer(int id) {
		mDB.delete(DB_TBL_PLAYER, KEY_ID + " = " + id, null);
	}
	
	public void updatePlayer(Player player) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_FIRSTNAME, player.getFirstname());
		initialValues.put(KEY_LASTNAME, player.getLastname());
		initialValues.put(KEY_KIT_NO, player.getKitNo());
		initialValues.put(KEY_LEAGUE_PLAYER, player.getLeague());
		initialValues.put(KEY_COUNTRY_PLAYER, player.getCountry());
		initialValues.put(KEY_TEAM_PLAYER, player.getTeam());
		initialValues.put(KEY_LINE_UP, player.getLineUp());
		initialValues.put(KEY_POSITION, player.getPosition());
		mDB.update(DB_TBL_PLAYER, initialValues, KEY_ID + " = " + String.valueOf(player.getId()) + "", null);
	}

	public Cursor getPlayerByLeagueAndTeam(String liga, String team) {
		return mDB.query(DB_TBL_PLAYER, 
				new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_KIT_NO, 
						KEY_LEAGUE_PLAYER, KEY_COUNTRY_PLAYER, KEY_TEAM_PLAYER, 
						KEY_LINE_UP, KEY_POSITION}, 
						KEY_LEAGUE_PLAYER + " LIKE '%" + liga + "%' AND " 
								+ KEY_TEAM_PLAYER + " LIKE '%" + team + "%'", 
				null, null, null, KEY_KIT_NO);
	}
	
	public Cursor getPlayerByLeagueAndTeam(String liga, String team, int lineUp) {
		return mDB.query(DB_TBL_PLAYER, 
				new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_KIT_NO, 
						KEY_LEAGUE_PLAYER, KEY_COUNTRY_PLAYER, KEY_TEAM_PLAYER, 
						KEY_LINE_UP, KEY_POSITION}, 
						KEY_LEAGUE_PLAYER + " LIKE '%" + liga + "%' AND " 
								+ KEY_TEAM_PLAYER + " LIKE '%" + team + "%' AND "
								+ KEY_LINE_UP + " = " + lineUp, 
				null, null, null, KEY_KIT_NO);
	}
	
	public Cursor getPlayerByTeam(String team, int lineUp) {
		return mDB.query(DB_TBL_PLAYER, 
				new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_KIT_NO,
						KEY_TEAM_PLAYER, KEY_COUNTRY_PLAYER,
						KEY_LINE_UP, KEY_POSITION}, 
							KEY_TEAM_PLAYER + " LIKE '%" + team + "%' AND "
								+ KEY_LINE_UP + " = " + lineUp, 
				null, null, null, KEY_KIT_NO);
	}
	
	public Cursor getPlayerByTeam(String team) {
		return mDB.query(DB_TBL_PLAYER, 
				new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_KIT_NO,
						KEY_TEAM_PLAYER, KEY_COUNTRY_PLAYER, 
						KEY_LINE_UP, KEY_POSITION}, 
							KEY_TEAM_PLAYER + " LIKE '%" + team + "%'", 
				null, null, null, KEY_KIT_NO);
	}
	
	public Cursor getPlayerById(String id) {
		return mDB.query(DB_TBL_PLAYER, 
				new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_KIT_NO,
						KEY_TEAM_PLAYER, KEY_COUNTRY_PLAYER, 
						KEY_LINE_UP, KEY_POSITION}, 
						KEY_ID + " = " + id, null, null, null, KEY_KIT_NO);
	}
	
	/*
	 * Venue
	 */
	public void addVenue(Venue venue) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_VENUE_NAME, venue.getCity());
		initialValues.put(KEY_COUNTRY, venue.getCountry());
		initialValues.put(KEY_LEAGUE, venue.getLeague());
		mDB.insertOrThrow(DB_TBL_VENUE, null, initialValues);
	}

	public void deleteAllVenue() {
		mDB.delete(DB_TBL_VENUE, null, null);
	}
	
	public void deleteVenue(int id) {
		mDB.delete(DB_TBL_VENUE, KEY_ID + " = " + id, null);
	}

	public Cursor getVenueByLeague(String liga) {
		return mDB.query(DB_TBL_VENUE, 
				new String[] { KEY_ID, KEY_VENUE_NAME, KEY_COUNTRY, KEY_LEAGUE}, 
				KEY_LEAGUE + "= '" + liga + "'", null, null, null, null);
	}
	
	/*
	 * Champs
	 */
	public Cursor getAllChamp() {
		return mDB.query(DB_TBL_CHAMP, new String[] { KEY_CHAMP_NAME, KEY_ON }, 
				KEY_CHAMP_NAME + "=" + KEY_CHAMP_NAME, 
				null, null, null, null);
	}
	
	public Cursor getChamp(String champ) {
		return mDB.query(DB_TBL_CHAMP, new String[] { KEY_CHAMP_NAME, KEY_ON }, 
				KEY_CHAMP_NAME + "= '" + champ + "'", 
				null, null, null, null);
	}
	
	public void addChamp(Champs ch) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CHAMP_NAME, ch.getName());
		initialValues.put(KEY_ON, ch.isOn() ? 1 : 0);
		mDB.insertOrThrow(DB_TBL_CHAMP, null, initialValues);
	}
	
	public void updateChamp(Champs ch) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_CHAMP_NAME, ch.getName());
		initialValues.put(KEY_ON, ch.isOn() ? 1 : 0);
		mDB.update(DB_TBL_CHAMP, initialValues, 
				KEY_CHAMP_NAME + "= '" + ch.getName() + "'", null);
	}
}
