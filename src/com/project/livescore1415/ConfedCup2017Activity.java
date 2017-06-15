package com.project.livescore1415;

import java.util.Calendar;

import com.project.livescore.data.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class ConfedCup2017Activity extends Activity {

	Button btnRound, btnGoalA, btnGoalB;
	Button btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1,
			btnPenB2, btnPenB3, btnPenB4, btnPenB5;
	TextView txtTeamA, txtTeamB, txtGoalA, txtGoalB, tvPenA, tvPenB,
			tvPenStage, txtDateTime;
	LinearLayout llPen;
	MediaPlayer mp, mpgoal;
	ImageView imgLogo, imgFlagA, imgFlagB;
	static DBAdapter mDB;
	static final int DATE_PICKER_DIALOG = 0;
	static final int TIME_PICKER_DIALOG = 1;
	public int yearSelected = 0, monthSelected = 0, daySelected = 0,
			hourSelected = 0, minuteSelected = 0;
	Button btnTime, btnDate;
	DigitalClock digiClock;
	MenuItem miDB;

	int idGoal = 0;
	int idMiss = 0;
	int idNone = 0;
	Drawable drMiss = null;
	Drawable drGoal = null;
	Drawable drNone = null;
	
	@SuppressWarnings("deprecation")
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
							// TODO Auto-generated method stub
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
		String hour = (hourSelected > 9) ? String.valueOf(hourSelected) : "0"
				+ hourSelected;
		String minute = (minuteSelected > 9) ? String.valueOf(minuteSelected)
				: "0" + minuteSelected;
		return hour + ":" + minute;
	}

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtGoalA.getText().toString();
		String scorerListB = txtGoalB.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		mDB.addMatch("FIFA Confederations Cup - Russia 2017", btnRound.getText().toString(),
				txtTeamA.getText().toString(), txtTeamB.getText().toString(),
				g1, g2, scorerListA, scorerListB, pA, pB);
	}

	void errNoti(String mes) {
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
	
	private int setFlag(String team) {
		if (team.equals("")) {
			return R.drawable.confedcup_logo;
		} else if (team.equals("GERMANY")) {
			return R.drawable.ger;
		} else if (team.equals("PORTUGAL")) {
			return R.drawable.por;
		} else if (team.equals("RUSSIA")) {
			return R.drawable.rus;
		} else if (team.equals("CAMEROON")) {
			return R.drawable.cmr;
		} else if (team.equals("NEW ZEALAND")) {
			return R.drawable.nzl;
		} else if (team.equals("AUSTRALIA")) {
			return R.drawable.aus;
		} else if (team.equals("MEXICO")) {
			return R.drawable.mex;
		} else if (team.equals("CHILE")) {
			return R.drawable.chi;
		}
		return 0;
	}

	void showNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}
	
	private void refresh() {
		txtTeamA.setText("");
		txtTeamB.setText("");
		txtGoalA.setText("");
		txtGoalB.setText("");
		btnRound.setText("Round");
		btnRound.setClickable(true);
		btnGoalA.setText("0");
		btnGoalB.setText("0");
		btnGoalA.setEnabled(false);
		btnGoalB.setEnabled(false);
		txtDateTime.setText("");

		tvPenA.setText("0");
		tvPenB.setText("0");
		llPen.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		miDB.setVisible(false);
		imgFlagA.setImageResource(R.drawable.confedcup_logo);
		imgFlagB.setImageResource(R.drawable.confedcup_logo);
	}
	
	private void handlePSO(int g1, int g2) {
		llPen.setVisibility(View.VISIBLE);
		int pA = Integer.parseInt(tvPenA.getText().toString());
		int pB = Integer.parseInt(tvPenB.getText().toString());
		int stage = Integer.parseInt(tvPenStage.getText().toString());
		if (stage == 0) {
			if (pA > pB && (remainingShot("B") < pA - pB)) {
				resultNoti(txtTeamA.getText().toString());
				miDB.setVisible(true);
			} else if (pA < pB && (remainingShot("A") < pB - pA)) {
				resultNoti(txtTeamB.getText().toString());
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
				resultNoti(txtTeamA.getText().toString());
				miDB.setVisible(true);
			} else if (pA < pB && (remainingShot("A") == remainingShot("B"))) {
				resultNoti(txtTeamB.getText().toString());
				miDB.setVisible(true);
			} else if (pA == pB) {
				resultNoti("");
				if (remainingShot("A") == 0 && remainingShot("B") == 0) {
					refreshPen();
				}
			}
		}
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

	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			if (btnRound.getText().toString().equals("FINAL"))
				mes = team + " - CONFEDERATIONS CUP WINNER!!!";
			else if (btnRound.getText().toString().contains("Group Stage"))
				mes = team + " has won the match!!!";
			else if (btnRound.getText().toString().contains("Round 1 of 16"))
				mes = team + " has qualified to Quarter Final round!!!";
			else if (btnRound.getText().toString().contains("Quarter Final"))
				mes = team + " has qualified to Semi Final round!!!";
			else if (btnRound.getText().toString().contains("Semi Final"))
				mes = team + " has qualified to the Final match!!!";
		} else {
			if (btnRound.getText().toString().contains("Group Stage"))
				mes = "Drawn match!";
			else
				mes = "Penalty shoot-out!";
		}
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confed_cup2017);
		
		mDB = new DBAdapter(this);
		mDB.open();

		idGoal = R.drawable.pen_button_goal;
		idMiss = R.drawable.pen_button_miss;
		idNone = R.drawable.pen_button_normal;

		drMiss = getResources().getDrawable(idMiss);
		drGoal = getResources().getDrawable(idGoal);
		drNone = getResources().getDrawable(idNone);

		btnRound = (Button) this.findViewById(R.id.btnConfedRound);
		btnGoalA = (Button) this.findViewById(R.id.btnConfedGoalA);
		btnGoalB = (Button) this.findViewById(R.id.btnConfedGoalB);
		txtTeamA = (TextView) this.findViewById(R.id.txtConfedTeamA);
		txtTeamB = (TextView) this.findViewById(R.id.txtConfedTeamB);
		txtGoalA = (TextView) this.findViewById(R.id.txtConfedScorerA);
		txtGoalB = (TextView) this.findViewById(R.id.txtConfedScorerB);
		txtDateTime = (TextView) this.findViewById(R.id.txtConfedDatetime);

		tvPenA = (TextView) this.findViewById(R.id.tvConfedPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvConfedPenB);
		llPen = (LinearLayout) this.findViewById(R.id.llConfedPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnPenCfcA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnPenCfcA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnPenCfcA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnPenCfcA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnPenCfcA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnPenCfcB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnPenCfcB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnPenCfcB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnPenCfcB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnPenCfcB5);
		tvPenStage = (TextView) this.findViewById(R.id.tvConfedPenStage);

		mp = MediaPlayer.create(this, R.raw.confedcup);
		mpgoal = MediaPlayer.create(this, R.raw.torhymne);
		
		mp.start();

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnRound.setText(pref.getString("btnRound", "Round"));
		btnGoalA.setText(pref.getString("btnGoal1", "0"));
		btnGoalB.setText(pref.getString("btnGoal2", "0"));
		txtGoalA.setText(pref.getString("txtGoal1", null));
		txtGoalB.setText(pref.getString("txtGoal2", null));
		txtTeamA.setText(pref.getString("TeamEU1", null));
		txtTeamB.setText(pref.getString("TeamEU2", null));
		txtDateTime.setText(pref.getString("Datetime", null));

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

		imgLogo = (ImageView) this.findViewById(R.id.imgConfedLogo);
		imgFlagA = (ImageView) this.findViewById(R.id.imgConfedTeamA);
		imgFlagB = (ImageView) this.findViewById(R.id.imgConfedTeamB);
		imgFlagA.setImageResource(setFlag(pref.getString("TeamEU1", "")));
		imgFlagB.setImageResource(setFlag(pref.getString("TeamEU2", "")));

		imgLogo.setOnClickListener(new View.OnClickListener() {

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
		final CharSequence Team[] = res.getStringArray(R.array.confedcup);
		final CharSequence Venue[] = res.getStringArray(R.array.confedcup_venue);
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
									if (btnSpl.getText().toString()
											.equals("FINAL")) {
										btnVenue.setText("Saint Petersburg");
										btnDate.setText("03.07.2017");
										btnTime.setText("01:00");
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

					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showDialog(TIME_PICKER_DIALOG);

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
								// TODO Auto-generated method stub
								if (!Team[which].equals(btnTeam2.getText())) {
									// TODO Auto-generated method stub
									btnTeam1.setText(Team[which]);
									dialog.cancel();
								} else {
									showNoti("Please select a team!");
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
									showNoti("Please select a team!");
								}
							}
						});
						dlgTeam.show();
					}
				});

				btnCancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
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
							// TODO Auto-generated method stub
							showNoti("Please select round and teams!");
						} else if (btnGrp.getVisibility() == View.VISIBLE
								&& btnGrp.getText().toString().equals("Group")) {
							showNoti("Please select group!");
						} else {
							txtTeamA.setText(btnTeam1.getText());
							txtTeamB.setText(btnTeam2.getText());
							if (btnGrp.getVisibility() == View.GONE)
								btnRound.setText(btnSpl.getText());
							else
								btnRound.setText(btnSpl.getText() + " - "
										+ btnGrp.getText().toString());
							btnGoalA.setEnabled(true);
							btnGoalB.setEnabled(true);
							btnRound.setClickable(false);
							txtDateTime.setText(btnDate.getText() + " "
									+ btnTime.getText() + " GMT+07 - "
									+ btnVenue.getText().toString());

							String teamA = txtTeamA.getText().toString();
							String teamB = txtTeamB.getText().toString();

							imgFlagA.setImageResource(setFlag(teamA));
							imgFlagB.setImageResource(setFlag(teamB));

							dlgRnd.cancel();
						}
					}

				});

				dlgRnd.show();
			}
		});
		
		btnGoalA.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!txtTeamB.getText().equals("GERMANY")) {
					mpgoal.start();
				} 
				
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

				tvGoalFor.setText("GOAAALLLL for " + txtTeamA.getText());
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("'.		");
				tvInThe.setText(" at ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mpgoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							showNoti("Please enter goalscorer and minute!");
						} else {
							int goal = Integer.parseInt(btnGoalA.getText()
									.toString());
							btnGoalA.setText(String.valueOf(goal + 1)
									.toString());

							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoalA.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtGoalA
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtGoalA.setText(temp);
							} else
								txtGoalA.append(mes + "\n");
							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeamA.getText().toString().equals("")
						&& !txtTeamB.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		btnGoalB.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!txtTeamA.getText().equals("GERMANY")) {
					mpgoal.start();
				} 
				
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

				tvGoalFor.setText("GOAAALLLL for " + txtTeamB.getText() + " ");
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("'.		");
				tvInThe.setText(" at ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");

				btnGoalCnl.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mpgoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (txtMin.getText().toString().equals("")
								|| txtScorer.getText().toString().equals("")) {
							showNoti("Please enter goalscorer and minute!");
						} else {
							int goal = Integer.parseInt(btnGoalB.getText()
									.toString());
							btnGoalB.setText(String.valueOf(goal + 1)
									.toString());

							String mes = txtScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoalB.getText().toString()
									.contains(txtScorer.getText())) {
								String temp = txtGoalB
										.getText()
										.toString()
										.replace(
												txtScorer.getText().toString(),
												mes);
								txtGoalB.setText(temp);
							} else
								txtGoalB.append(mes + "\n");
							dlgGoal.cancel();
						}
					}
				});
				if (!txtTeamA.getText().toString().equals("")
						&& !txtTeamB.getText().toString().equals(""))
					dlgGoal.show();
			}
		});

		txtGoalA.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtGoalA.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoalA.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtGoalA.setText(txtEdit.getText().toString());
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

		txtGoalB.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if (!txtGoalB.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit
							.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit
							.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit
							.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoalB.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							txtGoalB.setText(txtEdit.getText().toString());
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

		btnRound = (Button) this.findViewById(R.id.btnConfedRound);
		btnGoalA = (Button) this.findViewById(R.id.btnConfedGoalA);
		btnGoalB = (Button) this.findViewById(R.id.btnConfedGoalB);
		txtTeamA = (TextView) this.findViewById(R.id.txtConfedTeamA);
		txtTeamB = (TextView) this.findViewById(R.id.txtConfedTeamB);
		txtGoalA = (TextView) this.findViewById(R.id.txtConfedScorerA);
		txtGoalB = (TextView) this.findViewById(R.id.txtConfedScorerB);

		tvPenA = (TextView) this.findViewById(R.id.tvConfedPenA);
		tvPenB = (TextView) this.findViewById(R.id.tvConfedPenB);
		llPen = (LinearLayout) this.findViewById(R.id.llConfedPen);
		btnPenA1 = (Button) this.findViewById(R.id.btnPenCfcA1);
		btnPenA2 = (Button) this.findViewById(R.id.btnPenCfcA2);
		btnPenA3 = (Button) this.findViewById(R.id.btnPenCfcA3);
		btnPenA4 = (Button) this.findViewById(R.id.btnPenCfcA4);
		btnPenA5 = (Button) this.findViewById(R.id.btnPenCfcA5);
		btnPenB1 = (Button) this.findViewById(R.id.btnPenCfcB1);
		btnPenB2 = (Button) this.findViewById(R.id.btnPenCfcB2);
		btnPenB3 = (Button) this.findViewById(R.id.btnPenCfcB3);
		btnPenB4 = (Button) this.findViewById(R.id.btnPenCfcB4);
		btnPenB5 = (Button) this.findViewById(R.id.btnPenCfcB5);

		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		String runde = btnRound.getText().toString();
		editor.putString("btnRound", runde);
		String BtnTor1 = btnGoalA.getText().toString();
		editor.putString("btnGoal1", BtnTor1);
		String BtnTor2 = btnGoalB.getText().toString();
		editor.putString("btnGoal2", BtnTor2);
		String TxtTeam1 = txtTeamA.getText().toString();
		editor.putString("TeamEU1", TxtTeam1);
		String TxtTeam2 = txtTeamB.getText().toString();
		editor.putString("TeamEU2", TxtTeam2);
		String TxtTor1 = txtGoalA.getText().toString();
		editor.putString("txtGoal1", TxtTor1);
		String TxtTor2 = txtGoalB.getText().toString();
		editor.putString("txtGoal2", TxtTor2);
		String TxtDatetime = txtDateTime.getText().toString();
		editor.putString("Datetime", TxtDatetime);

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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.confed_cup2017, menu);
		miDB = menu.findItem(R.id.confedsavedb);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.confedexit) {
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
		} else if (id == R.id.confedrefresh) {
			refresh();
		} else if (id == R.id.confedsavedb) {
			int g1 = Integer.parseInt(btnGoalA.getText().toString());
			int g2 = Integer.parseInt(btnGoalB.getText().toString());

			if (btnRound.getText().toString().contains("Group Stage")) {
				addToDB(g1, g2, 0, 0);
			} else {
				if (g1 > g2) {
					addToDB(g1, g2, 0, 0);
				} else if (g1 < g2) {
					addToDB(g1, g2, 0, 0);
				} else {
					int pA = Integer.parseInt(tvPenA.getText().toString());
					int pB = Integer.parseInt(tvPenB.getText().toString());
					addToDB(g1, g2, pA, pB);
				}
			}
			
			errNoti("Result saved!");
		} else if (id == R.id.confedfulltime
				&& !btnRound.getText().toString().equals("Round")) {
			int g1 = Integer.parseInt(btnGoalA.getText().toString());
			int g2 = Integer.parseInt(btnGoalB.getText().toString());

			if (btnRound.getText().toString().contains("Group Stage")) {
				if (g1 > g2) {
					resultNoti(txtTeamA.getText().toString());
					miDB.setVisible(true);
				} else if (g1 < g2) {
					resultNoti(txtTeamB.getText().toString());
					miDB.setVisible(true);
				} else {
					resultNoti("");
					miDB.setVisible(true);
				}
			} else {
				if (g1 > g2) {
					resultNoti(txtTeamA.getText().toString());
					miDB.setVisible(true);
				} else if (g1 < g2) {
					resultNoti(txtTeamB.getText().toString());
					miDB.setVisible(true);
				} else {
					handlePSO(g1, g2);
				}
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
