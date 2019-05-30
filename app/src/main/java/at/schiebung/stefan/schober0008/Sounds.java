package at.schiebung.stefan.schober0008;

import android.content.Context;
import android.media.MediaPlayer;

class Sounds
{
    public static void MusicCreate(Context c)
    {
        Vars.player = MediaPlayer.create(c, R.raw.bm1);
        Vars.player.setLooping(true); // Set looping
        Vars.player.setVolume(100, 100);
    }

    public static void MusicResume()
    {
        if (Vars.bm)
        {
            Vars.player.start();
        }
    }

    public static void MusicStop()
    {
        Vars.player.pause();
    }

    public static void MusicKill()
    {
        Vars.player.stop();
        Vars.player.release();
    }

    public static void clickSound(Context c)
    {
        MediaPlayer player2 = MediaPlayer.create(c, R.raw.click);
        player2.start();
    }
}