package com.project.livescore.activities;

import java.util.ArrayList;

import com.project.livescore.data.DBAdapter;
import com.project.livescore.data.Player;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BundesligaActivity extends Activity {

	Button btnSpieltag, btnTor1, btnTor2;
	Button btnKitColorA, btnKitColorB;
	TextView txtTeam1, txtTeam2, txtTor1, txtTor2;
	MediaPlayer mp;
	ImageView imgLogo;
	static DBAdapter mDB;
	MenuItem miDB;

	MediaPlayer mpGoal;

	CharSequence kitColorA[];
	CharSequence kitColorB[];
	CharSequence kit[] = {"Home", "Away", "3rd Kit"};
	
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

		btnKitColorA = (Button) this.findViewById(R.id.btnKitColorBLA);
		btnKitColorB = (Button) this.findViewById(R.id.btnKitColorBLB);

		btnKitColorA.setBackgroundColor(pref.getInt("KitA", Color.WHITE));
		btnKitColorB.setBackgroundColor(pref.getInt("KitB", Color.WHITE));
		
		mpGoal = MediaPlayer.create(this, R.raw.torhymne);
		mp = MediaPlayer.create(this, R.raw.bundesligaintro);
		mp.start();
		final Resources res = getResources();

		final CharSequence Spieltag[] = res.getStringArray(R.array.spieltag);

		final Dialog dlgRnd = new Dialog(this);
		final Dialog dlgGoal = new Dialog(this);
		final Dialog dlgEdit = new Dialog(this);
		final Dialog dlgKitColor = new Dialog(this);
		final AlertDialog.Builder dlgSpl = new AlertDialog.Builder(this);
		final AlertDialog.Builder dlgTeam = new AlertDialog.Builder(this);

		imgLogo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mp.isPlaying())
					mp.pause();
				else
					mp.start();
			}
		});

		btnSpieltag.setOnClickListener(new View.OnClickListener() {

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

				final Button btnKCA = (Button) dlgRnd.findViewById(R.id.btnKCBLA);
				final Button btnKCB = (Button) dlgRnd.findViewById(R.id.btnKCBLB);
				
				btnKCA.setEnabled(false);
				btnKCA.setText("A");
				btnKCA.setBackgroundResource(android.R.drawable.btn_default);
				btnKCB.setBackgroundResource(android.R.drawable.btn_default);
				btnKCB.setEnabled(false);
				btnKCB.setText("B");
				
				btnSpl.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dlgSpl.setItems(Spieltag, new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								btnSpl.setText(Spieltag[which] + ". Spieltag");
								dialog.cancel();
							}
						});
						dlgSpl.show();
					}
				});

				btnKCA.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dlgKitColor.setContentView(R.layout.select_kitcolor);
						dlgKitColor.setTitle("Trikot auswählen");
						
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
						dlgKitColor.setTitle("Trikot auswählen");
						
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
				
				Cursor mCursor;
				final ArrayList<String> lstTeam = new ArrayList<String>();
				mCursor = mDB.getTeamByLeague(res.getString(R.string.bundesliga));
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
									kitColorA = setKitColorList(Team[which], res);
									btnKCA.setEnabled(true);
									dialog.cancel();
								} else {
									errNoti("Bitte wählen ein anderes Team aus!");
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
									kitColorB = setKitColorList(Team[which], res);
									btnKCB.setEnabled(true);
									dialog.cancel();
								} else {
									errNoti("Bitte wählen Sie ein anderes Team aus!");
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
							errNoti("Bitte wählen Sie ein anderes Team aus!");
						} else if (btnKCA.getText().equals("A") || btnKCB.getText().equals("B")) {
							errNoti("Bitte wählen Sie ein Trikot aus!");
						} else {
							txtTeam1.setText(btnTeam1.getText());
							txtTeam2.setText(btnTeam2.getText());
							btnSpieltag.setText(btnSpl.getText());
							btnTor1.setEnabled(true);
							btnTor2.setEnabled(true);
							btnSpieltag.setClickable(false);
							ColorDrawable clKCA = (ColorDrawable) btnKCA.getBackground();
							btnKitColorA.setBackgroundColor(clKCA.getColor());
							ColorDrawable clKCB = (ColorDrawable) btnKCB.getBackground();
							btnKitColorB.setBackgroundColor(clKCB.getColor());
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

		final AlertDialog.Builder dlgScr = new AlertDialog.Builder(this);

		btnTor1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!txtTeam2.getText().toString()
						.equalsIgnoreCase("FC Bayern München")) {
					mpGoal.start();
				}
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("TOOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal
						.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal
						.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal
						.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal
						.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal
						.findViewById(R.id.tvGoalFor2);
				final CheckBox chkOG = (CheckBox) dlgGoal
						.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal
						.findViewById(R.id.chkPen2);

				tvGoalFor.setText("TOORRRR für " + txtTeam1.getText() + " ");

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
								|| btnScorer.getText().toString().equals("")) {
							errNoti("Bitte geben Sie den Zeitpunkt und den Torschützer ein!");
						}
						else if (min < 0 || min > 120) {
							errNoti("Ungültige Nummer!");
						} else {
							int goal = Integer.parseInt(btnTor1.getText()
									.toString());
							btnTor1.setText(String.valueOf(goal + 1).toString());
							String mes = btnScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(Et)";
							}
							if (chkPen.isChecked()) {
								mes += "(Elf.)";
							}
							if (txtTor1.getText().toString()
									.contains(btnScorer.getText())) {
								String temp = txtTor1
										.getText()
										.toString()
										.replace(
												btnScorer.getText().toString(),
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
				dlgGoal.setContentView(R.layout.goalalert_new);
				dlgGoal.setTitle("TOOORRRR!!!!");

				final EditText txtMin = (EditText) dlgGoal
						.findViewById(R.id.txtMinute2);
				final Button btnScorer = (Button) dlgGoal
						.findViewById(R.id.btnScorerList);
				final Button btnGoalOK = (Button) dlgGoal
						.findViewById(R.id.btnGoalOK2);
				final Button btnGoalCnl = (Button) dlgGoal
						.findViewById(R.id.btnGoalCancel2);
				final TextView tvGoalFor = (TextView) dlgGoal
						.findViewById(R.id.tvGoalFor2);
				final CheckBox chkOG = (CheckBox) dlgGoal
						.findViewById(R.id.chkOG2);
				final CheckBox chkPen = (CheckBox) dlgGoal
						.findViewById(R.id.chkPen2);

				tvGoalFor.setText("TOORRRR für " + txtTeam2.getText() + " ");

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
								|| btnScorer.getText().toString().equals("")) {
							errNoti("Bitte geben Sie den Zeitpunkt und den Torschützer ein!");
						}
						else if (min < 0 || min > 120) {
							errNoti("Ungültige Nummer!");
						} else {
							int goal = Integer.parseInt(btnTor2.getText()
									.toString());
							btnTor2.setText(String.valueOf(goal + 1).toString());
							String mes = btnScorer.getText() + " "
									+ txtMin.getText() + "'";
							if (chkOG.isChecked()) {
								mes += "(E.t.)";
							}
							if (chkPen.isChecked()) {
								mes += "(E.m)";
							}
							if (txtTor2.getText().toString()
									.contains(btnScorer.getText())) {
								String temp = txtTor2
										.getText()
										.toString()
										.replace(
												btnScorer.getText().toString(),
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
							txtTor1.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
						}
					});

					btnEditCancel
							.setOnClickListener(new View.OnClickListener() {

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

		txtTor2.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
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
							txtTor2.setText(txtEdit.getText().toString());
							dlgEdit.cancel();
						}
					});

					btnEditCancel
							.setOnClickListener(new View.OnClickListener() {

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
		
		btnKitColorA.setBackgroundColor(Color.WHITE);
		btnKitColorB.setBackgroundColor(Color.WHITE);
	}

	@Override
	protected void onPause() {
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

		ColorDrawable kitColorA = (ColorDrawable) btnKitColorA.getBackground();
		ColorDrawable kitColorB = (ColorDrawable) btnKitColorB.getBackground();
		
		int colorKitA = kitColorA.getColor();
		editor.putInt("KitA", colorKitA);
		int colorKitB = kitColorB.getColor();
		editor.putInt("KitB", colorKitB);
		
		editor.commit();

	}
	
	private CharSequence[] setKitColorList(String team, Resources res) {
		if (team.equals("")) {
			return null;
		} else if (team.equals("FC BAYERN MÜNCHEN")) {
			return res.getStringArray(R.array.fcb);
		} else if (team.equals("BORUSSIA DORTMUND")) {
			return res.getStringArray(R.array.bvb);
		} else if (team.equals("FC SCHALKE 04")) {
			return res.getStringArray(R.array.s04);
		} else if (team.equals("TSG 1899 HOFFENHEIM")) {
			return res.getStringArray(R.array.tsg);
		} else if (team.equals("BAYER 04 LEVERKUSEN")) {
			return res.getStringArray(R.array.b04);
		} else if (team.equals("RB LEIPZIG")) {
			return res.getStringArray(R.array.rbl);
		} else if (team.equals("VFB STUTTGART")) {
			return res.getStringArray(R.array.vfb);
		} else if (team.equals("EINTRACHT FRANKFURT")) {
			return res.getStringArray(R.array.sge);
		} else if (team.equals("BORUSSIA MÖNCHENGLADBACH")) {
			return res.getStringArray(R.array.bmg);
		} else if (team.equals("FC AUGSBURG")) {
			return res.getStringArray(R.array.fca);
		} else if (team.equals("HERTHA BSC")) {
			return res.getStringArray(R.array.bsc);
		} else if (team.equals("SV WERDER BREMEN")) {
			return res.getStringArray(R.array.svw);
		} else if (team.equals("HANNOVER 96")) {
			return res.getStringArray(R.array.h96);
		} else if (team.equals("1. FSV MAINZ 05")) {
			return res.getStringArray(R.array.m05);
		} else if (team.equals("SPORT-CLUB FREIBURG")) {
			return res.getStringArray(R.array.scf);
		} else if (team.equals("VFL WOLFSBURG")) {
			return res.getStringArray(R.array.wob);
		} else if (team.equals("FORTUNA DÜSSELDORF 1895 E.V.")) {
			return res.getStringArray(R.array.f95);
		} else if (team.equals("1. FC NÜRNBERG")) {
			return res.getStringArray(R.array.fcn);
		} else if (team.equals("1. FC KÖLN")) {
			return res.getStringArray(R.array.koe);
		} else if (team.equals("1. FC UNION BERLIN")) {
			return res.getStringArray(R.array.fcu);
		} else if (team.equals("SC PADERBORN")) {
			return res.getStringArray(R.array.scp);
		} 

		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
