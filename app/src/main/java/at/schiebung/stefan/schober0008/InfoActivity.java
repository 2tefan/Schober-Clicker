package at.schiebung.stefan.schober0008;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity
{

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
        Sounds.MusicCreate(this);
        Sounds.MusicResume();
    }


}
