package com.project.livescore.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.livescore.data.DBAdapter;
import com.project.livescore1415.Euro2016Activity;
import com.project.livescore1415.R;

public class MainActivity extends Activity {

	ImageButton imgbBL, imgbCL, imgbDFB, imgbWC, imgbDFLS;
	static DBAdapter mDB;

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

		imgbBL.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent news = new Intent(MainActivity.this,
						BundesligaActivity.class);
				onStop();
				startActivity(news);

			}
		});

		imgbDFB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent news = new Intent(MainActivity.this,
						DFBPokalActivity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbCL.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent news = new Intent(MainActivity.this,
						ChampionsLeagueActivity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbWC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent news = new Intent(MainActivity.this,
						Euro2016Activity.class);
				onStop();
				startActivity(news);
			}
		});

		imgbDFLS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent news = new Intent(MainActivity.this,
						DFLSupercupActivity.class);
				onStop();
				startActivity(news);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
					// TODO Auto-generated method stub
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
		} else if (id == R.id.matches) {
			Intent intent = new Intent(MainActivity.this,
					MatchesListActivity.class);
			onStop();
			startActivity(intent);
		} 
		return super.onOptionsItemSelected(item);
	}
	
	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
	}

	private String Intro() {
		// TODO Auto-generated method stub
		String mes = "";
		mes += "Livescore v1.2 App for Galaxy Tab 8.9";
		mes += "\nAuthor : Nigellus Nguyen";
		mes += "\nYear released : 2016";
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
