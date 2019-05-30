package at.schiebung.stefan.schober0008;

import android.content.Context;

import static at.schiebung.stefan.schober0008.Vars.saved_file_boolean;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_cost;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_cost_multi;
import static at.schiebung.stefan.schober0008.Vars.saved_file_double_upgrades_count;

class Saves
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

		StringBuilder s1 = new StringBuilder();

		for (double v : d1)
		{
			s1.append(v).append(",");
		}

		//2. Save-Datei
		boolean[] b2 = new boolean[] {Vars.bm};

		StringBuilder s2 = new StringBuilder();

		for (boolean b : b2)
		{
			s2.append(b).append(",");
		}

		//3. Save-Datei
		double[] d3 = new double[Vars.UpgradeCost.length];

		StringBuilder s3 = new StringBuilder();

		for (int i = 0; i < d3.length; i++)
		{
			d3[i] = Vars.UpgradeCost[i];
			s3.append(d3[i]).append(",");
		}

		//4. Save-Datei
		double[] d4 = new double[Vars.UpgradeCostMulti.length];

		StringBuilder s4 = new StringBuilder();

		for (int i = 0; i < d4.length; i++)
		{
			d4[i] = Vars.UpgradeCostMulti[i];
			s4.append(d4[i]).append(",");
		}

		//5. Save-Datei
		double[] d5 = new double[Vars.UpgradeCount.length];

		StringBuilder s5 = new StringBuilder();

		for (int i = 0; i < d5.length; i++)
		{
			d5[i] = Vars.UpgradeCount[i];
			s5.append(d5[i]).append(",");
		}

		preferences.put(Vars.saved_file_double, s1.toString());
		preferences.put(Vars.saved_file_boolean, s2.toString());
		preferences.put(Vars.saved_file_double_upgrades_cost, s3.toString());
		preferences.put(Vars.saved_file_double_upgrades_cost_multi, s4.toString());
		preferences.put(Vars.saved_file_double_upgrades_count, s5.toString());
	}

	private static void verify()
	{
		double  tempCost;
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
