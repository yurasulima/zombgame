package com.mblueberry.zombgame_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.mblueberry.core.World;
import io.mblueberry.object.entity.Player;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Thread gameThread;
    private boolean running = false;
    private World world;
    private Player player;
    private Paint paint;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        setupGame();
    }

    public void setupGame() {
        // Ініціалізація гри
        //world = new World();
       //player = new Player();

        paint = new Paint();
        // Налаштування інших параметрів
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        running = false;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            if (!getHolder().getSurface().isValid())
                continue;

            Canvas canvas = getHolder().lockCanvas();
            update();
            drawGame(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        // Оновлення стану гри
        world.update();
     //   player.update();
    }

    private void drawGame(Canvas canvas) {
        if (canvas != null) {
            // Очистити екран
            canvas.drawColor(Color.BLACK);

            // Малювати гру
           //world.draw(canvas, paint);
           // player.draw(canvas, paint);

            // Малювати інші елементи інтерфейсу
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Обробка торкань екрану
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Обробка натискання
                break;
            case MotionEvent.ACTION_MOVE:
                // Обробка руху
                break;
            case MotionEvent.ACTION_UP:
                // Обробка відпускання
                break;
        }
        return true;
    }
}