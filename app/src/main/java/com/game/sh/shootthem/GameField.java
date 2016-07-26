package com.game.sh.shootthem;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Каролина on 26.07.2016.
 */
public class GameField extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new GameView(this));
    }


}
