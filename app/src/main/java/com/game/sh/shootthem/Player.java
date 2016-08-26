package com.game.sh.shootthem;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Каролина on 26.08.2016.
 */
public class Player {
    /**Объект главного класса*/
    GameView gameView;

    //спрайт
    Bitmap bmp;

    //х и у координаты рисунка
    int x;
    int y;

    //конструктор
    public Player(GameView gameView, Bitmap bmp)
    {
        this.gameView = gameView;
        this.bmp = bmp;                    //возвращаем рисунок

        this.x = 0;                        //отступ по х нет
        this.y = gameView.getHeight() / 2; //делаем по центру
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c)
    {
        c.drawBitmap(bmp, x, y, null);
    }
}
