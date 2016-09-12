package com.game.sh.shootthem;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Каролина on 12.09.2016.
 */
public class Enemy {
    /**Объект главного класса*/
    GameView gameView;
    private int speed;

    //спрайт
    Bitmap bmp;

    //х и у координаты рисунка
    int x;
    int y;

    /**Выосота и ширина спрайта*/
    public int width;
    public int height;

    //конструктор
    public Enemy(GameView gameView, Bitmap bmp)
    {
        this.gameView = gameView;
        this.bmp = bmp;                    //возвращаем рисунок

        Random rnd = new Random();
        this.x = 800;                        //отступ по х нет
        this.y = rnd.nextInt(300);
        this.speed = rnd.nextInt(10);

        this.width = 9;
        this.height = 8;
    }

    public void update(){
        x -= speed;
    }

    //рисуем наш спрайт
    public void onDraw(Canvas c)
    {
        update();
        c.drawBitmap(bmp, x, y, null);
    }
}
