package at.schiebung.stefan.schober0008;

import android.content.Context;

import static at.schiebung.stefan.schober0008.Vars.saved_file_boolean;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_cost;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_cost_multi;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_count;

public class Saves
{
	public static void loadSaves(Context context)
	{
		SecurePreferences preferences = new SecurePreferences(context,
		                                                      Vars.preferences_name,
		                                                      Vars.preferences_key,
		                                                      true);
		String s1 = preferences.getString(saved_file_double);
		String s2 = preferences.getString(saved_file_boolean);
		String s3 = preferences.getString(saved_file_double_upgrades_cost);
		String s4 = preferences.getString(saved_file_double_upgrades_cost_multi);
		String s5 = preferences.getString(saved_file_double_upgrades_count);

		try
		{
			if (s1 != null)
			{
				String[] array = s1.split(",");
				double[] d     = new double[array.length];

				for (int i = 0; i < array.length; i++)
				{
					d[i] = Double.parseDouble(array[i]);
				}
				Vars.Clicks = d[0];
				Vars.ClickStep = d[1];
				Vars.ClickStepAuto = d[2];
			}

			if (s2 != null)
			{
				String[]  array = s2.split(",");
				boolean[] b     = new boolean[array.length];

				for (int i = 0; i < array.length; i++)
				{
					b[i] = Boolean.parseBoolean(array[i]);
				}
				Vars.bm = b[0];
			}

			if (s3 != null)
			{
				String[] array = s3.split(",");

				for (int i = 0; i < array.length; i++)
				{
					Vars.UpgradeCost[i] = Double.parseDouble(array[i]);
				}
			}

			if (s4 != null)
			{
				String[] array = s4.split(",");

				for (int i = 0; i < array.length; i++)
				{
					Vars.UpgradeCostMulti[i] = Double.parseDouble(array[i]);
				}
			}

			if (s5 != null)
			{
				String[] array = s5.split(",");

				for (int i = 0; i < array.length; i++)
				{
					Vars.UpgradeCount[i] = Double.parseDouble(array[i]);
				}
			}
		}
		catch (Exception e)
		{
			Vars.Werte();
		}

		verify();
	}

	public static void saveSaves(Context context)
	{
		SecurePreferences preferences = new SecurePreferences(context,
		                                                      Vars.preferences_name,
		                                                      Vars.preferences_key,
		                                                      true);

		//1. Save-Datei
		double[] d1 = new double[] {Vars.Clicks, Vars.ClickStep, Vars.ClickStepAuto};

		String s1 = "";

		for (int i = 0; i < d1.length; i++)
		{
			s1 += String.valueOf(d1[i] + ",");
		}

		//2. Save-Datei
		boolean[] b2 = new boolean[] {Vars.bm};

		String s2 = "";

		for (int i = 0; i < b2.length; i++)
		{
			s2 += String.valueOf(b2[i] + ",");
		}

		//3. Save-Datei
		double[] d3 = new double[Vars.UpgradeCost.length];

		String s3 = "";

		for (int i = 0; i < d3.length; i++)
		{
			d3[i] = Vars.UpgradeCost[i];
			s3 += String.valueOf(d3[i] + ",");
		}

		//4. Save-Datei
		double[] d4 = new double[Vars.UpgradeCostMulti.length];

		String s4 = "";

		for (int i = 0; i < d4.length; i++)
		{
			d4[i] = Vars.UpgradeCostMulti[i];
			s4 += String.valueOf(d4[i] + ",");
		}

		//5. Save-Datei
		double[] d5 = new double[Vars.UpgradeCount.length];

		String s5 = "";

		for (int i = 0; i < d5.length; i++)
		{
			d5[i] = Vars.UpgradeCount[i];
			s5 += String.valueOf(d5[i] + ",");
		}

		preferences.put(Vars.saved_file_double, s1);
		preferences.put(Vars.saved_file_boolean, s2);
		preferences.put(Vars.saved_file_double_upgrades_cost, s3);
		preferences.put(Vars.saved_file_double_upgrades_cost_multi, s4);
		preferences.put(Vars.saved_file_double_upgrades_count, s5);
	}

	public static void verify()
	{
		double  tempCost          = 0;
		double  tempClickStep     = Vars.dClickStep;
		double  tempClickStepAuto = Vars.dClickStepAuto;
		boolean verified          = true;
		boolean costV             = true;
		boolean clickStepV        = true;

		for (int i = 0; i < Vars.UpgradeCount.length; i++)
		{
			tempCost = Vars.dUpgradeCost[i];

			for (int x = 0; x < Vars.UpgradeCount[i]; x++)
			{
				tempCost *= Vars.dUpgradeCostMulti[i];
			}

			if (tempCost != Vars.UpgradeCost[i])
			{
				//				Vars.UpgradeCost[i] = Vars.dUpgradeCost[i];
				//				Vars.UpgradeCount[i] = Vars.dUpgradeCount[i];
				//				Vars.UpgradeCostMulti[i] = Vars.dUpgradeCostMulti[i];
				//				Vars.UpgradeImprovement[i] = Vars.dUpgradeImprovement[i];

				Vars.UpgradeCost[i] = tempCost;
				costV = false;
			}

			for (int x = 0; x < Vars.UpgradeCount[i]; x++)
			{
				if (i >= 1)
				{
					tempClickStepAuto += Vars.UpgradeImprovement[i];
				}
				else
				{
					tempClickStep *= Vars.UpgradeImprovement[i];
				}
			}
		}

		if (tempClickStep != Vars.ClickStep || tempClickStepAuto != Vars.ClickStepAuto)
		{
			clickStepV = false;
		}

		if (!clickStepV)
		{
			Vars.ClickStep = tempClickStep;
			Vars.ClickStepAuto = tempClickStepAuto;
		}

		if (!clickStepV && !costV)
		{
			verified = false;
		}

		if (!verified)
		{
			Vars.Werte();
		}
	}
}
