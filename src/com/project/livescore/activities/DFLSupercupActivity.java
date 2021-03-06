package com.project.livescore.activities;

import java.util.ArrayList;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.Player;
import com.project.livescore1415.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DFLSupercupActivity extends Activity {

	Button btnGoalBVB, btnGoalFCB, btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1, btnPenB2, btnPenB3,
			btnPenB4, btnPenB5;
	TextView txtTeamBVB, txtTeamFCB, txtGoalBVB, txtGoalFCB, tvPenBVB, tvPenFCB, tvPenStage;
	LinearLayout llPen;
	MediaPlayer mp, mpGoal;
	static DBAdapter mDB;
	ImageView imgTrophy, imgFlagBVB, imgFlagFCB;
	DigitalClock digiClock;

	int idGoal = 0;
	int idMiss = 0;
	int idNone = 0;
	Drawable drMiss = null;
	Drawable drGoal = null;
	Drawable drNone = null;

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		saveData();
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		saveData();
		super.onPause();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dflsupercup);

		mDB = new DBAdapter(this);
		mDB.open();

		idGoal = R.drawable.pen_button_goal;
		idMiss = R.drawable.pen_button_miss;
		idNone = R.drawable.pen_button_normal;

		drMiss = getResources().getDrawable(idMiss);
		drGoal = getResources().getDrawable(idGoal);
		drNone = getResources().getDrawable(idNone);

		digiClock = (DigitalClock) this.findViewById(R.id.digitalClock1);
		btnGoalBVB = (Button) this.findViewById(R.id.btnBVBGoal);
		btnGoalFCB = (Button) this.findViewById(R.id.btnFCBGoal);
		txtTeamBVB = (TextView) this.findViewById(R.id.txtBVB);
		txtTeamFCB = (TextView) this.findViewById(R.id.txtFCB);
		txtGoalBVB = (TextView) this.findViewById(R.id.txtBVBScorer);
		txtGoalFCB = (TextView) this.findViewById(R.id.txtFCBScorer);

		tvPenBVB = (TextView) this.findViewById(R.id.tvPenBVB);
		tvPenFCB = (TextView) this.findViewById(R.id.tvPenFCB);
		llPen = (LinearLayout) this.findViewById(R.id.llPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnPenA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnPenA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnPenA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnPenA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnPenA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnPenB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnPenB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnPenB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnPenB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnPenB5);
		tvPenStage = (TextView) this.findViewById(R.id.tvPenStage);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnGoalBVB.setText(pref.getString("btnGoal1", "0"));
		btnGoalFCB.setText(pref.getString("btnGoal2", "0"));
		txtGoalBVB.setText(pref.getString("txtGoal1", null));
		txtGoalFCB.setText(pref.getString("txtGoal2", null));

		tvPenBVB.setText(pref.getString("tvPenBVB", "0"));
		tvPenFCB.setText(pref.getString("tvPenFCB", "0"));
		llPen.setVisibility(pref.getInt("llPenVisible", 8));

		btnPenA1.setBackgroundResource(pref.getInt("PenA1Color", R.drawable.pen_button_normal));
		btnPenA2.setBackgroundResource(pref.getInt("PenA2Color", R.drawable.pen_button_normal));
		btnPenA3.setBackgroundResource(pref.getInt("PenA3Color", R.drawable.pen_button_normal));
		btnPenA4.setBackgroundResource(pref.getInt("PenA4Color", R.drawable.pen_button_normal));
		btnPenA5.setBackgroundResource(pref.getInt("PenA5Color", R.drawable.pen_button_normal));
		btnPenB1.setBackgroundResource(pref.getInt("PenB1Color", R.drawable.pen_button_normal));
		btnPenB2.setBackgroundResource(pref.getInt("PenB2Color", R.drawable.pen_button_normal));
		btnPenB3.setBackgroundResource(pref.getInt("PenB3Color", R.drawable.pen_button_normal));
		btnPenB4.setBackgroundResource(pref.getInt("PenB4Color", R.drawable.pen_button_normal));
		btnPenB5.setBackgroundResource(pref.getInt("PenB5Color", R.drawable.pen_button_normal));

		imgTrophy = (ImageView) this.findViewById(R.id.imgDFLSTrophy);
		imgFlagBVB = (ImageView) this.findViewById(R.id.imgBVB);
		imgFlagFCB = (ImageView) this.findViewById(R.id.imgFCB);
		imgFlagBVB.setImageResource(R.drawable.bvb);
		imgFlagFCB.setImageResource(R.drawable.fcb);

		mp = MediaPlayer.create(this, R.raw.bundesligaintro);
		mpGoal = MediaPlayer.create(this, R.raw.torhymne);

		mp.start();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}

		imgTrophy.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mp.isPlaying())
					mp.pause();
				else
					mp.start();
			}
		});

		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final Resources res = getResources();
		final AlertDialog.Builder dlgScr = new AlertDialog.Builder(this);

		btnGoalBVB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("TOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal.findViewById(R.id.tvGoalFor2);

				final CheckBox chkOG = (CheckBox) dlgGoal.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal.findViewById(R.id.chkPen2);

				tvGoalFor.setText("TOORRRRRR f�r " + txtTeamBVB.getText().toString() + " ");

				btnScorer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final ArrayList<String> lstPlayer = !chkOG.isChecked()
								? getPlayerList(res, txtTeamBVB.getText().toString())
								: getPlayerList(res, txtTeamFCB.getText().toString());
						final String[] Team = lstPlayer.toArray(new String[0]);

						dlgScr.setItems(Team, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								btnScorer.setText(Team[which].substring(Team[which].indexOf("-") + 1));
								dialog.cancel();
							}
						});

						dlgScr.show();
					}

				});
				
				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("") || btnScorer.getText().toString().equals("")) {
							errNoti("Bitte geben Sie den Zeitpunkt und den Torsch�tzer ein!");
						} else {
							int goal = Integer.parseInt(btnGoalBVB.getText().toString());
							btnGoalBVB.setText(String.valueOf(goal + 1).toString());

							String mes = btnScorer.getText() + " " + txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(Et)";
							}
							if (chkPen.isChecked()) {
								mes += "(Elf.)";
							}
							if (txtGoalBVB.getText().toString().contains(btnScorer.getText())) {
								String temp = txtGoalBVB.getText().toString().replace(btnScorer.getText().toString(),
										mes);
								txtGoalBVB.setText(temp);
							} else
								txtGoalBVB.append(mes + "\n");
							dlgGoal.cancel();
						}
					}

				});

				dlgGoal.show();

			}
		});
		
		btnGoalFCB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mpGoal.start();
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("TOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal.findViewById(R.id.tvGoalFor2);

				final CheckBox chkOG = (CheckBox) dlgGoal.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal.findViewById(R.id.chkPen2);

				tvGoalFor.setText("TOORRRRRR f�r " + txtTeamFCB.getText().toString() + " ");

				btnScorer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final ArrayList<String> lstPlayer = !chkOG.isChecked()
								? getPlayerList(res, txtTeamFCB.getText().toString())
								: getPlayerList(res, txtTeamBVB.getText().toString());
						final String[] Team = lstPlayer.toArray(new String[0]);

						dlgScr.setItems(Team, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								btnScorer.setText(Team[which].substring(Team[which].indexOf("-") + 1));
								dialog.cancel();
							}
						});

						dlgScr.show();
					}

				});

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
						if (txtMin.getText().toString().equals("") || btnScorer.getText().toString().equals("")) {
							errNoti("Bitte geben Sie den Zeitpunkt und den Torsch�tzer ein!");
						} else {
							int goal = Integer.parseInt(btnGoalFCB.getText().toString());
							btnGoalFCB.setText(String.valueOf(goal + 1).toString());

							String mes = btnScorer.getText() + " " + txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(Et.)";
							}
							if (chkPen.isChecked()) {
								mes += "(Elf.)";
							}
							if (txtGoalFCB.getText().toString().contains(btnScorer.getText())) {
								String temp = txtGoalFCB.getText().toString().replace(btnScorer.getText().toString(),
										mes);
								txtGoalFCB.setText(temp);
							} else
								txtGoalFCB.append(mes + "\n");
							dlgGoal.cancel();
						}
					}

				});

				dlgGoal.show();

			}
		});

		txtGoalBVB.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (!txtGoalBVB.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoalBVB.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							txtGoalBVB.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
						}
					});

					btnEditCancel.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dlgEdit.cancel();
						}
					});

					dlgEdit.show();
				}
				return false;
			}
		});

		txtGoalFCB.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (!txtGoalFCB.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoalFCB.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							txtGoalFCB.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
						}
					});

					btnEditCancel.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							dlgEdit.cancel();
						}
					});

					dlgEdit.show();
				}
				return false;
			}
		});

		btnPenA1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenA1.getBackground().getConstantState() == drMiss.getConstantState()) {
					int p = Integer.parseInt(tvPenBVB.getText().toString());
					tvPenBVB.setText(String.valueOf(String.valueOf(p + 1).toString()).toString());
					btnPenA1.setBackgroundResource(R.drawable.pen_button_goal);
				} else if (btnPenA1.getBackground().getConstantState() == drNone.getConstantState()) {
					btnPenA1.setBackgroundResource(R.drawable.pen_button_miss);
				}
			}
		});

		btnPenA2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (btnPenA1.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenA2.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenBVB.getText().toString());
						tvPenBVB.setText(String.valueOf(String.valueOf(p + 1).toString()).toString());
						btnPenA2.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA2.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenA2.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (btnPenA1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenA3.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenBVB.getText().toString());
						tvPenBVB.setText(String.valueOf(p + 1).toString());
						btnPenA3.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA3.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenA3.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (btnPenA1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA3.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenA4.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenBVB.getText().toString());
						tvPenBVB.setText(String.valueOf(p + 1).toString());
						btnPenA4.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA4.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenA4.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (btnPenA1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA3.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenA4.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenA5.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenBVB.getText().toString());
						tvPenBVB.setText(String.valueOf(p + 1).toString());
						btnPenA5.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA5.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenA5.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() == drMiss.getConstantState()) {
					int p = Integer.parseInt(tvPenFCB.getText().toString());
					tvPenFCB.setText(String.valueOf(String.valueOf(p + 1).toString()).toString());
					btnPenB1.setBackgroundResource(R.drawable.pen_button_goal);
				} else if (btnPenB1.getBackground().getConstantState() == drNone.getConstantState()) {
					btnPenB1.setBackgroundResource(R.drawable.pen_button_miss);
				}
			}
		});

		btnPenB2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenB2.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenFCB.getText().toString());
						tvPenFCB.setText(String.valueOf(String.valueOf(p + 1).toString()).toString());
						btnPenB2.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB2.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenB2.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenB3.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenFCB.getText().toString());
						tvPenFCB.setText(String.valueOf(p + 1).toString());
						btnPenB3.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB3.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenB3.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB3.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenB4.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenFCB.getText().toString());
						tvPenFCB.setText(String.valueOf(p + 1).toString());
						btnPenB4.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB4.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenB4.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB3.getBackground().getConstantState() != drNone.getConstantState()
						&& btnPenB4.getBackground().getConstantState() != drNone.getConstantState()) {
					if (btnPenB5.getBackground().getConstantState() == drMiss.getConstantState()) {
						int p = Integer.parseInt(tvPenFCB.getText().toString());
						tvPenFCB.setText(String.valueOf(p + 1).toString());
						btnPenB5.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB5.getBackground().getConstantState() == drNone.getConstantState()) {
						btnPenB5.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			mes = team + " - DFL-SUPERCUP-2019 SIEGER!!!";
		} else {
			mes = "Elfmeter!";
		}
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	private void refreshPen() {
		btnPenA1.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenA2.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenA3.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenA4.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenA5.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenB1.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenB2.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenB3.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenB4.setBackgroundResource(R.drawable.pen_button_normal);
		btnPenB5.setBackgroundResource(R.drawable.pen_button_normal);
	}

	private int remainingShot(String team) {
		int res = 0;
		if (team.equalsIgnoreCase("A")) {
			if (btnPenA1.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenA2.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenA3.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenA4.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenA5.getBackground().getConstantState() == drNone.getConstantState())
				res++;
		} else if (team.equalsIgnoreCase("B")) {
			if (btnPenB1.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenB2.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenB3.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenB4.getBackground().getConstantState() == drNone.getConstantState())
				res++;
			if (btnPenB5.getBackground().getConstantState() == drNone.getConstantState())
				res++;
		}
		return res;
	}

	private void saveData() {
		btnGoalBVB = (Button) this.findViewById(R.id.btnBVBGoal);
		btnGoalFCB = (Button) this.findViewById(R.id.btnFCBGoal);
		txtTeamBVB = (TextView) this.findViewById(R.id.txtBVB);
		txtTeamFCB = (TextView) this.findViewById(R.id.txtFCB);
		txtGoalBVB = (TextView) this.findViewById(R.id.txtBVBScorer);
		txtGoalFCB = (TextView) this.findViewById(R.id.txtFCBScorer);

		tvPenBVB = (TextView) this.findViewById(R.id.tvPenBVB);
		tvPenFCB = (TextView) this.findViewById(R.id.tvPenFCB);
		llPen = (LinearLayout) this.findViewById(R.id.llPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnPenA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnPenA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnPenA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnPenA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnPenA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnPenB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnPenB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnPenB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnPenB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnPenB5);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		String BtnTor1 = btnGoalBVB.getText().toString();
		editor.putString("btnGoal1", BtnTor1);
		String BtnTor2 = btnGoalFCB.getText().toString();
		editor.putString("btnGoal2", BtnTor2);
		String TxtTor1 = txtGoalBVB.getText().toString();
		editor.putString("txtGoal1", TxtTor1);
		String TxtTor2 = txtGoalFCB.getText().toString();
		editor.putString("txtGoal2", TxtTor2);

		String PenA = tvPenBVB.getText().toString();
		editor.putString("tvPenA", PenA);
		String PenB = tvPenFCB.getText().toString();
		editor.putString("tvPenB", PenB);
		int llPenVisible = llPen.getVisibility();
		editor.putInt("llPenVisible", llPenVisible);
		int llAggVisible = llPen.getVisibility();
		editor.putInt("llAggVisible", llAggVisible);

		editor.putInt("PenA1Color", getButtonResId(btnPenA1));
		editor.putInt("PenA2Color", getButtonResId(btnPenA2));
		editor.putInt("PenA3Color", getButtonResId(btnPenA3));
		editor.putInt("PenA4Color", getButtonResId(btnPenA4));
		editor.putInt("PenA5Color", getButtonResId(btnPenA5));
		editor.putInt("PenB1Color", getButtonResId(btnPenB1));
		editor.putInt("PenB2Color", getButtonResId(btnPenB2));
		editor.putInt("PenB3Color", getButtonResId(btnPenB3));
		editor.putInt("PenB4Color", getButtonResId(btnPenB4));
		editor.putInt("PenB5Color", getButtonResId(btnPenB5));

		editor.commit();
	}

	private void refresh() {
		txtTeamBVB.setText("BORUSSIA DORTMUND");
		txtTeamFCB.setText("FC BAYERN M�NCHEN");
		txtGoalBVB.setText("");
		txtGoalFCB.setText("");
		btnGoalBVB.setText("0");
		btnGoalFCB.setText("0");
		btnGoalBVB.setEnabled(false);
		btnGoalFCB.setEnabled(false);
		tvPenBVB.setText("0");
		tvPenFCB.setText("0");
		llPen.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		imgFlagBVB.setImageResource(R.drawable.bvb);
		imgFlagFCB.setImageResource(R.drawable.fcb);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dflsupercup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.dflexit) {
			final AlertDialog.Builder dlgExit = new AlertDialog.Builder(this);
			dlgExit.setTitle("Beenden");
			dlgExit.setMessage("Sind Sie sicher?");

			dlgExit.setIcon(R.drawable.ic_launcher);
			dlgExit.setPositiveButton("Ja", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onPause();
					System.exit(0);
				}
			});

			dlgExit.setNegativeButton("Nein", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});

			dlgExit.show();
		} else if (id == R.id.dflrefresh) {
			refresh();
		} else if (id == R.id.dflfulltime) {
			int g1 = Integer.parseInt(btnGoalBVB.getText().toString());
			int g2 = Integer.parseInt(btnGoalFCB.getText().toString());

			if (g1 > g2) {
				resultNoti("BORUSSIA DORTMUND");
				addToDB(g1, g2, 0, 0);
			} else if (g1 < g2) {
				resultNoti("FC BAYERN M�NCHEN");
				addToDB(g1, g2, 0, 0);
			} else {
				handlePSO(g1, g2);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void handlePSO(int g1, int g2) {
		llPen.setVisibility(View.VISIBLE);
		int pA = Integer.parseInt(tvPenBVB.getText().toString());
		int pB = Integer.parseInt(tvPenFCB.getText().toString());
		int stage = Integer.parseInt(tvPenStage.getText().toString());
		if (stage == 0) {
			if (pA > pB && (remainingShot("B") < pA - pB)) {
				resultNoti(txtTeamBVB.getText().toString());
				addToDB(g1, g2, pA, pB);
			} else if (pA < pB && (remainingShot("A") < pB - pA)) {
				resultNoti(txtTeamFCB.getText().toString());
				addToDB(g1, g2, pA, pB);
			} else if (pA == pB) {
				resultNoti("");
				if (remainingShot("A") == 0 && remainingShot("B") == 0) {
					refreshPen();
					stage = Integer.parseInt(tvPenStage.getText().toString());
					tvPenStage.setText(String.valueOf(stage + 1).toString());
				}
			}
		} else {
			if (pA > pB && (remainingShot("A") == remainingShot("B"))) {
				resultNoti("BORUSSIA DORTMUND");
				addToDB(g1, g2, pA, pB);
			} else if (pA < pB && (remainingShot("A") == remainingShot("B"))) {
				resultNoti("FC BAYERN M�NCHEN");
				addToDB(g1, g2, pA, pB);
			} else if (pA == pB) {
				resultNoti("");
				if (remainingShot("A") == 0 && remainingShot("B") == 0) {
					refreshPen();
				}
			}
		}
	}

	int getButtonResId(Button button) {
		if (button.getBackground().getConstantState() == getResources().getDrawable(R.drawable.pen_button_miss)
				.getConstantState()) {
			return R.drawable.pen_button_miss;

		} else if (button.getBackground().getConstantState() == getResources().getDrawable(R.drawable.pen_button_normal)
				.getConstantState()) {
			return R.drawable.pen_button_normal;

		} else if (button.getBackground().getConstantState() == getResources().getDrawable(R.drawable.pen_button_goal)
				.getConstantState()) {
			return R.drawable.pen_button_goal;
		}
		return -1;
	}

	protected ArrayList<String> getPlayerList(final Resources res, String team) {
		Cursor cPlayer;
		final ArrayList<String> lstPlayer = new ArrayList<String>();
		cPlayer = mDB.getPlayerByTeam(team, 1);
		cPlayer.moveToFirst();
		while (!cPlayer.isAfterLast()) {
			Player player = new Player(cPlayer.getString(1), cPlayer.getString(2), cPlayer.getInt(3),
					cPlayer.getString(4), cPlayer.getString(5), "DEFAULT", cPlayer.getInt(6),
					cPlayer.getString(7));
			lstPlayer.add(player.getKitNo() + "-" + player.getLastname());
			cPlayer.moveToNext();
		}
		cPlayer.close();
		return lstPlayer;
	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtGoalBVB.getText().toString();
		String scorerListB = txtGoalFCB.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("DFL-Supercup 2019", "", "BORUSSIA DORTMUND", "FC BAYERN M�NCHEN", g1, g2, scorerListA,
				scorerListB, pA, pB);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_dflsupercup, container, false);
			return rootView;
		}
	}

}
