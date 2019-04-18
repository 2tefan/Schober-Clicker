package at.schiebung.stefan.schober0008;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.OrientationEventListener;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Stefan on 22.05.2018.
 */

public class Vars
{
	static String saved_file_double = "150";
	static String saved_file_boolean = "149";
	static String saved_file_double_upgrades_cost = "148";
	static String saved_file_double_upgrades_improvement = "147";
	static String saved_file_double_upgrades_cost_multi = "146";
	static String saved_file_double_upgrades_count = "145";

	static String preferences_name = "schober-preferences";
	static String preferences_key = "thisistheworldbestquerschoberinmyschober";

	static double dClicks = 0;
	static double dClickStep = 0.1;
	static double dClickStepAuto = 0;

	static double Clicks = dClicks;
	static double ClickStep = dClickStep;
	static double ClickStepAuto = dClickStepAuto;

	static int upgrades = 3;
	static double[] UpgradeCost = new double[upgrades];
	static double[] UpgradeCostMulti = new double[upgrades];
	static double[] UpgradeImprovement = new double[upgrades];
	static double[] UpgradeCount = new double[upgrades];

	static double[] dUpgradeCost = new double[upgrades];
	static double[] dUpgradeCostMulti = new double[upgrades];
	static double[] dUpgradeImprovement = new double[upgrades];
	static double[] dUpgradeCount = new double[upgrades];

	static String[] sivorsilben = {"",
			"Kilo",
			"Mega",
			"Giga",
			"Tera",
			"Peta",
			"Exa",
			"Zetta",
			"Yotta",
			"Nina",
			"Tena",
			"Tenakilo",
			"Tenakilo",
			"Tenamega",
			"Tenagiga",
			"Tenatera",
			"Tenapeta",
			"Tenaexa",
			"Tenazetta",
			"Tenayotta",
			"Tenanina",
			"Bitena",
			"Bitenakilo"};

	public static void Werte()
	{
		dUpgradeCost[0] = 1;
		dUpgradeCost[1] = 100;
		dUpgradeCost[2] = 500;

		dUpgradeImprovement[0] = 1.3;
		dUpgradeImprovement[1] = 0.0005;
		dUpgradeImprovement[2] = 0.0025;

		dUpgradeCostMulti[0] = 1.5;
		dUpgradeCostMulti[1] = 1.1;
		dUpgradeCostMulti[2] = 1.1;

		dUpgradeCount[0] = 0;
		dUpgradeCount[1] = 0;
		dUpgradeCount[2] = 0;

		for (int i = 0; i < upgrades; i++)
		{
			UpgradeCost[i] = dUpgradeCost[i];
			UpgradeCostMulti[i] = dUpgradeCostMulti[i];
			UpgradeImprovement[i] = dUpgradeImprovement[i];
			UpgradeCount[i] = dUpgradeCount[i];
		}

		Clicks = dClicks;
		ClickStep = dClickStep;
		ClickStepAuto = dClickStepAuto;
	}

	static DecimalFormat decimalFormat = new DecimalFormat("0.##" + " " + Vars.sivorsilben[Vars.vorsilbe] + "Schober");
	static DecimalFormat schoberpersecound = new DecimalFormat("+" + "0.##" + " SPS");

	static boolean bm = true;

	static MediaPlayer player;
	static boolean soundclicks = false;
	static int vorsilbe = 0;

	static int RC_SIGN_IN = 10;
	static final int RC_LEADERBOARD_UI = 9004;
	static final int RC_ACHIEVEMENT_UI = 9003;
	static Context main;

	static Timer timer;
	static TimerTask timerTaskAuto;
	static TimerTask timerTaskChanges;
	final static Handler handler = new Handler();

	static int pfeilRotateDuration = 700;
	static int popupDuration = 1000;
	static int popupDistance = -4000;
	static boolean popupVisble = false;
	static boolean backpressed = false;
	static boolean rotated = false;

	static boolean sivorsilbenoverflow = false;

	static String ppUrlSchober = "https://sites.google.com/view/schober/privacy-policy";
}
