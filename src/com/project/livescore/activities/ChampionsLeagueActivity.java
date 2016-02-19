package com.project.livescore.activities;

import com.project.livescore.data.DBAdapter;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ChampionsLeagueActivity extends Activity {

	Button btnRound, btnGoal1, btnGoal2;
	Button btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1,
			btnPenB2, btnPenB3, btnPenB4, btnPenB5;
	TextView txtTeam1, txtTeam2, txtGoal1, txtGoal2, tvPenA, tvPenB,
			tvPenStage, txtAgg1, txtAgg2;
	LinearLayout llPen, llCLAgg;
	MediaPlayer mp;
	ImageView imgTrophy;
	MenuItem miDB;
	
	int idGoal = 0;
	int idMiss = 0;
	int idNone = 0;
	Drawable drMiss = null;
	Drawable drGoal = null;
	Drawable drNone = null;
	static DBAdapter mDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_champions_league);

		mDB = new DBAdapter(this);
		mDB.open();

		idGoal = R.drawable.pen_button_goal;
		idMiss = R.drawable.pen_button_miss;
		idNone = R.drawable.pen_button_normal;

		drMiss = getResources().getDrawable(idMiss);
		drGoal = getResources().getDrawable(idGoal);
		drNone = getResources().getDrawable(idNone);

		btnRound = (Button) this.findViewById(R.id.btnCLRound);
		btnGoal1 = (Button) this.findViewById(R.id.btnCLGoal1);
		btnGoal2 = (Button) this.findViewById(R.id.btnCLGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtCLTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtCLTeam2);
		txtGoal1 = (TextView) this.findViewById(R.id.txtCLGoal1);
		txtGoal2 = (TextView) this.findViewById(R.id.txtCLGoal2);
		txtAgg1 = (TextView) this.findViewById(R.id.txtAgg1);
		txtAgg2 = (TextView) this.findViewById(R.id.txtAgg2);

		tvPenA = (TextView) this.findViewById(R.id.tvPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvPenB);
		llCLAgg = (LinearLayout) this.findViewById(R.id.llCLAgg);
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

		mp = MediaPlayer.create(this, R.raw.clanthem);
		mp.start();

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnRound.setText(pref.getString("btnRound", "Round"));
		btnGoal1.setText(pref.getString("btnGoal1", "0"));
		btnGoal2.setText(pref.getString("btnGoal2", "0"));
		txtGoal1.setText(pref.getString("txtGoal1", null));
		txtGoal2.setText(pref.getString("txtGoal2", null));
		txtTeam1.setText(pref.getString("TeamCL1", null));
		txtTeam2.setText(pref.getString("TeamCL2", null));
		txtAgg1.setText(pref.getString("txtAgg1", null));
		txtAgg2.setText(pref.getString("txtAgg2", null));

		tvPenA.setText(pref.getString("tvPenA", "0"));
		tvPenB.setText(pref.getString("tvPenB", "0"));
		llPen.setVisibility(pref.getInt("llPenVisible", 8));
		llCLAgg.setVisibility(pref.getInt("llAggVisible", 8));

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

		imgTrophy = (ImageView) this.findViewById(R.id.imgTrophy);

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

		Resources res = getResources();

		final CharSequence Runde[] = res.getStringArray(R.array.round);
		final CharSequence Leg[] = { "1st leg", "2nd leg" };
		final CharSequence Team[] = res.getStringArray(R.array.championsleague);
		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final AlertDialog.Builder dlgSpl = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgLeg = new AlertDialog.Builder(this);

		btnRound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dlgRnd.setContentView(R.layout.spieltag);
				dlgRnd.setTitle("Match Info");

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
				final Button btnLeg = (Button) dlgRnd.findViewById(R.id.btnLeg);
				final LinearLayout llDlgAgg = (LinearLayout) dlgRnd
						.findViewById(R.id.llDlgAgg);
				final EditText etAgg1 = (EditText) dlgRnd
						.findViewById(R.id.txtDlgAgg1);
				final EditText etAgg2 = (EditText) dlgRnd
						.findViewById(R.id.txtDlgAgg2);

				tvRundeAuswln.setText("Choose round");
				btnSpl.setText("Round");
				llDlgAgg.setVisibility(View.GONE);
				btnLeg.setText("Leg");
				btnLeg.setVisibility(View.GONE);
				btnTeam1.setText("Home");
				btnTeam2.setText("Away");

				btnSpl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgSpl.setItems(Runde, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnSpl.setText(Runde[which]);

								dialog.cancel();
								if (!btnSpl.getText().toString()
										.equals("Group Stage")
										&& !btnSpl.getText().toString()
												.equals("MILAN FINAL 2016")) {
									btnLeg.setVisibility(View.VISIBLE);
								} else {
									btnLeg.setVisibility(View.GONE);
								}
							}

						});
						dlgSpl.show();
					}
				});

				btnLeg.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlgLeg.setItems(Leg, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnLeg.setText(Leg[which]);
								if (btnLeg.getText().toString()
										.equals("2nd leg"))
									llDlgAgg.setVisibility(View.VISIBLE);
								else
									llDlgAgg.setVisibility(View.GONE);
								dialog.cancel();
							}
						});

						dlgLeg.show();
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
									errNoti("Please select a team!");
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
									errNoti("Please select a team!");
								}
							}
						});
						dlgTeam.show();
					}
				});

				btnOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (btnSpl.getText().toString().equals("Round")
								|| btnTeam1.getText().toString().equals("Home")
								|| btnTeam2.getText().toString().equals("Away")) {
							// TODO Auto-generated method stub
							errNoti("Please select rounds and teams!");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							if (btnLeg.getVisibility() == View.GONE)
								btnRound.setText(btnSpl.getText());
							else
								btnRound.setText(btnSpl.getText() + " - "
										+ btnLeg.getText().toString());

							btnRound.setClickable(false);
							btnGoal1.setEnabled(true);
							btnGoal2.setEnabled(true);
							if (btnLeg.getText().toString().equals("2nd leg")) {
								txtAgg1.setText(etAgg1.getText().toString());
								txtAgg2.setText(etAgg2.getText().toString());

								llCLAgg.setVisibility(View.VISIBLE);
							}
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

		btnGoal1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dlgGoal.setContentView(R.layout.goalalert);
				dlgGoal.setTitle("GOOAAAALLL!!!!");

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
				final TextView tvScoredBy = (TextView) dlgGoal
						.findViewById(R.id.tvScoredBy);
				final TextView tvInThe = (TextView) dlgGoal
						.findViewById(R.id.tvInThe);
				final TextView tvMin = (TextView) dlgGoal
						.findViewById(R.id.tvMinute);
				final CheckBox chkOG = (CheckBox) dlgGoal
						.findViewById(R.id.chkOG);
				final CheckBox chkPen = (CheckBox) dlgGoal
						.findViewById(R.id.chkPen);

				tvGoalFor.setText("GOAAALLLL for " + txtTeam1.getText());
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("");
				tvInThe.setText(" in the minute ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							// TODO Auto-generated method stub

							errNoti("Please enter goalscorer and minute!");
						} else {
							int goal = Integer.parseInt(btnGoal1.getText()
									.toString());
							btnGoal1.setText(String.valueOf(goal + 1)
									.toString());
							if (btnRound.getText().toString()
									.contains("2nd leg")) {
								int agg = Integer.parseInt(txtAgg1.getText()
										.toString());
								txtAgg1.setText(String.valueOf(agg + 1)
										.toString());
							}
							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoal1.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtGoal1
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtGoal1.setText(temp);
							} else
								txtGoal1.append(mes + "\n");
							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeam1.getText().toString().equals("")
						&& !txtTeam2.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		btnGoal2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dlgGoal.setContentView(R.layout.goalalert);
				dlgGoal.setTitle("GOOAAAALLL!!!!");

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
				final TextView tvScoredBy = (TextView) dlgGoal
						.findViewById(R.id.tvScoredBy);
				final TextView tvInThe = (TextView) dlgGoal
						.findViewById(R.id.tvInThe);
				final TextView tvMin = (TextView) dlgGoal
						.findViewById(R.id.tvMinute);

				tvGoalFor.setText("GOAAALLLL for " + txtTeam2.getText() + " ");
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("");
				tvInThe.setText(" in the minute ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							// TODO Auto-generated method stub

							errNoti("Please enter goalscorer and minute!");
						} else {
							int goal = Integer.parseInt(btnGoal2.getText()
									.toString());
							btnGoal2.setText(String.valueOf(goal + 1)
									.toString());
							if (btnRound.getText().toString()
									.contains("2nd leg")) {
								int agg = Integer.parseInt(txtAgg2.getText()
										.toString());
								txtAgg2.setText(String.valueOf(agg + 1)
										.toString());
							}
							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoal2.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtGoal2
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtGoal2.setText(temp);
							} else
								txtGoal2.append(mes + "\n");
							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeam1.getText().toString().equals("")
						&& !txtTeam2.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		txtGoal1.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtGoal1.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoal1.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtGoal1.setText(txtEdit.getText().toString());
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

		txtGoal2.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtGoal2.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoal2.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtGoal2.setText(txtEdit.getText().toString());
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
	protected void onPause() {
		super.onPause();

		btnRound = (Button) this.findViewById(R.id.btnCLRound);
		btnGoal1 = (Button) this.findViewById(R.id.btnCLGoal1);
		btnGoal2 = (Button) this.findViewById(R.id.btnCLGoal2);
		txtTeam1 = (TextView) this.findViewById(R.id.txtCLTeam1);
		txtTeam2 = (TextView) this.findViewById(R.id.txtCLTeam2);
		txtGoal1 = (TextView) this.findViewById(R.id.txtCLGoal1);
		txtGoal2 = (TextView) this.findViewById(R.id.txtCLGoal2);
		txtAgg1 = (TextView) this.findViewById(R.id.txtAgg1);
		txtAgg2 = (TextView) this.findViewById(R.id.txtAgg2);

		tvPenA = (TextView) this.findViewById(R.id.tvPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvPenB);
		llPen = (LinearLayout) this.findViewById(R.id.llPen);
		llCLAgg = (LinearLayout) this.findViewById(R.id.llCLAgg);
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

		String runde = btnRound.getText().toString();
		editor.putString("btnRound", runde);
		String BtnTor1 = btnGoal1.getText().toString();
		editor.putString("btnGoal1", BtnTor1);
		String BtnTor2 = btnGoal2.getText().toString();
		editor.putString("btnGoal2", BtnTor2);
		String TxtTeam1 = txtTeam1.getText().toString();
		editor.putString("TeamCL1", TxtTeam1);
		String TxtTeam2 = txtTeam2.getText().toString();
		editor.putString("TeamCL2", TxtTeam2);
		String TxtTor1 = txtGoal1.getText().toString();
		editor.putString("txtGoal1", TxtTor1);
		String TxtTor2 = txtGoal2.getText().toString();
		editor.putString("txtGoal2", TxtTor2);
		String TxtAgg1 = txtAgg1.getText().toString();
		editor.putString("txtAgg1", TxtAgg1);
		String TxtAgg2 = txtAgg2.getText().toString();
		editor.putString("txtAgg2", TxtAgg2);

		String PenA = tvPenA.getText().toString();
		editor.putString("tvPenA", PenA);
		String PenB = tvPenB.getText().toString();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.champions_league, menu);
		miDB = menu.findItem(R.id.clsavedb);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.clexit) {
			final AlertDialog.Builder dlgExit = new AlertDialog.Builder(this);
			dlgExit.setTitle("Exit");
			dlgExit.setMessage("Are you sure?");

			dlgExit.setIcon(R.drawable.ic_launcher);
			dlgExit.setPositiveButton("Yes", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					onPause();
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
		} else if (id == R.id.clrefresh) {
			refresh();
		} else if (id == R.id.clsavedb) {
			int g1 = Integer.parseInt(btnGoal1.getText().toString());
			int g2 = Integer.parseInt(btnGoal2.getText().toString());
			
			if (btnRound.getText().toString().equals("Group Stage")
					|| btnRound.getText().toString().equals("MILAN FINAL 2016")
					|| btnRound.getText().toString().contains("1st leg")) {
				if (g1 != g2) {
					addToDB(g1, g2, 0, 0);
				} else {
					if (btnRound.getText().toString()
							.equals("MILAN FINAL 2016")) {
						int pA = Integer.parseInt(tvPenA.getText().toString());
						int pB = Integer.parseInt(tvPenB.getText().toString());
						addToDB(g1, g2, pA, pB);
					} else {
						addToDB(g1, g2, 0, 0);
					}
				}
			} else {
				int agg1 = Integer.parseInt(txtAgg1.getText().toString());
				int agg2 = Integer.parseInt(txtAgg2.getText().toString());
				if (agg1 != agg2) {
					addToDB(g1, g2, 0, 0);
				} else {
					int g1_1leg = Integer
							.parseInt(txtAgg1.getText().toString()) - g1;
					int g2_1leg = Integer
							.parseInt(txtAgg2.getText().toString()) - g2;

					if (g1_1leg > g2 || g2_1leg > g1) {
						addToDB(g1, g2, 0, 0);
					} else {
						int pA = Integer.parseInt(tvPenA.getText().toString());
						int pB = Integer.parseInt(tvPenB.getText().toString());
						addToDB(g1, g2, pA, pB);
					}
				}
			}
			errNoti("Result saved!");
		} else if (id == R.id.clfulltime
				&& !btnRound.getText().toString().equals("Round")) {
			int g1 = Integer.parseInt(btnGoal1.getText().toString());
			int g2 = Integer.parseInt(btnGoal2.getText().toString());

			if (btnRound.getText().toString().equals("Group Stage")
					|| btnRound.getText().toString().equals("MILAN FINAL 2016")
					|| btnRound.getText().toString().contains("1st leg")) {
				if (g1 > g2) {
					resultNoti(txtTeam1.getText().toString());
					miDB.setVisible(true);
				} else if (g1 < g2) {
					resultNoti(txtTeam2.getText().toString());
					miDB.setVisible(true);
				} else {

					if (btnRound.getText().toString()
							.equals("MILAN FINAL 2016")) {
						handlePSO(g1, g2);
					} else {
						resultNoti("");
						miDB.setVisible(true);
					}

				}

			} else {
				int agg1 = Integer.parseInt(txtAgg1.getText().toString());
				int agg2 = Integer.parseInt(txtAgg2.getText().toString());
				if (agg1 > agg2) {
					resultNoti(txtTeam1.getText().toString());
					miDB.setVisible(true);
				} else if (agg1 < agg2) {
					resultNoti(txtTeam2.getText().toString());
					miDB.setVisible(true);
				} else {
					int g1_1leg = Integer
							.parseInt(txtAgg1.getText().toString()) - g1;
					int g2_1leg = Integer
							.parseInt(txtAgg2.getText().toString()) - g2;

					if (g1_1leg > g2) {
						resultNoti(txtTeam1.getText().toString());
						miDB.setVisible(true);
					} else if (g2_1leg > g1) {
						resultNoti(txtTeam2.getText().toString());
						miDB.setVisible(true);
					} else {
						handlePSO(g1, g2);
					}
				}
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void handlePSO(int g1, int g2) {
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
					stage = Integer.parseInt(tvPenStage.getText().toString());
					tvPenStage.setText(String.valueOf(stage + 1).toString());
				}
			}
		} else {
			if (pA > pB && (remainingShot("A") == remainingShot("B"))) {
				resultNoti(txtTeam1.getText().toString());
				miDB.setVisible(true);
			} else if (pA < pB && (remainingShot("A") == remainingShot("B"))) {
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
		txtGoal1.setText("");
		txtGoal2.setText("");
		btnRound.setText("Round");
		btnRound.setClickable(true);
		btnGoal1.setText("0");
		btnGoal2.setText("0");
		btnGoal1.setEnabled(false);
		btnGoal2.setEnabled(false);

		tvPenA.setText("0");
		tvPenB.setText("0");
		llPen.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		llCLAgg.setVisibility(View.GONE);
		miDB.setVisible(false);
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
			View rootView = inflater.inflate(
					R.layout.fragment_champions_league, container, false);
			return rootView;
		}
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			if (btnRound.getText().toString().equals("MILAN FINAL 2016"))
				mes = team + " - UEFA CHAMPIONS LEAGUE 2016 WINNER!!!";
			else if (btnRound.getText().toString().equals("Group Stage")
					|| btnRound.getText().toString().contains("1st leg"))
				mes = team + " has won the match!!!";
			else if (btnRound.getText().toString().contains("Round 1 of 16"))
				mes = team + " has qualified to the Quarter-Final!!!";
			else if (btnRound.getText().toString().contains("Quarter Final"))
				mes = team + " has qualified to the Semi-Final!!!";
			else if (btnRound.getText().toString().contains("Semi Final"))
				mes = team + " will take a trip to Milan!!!";
		} else {
			if (btnRound.getText().toString().equals("Group Stage")
					|| btnRound.getText().toString().contains("1st leg"))
				mes = "Drawn match!";
			else
				mes = "Penalty shoot-out!";
		}
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtGoal1.getText().toString();
		String scorerListB = txtGoal2.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("UEFA Champions League", btnRound.getText().toString(),
				txtTeam1.getText().toString(), txtTeam2.getText().toString(),
				g1, g2, scorerListA, scorerListB, pA, pB);
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
}
