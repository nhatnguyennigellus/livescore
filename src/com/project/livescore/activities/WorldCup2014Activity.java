package com.project.livescore.activities;

import java.util.Calendar;

import com.project.livescore.data.DBAdapter;
import com.project.livescore1415.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class WorldCup2014Activity extends Activity {

	Button btnRound, btnGoal1, btnGoal2;
	Button btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1,
			btnPenB2, btnPenB3, btnPenB4, btnPenB5;
	TextView txtTeam1, txtTeam2, txtGoal1, txtGoal2, tvPenA, tvPenB,
			tvPenStage, txtDateTime;
	LinearLayout llPen;
	MediaPlayer mp;
	ImageView imgTrophy, imgFlagA, imgFlagB;
	static DBAdapter mDB;
	static final int DATE_PICKER_DIALOG = 0;
	static final int TIME_PICKER_DIALOG = 1;
	public int yearSelected = 0, monthSelected = 0, daySelected = 0,
			hourSelected = 0, minuteSelected = 0;
	Button btnTime, btnDate;
	DigitalClock digiClock;

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		switch (id) {
		case DATE_PICKER_DIALOG:
			int cyear = c.get(Calendar.YEAR);
			int cmonth = c.get(Calendar.MONTH);
			int cday = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							yearSelected = year;
							monthSelected = monthOfYear + 1;
							daySelected = dayOfMonth;
							btnDate.setText(standardizeDate(daySelected,
									monthSelected, yearSelected));
						}
					}, cyear, cmonth, cday);

		case TIME_PICKER_DIALOG:
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(this,
					new TimePickerDialog.OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay,
								int minute) {
							// TODO Auto-generated method stub
							hourSelected = hourOfDay;
							minuteSelected = minute;
							btnTime.setText(standardizeTime(hourSelected,
									minuteSelected));
						}
					}, mHour, mMinute, true);
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_world_cup2014);

		mDB = new DBAdapter(this);
		mDB.open();

		digiClock = (DigitalClock) this.findViewById(R.id.digitalClock1);
		btnRound = (Button) this.findViewById(R.id.btnWCRound);
		btnGoal1 = (Button) this.findViewById(R.id.btnWCGoalA);
		btnGoal2 = (Button) this.findViewById(R.id.btnWCGoalB);
		txtTeam1 = (TextView) this.findViewById(R.id.txtWCTeamA);
		txtTeam2 = (TextView) this.findViewById(R.id.txtWCTeamB);
		txtGoal1 = (TextView) this.findViewById(R.id.txtWCScorerA);
		txtGoal2 = (TextView) this.findViewById(R.id.txtWCScorerB);
		txtDateTime = (TextView) this.findViewById(R.id.txtDatetime);

		tvPenA = (TextView) this.findViewById(R.id.tvPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvPenB);
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
		btnRound.setText(pref.getString("btnRound", "Round"));
		btnGoal1.setText(pref.getString("btnGoal1", "0"));
		btnGoal2.setText(pref.getString("btnGoal2", "0"));
		txtGoal1.setText(pref.getString("txtGoal1", null));
		txtGoal2.setText(pref.getString("txtGoal2", null));
		txtTeam1.setText(pref.getString("TeamCL1", null));
		txtTeam2.setText(pref.getString("TeamCL2", null));
		txtDateTime.setText(pref.getString("Datetime", null));

		tvPenA.setText(pref.getString("tvPenA", "0"));
		tvPenB.setText(pref.getString("tvPenB", "0"));
		llPen.setVisibility(pref.getInt("llPenVisible", 8));
		btnPenA1.setBackgroundColor(pref.getInt("PenA1Color", 0));
		btnPenA2.setBackgroundColor(pref.getInt("PenA2Color", 0));
		btnPenA3.setBackgroundColor(pref.getInt("PenA3Color", 0));
		btnPenA4.setBackgroundColor(pref.getInt("PenA4Color", 0));
		btnPenA5.setBackgroundColor(pref.getInt("PenA5Color", 0));
		btnPenB1.setBackgroundColor(pref.getInt("PenB1Color", 0));
		btnPenB2.setBackgroundColor(pref.getInt("PenB2Color", 0));
		btnPenB3.setBackgroundColor(pref.getInt("PenB3Color", 0));
		btnPenB4.setBackgroundColor(pref.getInt("PenB4Color", 0));
		btnPenB5.setBackgroundColor(pref.getInt("PenB5Color", 0));

		imgTrophy = (ImageView) this.findViewById(R.id.imgWCTrophy);
		imgFlagA = (ImageView) this.findViewById(R.id.imgWCFlagA);
		imgFlagB = (ImageView) this.findViewById(R.id.imgWCFlagB);
		imgFlagA.setImageResource(setFlag(pref.getString("TeamCL1", "")));
		imgFlagB.setImageResource(setFlag(pref.getString("TeamCL2", "")));

		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.SECOND) % 2 == 1)
			mp = MediaPlayer.create(this, R.raw.wc);
		else
			mp = MediaPlayer.create(this, R.raw.weareone);
		mp.start();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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

		Resources res = getResources();

		final CharSequence Round[] = res.getStringArray(R.array.wcround);
		final CharSequence Group[] = res.getStringArray(R.array.group);
		final CharSequence Team[] = res.getStringArray(R.array.worldcup);
		final CharSequence Venue[] = res.getStringArray(R.array.wc_venue);
		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);

		final AlertDialog.Builder dlgSpl = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgGroup = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgVenue = new AlertDialog.Builder(this);
		dlgRnd.setContentView(R.layout.wc_matchinfo);
		btnTime = (Button) dlgRnd.findViewById(R.id.btnTimeSel);
		btnDate = (Button) dlgRnd.findViewById(R.id.btnDateSel);

		btnRound.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				dlgRnd.setTitle("Match Info");

				Button btnOK = (Button) dlgRnd.findViewById(R.id.btnSplOK);
				Button btnCancel = (Button) dlgRnd
						.findViewById(R.id.btnSplCancel);
				final Button btnSpl = (Button) dlgRnd
						.findViewById(R.id.btnWCSelRound);
				final Button btnGrp = (Button) dlgRnd
						.findViewById(R.id.btnWCSelGroup);

				final Button btnVenue = (Button) dlgRnd
						.findViewById(R.id.btnVenueSel);

				final Button btnTeam1 = (Button) dlgRnd
						.findViewById(R.id.btnSplTeam1);
				final Button btnTeam2 = (Button) dlgRnd
						.findViewById(R.id.btnSplTeam2);

				btnSpl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlgSpl.setItems(Round, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnSpl.setText(Round[which]);

								dialog.cancel();
								if (btnSpl.getText().toString()
										.equals("Group Stage")) {
									btnGrp.setVisibility(View.VISIBLE);
								} else {
									btnGrp.setVisibility(View.GONE);
									if(btnSpl.getText().toString()
											.equals("FINAL"))
									{
										btnVenue.setText("Rio de Janeiro");
										btnDate.setText("14.07.2014");
										btnTime.setText("02:00");
									}
								}
							}
						});

						dlgSpl.show();
					}
				});

				btnGrp.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dlgGroup.setItems(Group, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnGrp.setText("Group " + Group[which]);
								dialog.cancel();
							}
						});

						dlgGroup.show();
					}
				});

				btnVenue.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlgVenue.setItems(Venue, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								btnVenue.setText(Venue[which]);
								dialog.cancel();
							}
						});

						dlgVenue.show();
					}
				});

				btnDate.setOnClickListener(new View.OnClickListener() {

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showDialog(DATE_PICKER_DIALOG);

					}

				});

				btnTime.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						showDialog(TIME_PICKER_DIALOG);

					}

				});

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
						dlgTeam.setItems(Team, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (!Team[which].equals(btnTeam1.getText())) {
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

				btnCancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgRnd.cancel();
					}
				});

				btnOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (btnSpl.getText().toString().equals("Round")
								|| btnTeam1.getText().toString()
										.equals("Team A")
								|| btnTeam2.getText().toString()
										.equals("Team B")) {
							errNoti("Please select round and teams!");
						} else if (btnGrp.getVisibility() == View.VISIBLE
								&& btnGrp.getText().toString().equals("Group")) {
							errNoti("Please select group!");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							if (btnGrp.getVisibility() == View.GONE)
								btnRound.setText(btnSpl.getText());
							else
								btnRound.setText(btnSpl.getText() + " - "
										+ btnGrp.getText().toString());
							btnGoal1.setEnabled(true);
							btnGoal2.setEnabled(true);
							btnRound.setClickable(false);
							txtDateTime.setText(btnDate.getText() + " "
									+ btnTime.getText() + " GMT+07 - "
									+ btnVenue.getText().toString());

							String teamA = txtTeam1.getText().toString();
							String teamB = txtTeam2.getText().toString();

							imgFlagA.setImageResource(setFlag(teamA));
							imgFlagB.setImageResource(setFlag(teamB));

							dlgRnd.cancel();
						}
					}

				});

				dlgRnd.show();
			}
		});

		btnGoal1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

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
							errNoti("Please enter goalscorer and minute!");
						} 
						else if (min < 0 || min > 120) {
							errNoti("Invalid minute number!");
						} else {
							int goal = Integer.parseInt(btnGoal1.getText()
									.toString());
							btnGoal1.setText(String.valueOf(goal + 1)
									.toString());

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
				ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
				if (bgPen1.getColor() == Color.RED) {
					int p = Integer.parseInt(tvPenA.getText().toString());
					tvPenA.setText(String.valueOf(
							String.valueOf(p + 1).toString()).toString());
					btnPenA1.setBackgroundColor(Color.GREEN);
				} else if (bgPen1.getColor() == Color.parseColor("#CCCCCC")) {
					btnPenA1.setBackgroundColor(Color.RED);
				}
			}
		});

		btnPenA2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
				if (bgPen1.getColor() != Color.parseColor("#CCCCCC")) {
					if (bgPen2.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(
								String.valueOf(p + 1).toString()).toString());
						btnPenA2.setBackgroundColor(Color.GREEN);
					} else if (bgPen2.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenA2.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenA3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenA3.getBackground();
				if ((bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen3.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA3.setBackgroundColor(Color.GREEN);
					} else if (bgPen3.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenA3.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenA4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenA3.getBackground();
				ColorDrawable bgPen4 = (ColorDrawable) btnPenA4.getBackground();

				if ((bgPen3.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen4.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA4.setBackgroundColor(Color.GREEN);
					} else if (bgPen4.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenA4.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenA5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenA3.getBackground();
				ColorDrawable bgPen4 = (ColorDrawable) btnPenA4.getBackground();
				ColorDrawable bgPen5 = (ColorDrawable) btnPenA5.getBackground();
				if ((bgPen4.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen3.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen5.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenA.getText().toString());
						tvPenA.setText(String.valueOf(p + 1).toString());
						btnPenA5.setBackgroundColor(Color.GREEN);
					} else if (bgPen5.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenA5.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenB1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenB1.getBackground();
				if (bgPen1.getColor() == Color.RED) {
					int p = Integer.parseInt(tvPenB.getText().toString());
					tvPenB.setText(String.valueOf(
							String.valueOf(p + 1).toString()).toString());
					btnPenB1.setBackgroundColor(Color.GREEN);
				} else if (bgPen1.getColor() == Color.parseColor("#CCCCCC")) {
					btnPenB1.setBackgroundColor(Color.RED);
				}
			}
		});

		btnPenB2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ColorDrawable bgPen1 = (ColorDrawable) btnPenB1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenB2.getBackground();
				if (bgPen1.getColor() != Color.parseColor("#CCCCCC")) {
					if (bgPen2.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(
								String.valueOf(p + 1).toString()).toString());
						btnPenB2.setBackgroundColor(Color.GREEN);
					} else if (bgPen2.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenB2.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenB3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenB1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenB2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenB3.getBackground();
				if ((bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen3.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB3.setBackgroundColor(Color.GREEN);
					} else if (bgPen3.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenB3.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenB4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenB1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenB2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenB3.getBackground();
				ColorDrawable bgPen4 = (ColorDrawable) btnPenB4.getBackground();

				if ((bgPen3.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen4.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB4.setBackgroundColor(Color.GREEN);
					} else if (bgPen4.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenB4.setBackgroundColor(Color.RED);
					}
				}
			}
		});

		btnPenB5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ColorDrawable bgPen1 = (ColorDrawable) btnPenB1.getBackground();
				ColorDrawable bgPen2 = (ColorDrawable) btnPenB2.getBackground();
				ColorDrawable bgPen3 = (ColorDrawable) btnPenB3.getBackground();
				ColorDrawable bgPen4 = (ColorDrawable) btnPenB4.getBackground();
				ColorDrawable bgPen5 = (ColorDrawable) btnPenB5.getBackground();
				if ((bgPen4.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen3.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen2.getColor() != Color.parseColor("#CCCCCC"))
						&& (bgPen1.getColor() != Color.parseColor("#CCCCCC"))) {
					if (bgPen5.getColor() == Color.RED) {
						int p = Integer.parseInt(tvPenB.getText().toString());
						tvPenB.setText(String.valueOf(p + 1).toString());
						btnPenB5.setBackgroundColor(Color.GREEN);
					} else if (bgPen5.getColor() == Color.parseColor("#CCCCCC")) {
						btnPenB5.setBackgroundColor(Color.RED);
					}
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.world_cup2014, menu);
		return true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		saveData();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		saveData();
	}

	private void saveData() {
		btnRound = (Button) this.findViewById(R.id.btnWCRound);
		btnGoal1 = (Button) this.findViewById(R.id.btnWCGoalA);
		btnGoal2 = (Button) this.findViewById(R.id.btnWCGoalB);
		txtTeam1 = (TextView) this.findViewById(R.id.txtWCTeamA);
		txtTeam2 = (TextView) this.findViewById(R.id.txtWCTeamB);
		txtGoal1 = (TextView) this.findViewById(R.id.txtWCScorerA);
		txtGoal2 = (TextView) this.findViewById(R.id.txtWCScorerB);
		txtDateTime = (TextView) this.findViewById(R.id.txtDatetime);

		tvPenA = (TextView) this.findViewById(R.id.tvPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvPenB);
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
		String TxtDateTime = txtDateTime.getText().toString();
		editor.putString("Datetime", TxtDateTime);

		String PenA = tvPenA.getText().toString();
		editor.putString("tvPenA", PenA);
		String PenB = tvPenB.getText().toString();
		editor.putString("tvPenB", PenB);
		int llPenVisible = llPen.getVisibility();
		editor.putInt("llPenVisible", llPenVisible);
		int llAggVisible = llPen.getVisibility();
		editor.putInt("llAggVisible", llAggVisible);

		ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
		ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
		ColorDrawable bgPen3 = (ColorDrawable) btnPenA3.getBackground();
		ColorDrawable bgPen4 = (ColorDrawable) btnPenA4.getBackground();
		ColorDrawable bgPen5 = (ColorDrawable) btnPenA5.getBackground();
		ColorDrawable bgPenB1 = (ColorDrawable) btnPenB1.getBackground();
		ColorDrawable bgPenB2 = (ColorDrawable) btnPenB2.getBackground();
		ColorDrawable bgPenB3 = (ColorDrawable) btnPenB3.getBackground();
		ColorDrawable bgPenB4 = (ColorDrawable) btnPenB4.getBackground();
		ColorDrawable bgPenB5 = (ColorDrawable) btnPenB5.getBackground();
		int PenA1Color = bgPen1.getColor();
		editor.putInt("PenA1Color", PenA1Color);
		int PenA2Color = bgPen2.getColor();
		editor.putInt("PenA2Color", PenA2Color);
		int PenA3Color = bgPen3.getColor();
		editor.putInt("PenA3Color", PenA3Color);
		int PenA4Color = bgPen4.getColor();
		editor.putInt("PenA4Color", PenA4Color);
		int PenA5Color = bgPen5.getColor();
		editor.putInt("PenA5Color", PenA5Color);
		int PenB1Color = bgPenB1.getColor();
		editor.putInt("PenB1Color", PenB1Color);
		int PenB2Color = bgPenB2.getColor();
		editor.putInt("PenB2Color", PenB2Color);
		int PenB3Color = bgPenB3.getColor();
		editor.putInt("PenB3Color", PenB3Color);
		int PenB4Color = bgPenB4.getColor();
		editor.putInt("PenB4Color", PenB4Color);
		int PenB5Color = bgPenB5.getColor();
		editor.putInt("PenB5Color", PenB5Color);

		editor.commit();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.wcexit) {
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
		} else if (id == R.id.wcrefresh) {
			refresh();
		} else if (id == R.id.wcfulltime
				&& !btnRound.getText().toString().equals("Round")) {
			int g1 = Integer.parseInt(btnGoal1.getText().toString());
			int g2 = Integer.parseInt(btnGoal2.getText().toString());

			if (btnRound.getText().toString().contains("Group Stage")) {
				if (g1 > g2) {
					resultNoti(txtTeam1.getText().toString());

				} else if (g1 < g2) {
					resultNoti(txtTeam2.getText().toString());

				} else {
					resultNoti("");

				}
				addToDB(g1, g2, 0, 0);
			} else {
				if (g1 > g2) {
					resultNoti(txtTeam1.getText().toString());
					addToDB(g1, g2, 0, 0);
				} else if (g1 < g2) {
					resultNoti(txtTeam2.getText().toString());
					addToDB(g1, g2, 0, 0);
				} else {

					handlePSO(g1, g2);

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
				addToDB(g1, g2, pA, pB);
			} else if (pA < pB && (remainingShot("A") < pB - pA)) {
				resultNoti(txtTeam2.getText().toString());
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
				resultNoti(txtTeam1.getText().toString());
				addToDB(g1, g2, pA, pB);
			} else if (pA < pB && (remainingShot("A") == remainingShot("B"))) {
				resultNoti(txtTeam2.getText().toString());
				addToDB(g1, g2, pA, pB);
			} else if (pA == pB) {
				resultNoti("");
				if (remainingShot("A") == 0 && remainingShot("B") == 0) {
					refreshPen();
				}
			}
		}
	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		// TODO Auto-generated method stub
		String scorerListA = txtGoal1.getText().toString();
		String scorerListB = txtGoal2.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("FIFA World Cup - Brazil 2014", btnRound.getText()
				.toString(), txtTeam1.getText().toString(), txtTeam2.getText()
				.toString(), g1, g2, scorerListA, scorerListB, pA, pB);
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			if (btnRound.getText().toString().equals("FINAL"))
				mes = team + " - FIFA WORLD CUP 2014 WINNER!!!";
			else if (btnRound.getText().toString().contains("Group Stage"))
				mes = team + " has won the match!!!";
			else
				mes = team + " has qualified to the next round!!!";
		} else {
			if (btnRound.getText().toString().contains("Group Stage"))
				mes = "Drawn match!";
			else
				mes = "Penalty shoot-out!";
		}
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	private void refreshPen() {
		btnPenA1.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenA2.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenA3.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenA4.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenA5.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenB1.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenB2.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenB3.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenB4.setBackgroundColor(Color.parseColor("#CCCCCC"));
		btnPenB5.setBackgroundColor(Color.parseColor("#CCCCCC"));
	}

	private int setFlag(String team) {
		if (team.equals("")) {
			return R.drawable.trophywc;
		} else if (team.equals("BRAZIL")) {
			return R.drawable.bra;
		} else if (team.equals("MEXICO")) {
			return R.drawable.mex;
		} else if (team.equals("CROATIA")) {
			return R.drawable.cro;
		} else if (team.equals("CAMEROON")) {
			return R.drawable.cmr;
		} else if (team.equals("ALGERIA")) {
			return R.drawable.alg;
		} else if (team.equals("AUSTRALIA")) {
			return R.drawable.aus;
		} else if (team.equals("ARGENTINA")) {
			return R.drawable.arg;
		} else if (team.equals("BELGIUM")) {
			return R.drawable.bel;
		} else if (team.equals("BOSNIA-HERZEGOVINA")) {
			return R.drawable.bih;
		} else if (team.equals("CHILE")) {
			return R.drawable.chi;
		} else if (team.equals("CÔTE D\'IVOIRE")) {
			return R.drawable.civ;
		} else if (team.equals("COLOMBIA")) {
			return R.drawable.col;
		} else if (team.equals("COSTA RICA")) {
			return R.drawable.crc;
		} else if (team.equals("ECUADOR")) {
			return R.drawable.ecu;
		} else if (team.equals("ENGLAND")) {
			return R.drawable.eng;
		} else if (team.equals("SPAIN")) {
			return R.drawable.esp;
		} else if (team.equals("FRANCE")) {
			return R.drawable.fra;
		} else if (team.equals("GERMANY")) {
			return R.drawable.ger;
		} else if (team.equals("GHANA")) {
			return R.drawable.gha;
		} else if (team.equals("GREECE")) {
			return R.drawable.gre;
		} else if (team.equals("HONDURAS")) {
			return R.drawable.hon;
		} else if (team.equals("IRAN")) {
			return R.drawable.irn;
		} else if (team.equals("ITALY")) {
			return R.drawable.ita;
		} else if (team.equals("JAPAN")) {
			return R.drawable.jpn;
		} else if (team.equals("KOREA REPUBLIC")) {
			return R.drawable.kor;
		} else if (team.equals("NETHERLANDS")) {
			return R.drawable.ned;
		} else if (team.equals("NIGERIA")) {
			return R.drawable.nga;
		} else if (team.equals("PORTUGAL")) {
			return R.drawable.por;
		} else if (team.equals("RUSSIA")) {
			return R.drawable.rus;
		} else if (team.equals("SWITZERLAND")) {
			return R.drawable.sui;
		} else if (team.equals("URUGUAY")) {
			return R.drawable.uru;
		} else if (team.equals("UNITED STATES")) {
			return R.drawable.usa;
		}

		return R.drawable.trophywc;
	}

	private int remainingShot(String team) {
		int res = 0;
		ColorDrawable bgPen1 = (ColorDrawable) btnPenA1.getBackground();
		ColorDrawable bgPen2 = (ColorDrawable) btnPenA2.getBackground();
		ColorDrawable bgPen3 = (ColorDrawable) btnPenA3.getBackground();
		ColorDrawable bgPen4 = (ColorDrawable) btnPenA4.getBackground();
		ColorDrawable bgPen5 = (ColorDrawable) btnPenA5.getBackground();
		ColorDrawable bgPenB1 = (ColorDrawable) btnPenB1.getBackground();
		ColorDrawable bgPenB2 = (ColorDrawable) btnPenB2.getBackground();
		ColorDrawable bgPenB3 = (ColorDrawable) btnPenB3.getBackground();
		ColorDrawable bgPenB4 = (ColorDrawable) btnPenB4.getBackground();
		ColorDrawable bgPenB5 = (ColorDrawable) btnPenB5.getBackground();
		if (team.equalsIgnoreCase("A")) {
			if (bgPen1.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPen2.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPen3.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPen4.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPen5.getColor() == Color.parseColor("#CCCCCC"))
				res++;
		} else if (team.equalsIgnoreCase("B")) {
			if (bgPenB1.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPenB2.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPenB3.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPenB4.getColor() == Color.parseColor("#CCCCCC"))
				res++;
			if (bgPenB5.getColor() == Color.parseColor("#CCCCCC"))
				res++;
		}
		return res;
	}

	private void refresh() {
		txtTeam1.setText("");
		txtTeam2.setText("");
		txtGoal1.setText("");
		txtGoal2.setText("");
		txtDateTime.setText("");
		btnRound.setText("Round");
		btnRound.setClickable(true);
		btnGoal1.setText("0");
		btnGoal2.setText("0");
		btnGoal1.setEnabled(false);
		btnGoal2.setEnabled(false);

		daySelected = 0;
		monthSelected = 0;
		yearSelected = 0;
		hourSelected = 0;
		minuteSelected = 0;

		tvPenA.setText("0");
		tvPenB.setText("0");
		llPen.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		imgFlagA.setImageResource(R.drawable.trophywc);
		imgFlagB.setImageResource(R.drawable.trophywc);
	}

	public String standardizeDate(int daySelected, int monthSelected,
			int yearSelected) {
		// TODO Auto-generated method stub
		String day = (daySelected > 9) ? String.valueOf(daySelected) : "0"
				+ daySelected;
		String month = (monthSelected > 9) ? String.valueOf(monthSelected)
				: "0" + monthSelected;
		return day + "." + month + "." + yearSelected;
	}

	public String standardizeTime(int hourSelected, int minuteSelected) {
		// TODO Auto-generated method stub
		String hour = (hourSelected > 9) ? String.valueOf(hourSelected) : "0"
				+ hourSelected;
		String minute = (minuteSelected > 9) ? String.valueOf(minuteSelected)
				: "0" + minuteSelected;
		return hour + ":" + minute;
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
			View rootView = inflater.inflate(R.layout.fragment_world_cup2014,
					container, false);
			return rootView;
		}
	}

}
