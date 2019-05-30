package at.schiebung.stefan.schober0008;

import android.media.MediaPlayer;
import android.os.Handler;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Stefan on 22.05.2018.
 */

class Vars
{
	static final String saved_file_double                     = "150";
	static final String saved_file_boolean                    = "149";
	static final String saved_file_double_upgrades_cost       = "148";
	// --Commented out by Inspection (31.05.2019 01:01):static       String saved_file_double_upgrades_improvement = "147";
	static final String saved_file_double_upgrades_cost_multi = "146";
	static final String saved_file_double_upgrades_count      = "145";

	static final         String        preferences_name    = "schober-preferences";
	static final         String        preferences_key     = "thisistheworldbestquerschoberinmyschober";
	static final         double        dClickStep          = 0.1;
	static final         double        dClickStepAuto      = 0;
	static final         String[]      sivorsilben         = {"",
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
	static final         DecimalFormat schoberpersecound   = new DecimalFormat("+" + "0.##" + " SPS");
	static final         boolean       soundclicks         = false;
	static final         int           RC_SIGN_IN          = 10;
	static final         int           RC_LEADERBOARD_UI   = 9004;
	static final         int           RC_ACHIEVEMENT_UI   = 9003;
	static final         int           pfeilRotateDuration = 700;
	static final         int           popupDistance       = -4000;
	static final         String        ppUrlSchober        = "https://sites.google.com/view/schober/privacy-policy";
	final static         Handler       handler             = new Handler();
	private static final double        dClicks             = 0;
	private static final int           upgrades            = 3;
	static final         double[]      UpgradeCost         = new double[upgrades];
	static final         double[]      UpgradeCostMulti    = new double[upgrades];

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
	static final         double[]      UpgradeImprovement  = new double[upgrades];
	static final         double[]      UpgradeCount        = new double[upgrades];
	static final         double[]      dUpgradeCost        = new double[upgrades];
	static final         double[]      dUpgradeCostMulti   = new double[upgrades];
	private static final double[]      dUpgradeImprovement = new double[upgrades];
	private static final double[]      dUpgradeCount       = new double[upgrades];
	static               double        Clicks              = dClicks;
	static               double        ClickStep           = dClickStep;
	static               double        ClickStepAuto       = dClickStepAuto;
	// --Commented out by Inspection (31.05.2019 01:06):static       Context main;
	static               boolean       bm                  = true;
	static               Timer         timer;
	static               TimerTask     timerTaskAuto;
	static               TimerTask     timerTaskChanges;
	static               MediaPlayer   player;
	static               int           vorsilbe            = 0;
	static               DecimalFormat decimalFormat       = new DecimalFormat("0.##" + " " + Vars.sivorsilben[Vars.vorsilbe] + "Schober");
	static               int           popupDuration       = 1000;
	static               boolean       popupVisble         = false;
	static               boolean       backpressed         = false;

	static boolean sivorsilbenoverflow = false;
	static boolean rotated             = false;
}
