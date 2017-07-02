package com.project.livescore.activities;

import java.util.ArrayList;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.Team;
import com.project.livescore1415.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DFBPokalActivity extends Activity {

	Button btnRunde, btnTor1, btnTor2;
	Button btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1,
			btnPenB2, btnPenB3, btnPenB4, btnPenB5;
	TextView txtTeam1, txtTeam2, txtTor1, txtTor2, tvPenA, tvPenB, tvPenStage;
	LinearLayout llPen;
	MenuItem miDB;
	MediaPlayer mpGoal;

	int idGoal = 0;
	int idMiss = 0;
	int idNone = 0;
	Drawable drMiss = null;
	Drawable drGoal = null;
	Drawable drNone = null;
	static DBAdapter mDB;
	Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dfbpokal);

		mDB = new DBAdapter(this);
		mDB.open();

		idGoal = R.drawable.pen_button_goal;
		idMiss = R.drawable.pen_button_miss;
		idNone = R.drawable.pen_button_normal;

		drMiss = getResources().getDrawable(idMiss);
		drGoal = getResources().getDrawable(idGoal);
		drNone = getResources().getDrawable(idNone);

		btnRunde = (Button) this.findViewById(R.id.btnDFBRunde);
		btnTor1 = (Button) this.findViewById(R.id.btnDFBGoal1);
		btnTor2 = (Button) this.findViewById(R.id.btnDFBGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtDFBTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtDFBTeam2);
		txtTor1 = (TextView) this.findViewById(R.id.txtDFBGoal1);
		txtTor2 = (TextView) this.findViewById(R.id.txtDFBGoal2);

		tvPenA = (TextView) this.findViewById(R.id.tvDFBPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvDFBPenB);
		llPen = (LinearLayout) this.findViewById(R.id.llDFBPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnDFBPenA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnDFBPenA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnDFBPenA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnDFBPenA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnDFBPenA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnDFBPenB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnDFBPenB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnDFBPenB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnDFBPenB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnDFBPenB5);
		tvPenStage = (TextView) this.findViewById(R.id.tvPenStage);
		
		mpGoal = MediaPlayer.create(this, R.raw.torhymne);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnRunde.setText(pref.getString("btnRunde", "Runde"));
		btnTor1.setText(pref.getString("btnTor1", "0"));
		btnTor2.setText(pref.getString("btnTor2", "0"));
		txtTor1.setText(pref.getString("txtTor1", null));
		txtTor2.setText(pref.getString("txtTor2", null));
		txtTeam1.setText(pref.getString("TeamDFB1", null));
		txtTeam2.setText(pref.getString("TeamDFB2", null));

		tvPenA.setText(pref.getString("tvPenA", "0"));
		tvPenB.setText(pref.getString("tvPenB", "0"));
		llPen.setVisibility(pref.getInt("llPenVisible", 8));

		btnPenA1.setBackgroundResource(pref.getInt("PenA1Color",
				R.drawable.pen_button_normal));
		btnPenA2.setBackgroundResource(pref.getInt("PenA2Color",
				R.drawable.pen_button_normal));
		btnPenA3.setBackgroundResource(pref.getInt("PenA3Color",
				R.drawable.pen_button_normal));
		btnPenA4.setBackgroundResource(pref.getInt("PenA4Color",
				R.drawable.pen_button_normal));
		btnPenA5.setBackgroundResource(pref.getInt("PenA5Color",
				R.drawable.pen_button_normal));
		btnPenB1.setBackgroundResource(pref.getInt("PenB1Color",
				R.drawable.pen_button_normal));
		btnPenB2.setBackgroundResource(pref.getInt("PenB2Color",
				R.drawable.pen_button_normal));
		btnPenB3.setBackgroundResource(pref.getInt("PenB3Color",
				R.drawable.pen_button_normal));
		btnPenB4.setBackgroundResource(pref.getInt("PenB4Color",
				R.drawable.pen_button_normal));
		btnPenB5.setBackgroundResource(pref.getInt("PenB5Color",
				R.drawable.pen_button_normal));

		final Resources res = getResources();

		final CharSequence Runde[] = res.getStringArray(R.array.runde);
		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final AlertDialog.Builder dlgSpl = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(this);

		btnRunde.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
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

				TextView tvRundeAuswln = (TextView) dlgRnd
						.findViewById(R.id.tvRoundChoose);

				tvRundeAuswln.setText("Runde auswählen");
				btnSpl.setText("Runde");

				btnSpl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgSpl.setItems(Runde, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								btnSpl.setText(Runde[which]);
								dialog.cancel();
							}
						});
						dlgSpl.show();
					}
				});

				final ArrayList<String> lstTeam = new ArrayList<String>();
				mCursor = mDB.getTeamByLeague(res.getString(R.string.dfb));
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					Team team = new Team();
					team.setName(mCursor.getString(1));
					
					lstTeam.add(team.getName());
					mCursor.moveToNext();
				}
				mCursor.close();
				final String Team[] = lstTeam.toArray(new String[0]);
				btnTeam1.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgTeam.setItems(Team, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (!Team[which].equals(btnTeam2.getText())) {
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
						dlgTeam.setItems(Team, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (!Team[which].equals(btnTeam1.getText())) {
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
						if (btnSpl.getText().toString().equals("Runde")
								|| btnTeam1.getText().toString().equals("Heim")
								|| btnTeam2.getText().toString()
										.equals("Auswärt")) {
							errNoti("Bitte Teams und Spieltag auswählen");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							btnRunde.setText(btnSpl.getText());
							btnTor1.setEnabled(true);
							btnTor2.setEnabled(true);
							btnRunde.setClickable(false);
							dlgRnd.cancel();
						}
					}
				});

				btnCancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgRnd.cancel();
					}
				});
				dlgRnd.show();
			}
		});

		btnTor1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (txtTeam1.getText().toString()
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
						int min = 0;
						if(!txtMin.getText().toString().isEmpty()
								&& !txtMin.getText().toString().startsWith("45+")
								&& !txtMin.getText().toString().startsWith("90+")
								&& !txtMin.getText().toString().startsWith("105+")
								&& !txtMin.getText().toString().startsWith("120+")) {
							min = Integer.parseInt(txtMin.getText().toString());
						}
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							errNoti("Bitte die Minute und den Torschützer eingeben!");
						} else if (min < 0 || min > 120) {
							errNoti("Ungültige Nummer!");
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
				if (!txtTeam1.getText().equals("FC Bayern München")) {
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
						int min = 0;
						if(!txtMin.getText().toString().isEmpty()
								&& !txtMin.getText().toString().startsWith("45+")
								&& !txtMin.getText().toString().startsWith("90+")
								&& !txtMin.getText().toString().startsWith("105+")
								&& !txtMin.getText().toString().startsWith("120+")) {
							min = Integer.parseInt(txtMin.getText().toString());
						}
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							errNoti("Bitte die Minute und den Torschützer eingeben!");
						}
						else if (min < 0 || min > 120) {
							errNoti("Ungültige Nummer!");
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
				if (!txtTeam2.getText().toString()
						.equalsIgnoreCase("FC Bayern München")) {
					mpGoal.start();
				}
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

		final int idGoal = R.drawable.pen_button_goal;
		final int idMiss = R.drawable.pen_button_miss;
		final int idNone = R.drawable.pen_button_normal;
		final Drawable drMiss = getResources().getDrawable(idMiss);
		final Drawable drGoal = getResources().getDrawable(idGoal);
		final Drawable drNone = getResources().getDrawable(idNone);

		btnPenA1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (btnPenA1.getBackground().getConstantState() == drMiss
						.getConstantState()) {
					int p = Integer.parseInt(tvPenA.getText().toString());
					tvPenA.setText(String.valueOf(
							String.valueOf(p + 1).toString()).toString());
					btnPenA1.setBackgroundResource(R.drawable.pen_button_goal);
				} else if (btnPenA1.getBackground().getConstantState() == drNone
						.getConstantState()) {

					btnPenA1.setBackgroundResource(R.drawable.pen_button_miss);
				}
			}
		});

		btnPenA2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenA1.getBackground().getConstantState() != drNone
						.getConstantState()) {
					if (btnPenA2.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(
								String.valueOf(p + 1).toString()).toString());
						btnPenA2.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA2.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenA2.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenA1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenA3.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA3.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA3.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenA3.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenA1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenA3.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenA4.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA4.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA4.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenA4.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenA5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenA1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenA2.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenA3.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenA4.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenA5.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA5.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenA5.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenA5.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (btnPenB1.getBackground().getConstantState() == drMiss
						.getConstantState()) {
					int p = Integer.parseInt(tvPenB.getText().toString());
					tvPenB.setText(String.valueOf(
							String.valueOf(p + 1).toString()).toString());
					btnPenB1.setBackgroundResource(R.drawable.pen_button_goal);
				} else if (btnPenB1.getBackground().getConstantState() == drNone
						.getConstantState()) {

					btnPenB1.setBackgroundResource(R.drawable.pen_button_miss);
				}
			}
		});

		btnPenB2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone
						.getConstantState()) {
					if (btnPenB2.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(
								String.valueOf(p + 1).toString()).toString());
						btnPenB2.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB2.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenB2.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenB3.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB3.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB3.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenB3.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenB3.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenB4.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB4.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB4.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenB4.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});

		btnPenB5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btnPenB1.getBackground().getConstantState() != drNone
						.getConstantState()
						&& btnPenB2.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenB3.getBackground().getConstantState() != drNone
								.getConstantState()
						&& btnPenB4.getBackground().getConstantState() != drNone
								.getConstantState()) {
					if (btnPenB5.getBackground().getConstantState() == drMiss
							.getConstantState()) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB5.setBackgroundResource(R.drawable.pen_button_goal);
					} else if (btnPenB5.getBackground().getConstantState() == drNone
							.getConstantState()) {
						btnPenB5.setBackgroundResource(R.drawable.pen_button_miss);
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dfbpokal, menu);
		miDB = menu.findItem(R.id.dfbsavedb);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.dfbexit) {
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
		} else if (id == R.id.dfbrefresh) {
			refresh();
		} else if (id == R.id.dfbsavedb) {

			int g1 = Integer.parseInt(btnTor1.getText().toString());
			int g2 = Integer.parseInt(btnTor2.getText().toString());

			if (g1 > g2) {
				addToDB(g1, g2, 0, 0);
			} else if (g1 < g2) {
				addToDB(g1, g2, 0, 0);
			} else {
				int pA = Integer.parseInt(tvPenA.getText().toString());
				int pB = Integer.parseInt(tvPenB.getText().toString());
				addToDB(g1, g2, pA, pB);
			}
			errNoti("Ergebnis gespeichert!");
		} else if (id == R.id.dfbfulltime
				&& !btnRunde.getText().toString().equals("Runde")) {

			int g1 = Integer.parseInt(btnTor1.getText().toString());
			int g2 = Integer.parseInt(btnTor2.getText().toString());

			if (g1 > g2) {
				resultNoti(txtTeam1.getText().toString());

				miDB.setVisible(true);
			} else if (g1 < g2) {
				resultNoti(txtTeam2.getText().toString());

				miDB.setVisible(true);
			} else {
				llPen.setVisibility(View.VISIBLE);
				int pA = Integer.parseInt(tvPenA.getText().toString());
				int pB = Integer.parseInt(tvPenB.getText().toString());
				int stage = Integer.parseInt(tvPenStage.getText().toString());
				if (stage == 0) {
					if (pA > pB && (remainingShot("B") < pA - pB)) {
						resultNoti(txtTeam1.getText().toString());
						miDB.setVisible(true);
					} else if (pA < pB && (remainingShot("A") < pB - pA)) {
						resultNoti(txtTeam2.getText().toString());
						miDB.setVisible(true);
					} else if (pA == pB) {
						resultNoti("");
						if (remainingShot("A") == 0 && remainingShot("B") == 0) {
							refreshPen();
							stage = Integer.parseInt(tvPenStage.getText()
									.toString());
							tvPenStage.setText(String.valueOf(stage + 1)
									.toString());
						}
					}
				} else {
					if (pA > pB && (remainingShot("A") == remainingShot("B"))) {
						resultNoti(txtTeam1.getText().toString());
						miDB.setVisible(true);
					} else if (pA < pB
							&& (remainingShot("A") == remainingShot("B"))) {
						resultNoti(txtTeam2.getText().toString());
						miDB.setVisible(true);
					} else if (pA == pB) {
						resultNoti("");
						if (remainingShot("A") == 0 && remainingShot("B") == 0) {
							refreshPen();
						}
					}
				}
			}

		}
		return super.onOptionsItemSelected(item);

	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtTor1.getText().toString();
		String scorerListB = txtTor2.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("DFB Pokal", btnRunde.getText().toString(), txtTeam1
				.getText().toString(), txtTeam2.getText().toString(), g1, g2,
				scorerListA, scorerListB, pA, pB);
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
			if (btnPenA1.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenA2.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenA3.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenA4.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenA5.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
		} else if (team.equalsIgnoreCase("B")) {
			if (btnPenB1.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenB2.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenB3.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenB4.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
			if (btnPenB5.getBackground().getConstantState() == drNone
					.getConstantState())
				res++;
		}
		return res;
	}

	private void refresh() {
		// TODO Auto-generated method stub
		txtTeam1.setText("");
		txtTeam2.setText("");
		txtTor1.setText("");
		txtTor2.setText("");
		btnRunde.setText("Runde");
		btnRunde.setClickable(true);
		btnTor1.setText("0");
		btnTor2.setText("0");
		btnTor1.setEnabled(false);
		btnTor2.setEnabled(false);

		tvPenA.setText("0");
		tvPenB.setText("0");
		llPen.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		miDB.setVisible(false);
	}

	@Override
	protected void onPause() {
		super.onPause();
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		btnRunde = (Button) this.findViewById(R.id.btnDFBRunde);
		btnTor1 = (Button) this.findViewById(R.id.btnDFBGoal1);
		btnTor2 = (Button) this.findViewById(R.id.btnDFBGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtDFBTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtDFBTeam2);
		txtTor1 = (TextView) this.findViewById(R.id.txtDFBGoal1);
		txtTor2 = (TextView) this.findViewById(R.id.txtDFBGoal2);

		tvPenA = (TextView) this.findViewById(R.id.tvDFBPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvDFBPenB);
		llPen = (LinearLayout) this.findViewById(R.id.llDFBPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnDFBPenA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnDFBPenA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnDFBPenA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnDFBPenA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnDFBPenA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnDFBPenB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnDFBPenB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnDFBPenB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnDFBPenB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnDFBPenB5);

		String runde = btnRunde.getText().toString();
		editor.putString("btnRunde", runde);
		String BtnTor1 = btnTor1.getText().toString();
		editor.putString("btnTor1", BtnTor1);
		String BtnTor2 = btnTor2.getText().toString();
		editor.putString("btnTor2", BtnTor2);
		String TxtTeam1 = txtTeam1.getText().toString();
		editor.putString("TeamDFB1", TxtTeam1);
		String TxtTeam2 = txtTeam2.getText().toString();
		editor.putString("TeamDFB2", TxtTeam2);
		String TxtTor1 = txtTor1.getText().toString();
		editor.putString("txtTor1", TxtTor1);
		String TxtTor2 = txtTor2.getText().toString();
		editor.putString("txtTor2", TxtTor2);

		String PenA = tvPenA.getText().toString();
		editor.putString("tvPenA", PenA);
		String PenB = tvPenB.getText().toString();
		editor.putString("tvPenB", PenB);
		int llPenVisible = llPen.getVisibility();
		editor.putInt("llPenVisible", llPenVisible);

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_dfbpokal,
					container, false);
			return rootView;
		}
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			if (btnRunde.getText().toString().equals("FINALE"))
				mes = team + " - DEUTSCHER-POKAL-SIEGER 2016!!!";
			else if (btnRunde.getText().toString().equals("1. Hauptrunde"))
				mes = team + " steht in der 2. Hauptrunde!!!";
			else if (btnRunde.getText().toString().equals("2. Hauptrunde"))
				mes = team + " steht in der Achtelfinale!!!";
			else if (btnRunde.getText().toString().equals("Achtelfinale"))
				mes = team + " steht in der Viertelfinale!!!";
			else if (btnRunde.getText().toString().equals("Viertelfinale"))
				mes = team + " steht in der Halbfinale!!!";
			else if (btnRunde.getText().toString().equals("Halbfinale"))
				mes = team + " fliegt nach Berlin!!!";
		} else
			mes = "Elfmeterschießen";
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	int getButtonResId(Button button) {
		if (button.getBackground().getConstantState() == getResources()
				.getDrawable(R.drawable.pen_button_miss).getConstantState()) {
			return R.drawable.pen_button_miss;

		} else if (button.getBackground().getConstantState() == getResources()
				.getDrawable(R.drawable.pen_button_normal).getConstantState()) {
			return R.drawable.pen_button_normal;

		} else if (button.getBackground().getConstantState() == getResources()
				.getDrawable(R.drawable.pen_button_goal).getConstantState()) {
			return R.drawable.pen_button_goal;

		}

		return -1;
	}
	
	private Team crsToObj(Cursor c) {
		Team team = new Team();
		team.setId(c.getInt(0));
		team.setName(c.getString(1));
		team.setCountry(c.getString(2));
		team.setLiga(c.getString(3));
		return team;
	}
}
