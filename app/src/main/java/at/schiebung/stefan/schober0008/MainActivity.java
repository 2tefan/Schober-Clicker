package at.schiebung.stefan.schober0008;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Timer;

import static at.schiebung.stefan.schober0008.Vars.RC_LEADERBOARD_UI;
import static at.schiebung.stefan.schober0008.Vars.RC_SIGN_IN;
import static at.schiebung.stefan.schober0008.Vars.handler;

@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class MainActivity extends AppCompatActivity
{
    private final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

    private static void Refresh(TextView ClicksCounter)
    {
        ClicksCounter.setText(Upgrades.format(Vars.Clicks));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Vars.Werte();
        Saves.loadSaves(this);
        startTimer();
        Sounds.MusicCreate(this);
        animations();
        setWindow();
        refreshUpgrades();
    }

    private void setWindow()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        TextViewCompat.setAutoSizeTextTypeWithDefaults((TextView) findViewById(R.id.txtUpgrades), TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        TextViewCompat.setAutoSizeTextTypeWithDefaults((TextView) findViewById(R.id.ClicksCounter), TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        Saves.saveSaves(this);

        Sounds.MusicStop();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        TextView ClicksCounter = this.findViewById(R.id.ClicksCounter);
        Refresh(ClicksCounter);
        Sounds.MusicResume();

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pfc_lpd", false))
        {
            findViewById(R.id.gifClick).setVisibility(View.GONE);
            findViewById(R.id.pngClick).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.gifClick).setVisibility(View.VISIBLE);
            findViewById(R.id.pngClick).setVisibility(View.GONE);
        }

        if (isSignedIn())
        {
            findViewById(R.id.sign_in_button_iv).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button_iv).setVisibility(View.VISIBLE);
        }
        else
        {
            findViewById(R.id.sign_in_button_iv).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button_iv).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Sounds.MusicKill();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            if (Vars.popupVisble)
            {
                Vars.backpressed = true;
                upgradesMethode();
                Vars.backpressed = false;
                return false;
            }
            else
            {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Pressed(View V)
    {
        if (Vars.soundclicks)
        {
            Sounds.clickSound(this);
        }

        TextView ClicksCounter = findViewById(R.id.ClicksCounter);
        Vars.Clicks += Vars.ClickStep;

        lookingFor();

        ClicksCounter.setText(Upgrades.format(Vars.Clicks));
    }

    public void Info(View V)
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void lookingFor()
    {
        vorsilbe();

        if (isSignedIn() && GoogleSignIn.getLastSignedInAccount(this) != null)
        {
            pushStats();
        }
    }

    private void pushStats()
    {
        Games.getLeaderboardsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).submitScore(getString(R.string.leaderboard_clicks), (long) (Vars.Clicks * 100));


        if (Vars.Clicks >= 1)
        {
            Games.getAchievementsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).unlock(getString(R.string.achievement_first_schober));
        }
        if (Vars.Clicks >= 5)
        {
            Games.getAchievementsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).unlock(getString(R.string.achievement_bimbo));
        }

        if (Vars.Clicks >= 1000)
        {
            Games.getAchievementsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).unlock(getString(R.string.achievement_kiloschober));
        }
    }

    private void vorsilbe()
    {
        while ((Vars.Clicks >= (Math.pow(1000, (double) Vars.vorsilbe + 1))) && Vars.vorsilbe < Vars.sivorsilben.length - 1)
        {
            Vars.vorsilbe++;
        }

        if (Vars.vorsilbe >= Vars.sivorsilben.length)
        {
            Vars.sivorsilbenoverflow = true;
            Vars.decimalFormat = new DecimalFormat("0.##E0" + " Schober");
        }
        else
        {
            Vars.sivorsilbenoverflow = false;
            Vars.decimalFormat = new DecimalFormat("0.##" + " " + Vars.sivorsilben[Vars.vorsilbe] + "Schober");
        }

    }

    private void startTimer()
    {
        //set a new Vars.timer
        Vars.timer = new Timer();

        //initialize the Vars.timerTask's job
        initializeTimerTask();

        //schedule the Vars.timer, after the first 5000ms the Vars.timerTask will run every 10000ms
        Vars.timer.schedule(Vars.timerTaskAuto, 50, 50);
        Vars.timer.schedule(Vars.timerTaskChanges, 10, 10000);
    }

    private void initializeTimerTask()
    {
        Vars.timerTaskAuto = new java.util.TimerTask()
        {
            public void run()
            {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        Vars.Clicks += Vars.ClickStepAuto;
                        TextView textView = findViewById(R.id.ClicksCounter);
                        Refresh(textView);
                    }
                });
            }
        };

        Vars.timerTaskChanges = new java.util.TimerTask()
        {
            public void run()
            {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        lookingFor();
                    }
                });
            }
        };
    }

    public void Upgrades(View V)
    {
        upgradesMethode();
    }

    private void upgradesMethode()
    {
        boolean b = false;

        if (Vars.popupVisble || Vars.backpressed || Vars.rotated)
        {
            b = true;
        }
        else
        {
            refreshUpgrades();
        }

        pfeilAnimator(b);
        upgradeAnimator(b);

        Vars.popupVisble = !Vars.popupVisble;
    }

    public void upgradesBought(View view)
    {
        Context  context        = this.getApplicationContext();
        TextView txtUpgradeCost = null;
        int      x              = 0;


        if (view.getId() == R.id.btnUpgrade1)
        {
            txtUpgradeCost = this.findViewById(R.id.txtUpgradeCost1);
            x = 0;
        }
        else if (view.getId() == R.id.btnUpgrade2)
        {
            txtUpgradeCost = this.findViewById(R.id.txtUpgradeCost2);
            x = 1;
        }
        else if (view.getId() == R.id.btnUpgrade3)
        {
            txtUpgradeCost = this.findViewById(R.id.txtUpgradeCost3);
            x = 2;
        }

        Upgrades.Upgrade(x, context, txtUpgradeCost);
    }

    private void refreshUpgrades()
    {
        TextView txtUpgradeCost1 = this.findViewById(R.id.txtUpgradeCost1);
        TextView txtUpgradeCost2 = this.findViewById(R.id.txtUpgradeCost2);
        TextView txtUpgradeCost3 = this.findViewById(R.id.txtUpgradeCost3);

        //		double[] temp = Vars.UpgradeCost;
        //
        //		for (int i = 0; i < Vars.UpgradeCost.length; i++)
        //		{
        //			temp[i] = Upgrade.round(temp[i], 2);
        //		}

        txtUpgradeCost1.setText(Upgrades.format(Vars.UpgradeCost[0]));
        txtUpgradeCost2.setText(Upgrades.format(Vars.UpgradeCost[1]));
        txtUpgradeCost3.setText(Upgrades.format(Vars.UpgradeCost[2]));

        TextView schoberpersecound = this.findViewById(R.id.schoberpersecound);
        double   sps               = Vars.ClickStepAuto;
        schoberpersecound.setText(Upgrades.formatsps(sps));
    }

    private void pfeilAnimator(boolean backwards)
    {
        if (!Vars.rotated)
        {
            ImageView       schoberPfeilL = findViewById(R.id.schoberPfeilL);
            ImageView       schoberPfeilR = findViewById(R.id.schoberPfeilR);
            RotateAnimation animL;
            RotateAnimation animR;

            if (!backwards)
            {
                animL = new RotateAnimation(180f, 360f, schoberPfeilL.getHeight() / 2, schoberPfeilL.getWidth() / 2);
                animL.setInterpolator(new AnticipateOvershootInterpolator());
                animL.setDuration(Vars.pfeilRotateDuration);

                animR = new RotateAnimation(-180f, -360f, schoberPfeilR.getHeight() / 2, schoberPfeilR.getWidth() / 2);
                animR.setInterpolator(new AnticipateOvershootInterpolator());
                animR.setDuration(Vars.pfeilRotateDuration);


                // Start animating the image
                schoberPfeilL.startAnimation(animL);
                schoberPfeilR.startAnimation(animR);

                schoberPfeilL.setRotationX(180);
                schoberPfeilR.setRotationX(-180);
                schoberPfeilL.setRotationY(180);
                schoberPfeilR.setRotationY(180);
            }
            else
            {
                animL = new RotateAnimation(180, 0, schoberPfeilL.getHeight() / 2, schoberPfeilL.getWidth() / 2);
                animL.setInterpolator(new AnticipateOvershootInterpolator());
                animL.setDuration(Vars.pfeilRotateDuration);

                animR = new RotateAnimation(-180, 0, schoberPfeilR.getHeight() / 2, schoberPfeilR.getWidth() / 2);
                animR.setInterpolator(new AnticipateOvershootInterpolator());
                animR.setDuration(Vars.pfeilRotateDuration);


                // Start animating the image

                schoberPfeilL.startAnimation(animL);
                schoberPfeilR.startAnimation(animR);

                schoberPfeilL.setRotationX(0);
                schoberPfeilR.setRotationX(0);
                schoberPfeilL.setRotationY(0);
                schoberPfeilR.setRotationY(0);
            }
        }
    }

    private void upgradeAnimator(boolean backwards)
    {
        ConstraintLayout upgradeArea = findViewById(R.id.upgradeArea);
        ObjectAnimator   areaani;
        ObjectAnimator   popupani;
        //upgradeArea.setBackground(ContextCompat.getDrawable(this, R.drawable.popup_bg));

        ConstraintLayout upgradesPopup = findViewById(R.id.upgradesPopup);
        //TextView         click         = findViewById(R.id.ClicksCounter);
        //int              heightClick   = click.getHeight();

        //ConstraintLayout all       = findViewById(R.id.allContent);
        //int              heightAll = all.getHeight();

        //int area = upgradeArea.getHeight();

        float height = upgradesPopup.getHeight();
        //height = heightAll - heightClick - area - 20;


        if (!backwards)
        {
            areaani = ObjectAnimator.ofFloat(upgradeArea, "translationY", -height);
            popupani = ObjectAnimator.ofFloat(upgradesPopup, "translationY", 0);
        }
        else
        {
            areaani = ObjectAnimator.ofFloat(upgradeArea, "translationY", 0f);
            popupani = ObjectAnimator.ofFloat(upgradesPopup, "translationY", Vars.popupDistance);
        }

        int temp = Vars.popupDuration;
        if (Vars.rotated)
        {
            Vars.popupDuration = 0;
        }
        areaani.setInterpolator(new FastOutSlowInInterpolator());
        areaani.setDuration(Vars.popupDuration);
        areaani.start();

        popupani.setInterpolator(new FastOutSlowInInterpolator());
        popupani.setDuration(Vars.popupDuration);
        popupani.start();

        Vars.popupDuration = temp;
    }

    private void animations()
    {
        ConstraintLayout  myLayout          = findViewById(R.id.myLayout);           //Hintergrund
        AnimationDrawable animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(getResources().getInteger(R.integer.duration));
        animationDrawable.setExitFadeDuration(getResources().getInteger(R.integer.duration));
        animationDrawable.start();

        ConstraintLayout upgradesPopup = findViewById(R.id.upgradesPopup);
        upgradesPopup.setVisibility(View.VISIBLE);

        //Damit die Animation zurückgesetzt wird
        Vars.popupVisble = true;
        Vars.rotated = true;
        upgradesMethode();
        Vars.rotated = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (!result.isSuccess())
            {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty())
                {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
            }
            //            else
            //            {
            //                // The signed in account is stored in the result.
            //                //  GoogleSignInAccount signedInAccount = result.getSignInAccount();
            //            }
        }
    }

    public void showAchievements(View V)
    {
        if (isSignedIn())
        {
            Games.getAchievementsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this))).getAchievementsIntent().addOnSuccessListener(new OnSuccessListener<Intent>()
            {
                @Override
                public void onSuccess(Intent intent)
                {
                    startActivityForResult(intent, Vars.RC_ACHIEVEMENT_UI);
                }
            });
        }
        else
        {
            String message = getResources().getString(R.string.not_logged_in);
            new android.app.AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
        }
    }

    public void showLeaderboard(View V)
    {
        if (isSignedIn())
        {
            Games.getLeaderboardsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)))
                 .getLeaderboardIntent(getString(R.string.leaderboard_clicks))
                 .addOnSuccessListener(new OnSuccessListener<Intent>()
                 {
                     @Override
                     public void onSuccess(Intent intent)
                     {
                         startActivityForResult(intent, RC_LEADERBOARD_UI);
                     }
                 });
        }
        else
        {

            String message = getResources().getString(R.string.not_logged_in);
            new android.app.AlertDialog.Builder(this).setMessage(message).setNeutralButton(android.R.string.ok, null).show();
        }
    }

    private boolean isSignedIn()
    {
        return GoogleSignIn.getLastSignedInAccount(this) != null;
    }

    private void signOut()
    {
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut();
    }

    private void startSignInIntent()
    {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent             intent       = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void btnLogin(View V)
    {
        startSignInIntent();
    }

    public void btnLogout(View V)
    {
        // sign out.
        signOut();
        // show sign-in button, hide the sign-out button
        findViewById(R.id.sign_in_button_iv).setVisibility(View.VISIBLE);
        findViewById(R.id.sign_out_button_iv).setVisibility(View.GONE);
    }
}
