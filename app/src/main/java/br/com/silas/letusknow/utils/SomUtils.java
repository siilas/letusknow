package br.com.silas.letusknow.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;

public class SomUtils {

    private static final int DEFAULT_SOUND = RingtoneManager.TYPE_NOTIFICATION;

    public static void play(Context context) {
        MediaPlayer.create(context, RingtoneManager.getDefaultUri(DEFAULT_SOUND)).start();
    }

}
