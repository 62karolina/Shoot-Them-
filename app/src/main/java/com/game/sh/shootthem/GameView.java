package com.game.sh.shootthem;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Каролина on 26.07.2016.
 */
public class GameView extends SurfaceView {

    /**Объект класса GameLoopThread*/
    private GameThread mThread;

    public int shotX;
    public int shotY;

    private List<Bullet> ball = new ArrayList<Bullet>();

    private Player player;

    Bitmap players;

    /**Переменная запускающая поток рисования*/
    private boolean running = false;

    //-------------Start of GameThread--------------------------------------------------\\

    public class GameThread extends Thread
    {
        /**Объект класса*/
        private GameView view;

        /**Конструктор класса*/
        public GameThread(GameView view)
        {
            this.view = view;
        }

        /**Задание состояния потока*/
        public void setRunning(boolean run)
        {
            running = run;
        }

        /** Действия, выполняемые в потоке */
        public void run()
        {
            while (running)
            {
                Canvas canvas = null;
                try
                {
                    // подготовка Canvas-а
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHolder())
                    {
                        // собственно рисование
                        onDraw(canvas);
                    }
                }
                catch (Exception e) { }
                finally
                {
                    if (canvas != null)
                    {
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    //-------------End of GameThread--------------------------------------------------\\

    public GameView(Context context)
    {
        super(context);

        mThread = new GameThread(this);

        players= BitmapFactory.decodeResource(getResources(), R.mipmap.unit);
        player= new Player(this, players);

        /*Рисуем все наши объекты и все все все*/
        getHolder().addCallback(new SurfaceHolder.Callback()
        {
            /*** Уничтожение области рисования */
            public void surfaceDestroyed(SurfaceHolder holder)
            {
                boolean retry = true;
                mThread.setRunning(false);
                while (retry)
                {
                    try
                    {
                        // ожидание завершение потока
                        mThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) { }
                }
            }

            /** Создание области рисования */
            public void surfaceCreated(SurfaceHolder holder)
            {
                mThread.setRunning(true);
                mThread.start();
            }

            /** Изменение области рисования */
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
            {
            }
        });
    }

    /**Функция рисующая все спрайты и фон*/
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        Iterator<Bullet> j = ball.iterator();
        while(j.hasNext()) {
            Bullet b = j.next();
            if(b.x >= 1000 || b.x <= 1000) {
                b.onDraw(canvas);
            } else {
                j.remove();
            }
        }

        canvas.drawBitmap(players, 25, 150, null);
    }

    public Bullet createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Bullet(this, bmp);
    }

    public boolean onTouchEvent(MotionEvent e)
    {
        shotX = (int) e.getX();
        shotY = (int) e.getY();

        if(e.getAction() == MotionEvent.ACTION_DOWN)
            ball.add(createSprite(R.drawable.bullet));

        return true;
    }



}
