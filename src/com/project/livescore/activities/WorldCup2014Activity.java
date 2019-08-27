package com.project.livescore.activities;

import java.util.ArrayList;
import java.util.Calendar;

import com.project.livescore1415.R;
import com.project.livescore.data.*;

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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	Button btnPenA1, btnPenA2, btnPenA3, btnPenA4, btnPenA5, btnPenB1, btnPenB2, btnPenB3, btnPenB4, btnPenB5;
	Button btnKitColorA, btnKitColorB;
	TextView txtTeam1, txtTeam2, txtGoal1, txtGoal2, tvPenA, tvPenB, tvPenStage, txtDateTime;
	LinearLayout llPen, llWCScorer;
	MediaPlayer mp, mpgoal;
	ImageView imgTrophy, imgFlagA, imgFlagB;
	static DBAdapter mDB;
	static final int DATE_PICKER_DIALOG = 0;
	static final int TIME_PICKER_DIALOG = 1;
	public int yearSelected = 0, monthSelected = 0, daySelected = 0, hourSelected = 0, minuteSelected = 0;
	Button btnTime, btnDate;
	DigitalClock digiClock;
	MenuItem miDB;
	
	CharSequence kitColorA[];
	CharSequence kitColorB[];
	CharSequence kit[] = {"Home", "Away", "3rd Kit"};
	int idGoal = 0;
	int idMiss = 0;
	int idNone = 0;
	Drawable drMiss = null;
	Drawable drGoal = null;
	Drawable drNone = null;
	@Override
	protected Dialog onCreateDialog(int id) {

		Calendar c = Calendar.getInstance();
		switch (id) {
		case DATE_PICKER_DIALOG:
			int cyear = c.get(Calendar.YEAR);
			int cmonth = c.get(Calendar.MONTH);
			int cday = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					yearSelected = year;
					monthSelected = monthOfYear + 1;
					daySelected = dayOfMonth;
					btnDate.setText(standardizeDate(daySelected, monthSelected, yearSelected));
				}
			}, cyear, cmonth, cday);

		case TIME_PICKER_DIALOG:
			int mHour = c.get(Calendar.HOUR_OF_DAY);
			int mMinute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					hourSelected = hourOfDay;
					minuteSelected = minute;
					btnTime.setText(standardizeTime(hourSelected, minuteSelected));
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

		idGoal = R.drawable.pen_button_goal;
		idMiss = R.drawable.pen_button_miss;
		idNone = R.drawable.pen_button_normal;

		drMiss = getResources().getDrawable(idMiss);
		drGoal = getResources().getDrawable(idGoal);
		drNone = getResources().getDrawable(idNone);
		
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

		llWCScorer = (LinearLayout) this.findViewById(R.id.llWCScorer);
		
		SharedPreferences pref = getPreferences(MODE_PRIVATE);
		btnRound.setText(pref.getString("btnRound", "Round"));
		btnGoal1.setText(pref.getString("btnGoal1", "0"));
		btnGoal2.setText(pref.getString("btnGoal2", "0"));
		txtGoal1.setText(pref.getString("txtGoal1", null));
		txtGoal2.setText(pref.getString("txtGoal2", null));
		txtTeam1.setText(pref.getString("TeamCL1", null));
		txtTeam2.setText(pref.getString("TeamCL2", null));
		txtDateTime.setText(pref.getString("Datetime", null));
		txtDateTime.setVisibility(pref.getInt("datimeVisible", View.GONE));

		tvPenA.setText(pref.getString("tvPenA", "0"));
		tvPenB.setText(pref.getString("tvPenB", "0"));
		llPen.setVisibility(pref.getInt("llPenVisible", View.GONE));
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
		
		llWCScorer.setVisibility(pref.getInt("llWCScorerVisible", View.GONE));
		
		btnKitColorA = (Button) this.findViewById(R.id.btnKitColorA);
		btnKitColorB = (Button) this.findViewById(R.id.btnKitColorB);
		imgTrophy = (ImageView) this.findViewById(R.id.imgWCTrophy);
		imgFlagA = (ImageView) this.findViewById(R.id.imgWCFlagA);
		imgFlagB = (ImageView) this.findViewById(R.id.imgWCFlagB);
		imgFlagA.setImageResource(setFlag(pref.getString("TeamCL1", "")));
		imgFlagB.setImageResource(setFlag(pref.getString("TeamCL2", "")));

		btnKitColorA.setBackgroundColor(pref.getInt("KitA", Color.WHITE));
		btnKitColorB.setBackgroundColor(pref.getInt("KitB", Color.WHITE));
		
		Calendar c = Calendar.getInstance();
		if (c.get(Calendar.SECOND) % 3 == 1)
			mp = MediaPlayer.create(this, R.raw.komanda);
		else if (c.get(Calendar.SECOND) % 3 == 2)
			mp = MediaPlayer.create(this, R.raw.color);
		else
			mp = MediaPlayer.create(this, R.raw.liveitup);
		mp.start();
		mpgoal = MediaPlayer.create(this, R.raw.torhymne_wc2018);

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

		final Resources res = getResources();

		final CharSequence Round[] = res.getStringArray(R.array.wcround);
		final CharSequence Group[] = res.getStringArray(R.array.group); 
		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final Dialog dlgKitColor = new Dialog(this);

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
				dlgRnd.setTitle("Match Info");

				Button btnOK = (Button) dlgRnd.findViewById(R.id.btnWCRndOK);
				Button btnCancel = (Button) dlgRnd.findViewById(R.id.btnWCRndCancel);
				final Button btnSpl = (Button) dlgRnd.findViewById(R.id.btnWCSelRound);
				final Button btnGrp = (Button) dlgRnd.findViewById(R.id.btnWCSelGroup);

				final Button btnVenue = (Button) dlgRnd.findViewById(R.id.btnVenueSel);

				final Button btnTeam1 = (Button) dlgRnd.findViewById(R.id.btnRndTeam1);
				final Button btnTeam2 = (Button) dlgRnd.findViewById(R.id.btnRndTeam2);
				
				final Button btnKCA = (Button) dlgRnd.findViewById(R.id.btnKCA);
				final Button btnKCB = (Button) dlgRnd.findViewById(R.id.btnKCB);
				
				btnKCA.setEnabled(false);
				btnKCA.setText("A");
				btnKCA.setBackgroundResource(android.R.drawable.btn_default);
				btnKCB.setBackgroundResource(android.R.drawable.btn_default);
				btnKCB.setEnabled(false);
				btnKCB.setText("B");
				btnSpl.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						dlgSpl.setItems(Round, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								btnSpl.setText(Round[which]);

								dialog.cancel();
								if (btnSpl.getText().toString().equals("Group Stage")) {
									btnGrp.setVisibility(View.VISIBLE);
								} else {
									btnGrp.setVisibility(View.GONE);
									if (btnSpl.getText().toString().equals("FINAL")) {
										btnVenue.setText("Moscow");
										btnDate.setText("15.07.2018");
										btnTime.setText("01:00");
										imgTrophy.setImageResource(R.drawable.trophywc);

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
						dlgGroup.setItems(Group, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								btnGrp.setText("Group " + Group[which]);
								dialog.cancel();
							}
						});

						dlgGroup.show();
					}
				});

				Cursor cVenue;
				final ArrayList<String> lstVenue = new ArrayList<String>();
				cVenue = mDB.getVenueByLeague(res.getString(R.string.worldcup));
				cVenue.moveToFirst();
				while (!cVenue.isAfterLast()) {
					Venue venue = new Venue(cVenue.getString(0), cVenue.getString(1), cVenue.getString(2),
							cVenue.getString(3));
					lstVenue.add(venue.getCity());
					cVenue.moveToNext();
				}
				cVenue.close();
				final String[] Venue = lstVenue.toArray(new String[0]);
				btnVenue.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dlgVenue.setItems(Venue, new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
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
						showDialog(DATE_PICKER_DIALOG);
					}

				});

				btnTime.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(TIME_PICKER_DIALOG);
					}
				});

				Cursor mCursor;
				final ArrayList<String> lstTeam = new ArrayList<String>();
				mCursor = mDB.getTeamByLeague(res.getString(R.string.worldcup));
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
							public void onClick(DialogInterface dialog, int which) {
								if (!Team[which].equals(btnTeam2.getText())) {
									btnTeam1.setText(Team[which]);
									kitColorA = setKitColorList(Team[which], res);
									btnKCA.setEnabled(true);
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
							public void onClick(DialogInterface dialog, int which) {
								if (!Team[which].equals(btnTeam1.getText())) {
									btnTeam2.setText(Team[which]);
									kitColorB = setKitColorList(Team[which], res);
									btnKCB.setEnabled(true);
									dialog.cancel();
								} else {
									errNoti("Please select a team!");
								}
							}
						});
						dlgTeam.show();
					}
				});
				
				btnKCA.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dlgKitColor.setContentView(R.layout.select_kitcolor);
						dlgKitColor.setTitle("Choose kit");
						
						Button btnHomeKit = (Button) dlgKitColor.findViewById(R.id.btnHomeKit);
						Button btnAwayKit = (Button) dlgKitColor.findViewById(R.id.btnAwayKit);
						Button btn3rdKit = (Button) dlgKitColor.findViewById(R.id.btn3rdKit);
						
						btnHomeKit.setBackgroundColor(Color.parseColor(kitColorA[0].toString()));
						btnAwayKit.setBackgroundColor(Color.parseColor(kitColorA[1].toString()));
						btn3rdKit.setBackgroundColor(Color.parseColor(kitColorA[2].toString()));
						
						btnHomeKit.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								btnKCA.setBackgroundColor(Color.parseColor(kitColorA[0].toString()));
								dlgKitColor.cancel();
								btnKCA.setText("");
							}
						});
						btnAwayKit.setOnClickListener(new View.OnClickListener() {
													
							@Override
							public void onClick(View v) {
								btnKCA.setBackgroundColor(Color.parseColor(kitColorA[1].toString()));
								dlgKitColor.cancel();
								btnKCA.setText("");
							}
						});
						btn3rdKit.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								btnKCA.setBackgroundColor(Color.parseColor(kitColorA[2].toString()));
								dlgKitColor.cancel();
								btnKCA.setText("");
							}
						});
						
						dlgKitColor.show();
					}
				});
				
				btnKCB.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dlgKitColor.setContentView(R.layout.select_kitcolor);
						dlgKitColor.setTitle("Choose kit");
						
						Button btnHomeKit = (Button) dlgKitColor.findViewById(R.id.btnHomeKit);
						Button btnAwayKit = (Button) dlgKitColor.findViewById(R.id.btnAwayKit);
						Button btn3rdKit = (Button) dlgKitColor.findViewById(R.id.btn3rdKit);
						
						btnHomeKit.setBackgroundColor(Color.parseColor(kitColorB[0].toString()));
						btnAwayKit.setBackgroundColor(Color.parseColor(kitColorB[1].toString()));
						btn3rdKit.setBackgroundColor(Color.parseColor(kitColorB[2].toString()));
						
						btnHomeKit.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								btnKCB.setBackgroundColor(Color.parseColor(kitColorB[0].toString()));
								dlgKitColor.cancel();
								btnKCB.setText("");
							}
						});
						btnAwayKit.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								btnKCB.setBackgroundColor(Color.parseColor(kitColorB[1].toString()));
								dlgKitColor.cancel();
								btnKCB.setText("");
							}
						});
						btn3rdKit.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								btnKCB.setBackgroundColor(Color.parseColor(kitColorB[2].toString()));
								dlgKitColor.cancel();
								btnKCB.setText("");
							}
						});
						
						dlgKitColor.show();
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
								|| btnTeam1.getText().toString().equals("Team A")
								|| btnTeam2.getText().toString().equals("Team B")) {
							errNoti("Please select round and teams!");
						} else if (btnGrp.getVisibility() == View.VISIBLE
								&& btnGrp.getText().toString().equals("Group")) {
							errNoti("Please select group!");
						} else if (btnKCA.getText().equals("A") || btnKCB.getText().equals("B")) {
							errNoti("Please select kit color!");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							if (btnGrp.getVisibility() == View.GONE)
								btnRound.setText(btnSpl.getText());
							else
								btnRound.setText(btnSpl.getText() + " - " + btnGrp.getText().toString());
							btnGoal1.setEnabled(true);
							btnGoal2.setEnabled(true);
							btnRound.setClickable(false);
							txtDateTime.setText(btnDate.getText() + " " + btnTime.getText() + " GMT+07 - "
									+ btnVenue.getText().toString());
							txtDateTime.setVisibility(View.VISIBLE);
							
							String teamA = txtTeam1.getText().toString();
							String teamB = txtTeam2.getText().toString();

							imgFlagA.setImageResource(setFlag(teamA));
							imgFlagB.setImageResource(setFlag(teamB));
							
							ColorDrawable clKCA = (ColorDrawable) btnKCA.getBackground();
							btnKitColorA.setBackgroundColor(clKCA.getColor());
							ColorDrawable clKCB = (ColorDrawable) btnKCB.getBackground();
							btnKitColorB.setBackgroundColor(clKCB.getColor());

							dlgRnd.cancel();
						}
					}

				});

				dlgRnd.show();
			}
		});
		
		
		final AlertDialog.Builder dlgScr = new AlertDialog.Builder(this);

		btnGoal1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!txtTeam2.getText().equals("GERMANY")) {
					mpgoal.start();
				} 
				
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("GOOAAAALLL!!!!");

				final EditText txtMin = (EditText) dlgGoal.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal.findViewById(R.id.tvGoalFor2);
				final TextView tvScoredBy = (TextView) dlgGoal.findViewById(R.id.tvScoredBy2);
				final TextView tvInThe = (TextView) dlgGoal.findViewById(R.id.tvInThe2);
				final TextView tvMin = (TextView) dlgGoal.findViewById(R.id.tvMinute2);
				final CheckBox chkOG = (CheckBox) dlgGoal.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal.findViewById(R.id.chkPen2);

				tvGoalFor.setText("GOAAALLLL for " + txtTeam1.getText());
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("");
				tvInThe.setText(" in the minute ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");
				 
				btnScorer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final ArrayList<String> lstPlayer = !chkOG.isChecked() 
								? getPlayerList(res, txtTeam1.getText().toString())
								: getPlayerList(res, txtTeam2.getText().toString());
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
						mpgoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int min = 0;
						if (!txtMin.getText().toString().isEmpty() && !txtMin.getText().toString().startsWith("45+")
								&& !txtMin.getText().toString().startsWith("90+")
								&& !txtMin.getText().toString().startsWith("105+")
								&& !txtMin.getText().toString().startsWith("120+")) {
							min = Integer.parseInt(txtMin.getText().toString());
						}

						if (txtMin.getText().toString().equals("") || btnScorer.getText().toString().equals("")) {
							errNoti("Please enter goal scorer and goal time!");
						} else if (min < 0 || min > 120) {
							errNoti("Invalid minute number!");
						} else {
							int goal = Integer.parseInt(btnGoal1.getText().toString());
							btnGoal1.setText(String.valueOf(goal + 1).toString());

							String mes = btnScorer.getText() + " " + txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoal1.getText().toString().contains(btnScorer.getText())) {
								String temp = txtGoal1.getText().toString().replace(btnScorer.getText().toString(),
										mes);
								txtGoal1.setText(temp);
							} else
								txtGoal1.append(mes + "\n");
							dlgGoal.cancel();
						}

						llWCScorer.setVisibility(View.VISIBLE);
					}
				});

				if (!txtTeam1.getText().toString().equals("") && !txtTeam2.getText().toString().equals("")) {
					dlgGoal.show();
				}
			}
		});

		btnGoal2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!txtTeam1.getText().equals("GERMANY")) {
					mpgoal.start();
				} 
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("GOOAAAALLL!!!!");

				final EditText txtMin = (EditText) dlgGoal.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal.findViewById(R.id.tvGoalFor2);
				final CheckBox chkOG = (CheckBox) dlgGoal.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal.findViewById(R.id.chkPen2);
				final TextView tvScoredBy = (TextView) dlgGoal.findViewById(R.id.tvScoredBy2);
				final TextView tvInThe = (TextView) dlgGoal.findViewById(R.id.tvInThe2);
				final TextView tvMin = (TextView) dlgGoal.findViewById(R.id.tvMinute2);

				tvGoalFor.setText("GOAAALLLL for " + txtTeam2.getText() + " ");
				tvScoredBy.setText("Goal scored by ");
				tvMin.setText("");
				tvInThe.setText(" in the minute ");
				chkOG.setText("Own goal");
				chkPen.setText("Penalty");
				
				btnScorer.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						final ArrayList<String> lstPlayer = !chkOG.isChecked() 
								? getPlayerList(res, txtTeam2.getText().toString())
								: getPlayerList(res, txtTeam1.getText().toString());
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
						mpgoal.pause();
						dlgGoal.cancel();
					}
				});

				btnGoalOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						int min = 0;
						if (!txtMin.getText().toString().isEmpty() && !txtMin.getText().toString().startsWith("45+")
								&& !txtMin.getText().toString().startsWith("90+")
								&& !txtMin.getText().toString().startsWith("105+")
								&& !txtMin.getText().toString().startsWith("120+")) {
							min = Integer.parseInt(txtMin.getText().toString());
						}

						if (txtMin.getText().toString().equals("") || btnScorer.getText().toString().equals("")) {
							errNoti("Please enter goal scorer and goal time!");
						} else if (min < 0 || min > 120) {
							errNoti("Invalid minute number!"); 
						} else {
							int goal = Integer.parseInt(btnGoal2.getText().toString());
							btnGoal2.setText(String.valueOf(goal + 1).toString());

							String mes = btnScorer.getText() + " " + txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(OG)";
							}
							if (chkPen.isChecked()) {
								mes += "(P)";
							}
							if (txtGoal2.getText().toString().contains(btnScorer.getText())) {
								String temp = txtGoal2.getText().toString().replace(btnScorer.getText().toString(),
										mes);
								txtGoal2.setText(temp);
							} else
								txtGoal2.append(mes + "\n");

							dlgGoal.cancel();
						}

						llWCScorer.setVisibility(View.VISIBLE);
					}
				});
				if (!txtTeam1.getText().toString().equals("") && !txtTeam2.getText().toString().equals("")) {
					dlgGoal.show();
				}
			}
		});

		txtGoal1.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (!txtGoal1.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoal1.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							txtGoal1.setText(txtEdit.getText().toString());
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

		txtGoal2.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (!txtGoal2.getText().toString().equals("")) {
					dlgEdit.setContentView(R.layout.edit);
					final EditText txtEdit = (EditText) dlgEdit.findViewById(R.id.txtEdit);
					Button btnEditOK = (Button) dlgEdit.findViewById(R.id.btnEditOK);
					Button btnEditCancel = (Button) dlgEdit.findViewById(R.id.btnEditCancel);

					txtEdit.setText(txtGoal2.getText().toString());

					btnEditOK.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							txtGoal2.setText(txtEdit.getText().toString());
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
	
	private CharSequence[] setKitColorList(String team, Resources res) {
		if (team.equals("")) {
			return null;
		} else if (team.equals("BRAZIL")) {
			return res.getStringArray(R.array.bra);
		} else if (team.equals("MEXICO")) {
			return res.getStringArray(R.array.mex);
		} else if (team.equals("CROATIA")) {
			return res.getStringArray(R.array.cro);
		} else if (team.equals("SERBIA")) {
			return res.getStringArray(R.array.srb);
		} else if (team.equals("POLAND")) {
			return res.getStringArray(R.array.pol);
		} else if (team.equals("AUSTRALIA")) {
			return res.getStringArray(R.array.aus);
		} else if (team.equals("ARGENTINA")) {
			return res.getStringArray(R.array.arg);
		} else if (team.equals("BELGIUM")) {
			return res.getStringArray(R.array.bel);
		} else if (team.equals("ICELAND")) {
			return res.getStringArray(R.array.isl);
		} else if (team.equals("SWEDEN")) {
			return res.getStringArray(R.array.swe);
		} else if (team.equals("DENMARK")) {
			return res.getStringArray(R.array.den);
		} else if (team.equals("COLOMBIA")) {
			return res.getStringArray(R.array.col);
		} else if (team.equals("COSTA RICA")) {
			return res.getStringArray(R.array.crc);
		} else if (team.equals("SAUDI ARABIA")) {
			return res.getStringArray(R.array.ksa);
		} else if (team.equals("ENGLAND")) {
			return res.getStringArray(R.array.eng);
		} else if (team.equals("SPAIN")) {
			return res.getStringArray(R.array.esp);
		} else if (team.equals("FRANCE")) {
			return res.getStringArray(R.array.fra);
		} else if (team.equals("GERMANY")) {
			return res.getStringArray(R.array.ger);
		} else if (team.equals("PANAMA")) {
			return res.getStringArray(R.array.pan);
		} else if (team.equals("PERU")) {
			return res.getStringArray(R.array.per);
		} else if (team.equals("TUNISIA")) {
			return res.getStringArray(R.array.tun);
		} else if (team.equals("IR IRAN")) {
			return res.getStringArray(R.array.irn);
		} else if (team.equals("MOROCCO")) {
			return res.getStringArray(R.array.mar);
		} else if (team.equals("JAPAN")) {
			return res.getStringArray(R.array.jpn);
		} else if (team.equals("KOREA REPUBLIC")) {
			return res.getStringArray(R.array.kor);
		} else if (team.equals("SENEGAL")) {
			return res.getStringArray(R.array.sen);
		} else if (team.equals("NIGERIA")) {
			return res.getStringArray(R.array.nga);
		} else if (team.equals("PORTUGAL")) {
			return res.getStringArray(R.array.por);
		} else if (team.equals("RUSSIA")) {
			return res.getStringArray(R.array.rus);
		} else if (team.equals("SWITZERLAND")) {
			return res.getStringArray(R.array.sui);
		} else if (team.equals("URUGUAY")) {
			return res.getStringArray(R.array.uru);
		} else if (team.equals("EGYPT")) {
			return res.getStringArray(R.array.egy);
		}

		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.world_cup2014, menu);
		miDB = menu.findItem(R.id.wcsavedb);
		return true;
	}

	@Override
	protected void onStop() {
		super.onStop();

		saveData();
	}

	@Override
	protected void onPause() {
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
		editor.putInt("llWCScorerVisible", llWCScorer.getVisibility());
		int datimeVisible = txtDateTime.getVisibility();
		editor.putInt("datimeVisible", datimeVisible);

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
		
		ColorDrawable kitColorA = (ColorDrawable) btnKitColorA.getBackground();
		ColorDrawable kitColorB = (ColorDrawable) btnKitColorB.getBackground();
		
		int colorKitA = kitColorA.getColor();
		editor.putInt("KitA", colorKitA);
		int colorKitB = kitColorB.getColor();
		editor.putInt("KitB", colorKitB);
		
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
					onPause();
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
		} else if (id == R.id.wcsavedb) {
			int g1 = Integer.parseInt(btnGoal1.getText().toString());
			int g2 = Integer.parseInt(btnGoal2.getText().toString());

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
		} else if (id == R.id.wcrefresh) {
			refresh();
		} else if (id == R.id.wcfulltime && !btnRound.getText().toString().equals("Round")) {
			int g1 = Integer.parseInt(btnGoal1.getText().toString());
			int g2 = Integer.parseInt(btnGoal2.getText().toString());

			if (btnRound.getText().toString().contains("Group Stage")) {
				if (g1 > g2) {
					resultNoti(txtTeam1.getText().toString());
					miDB.setVisible(true);
				} else if (g1 < g2) {
					resultNoti(txtTeam2.getText().toString());
					miDB.setVisible(true);
				} else {
					resultNoti("");
					miDB.setVisible(true);
				}
			} else {
				if (g1 > g2) {
					resultNoti(txtTeam1.getText().toString());
					miDB.setVisible(true);
				} else if (g1 < g2) {
					resultNoti(txtTeam2.getText().toString());
					miDB.setVisible(true);
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

	private void addToDB(int g1, int g2, int pA, int pB) {
		String scorerListA = txtGoal1.getText().toString();
		String scorerListB = txtGoal2.getText().toString();

		scorerListA.replace("\n", " ");
		scorerListB.replace("\n", " ");
		final Resources res = getResources(); 
		mDB.addMatch(res.getString(R.string.worldcup), btnRound.getText().toString(), txtTeam1.getText().toString(),
				txtTeam2.getText().toString(), g1, g2, scorerListA, scorerListB, pA, pB);
	}

	void errNoti(String mes) {
		Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
	}

	protected ArrayList<String> getPlayerList(final Resources res, String team) {
		Cursor cPlayer;
		final ArrayList<String> lstPlayer = new ArrayList<String>();
		cPlayer = mDB.getPlayerByLeagueAndTeam(res.getString(R.string.worldcup), team, 1);
		cPlayer.moveToFirst();
		while (!cPlayer.isAfterLast()) {
			Player player = new Player(cPlayer.getString(1), cPlayer.getString(2), cPlayer.getInt(3),
					cPlayer.getString(4), cPlayer.getString(5), cPlayer.getString(6), cPlayer.getInt(7),
					cPlayer.getString(8));
			lstPlayer.add(player.getKitNo() + "-" + player.getLastname());
			cPlayer.moveToNext();
		}
		cPlayer.close();
		return lstPlayer;
	}
	
	void resultNoti(String team) {
		String mes = "";
		if (!team.equals("")) {
			if (btnRound.getText().toString().equals("FINAL"))
				mes = team + " - WORLD CHAMPION 2018!!!";
			else if (btnRound.getText().toString().contains("Group Stage"))
				mes = team + " has won the match!!!";
			else if (btnRound.getText().toString().contains("Round of 16"))
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
			return R.drawable.trophywc;
		} else if (team.equals("BRAZIL")) {
			return R.drawable.bra_wc;
		} else if (team.equals("MEXICO")) {
			return R.drawable.mex_wc;
		} else if (team.equals("CROATIA")) {
			return R.drawable.cro_wc;
		} else if (team.equals("SERBIA")) {
			return R.drawable.srb_wc;
		} else if (team.equals("POLAND")) {
			return R.drawable.pol_wc;
		} else if (team.equals("AUSTRALIA")) {
			return R.drawable.aus_wc;
		} else if (team.equals("ARGENTINA")) {
			return R.drawable.arg_wc;
		} else if (team.equals("BELGIUM")) {
			return R.drawable.bel_wc;
		} else if (team.equals("ICELAND")) {
			return R.drawable.isl_wc;
		} else if (team.equals("SWEDEN")) {
			return R.drawable.swe_wc;
		} else if (team.equals("DENMARK")) {
			return R.drawable.den_wc;
		} else if (team.equals("COLOMBIA")) {
			return R.drawable.col_wc;
		} else if (team.equals("COSTA RICA")) {
			return R.drawable.crc_wc;
		} else if (team.equals("SAUDI ARABIA")) {
			return R.drawable.ksa_wc;
		} else if (team.equals("ENGLAND")) {
			return R.drawable.eng_wc;
		} else if (team.equals("SPAIN")) {
			return R.drawable.esp_wc;
		} else if (team.equals("FRANCE")) {
			return R.drawable.fra_wc;
		} else if (team.equals("GERMANY")) {
			return R.drawable.ger_wc;
		} else if (team.equals("PANAMA")) {
			return R.drawable.pan_wc;
		} else if (team.equals("PERU")) {
			return R.drawable.per_wc;
		} else if (team.equals("TUNISIA")) {
			return R.drawable.tun_wc;
		} else if (team.equals("IR IRAN")) {
			return R.drawable.irn_wc;
		} else if (team.equals("MOROCCO")) {
			return R.drawable.mar_wc;
		} else if (team.equals("JAPAN")) {
			return R.drawable.jpn_wc;
		} else if (team.equals("KOREA REPUBLIC")) {
			return R.drawable.kor_wc;
		} else if (team.equals("SENEGAL")) {
			return R.drawable.sen_wc;
		} else if (team.equals("NIGERIA")) {
			return R.drawable.nga_wc;
		} else if (team.equals("PORTUGAL")) {
			return R.drawable.por_wc;
		} else if (team.equals("RUSSIA")) {
			return R.drawable.rus_wc;
		} else if (team.equals("SWITZERLAND")) {
			return R.drawable.sui_wc;
		} else if (team.equals("URUGUAY")) {
			return R.drawable.uru_wc;
		} else if (team.equals("EGYPT")) {
			return R.drawable.egy_wc;
		}

		return R.drawable.trophywc;
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
		txtTeam1.setText("");
		txtTeam2.setText("");
		txtGoal1.setText("");
		txtGoal2.setText("");
		txtDateTime.setText("");
		txtDateTime.setVisibility(View.GONE);
		btnRound.setText("Round");
		btnRound.setClickable(true);
		btnGoal1.setText("0");
		btnGoal2.setText("0");
		btnGoal1.setEnabled(false);
		btnGoal2.setEnabled(false);

		miDB.setVisible(false);
		daySelected = 0;
		monthSelected = 0;
		yearSelected = 0;
		hourSelected = 0;
		minuteSelected = 0;

		tvPenA.setText("0");
		tvPenB.setText("0");
		llPen.setVisibility(View.GONE);
		llWCScorer.setVisibility(View.GONE);
		refreshPen();
		tvPenStage.setText("0");
		imgFlagA.setImageResource(R.drawable.trophywc);
		imgFlagB.setImageResource(R.drawable.trophywc);
		
		btnKitColorA.setBackgroundColor(Color.WHITE);
		btnKitColorB.setBackgroundColor(Color.WHITE);
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

	public String standardizeDate(int daySelected, int monthSelected, int yearSelected) {
		String day = (daySelected > 9) ? String.valueOf(daySelected) : "0" + daySelected;
		String month = (monthSelected > 9) ? String.valueOf(monthSelected) : "0" + monthSelected;
		return day + "." + month + "." + yearSelected;
	}

	public String standardizeTime(int hourSelected, int minuteSelected) {
		String hour = (hourSelected > 9) ? String.valueOf(hourSelected) : "0" + hourSelected;
		String minute = (minuteSelected > 9) ? String.valueOf(minuteSelected) : "0" + minuteSelected;
		return hour + ":" + minute;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_world_cup2014, container, false);
			return rootView;
		}
	}
}
