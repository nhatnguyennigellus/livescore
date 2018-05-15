package com.project.livescore.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.League;
import com.project.livescore.data.Player;
import com.project.livescore.data.Team;
import com.project.livescore1415.R;

import android.R.bool;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerFragment extends Fragment {
	public PlayerFragment() {
	}

	EditText txtFirstname, txtLastname;
	Button btnAdd, btnCountry, btnLiga, btnKitNo, btnTeam, btnUpdate;
	ListView listPlayer;
	TextView tvPlayerIdHid;
	Switch swLineup;
	static DBAdapter mDB;
	Cursor mCursor;
	String criteriaTeam = "";
	String criteriaLeague = "";
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlayerFragment newInstance(int sectionNumber) {
		PlayerFragment fragment = new PlayerFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_player, container, false);
		txtFirstname = (EditText) rootView.findViewById(R.id.txtFirstname);
		txtLastname = (EditText) rootView.findViewById(R.id.txtLastname);
		btnAdd = (Button) rootView.findViewById(R.id.btnAddPlayer);
		btnUpdate = (Button) rootView.findViewById(R.id.btnPlayerUpdate);
		btnCountry = (Button) rootView.findViewById(R.id.btnCountryPlayer);
		btnLiga = (Button) rootView.findViewById(R.id.btnLigaPlayer);
		btnKitNo = (Button) rootView.findViewById(R.id.btnKitNo);
		btnTeam = (Button) rootView.findViewById(R.id.btnTeamPlayer);
		listPlayer = (ListView) rootView.findViewById(R.id.listPlayer);
		tvPlayerIdHid = (TextView) rootView.findViewById(R.id.tvPlayerIdHidden);
		swLineup = (Switch) rootView.findViewById(R.id.swLineup);
		
		mDB = new DBAdapter(getActivity());
		mDB.open();

		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (txtLastname.getText().toString().isEmpty()) {
					noti("Please enter a name!");
					return;
				}
				if (btnCountry.getText().toString().equals("Select Country...")) {
					noti("Please choose a country!");
					return;
				}
				if (btnLiga.getText().toString().equals("Select League...")) {
					noti("Please choose a league!");
					return;
				}
				if (btnKitNo.getText().toString().equals("Select Kit No...")) {
					noti("Please choose a kit number!");
					return;
				}
				if (btnTeam.getText().toString().equals("Select Team...")) {
					noti("Please choose a team!");
					return;
				}
				StringBuffer sbFN = new StringBuffer();
				sbFN.append(Character.toUpperCase(txtFirstname.getText().toString().charAt(0)));
				sbFN.append(txtFirstname.getText().toString().substring(1));
				String newFirstname = sbFN.toString();
				Player player = new Player(newFirstname, 
						txtLastname.getText().toString().toUpperCase(),
						Integer.parseInt(btnKitNo.getText().toString()), 
						btnTeam.getText().toString(),
						btnCountry.getText().toString(), 
						btnLiga.getText().toString(), 
						swLineup.isChecked() ? 1 : 0);

				mDB.addPlayer(player);
				noti("Player added!");
				txtLastname.setText("");
				txtFirstname.setText("");
				btnKitNo.setText("Select Kit No...");
				if (!criteriaTeam.equals("Select Team...") && !criteriaLeague.equals("Select League...")) {
					loadData(criteriaLeague, criteriaTeam, "ALL");
				}
			}
		});

		final AlertDialog.Builder dlgLiga = new AlertDialog.Builder(getActivity());
		final ArrayList<String> ligen = new ArrayList<String>();
		mCursor = mDB.getAllLeagues();
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			League liga = new League();
			liga.setId(mCursor.getInt(0));
			liga.setName(mCursor.getString(1));

			ligen.add(liga.getName());
			mCursor.moveToNext();
		}
		mCursor.close();
		final String Ligen[] = ligen.toArray(new String[0]);
		btnLiga.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dlgLiga.setItems(Ligen, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						btnLiga.setText(Ligen[which]);
						criteriaLeague = btnLiga.getText().toString();
						loadData(criteriaLeague, criteriaTeam, "ALL");
						dialog.cancel();
					}
				});
				dlgLiga.show();
			}
		});

		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(getActivity());
		final ArrayList<String> teams = new ArrayList<String>();
		
		btnTeam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btnLiga.getText().toString().equals("Select League...")) {
					noti("Please choose a league!");
					return;
				}
				mCursor = mDB.getTeamByLeague(criteriaLeague);
				mCursor.moveToFirst();
				teams.clear();
				while (!mCursor.isAfterLast()) {
					Team team = new Team(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), mCursor.getString(3));
					teams.add(team.getName());
					mCursor.moveToNext();
				}
				mCursor.close();
				
				final String Team[] = teams.toArray(new String[0]);
				dlgTeam.setItems(Team, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						btnTeam.setText(Team[which]);
						criteriaTeam = btnTeam.getText().toString();
						loadData(criteriaLeague, criteriaTeam, "ALL");
						dialog.cancel();
					}
				});

				dlgTeam.show();
			}
		});

		Resources res = getResources();
		final CharSequence Land[] = res.getStringArray(R.array.countries);
		final AlertDialog.Builder dlgLand = new AlertDialog.Builder(getActivity());
		btnCountry.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dlgLand.setItems(Land, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						btnCountry.setText(Land[which]);
						dialog.cancel();
					}
				});
				dlgLand.show();
			}
		});

		final ArrayList<String> kits = new ArrayList<String>();
		
		final AlertDialog.Builder dlgKit = new AlertDialog.Builder(getActivity());
		btnKitNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (btnTeam.getText().toString().equals("Select Team...")
						|| criteriaTeam.isEmpty()) {
					noti("Please select team!");
					return;
				}
				if (btnLiga.getText().toString().equals("Select League...")
						|| criteriaLeague.isEmpty()) {
					noti("Please select league!");
					return;
				}
				
				mCursor = mDB.getPlayerByLeagueAndTeam(criteriaLeague, criteriaTeam);
				mCursor.moveToFirst();
				final ArrayList<Player> listPlayer = new ArrayList<Player>();
				while (!mCursor.isAfterLast()) {
					Player p = crsToObj(mCursor);
					listPlayer.add(p);
					mCursor.moveToNext();
				}
				mCursor.close();
				kits.clear();
				for (int kitNo = 1; kitNo <= 30; kitNo++) {
					boolean existed = false;
					for (Player player : listPlayer) {
						if(kitNo == player.getKitNo()) {
							existed = true;
							break;
						}
					}
					
					if(!existed) {
						kits.add(String.valueOf(kitNo));
					}
				}
				final String Kits[] = kits.toArray(new String[0]);
				dlgKit.setItems(Kits, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						btnKitNo.setText(Kits[which]);
						dialog.cancel();
					}
				});
				dlgKit.show();
			}
		});

		final AlertDialog.Builder dlgDelCfm = new AlertDialog.Builder(getActivity());
		listPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				
				TextView tvId = (TextView) view.findViewById(R.id.txtPlayerId);
				final int playerId = Integer.parseInt(tvId.getText().toString());

				dlgDelCfm.setTitle("Remove comfirm");
				dlgDelCfm.setMessage("Are you sure?");

				dlgDelCfm.setIcon(R.drawable.ic_launcher);

				dlgDelCfm.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mDB.deletePlayer(playerId);
						loadData(criteriaLeague, criteriaTeam, "ALL");
						dialog.cancel();
					}

				});

				dlgDelCfm.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				if(btnUpdate.getVisibility() != View.VISIBLE) {
					dlgDelCfm.show();
				}

			}
		});
		
		listPlayer.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tvId = (TextView) view.findViewById(R.id.txtPlayerId);
				tvPlayerIdHid.setText(tvId.getText().toString());
				tvPlayerIdHid.setVisibility(View.INVISIBLE);
				mCursor = mDB.getPlayerById(tvId.getText().toString());
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					Player player = crsToObj(mCursor);
					btnLiga.setText(player.getLeague());
					btnTeam.setText(player.getTeam());
					btnCountry.setText(player.getCountry());
					btnKitNo.setText(String.valueOf(player.getKitNo()));
					txtFirstname.setText(player.getFirstname());
					txtLastname.setText(player.getLastname());
					swLineup.setChecked(player.getLineUp() == 1 ? true : false);
					mCursor.moveToNext();
				}
				mCursor.close();
				btnUpdate.setVisibility(View.VISIBLE);
				btnAdd.setVisibility(View.GONE);
				return false;
			}
			
		});
		
		btnUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				StringBuffer sbFN = new StringBuffer();
				sbFN.append(Character.toUpperCase(txtFirstname.getText().toString().charAt(0)));
				sbFN.append(txtFirstname.getText().toString().substring(1));
				String newFirstname = sbFN.toString();
				
				Player player = new Player(newFirstname, 
						txtLastname.getText().toString(), 
						Integer.parseInt(btnKitNo.getText().toString()), 
						btnTeam.getText().toString(), 
						btnCountry.getText().toString(), 
						btnLiga.getText().toString(),
						swLineup.isChecked() ? 1 : 0);
				player.setId(Integer.parseInt(tvPlayerIdHid.getText().toString()));
				
				mDB.updatePlayer(player);
				noti("Player updated!");
				
				btnAdd.setVisibility(View.VISIBLE);
				btnUpdate.setVisibility(View.GONE);
				
				txtLastname.setText("");
				txtFirstname.setText("");
				btnKitNo.setText("Select Kit No...");
				if (!criteriaTeam.equals("Select Team...") && !criteriaLeague.equals("Select League...")) {
					loadData(criteriaLeague, criteriaTeam, "ALL");
				}
			}
		});
		
		swLineup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				criteriaTeam = btnTeam.getText().toString();
				criteriaLeague = btnLiga.getText().toString();
				if ((!criteriaTeam.equals("Select Team...") && !criteriaLeague.equals("Select League..."))
						|| btnUpdate.getVisibility() == View.VISIBLE
						) {
					loadData(criteriaLeague, criteriaTeam, isChecked ? "on" : "sub");
				}
			}
		});
		return rootView;
	}

	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

	private ArrayList<HashMap<String, Object>> loadData(String liga, String team, String lineUp) {
		final ArrayList<HashMap<String, Object>> lstPlayer = new ArrayList<HashMap<String, Object>>();
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lstPlayer, R.layout.list_player_item_row,
				new String[] { "Id", "Firstname", "Lastname", "KitNo", "Country", "Team", "Lineup" },
				new int[] { R.id.txtPlayerId, R.id.txtListFirstname, R.id.txtListLastName, R.id.txtListKitNo,
						R.id.tvLandPlayer, R.id.tvTeamPlayer, R.id.txtLineup });
		mCursor = lineUp.equals("ALL") 
				? mDB.getPlayerByLeagueAndTeam(liga, team)
				: mDB.getPlayerByLeagueAndTeam(liga, team, lineUp.equals("on") ? 1 : 0);
		mCursor.moveToFirst();
		list.clear();
		while (!mCursor.isAfterLast()) {
			Player player = crsToObj(mCursor);
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("Id", String.valueOf(player.getId()));
			temp.put("Firstname", player.getFirstname());
			temp.put("Lastname", player.getLastname());
			temp.put("KitNo", String.valueOf(player.getKitNo()));
			temp.put("League", player.getLeague());
			temp.put("Country", player.getCountry());
			temp.put("Team", player.getTeam());
			temp.put("Lineup", player.getLineUp() == 1 ? "ON" : "SUB");

			lstPlayer.add(temp);
			list.add(temp);
			mCursor.moveToNext();
		}

		listPlayer.setAdapter(adapter);
		mCursor.close();
		
		return lstPlayer;
	}

	private Player crsToObj(Cursor c) {
		Player player = new Player();
		player.setId(c.getInt(0));
		player.setFirstname(c.getString(1));
		player.setLastname(c.getString(2));
		player.setKitNo(c.getInt(3));
		player.setLeague(c.getString(4));
		player.setCountry(c.getString(5));
		player.setTeam(c.getString(6));
		player.setLineUp(c.getInt(7));
		return player;
	}

	void noti(String mes) {
		Toast.makeText(getActivity(), mes, Toast.LENGTH_LONG).show();
	}
}
