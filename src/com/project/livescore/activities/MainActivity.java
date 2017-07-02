package com.project.livescore.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.project.livescore.data.Champs;
import com.project.livescore.data.DBAdapter;
import com.project.livescore1415.AdminActivity;
import com.project.livescore1415.ConfedCup2017Activity;
import com.project.livescore1415.Euro2016Activity;
import com.project.livescore1415.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageButton imgbBL, imgbCL, imgbDFB, imgbWC, imgbDFLS, imgbCfC, imgbEuro;
	static DBAdapter mDB;
	Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDB = new DBAdapter(this);
		mDB.open();


		imgbBL = (ImageButton) this.findViewById(R.id.imgbBL);
		imgbCL = (ImageButton) this.findViewById(R.id.imgbCL);
		imgbWC = (ImageButton) this.findViewById(R.id.imgbWC);
		imgbDFB = (ImageButton) this.findViewById(R.id.imgbDFB);
		imgbDFLS = (ImageButton) this.findViewById(R.id.imgbDFLS);
		imgbCfC = (ImageButton) this.findViewById(R.id.imgbCfC);
		imgbEuro = (ImageButton) this.findViewById(R.id.imgbEuro);

		Resources res = getResources();
		final CharSequence Champ[] = res.getStringArray(R.array.champs);
		
		imgbBL.setVisibility(setVisible(Champ[0].toString()));
		imgbDFB.setVisibility(setVisible(Champ[1].toString()));
		imgbDFLS.setVisibility(setVisible(Champ[2].toString()));
		imgbCL.setVisibility(setVisible(Champ[3].toString()));
		imgbEuro.setVisibility(setVisible(Champ[4].toString()));
		imgbWC.setVisibility(setVisible(Champ[6].toString()));
		imgbCfC.setVisibility(setVisible(Champ[7].toString()));
		
		imgbBL.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						BundesligaActivity.class);
				onStop();
				startActivity(news);

			}
		});

		imgbDFB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						DFBPokalActivity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbCL.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						ChampionsLeagueActivity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbWC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						WorldCup2014Activity.class);
				onStop();
				startActivity(news);
			}
		});
		
		imgbCfC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						ConfedCup2017Activity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbDFLS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						DFLSupercupActivity.class);
				onStop();
				startActivity(news);
			}
		});
		
		imgbEuro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent news = new Intent(MainActivity.this,
						Euro2016Activity.class);
				onStop();
				startActivity(news);
			}
		});
	}
	
	private Champs crsToObj(Cursor c) {
		Champs ch = new Champs();
		ch.setName(c.getString(0));
		ch.setOn(c.getInt(1) == 0 ? false : true);
		
		return ch;
	}

	private int setVisible(String champ) {
		mCursor = mDB.getChamp(champ);
		mCursor.moveToFirst();
		while (!mCursor.isAfterLast()) {
			Champs ch = crsToObj(mCursor);
			return ch.isOn() ? View.VISIBLE : View.GONE;
		}
		
		
		return View.GONE;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Resources res = getResources();
		final CharSequence Champ[] = res.getStringArray(R.array.champs);
		
		imgbBL.setVisibility(setVisible(Champ[0].toString()));
		imgbDFB.setVisibility(setVisible(Champ[1].toString()));
		imgbDFLS.setVisibility(setVisible(Champ[2].toString()));
		imgbCL.setVisibility(setVisible(Champ[3].toString()));
		imgbWC.setVisibility(setVisible(Champ[6].toString()));
		imgbCfC.setVisibility(setVisible(Champ[7].toString()));
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.about) {
			String mes = Intro();
			final AlertDialog.Builder dlgAbout = new AlertDialog.Builder(this);
			dlgAbout.setTitle("About App");
			dlgAbout.setMessage(mes);
			dlgAbout.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dlgAbout.show();
		} else if (id == R.id.exit) {
			final AlertDialog.Builder dlgExit = new AlertDialog.Builder(this);
			dlgExit.setTitle("Exit Livescore");
			dlgExit.setMessage("Are you sure?");

			dlgExit.setIcon(R.drawable.ic_launcher);
			dlgExit.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			});

			dlgExit.setNegativeButton("No", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			dlgExit.show();
		} else if (id == R.id.matches) {
			Intent intent = new Intent(MainActivity.this,
					MatchesListActivity.class);
			onStop();
			startActivity(intent);
		} else if (id == R.id.admin) {
			Intent news = new Intent(MainActivity.this,
					AdminActivity.class);
			onStop();
			startActivity(news);
		}
		return super.onOptionsItemSelected(item);
	}
	
	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
	}

	private String Intro() {
		String mes = "";
		mes += "Livescore v1.4 App for Galaxy Tab A";
		mes += "\nAuthor : Nigellus Nguyen";
		mes += "\nYear released : 2017";
		return mes;
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
