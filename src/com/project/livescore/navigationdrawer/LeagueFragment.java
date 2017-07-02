package com.project.livescore.navigationdrawer;
import java.util.ArrayList;
import java.util.HashMap;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.League;
import com.project.livescore1415.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class LeagueFragment extends Fragment {
	public LeagueFragment() {
	}
	
	EditText txtLiga;
	Button btnAdd, btnLigaDel;
	ListView listLiga;
	static DBAdapter mDB;
	Cursor mCursor;
	
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static LeagueFragment newInstance(int sectionNumber) {
		LeagueFragment fragment = new LeagueFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_league, container, false);
        btnAdd = (Button) rootView.findViewById(R.id.btnAddLiga);
        txtLiga = (EditText) rootView.findViewById(R.id.txtLiga);
        listLiga = (ListView) rootView.findViewById(R.id.listLiga);
        
        mDB = new DBAdapter(getActivity());
		mDB.open();
        loadData();
        
        btnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(txtLiga.getText().toString().isEmpty()) {
					noti("Please enter league name!");
					return;
				}
				League liga = new League();
				liga.setName(txtLiga.getText().toString().trim());
				mDB.addLeague(liga);
				noti("League added!");
				txtLiga.setText("");
				loadData();
			}
		});
        
        final AlertDialog.Builder dlgDelCfm = new AlertDialog.Builder(getActivity());
        listLiga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				TextView tvId = (TextView) view.findViewById(R.id.txtLigaId);
				final int matchId = Integer.parseInt(tvId.getText().toString());
				
				dlgDelCfm.setTitle("Remove comfirm");
				dlgDelCfm.setMessage("Are you sure?");

				dlgDelCfm.setIcon(R.drawable.ic_launcher);

				dlgDelCfm.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						mDB.deleteLeague(matchId);
						loadData();
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
        
        return rootView;
    }
	
	private League crsToObj(Cursor c) {
		League liga = new League();
		liga.setId(c.getInt(0));
		liga.setName(c.getString(1));
		
		return liga;
	}
	
	private void loadData() {
		final ArrayList<HashMap<String, String>> lstLiga = new ArrayList<HashMap<String, String>>();
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lstLiga,
				R.layout.list_liga_item_row, new String[] { "Id", "Name" }, 
				new int[] { R.id.txtLigaId, R.id.txtChamps});
		mCursor = mDB.getAllLeagues();
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			League liga = crsToObj(mCursor);
			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("Id", String.valueOf(liga.getId()));
			temp.put("Name", liga.getName());
			
			lstLiga.add(temp);
			mCursor.moveToNext();
		}
		
		listLiga.setAdapter(adapter);
		mCursor.close();
	}
	
	void noti(String mes) {
		Toast.makeText(getActivity(), mes, Toast.LENGTH_LONG).show();
	}
}
