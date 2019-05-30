package at.schiebung.stefan.schober0008;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
