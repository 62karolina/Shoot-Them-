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
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Каролина on 26.07.2016.
 */
public class GameView extends SurfaceView {

    private GameThread mThread;

    public int shotX;
    public int shotY;

    private boolean running = false;
    private Paint paint;



    public class GameThread extends Thread{
        //Объект класса
        private  GameView view;

        //Конструктор класса
        public GameThread(GameView view){
            this.view = view;
        }

        //Задание состояния потока
        public void setRunning(boolean run){
            running = run;
        }

        //Действия, выполняемые в потоке
        public void run(){
            while(running){
                Canvas canvas = null;
                try{
                    //Подготовка канваса
                    canvas = view.getHolder().lockCanvas();
                    synchronized (view.getHandler()){
                        //рисование
                        onDraw(canvas);
                    }
                }
                catch (Exception e){}
                finally {
                    if(canvas != null){
                        view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public GameView(Context context) {
        super(context);

        mThread = new GameThread(this);

        getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mThread.setRunning(true);
                mThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                mThread.setRunning(false);
                while(retry){
                    try{
                        //ожиданиие завершения потока
                        mThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e){}
                }
            }
        });

    }


    protected void onDraw(Canvas canvas){
        Bitmap backgr = BitmapFactory.decodeResource(getResources(), R.drawable.background_field);
        canvas.drawBitmap(backgr, 0, 0, paint);

    }


}
