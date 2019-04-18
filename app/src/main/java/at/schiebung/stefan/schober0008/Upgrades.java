package at.schiebung.stefan.schober0008;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Stefan on 22.05.2018.
 */

public class Upgrades
{

	public static void Upgrades(int x, Context context, TextView txtUpgradeCost)
	{
		if (Vars.Clicks >= Vars.UpgradeCost[x])
		{
			if (x >= 1)
			{
				Vars.ClickStepAuto += Vars.UpgradeImprovement[x];
			}
			else
			{
				Vars.ClickStep *= Vars.UpgradeImprovement[x];
			}

			Vars.Clicks -= Vars.UpgradeCost[x];
			Vars.UpgradeCost[x] *= Vars.UpgradeCostMulti[x];

			//			double tempUpgradeCost = Vars.UpgradeCost[x];
			//			double tempClicks      = Vars.Clicks;
			//
			//			tempUpgradeCost = round(tempUpgradeCost, 2);
			//			tempClicks = round(tempClicks, 2);
			Vars.UpgradeCount[x]++;

			txtUpgradeCost.setText(Upgrades.format(Vars.UpgradeCost[x]));
		}
		else
		{
			Upgrades.toast(x, context, txtUpgradeCost);
		}
	}

	public static void toast(int x, final Context context, final TextView txtUpgradeCost)
	{
		txtUpgradeCost.setTextColor(Color.RED);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			public void run()
			{
				txtUpgradeCost.setTextColor(ContextCompat.getColor(context,
				                                                   R.color.textview_color1));
			}
		}, 5000);   //5 seconds
	}

	public static String format(double d)
	{
		String output;
		if(Vars.sivorsilbenoverflow)
		{
			output = Vars.decimalFormat.format(d);
		}
		else
		{
			output = Vars.decimalFormat.format(d * Math.pow(1000, (double) -Vars.vorsilbe));
		}
		return output;
	}

	public static String formatsps(double d)
	{
		d *= 20;
		//return Vars.schoberpersecound.format(d * Math.pow(1000, (double) -Vars.vorsilbe));
		return Vars.schoberpersecound.format(d);
	}
}
