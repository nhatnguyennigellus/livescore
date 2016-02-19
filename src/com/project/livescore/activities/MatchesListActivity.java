package com.project.livescore.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.Match;
import com.project.livescore1415.R;

public class MatchesListActivity extends ListActivity {

	ListView list;
	TextView tvNoData;
	DBAdapter mDB;
	Cursor mCursor;
	String currentCondition = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matches_list);

		list = (ListView) this.findViewById(android.R.id.list);
		tvNoData = (TextView) this.findViewById(R.id.tvNoData);
		mDB = new DBAdapter(this);
		mDB.open();

		// List<String> item = null ;

		if (mDB.countRows() == 0) {
			tvNoData.setVisibility(View.VISIBLE);
		} else {
			tvNoData.setVisibility(View.GONE);
			loadData("All");
		}

		final AlertDialog.Builder dlgDelCfm = new AlertDialog.Builder(this);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				TextView tvId = (TextView) view.findViewById(R.id.tvID);
				final int matchId = Integer.parseInt(tvId.getText().toString());

				dlgDelCfm.setTitle("Remove match comfirm");
				dlgDelCfm.setMessage("Are you sure?");

				dlgDelCfm.setIcon(R.drawable.ic_launcher);

				dlgDelCfm.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mDB.removeMatch(matchId);
						loadData(currentCondition);
						dialog.cancel();
					}
				});

				dlgDelCfm.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				dlgDelCfm.show();
			}

		});
	}

	private void loadData(String condition) {
		final ArrayList<HashMap<String, String>> lstMatch = new ArrayList<HashMap<String, String>>();
		SimpleAdapter adapter = new SimpleAdapter(this, lstMatch,
				R.layout.match_info_row, new String[] { "Id", "TeamGoal",
						"LeagueRound", "ScorerList" }, new int[] { R.id.tvID,
						R.id.tvTeamGoal, R.id.tvLeague, R.id.tvScorer });

		if (condition.equals("All")) {
			mCursor = mDB.getAllMatches();
			tvNoData.setVisibility(View.GONE);
		} else if (condition.contains("Bundesliga") || condition.contains("Champions League")
				|| condition.contains("DFB Pokal") || condition.contains("World Cup")) {
			mCursor = mDB.getMatchByLeague(condition);
			
		}
		else {
			mCursor = mDB.getMatchByTeam(condition);
		}

		if (mCursor.getCount() == 0) {
			tvNoData.setVisibility(View.VISIBLE);
			return;
		} else
			tvNoData.setVisibility(View.GONE);
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			Match m = cursorToObject(mCursor);

			HashMap<String, String> temp = new HashMap<String, String>();
			String result = m.getTeamA() + " " + m.getGoalA() + ":"
					+ m.getGoalB() + " " + m.getTeamB();
			if (m.getPSO_A() > 0 && m.getPSO_B() > 0)
				result += " (" + m.getPSO_A() + ":" + m.getPSO_B() + " PSO)";

			temp.put("Id", String.valueOf(m.getId()));
			temp.put("TeamGoal", result);
			temp.put("LeagueRound", m.getLeague() + " - " + m.getRound());
			if (m.getScorerListA().equals("")) {
				temp.put("ScorerList", m.getScorerListB());
			} else if (m.getScorerListB().equals("")) {
				temp.put("ScorerList", m.getScorerListA());
			} else
				temp.put("ScorerList",
						m.getScorerListA() + "; " + m.getScorerListB());

			lstMatch.add(temp);
			mCursor.moveToNext();
		}

		setListAdapter(adapter);
		mCursor.close();

		if (mDB.countRows() == 0) {
			tvNoData.setVisibility(View.VISIBLE);
		}
	}

	private Match cursorToObject(Cursor c) {
		// TODO Auto-generated method stub
		Match m = new Match();
		m.setId(c.getInt(0));
		m.setLeague(c.getString(1));
		m.setRound(c.getString(2));
		m.setTeamA(c.getString(3));
		m.setTeamB(c.getString(4));
		m.setGoalA(c.getInt(5));
		m.setGoalB(c.getInt(6));
		m.setScorerListA(c.getString(7));
		m.setScorerListB(c.getString(8));
		m.setPSO_A(c.getInt(9));
		m.setPSO_B(c.getInt(10));

		return m;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matches_list, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		final Dialog dlgEdit = new Dialog(this);

		if (id == R.id.exit) {
			final AlertDialog.Builder dlgExit = new AlertDialog.Builder(this);
			dlgExit.setTitle("Exit");
			dlgExit.setMessage("Are you sure?");

			dlgExit.setIcon(R.drawable.ic_launcher);
			dlgExit.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});

			dlgExit.setNegativeButton("No", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});

			dlgExit.show();
			return true;
		} else if (id == R.id.sort) {
			final AlertDialog.Builder dlgLiga = new AlertDialog.Builder(this);
			final CharSequence Liga[] = { "All", "Bundesliga",
					"Champions League", "DFB Pokal", "World Cup 2014" };

			dlgLiga.setItems(Liga, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if (Liga[which].equals("Bundesliga")) {
						loadData("Bundesliga");
					} else if (Liga[which].equals("Champions League")) {
						loadData("Champions League");
					} else if (Liga[which].equals("DFB Pokal")) {
						loadData("DFB Pokal");
					} else if (Liga[which].equals("World Cup 2014")) {
						loadData("World Cup");
					} else
						loadData("All");
					dialog.cancel();
					currentCondition = (String) Liga[which];
				}
			});

			dlgLiga.show();
		} else if (id == R.id.sortTeam) {
			dlgEdit.setContentView(R.layout.edit);
			final EditText txtEdit = (EditText) dlgEdit
					.findViewById(R.id.txtEdit);
			Button btnEditOK = (Button) dlgEdit
					.findViewById(R.id.btnEditOK);
			Button btnEditCancel = (Button) dlgEdit
					.findViewById(R.id.btnEditCancel);

			btnEditOK.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					loadData(txtEdit.getText().toString());
					dlgEdit.cancel();
					currentCondition = txtEdit.getText().toString();
				}
			});

			btnEditCancel
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dlgEdit.cancel();
						}
					});

			dlgEdit.show();
		} else if (id == R.id.export) {
			export();
		}
		return super.onOptionsItemSelected(item);
	}

	private void export() {
		mCursor = mDB.getAllMatches();
		mCursor.moveToFirst();
		List<Match> listMatch = new ArrayList<Match>();
		
		while (!mCursor.isAfterLast()) {
			Match m = cursorToObject(mCursor);
			listMatch.add(m);

			mCursor.moveToNext();
		}

		mDB.export(listMatch);
		mCursor.close();

		if (mDB.countRows() == 0) {
			Toast.makeText(this, "No data", Toast.LENGTH_SHORT);
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_matches_list,
					container, false);
			return rootView;
		}
	}

}
