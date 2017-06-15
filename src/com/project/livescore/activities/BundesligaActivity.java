package com.project.livescore.activities;

import com.project.livescore.data.DBAdapter;
import com.project.livescore1415.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BundesligaActivity extends Activity {

	Button btnSpieltag, btnTor1, btnTor2;
	TextView txtTeam1, txtTeam2, txtTor1, txtTor2;
	MediaPlayer mp;
	ImageView imgLogo;
	static DBAdapter mDB;
	MenuItem miDB;

	MediaPlayer mpGoal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bundesliga);

		mDB = new DBAdapter(this);
		mDB.open();

		imgLogo = (ImageView) this.findViewById(R.id.imgSilverware);
		btnSpieltag = (Button) this.findViewById(R.id.btnRound);
		btnTor1 = (Button) this.findViewById(R.id.btnBLGoal1);
		btnTor2 = (Button) this.findViewById(R.id.btnBLGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtBLTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtBLTeam2);
		txtTor1 = (TextView) this.findViewById(R.id.txtBLGoal1);
		txtTor2 = (TextView) this.findViewById(R.id.txtBLGoal2);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnSpieltag.setText(pref.getString("btnSpieltag", "Spieltag"));
		btnTor1.setText(pref.getString("btnTor1", "0"));
		btnTor2.setText(pref.getString("btnTor2", "0"));
		txtTor1.setText(pref.getString("txtTor1", null));
		txtTor2.setText(pref.getString("txtTor2", null));
		txtTeam1.setText(pref.getString("TeamBL1", null));
		txtTeam2.setText(pref.getString("TeamBL2", null));

		mpGoal = MediaPlayer.create(this, R.raw.torhymne);
		mp = MediaPlayer.create(this, R.raw.bundesligaintro);
		mp.start();
		Resources res = getResources();

		final CharSequence Spieltag[] = res.getStringArray(R.array.spieltag);
		final CharSequence Team[] = res.getStringArray(R.array.bundesliga);

		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final AlertDialog.Builder dlgSpl = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(this);

		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mp.isPlaying())
					mp.pause();
				else
					mp.start();
			}
		});

		btnSpieltag.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dlgRnd.setContentView(R.layout.spieltag);
				dlgRnd.setTitle("Spielinfo");

				Button btnOK = (Button) dlgRnd.findViewById(R.id.btnSplOK);
				Button btnCancel = (Button) dlgRnd
						.findViewById(R.id.btnSplCancel);

				final Button btnSpl = (Button) dlgRnd.findViewById(R.id.btnSpl);
				final Button btnTeam1 = (Button) dlgRnd
						.findViewById(R.id.btnSplTeam1);
				final Button btnTeam2 = (Button) dlgRnd
						.findViewById(R.id.btnSplTeam2);

				btnSpl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgSpl.setItems(Spieltag, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnSpl.setText(Spieltag[which] + ". Spieltag");
								dialog.cancel();
							}
						});
						dlgSpl.show();
					}
				});

				btnTeam1.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgTeam.setItems(Team, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (!Team[which].equals(btnTeam2.getText())) {
									// TODO Auto-generated method stub
									btnTeam1.setText(Team[which]);
									dialog.cancel();
								} else {
									errNoti("Bitte ein anderes Team auswählen");
								}
							}
						});
						dlgTeam.show();
					}
				});

				btnTeam2.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgTeam.setItems(Team, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								if (!Team[which].equals(btnTeam1.getText())) {
									// TODO Auto-generated method stub
									btnTeam2.setText(Team[which]);
									dialog.cancel();
								} else {
									errNoti("Bitte ein anderes Team auswählen");
								}
							}
						});
						dlgTeam.show();
					}
				});

				btnOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (btnSpl.getText().toString().equals("Spieltag")
								|| btnTeam1.getText().toString().equals("Heim")
								|| btnTeam2.getText().toString()
										.equals("Auswärt")) {
							// TODO Auto-generated method stub
							errNoti("Bitte Teams und Spieltag auswählen");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							btnSpieltag.setText(btnSpl.getText());
							btnTor1.setEnabled(true);
							btnTor2.setEnabled(true);
							btnSpieltag.setClickable(false);
							dlgRnd.cancel();
						}
					}
				});

				btnCancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgRnd.cancel();
					}
				});
				dlgRnd.show();
			}

		});

		btnTor1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!txtTeam2.getText().toString()
						.equalsIgnoreCase("FC Bayern München")) {
					mpGoal.start();
				}
				dlgGoal.setContentView(R.layout.goalalert);
				dlgGoal.setTitle("TOOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal
						.findViewById(R.id.txtMinute);
				final EditText txtScorer = (EditText) dlgGoal
						.findViewById(R.id.txtScorerName);
				final Button btnGoalOK = (Button) dlgGoal
						.findViewById(R.id.btnGoalOK);
				final Button btnGoalCnl = (Button) dlgGoal
						.findViewById(R.id.btnGoalCancel);
				final TextView tvGoalFor = (TextView) dlgGoal
						.findViewById(R.id.tvGoalFor);
				final CheckBox chkOG = (CheckBox) dlgGoal
						.findViewById(R.id.chkOG);
				final CheckBox chkPen = (CheckBox) dlgGoal
						.findViewById(R.id.chkPen);

				tvGoalFor.setText("TOORRRR für " + txtTeam1.getText() + " ");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mpGoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							errNoti("Bitte die Minute und den Torschützer eingeben!");
						} else {
							int goal = Integer.parseInt(btnTor1.getText()
									.toString());
							btnTor1.setText(String.valueOf(goal + 1).toString());
							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(E.t.)";
							}
							if (chkPen.isChecked()) {
								mes += "(E.m)";
							}
							if (txtTor1.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtTor1
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtTor1.setText(temp);
							} else
								txtTor1.append(mes + "\n");

							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeam1.getText().toString().equals("")
						&& !txtTeam2.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		btnTor2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!txtTeam1.getText().toString()
						.equalsIgnoreCase("FC Bayern München")) {
					mpGoal.start();
				}
				dlgGoal.setContentView(R.layout.goalalert);
				dlgGoal.setTitle("TOOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal
						.findViewById(R.id.txtMinute);
				final EditText txtScorer = (EditText) dlgGoal
						.findViewById(R.id.txtScorerName);
				final Button btnGoalOK = (Button) dlgGoal
						.findViewById(R.id.btnGoalOK);
				final Button btnGoalCnl = (Button) dlgGoal
						.findViewById(R.id.btnGoalCancel);
				final TextView tvGoalFor = (TextView) dlgGoal
						.findViewById(R.id.tvGoalFor);
				final CheckBox chkOG = (CheckBox) dlgGoal
						.findViewById(R.id.chkOG);
				final CheckBox chkPen = (CheckBox) dlgGoal
						.findViewById(R.id.chkPen);

				tvGoalFor.setText("TOORRRR für " + txtTeam2.getText() + " ");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mpGoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							errNoti("Bitte die Minute und den Torschützer eingeben!");
						} else {
							int goal = Integer.parseInt(btnTor2.getText()
									.toString());
							btnTor2.setText(String.valueOf(goal + 1).toString());
							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(E.t.)";
							}
							if (chkPen.isChecked()) {
								mes += "(E.m)";
							}
							if (txtTor2.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtTor2
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtTor2.setText(temp);
							} else
								txtTor2.append(mes + "\n");
							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeam1.getText().toString().equals("")
						&& !txtTeam2.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		txtTor1.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtTor1.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtTor1.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtTor1.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
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
				}
				return false;
			}
		});

		txtTor2.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtTor2.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtTor2.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtTor2.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
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
				}
				return false;
			}
		});
	}

	private void refresh() {
		txtTeam1.setText("");
		txtTeam2.setText("");
		txtTor1.setText("");
		txtTor2.setText("");
		btnSpieltag.setText("Spieltag");
		btnSpieltag.setClickable(true);
		btnTor1.setText("0");
		btnTor2.setText("0");
		miDB.setVisible(false);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		btnSpieltag = (Button) this.findViewById(R.id.btnRound);
		btnTor1 = (Button) this.findViewById(R.id.btnBLGoal1);
		btnTor2 = (Button) this.findViewById(R.id.btnBLGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtBLTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtBLTeam2);
		txtTor1 = (TextView) this.findViewById(R.id.txtBLGoal1);
		txtTor2 = (TextView) this.findViewById(R.id.txtBLGoal2);

		String spieltag = btnSpieltag.getText().toString();
		editor.putString("btnSpieltag", spieltag);
		String BtnTor1 = btnTor1.getText().toString();
		editor.putString("btnTor1", BtnTor1);
		String BtnTor2 = btnTor2.getText().toString();
		editor.putString("btnTor2", BtnTor2);
		String TxtTeam1 = txtTeam1.getText().toString();
		editor.putString("TeamBL1", TxtTeam1);
		String TxtTeam2 = txtTeam2.getText().toString();
		editor.putString("TeamBL2", TxtTeam2);
		String TxtTor1 = txtTor1.getText().toString();
		editor.putString("txtTor1", TxtTor1);
		String TxtTor2 = txtTor2.getText().toString();
		editor.putString("txtTor2", TxtTor2);

		editor.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bundesliga, menu);
		miDB = menu.findItem(R.id.savedb);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.exit) {
			final AlertDialog.Builder dlgExit = new AlertDialog.Builder(this);
			dlgExit.setTitle("Beenden");
			dlgExit.setMessage("Sind Sie sicher?");

			dlgExit.setIcon(R.drawable.ic_launcher);
			dlgExit.setPositiveButton("Ja", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					onPause();
					System.exit(0);
				}
			});

			dlgExit.setNegativeButton("Nein", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			dlgExit.show();
		} else if (id == R.id.refresh) {
			refresh();
		} else if (id == R.id.fulltime
				&& !btnSpieltag.getText().toString().equals("Spieltag")) {
			int g1 = Integer.parseInt(btnTor1.getText().toString());
			int g2 = Integer.parseInt(btnTor2.getText().toString());

			if (g1 > g2) {
				resultNoti(txtTeam1.getText().toString());
			} else if (g1 < g2) {
				resultNoti(txtTeam2.getText().toString());
			} else
				resultNoti("");
			miDB.setVisible(true);
		} else if (id == R.id.savedb) {
			int g1 = Integer.parseInt(btnTor1.getText().toString());
			int g2 = Integer.parseInt(btnTor2.getText().toString());
			addToDB(g1, g2, 0, 0);
			errNoti("Ergebnis gespeichert!");
		}

		return super.onOptionsItemSelected(item);
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
			View rootView = inflater.inflate(R.layout.fragment_bundesliga,
					container, false);
			return rootView;
		}
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals(""))
			mes = team + " gewinnt dieses Spiel!!!";
		else {
			mes = "Das Spiel ist unentschieden";
		}
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtTor1.getText().toString();
		String scorerListB = txtTor2.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("Deutsche Bundesliga", btnSpieltag.getText().toString(),
				txtTeam1.getText().toString(), txtTeam2.getText().toString(),
				g1, g2, scorerListA, scorerListB, pA, pB);
	}
}
