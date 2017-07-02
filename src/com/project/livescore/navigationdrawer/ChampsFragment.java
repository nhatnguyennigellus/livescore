package com.project.livescore.navigationdrawer;
import java.util.ArrayList;
import java.util.HashMap;

import com.project.livescore.data.Champs;
import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.League;
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
import android.widget.ToggleButton;

public class ChampsFragment extends Fragment {
	public ChampsFragment() {
	}
	
	ListView listChamps;
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
	public static ChampsFragment newInstance(int sectionNumber) {
		ChampsFragment fragment = new ChampsFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		updateData();
	}

	private void updateData() {
		Resources res = getResources();
		final CharSequence Champ[] = res.getStringArray(R.array.champs);
		
		for (int i = 0; i < Champ.length; i++) {
			View childView = listChamps.getChildAt(i);
			ToggleButton tglChamp = (ToggleButton) childView.findViewById(R.id.tglChamp);
			Champs ch = new Champs();
			ch.setName(Champ[i].toString());
			ch.setOn(tglChamp.isChecked());
			mDB.updateChamp(ch);
		}
		
		loadData();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_champs, container, false);
        listChamps = (ListView) rootView.findViewById(R.id.listChamps);
        
        mDB = new DBAdapter(getActivity());
		mDB.open();
        loadData();
        
        return rootView;
    }
	
	private Champs crsToObj(Cursor c) {
		Champs ch = new Champs();
		ch.setName(c.getString(0));
		ch.setOn(c.getInt(1) == 0 ? false : true);
		
		return ch;
	}
	
	private void loadData() {
		final ArrayList<HashMap<String, Object>> lstLiga = new ArrayList<HashMap<String, Object>>();
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), lstLiga,
				R.layout.list_champs_item_row, new String[] { "Name", "On" }, 
				new int[] { R.id.txtChamps, R.id.tglChamp});
		mCursor = mDB.getAllChamp();
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			Champs ch = crsToObj(mCursor);
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("Name", ch.getName());
			temp.put("On", ch.isOn());
			
			lstLiga.add(temp);
			mCursor.moveToNext();
		}
		
		if(lstLiga.isEmpty()) {

			Resources res = getResources();
			final CharSequence Champ[] = res.getStringArray(R.array.champs);
			for (int i = 0; i < Champ.length; i++) {
				Champs ch = new Champs();
				ch.setName(Champ[i].toString());
				ch.setOn(false);
				mDB.addChamp(ch);
			}
			
			loadData();
			return;
		}
		
		listChamps.setAdapter(adapter);
		mCursor.close();
	}
	
	void noti(String mes) {
		Toast.makeText(getActivity(), mes, Toast.LENGTH_LONG).show();
	}
}
