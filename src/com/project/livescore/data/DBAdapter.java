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

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;

	private static final String DATABASE_CREATE = "create table Match ("
			+ KEY_ID + " integer primary key autoincrement, " + KEY_LEAGUE
			+ " text not null, " + KEY_ROUND + " text not null, " + KEY_TEAM_A
			+ " text not null, " + KEY_TEAM_B + " text not null, " + KEY_GOAL_A
			+ " integer not null, " + KEY_GOAL_B + " integer not null, "
			+ KEY_SCORER_A + " text not null, " + KEY_SCORER_B
			+ " text not null, " + KEY_PSO_A + " integer null, " + KEY_PSO_B
			+ " integer null);";
	private static final String DATABASE_NAME = "LivescoreDB";
	private static final String DATABASE_TABLE = "Match";
	private static final int DATABASE_VERSION = 3;

	private final Context mContext;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS Match");
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
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

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
		mDB.insertOrThrow(DATABASE_TABLE, null, initialValues);
	}

	public void deleteAll() {
		mDB.delete(DATABASE_TABLE, null, null);
	}

	public Cursor getAllMatches() {
		return mDB.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, "id" + "="
				+ KEY_ID, null, null, null, null);
	}

	public Cursor getMatchByLeague(String league) {
		Cursor mCursor = mDB.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, KEY_LEAGUE + " LIKE '%"
				+ league + "%'", null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}
	
	public Cursor getMatchByTeam(String team) {
		Cursor mCursor = mDB.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_LEAGUE,
				KEY_ROUND, KEY_TEAM_A, KEY_TEAM_B, KEY_GOAL_A, KEY_GOAL_B,
				KEY_SCORER_A, KEY_SCORER_B, KEY_PSO_A, KEY_PSO_B }, KEY_TEAM_A + " LIKE '%"
				+ team + "%' OR " + KEY_TEAM_B + " LIKE '%"	+ team + "%'", null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;
	}

	public void removeMatch(int id) {
		mDB.delete(DATABASE_TABLE, KEY_ID + "=" + id, null);
	}

	public int countRows() {
		Cursor c;

		c = mDB.rawQuery("SELECT * FROM Match", null);
		return c.getCount();
	}
	
	public boolean export(List<Match> list) {		
		File sd = Environment.getExternalStorageDirectory();
		
		try {
			File file = new File(sd + "/Android/data/com.project.livescore/201415.txt");
			if (!file.exists()) {
				file.mkdir();
			}
			FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("\tRESULT 2014 - 2015 SEASON");
            bw.newLine();
            return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void print(List<Match> list, BufferedWriter bw) throws IOException {
		if (list.size() > 0) {
			bw.write("-------\t-------");
			bw.newLine();
			for (Match m : list) {
				
				bw.write(m.getTeamA() + " " + m.getGoalA() + ":"
						+ m.getGoalB() + " " + m.getTeamB());
				bw.newLine();
				if (m.getPSO_A() > 0 && m.getPSO_B() > 0) {
					bw.write(" (" + m.getPSO_A() + ":" + m.getPSO_B() + " PSO)");
					bw.newLine();
				}
				bw.write(m.getLeague() + " - " + m.getRound());
				bw.newLine();
				if (m.getScorerListA().equals("")) {
					bw.write(m.getScorerListB());
				} else if (m.getScorerListB().equals("")) {
					bw.write(m.getScorerListA());
				} else
					bw.write(m.getScorerListA() + " ; " + m.getScorerListB());
				bw.newLine();
			}
		} else {
			bw.write("\tNo data");
			bw.newLine();
		}
	}
}
