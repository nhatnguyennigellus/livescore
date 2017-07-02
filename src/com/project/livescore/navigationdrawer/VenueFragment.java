package com.project.livescore.navigationdrawer;

import java.util.ArrayList;
import java.util.HashMap;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.League;
import com.project.livescore.data.Team;
import com.project.livescore.data.Venue;
import com.project.livescore1415.R;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class VenueFragment extends Fragment {
	public VenueFragment() {
	}
	
	EditText txtVenue;
	Button btnAdd, btnCountry, btnLiga;
	ListView listVenue;
	static DBAdapter mDB;
	Cursor mCursor;
	String currentCondition = "";
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static VenueFragment newInstance(int sectionNumber) {
		VenueFragment fragment = new VenueFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_venue, container, false);
        txtVenue = (EditText) rootView.findViewById(R.id.txtVenue);
        btnAdd = (Button) rootView.findViewById(R.id.btnAddVenue);
        btnCountry = (Button) rootView.findViewById(R.id.btnVenueLand);
        btnLiga = (Button) rootView.findViewById(R.id.btnVenueLiga);
        listVenue = (ListView) rootView.findViewById(R.id.listVenue);
        
        mDB = new DBAdapter(getActivity());
		mDB.open();
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(txtVenue.getText().toString().isEmpty()) {
					noti("Please enter a venue (city)!");
					return;
				}
				if(btnCountry.getText().toString().equals("Select Country...")) {
					noti("Please choose a country!");
					return;
				}
				if(btnLiga.getText().toString().equals("Select League...")) {
					noti("Please choose a league!");
					return;
				}
				Venue venue = new Venue();
				venue.setCity(txtVenue.getText().toString().trim());
				venue.setCountry(btnCountry.getText().toString());
				venue.setLeague(btnLiga.getText().toString());
				mDB.addVenue(venue);
				noti("Venue added!");
				txtVenue.setText("");
				if(!currentCondition.equals("Select League..."))	{
					loadData(currentCondition);
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
					public void onClick(DialogInterface dialog,
							int which) {
						btnLiga.setText(Ligen[which]);
						loadData(btnLiga.getText().toString());
						currentCondition = btnLiga.getText().toString();
						dialog.cancel();
					}
				});
				dlgLiga.show();
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
		
		

		final AlertDialog.Builder dlgDelCfm = new AlertDialog.Builder(getActivity());
		listVenue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				TextView tvId = (TextView) view.findViewById(R.id.txtVenueId);
				final int teamId = Integer.parseInt(tvId.getText().toString());
				
				dlgDelCfm.setTitle("Remove comfirm");
				dlgDelCfm.setMessage("Are you sure?");

				dlgDelCfm.setIcon(R.drawable.ic_launcher);

				dlgDelCfm.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mDB.deleteVenue(teamId);
						loadData(currentCondition);
						dialog.cancel();
					}

				});

				dlgDelCfm.setNegativeButton("No", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				dlgDelCfm.show();
				
				
			}
		});
		return rootView;
	}
	
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private void loadData(String liga) {
		final ArrayList<HashMap<String, String>> lstVenue = new ArrayList<HashMap<String, String>>();
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lstVenue,
				R.layout.list_venue_item_row, new String[] { "Id", "City", "Country" }, 
				new int[] { R.id.txtVenueId, R.id.tvVenue, R.id.tvVenueLand});
		mCursor = mDB.getVenueByLeague(liga);
		mCursor.moveToFirst();
		list.clear();
		while (!mCursor.isAfterLast()) {
			Team team = crsToObj(mCursor);
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("Id", String.valueOf(team.getId()));
			temp.put("City", team.getName());
			temp.put("Country", team.getCountry());
			temp.put("League", team.getLiga());
			
			lstVenue.add(temp);
			list.add(temp);
			mCursor.moveToNext();
		}
		
		listVenue.setAdapter(adapter);
		mCursor.close();
	}
	
	private Team crsToObj(Cursor c) {
		Team team = new Team();
		team.setId(c.getInt(0));
		team.setName(c.getString(1));
		team.setCountry(c.getString(2));
		team.setLiga(c.getString(3));
		return team;
	}
	
	void noti(String mes) {
		Toast.makeText(getActivity(), mes, Toast.LENGTH_LONG).show();
	}
}
