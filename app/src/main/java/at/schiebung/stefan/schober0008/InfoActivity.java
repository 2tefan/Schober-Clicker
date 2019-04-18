package at.schiebung.stefan.schober0008;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static at.schiebung.stefan.schober0008.Vars.RC_SIGN_IN;

public class InfoActivity extends AppCompatActivity
{
    private static final int RC_LEADERBOARD_UI = 9004;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Sounds.MusicResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Sounds.MusicStop();
    }





    public void Change_Musik(View V)
    {
        Vars.bm = !Vars.bm;
        Sounds.MusicKill();
        Sounds.MusicCreate(Vars.main);
        Sounds.MusicResume();
    }

    public void showPP(View view)
    {
        // opening a URL in a Browser in Android:
        String url = Vars.ppUrlSchober;
        Intent i   = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}
