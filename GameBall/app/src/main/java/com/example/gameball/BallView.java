package com.example.gameball;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.Context.SENSOR_SERVICE;

public class BallView extends View  {

    private Paint ballPaint;
     int diameter;
    public int directionX ;
    public int directionY ;
     int color ;
     int control;
    private int viewWidth;
    private int viewHeight;

    public BallView(Context context){super(context);
    init();
    }

    public BallView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        TypedArray a = context.obtainStyledAttributes(attributeSet,R.styleable.BallView);

        try {
            diameter = a.getInt(R.styleable.BallView_diameter,40);
            directionX = a.getInt(R.styleable.BallView_directionX,40);
            directionY = a.getInt(R.styleable.BallView_directionY,40);
            color = a.getColor(R.styleable.BallView_color,Color.BLUE);
            control = a.getInt(R.styleable.BallView_control,1);
        }finally {
            a.recycle();
        }
        init();
    }


    public int returnControl()
    {
        return control;
    }

    public void setDiameter(int diameter)
    {
        this.diameter = diameter;
        invalidate();
        requestLayout();
    }

    public void setControl(int control)
    {
        this.control = control;
        invalidate();
        requestLayout();
    }

    public void setDirectionX(int x)
    {
        this.directionX = x;
        invalidate();
    }

    public int getDirectionX()
    {
        return this.directionX;
    }

    public int getDirectionY()
    {
        return this.directionY;
    }

    public void setDirectionY(int y)
    {
        this.directionY = y;
        invalidate();
    }

    public void setColor(int color)
    {
        this.color = color;
        invalidate();
        requestLayout();
    }

    private void init()
    {
        ballPaint = new Paint();
        ballPaint.setStyle(Paint.Style.FILL);
        ballPaint.setStrokeWidth(5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ballPaint.setColor(color);
        if(control == 0) {
            canvas.drawCircle(directionX, directionY, diameter, ballPaint);
            Log.d("drag","drag draw");
            invalidate();
        }
        else
        {
            canvas.drawCircle(directionX, directionY, diameter,ballPaint);
            Log.d("gravity", "gravity draw555555555555555555555555555555555555555555");
            invalidate();
        }
    }


    public void onSensorEvent(SensorEvent event) {
        if(control ==1) {
            Log.d("gravity", "gravity move4444444444444444444444444444444444444444444444444444");
//            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//
//                x = x - (int) event.values[0];
//                y = y + (int) event.values[1];
//                if (x <= diameter) {
//                    x = diameter;
//                }
//                if (x >= viewWidth - diameter) {
//                    x = viewWidth - diameter;
//                }
//                if (y <= diameter) {
//                    directionY = diameter;
//                }
//                if (y >= viewHeight - diameter) {
//                    y = viewHeight - diameter;
//                }
//
//            }
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                Log.d("before moving",directionX+"            "+directionY);
                directionX = directionX - (int) (event.values[0]);
                directionY = directionY + (int) (event.values[1]);
                Log.d("when moving speed", event.values[0]+"            "+event.values[1]);
                Log.d("when moving", directionX+"             "+ directionY);
                if (directionX <= diameter) {
                    directionX = diameter;
                    Log.d("fix positionX 111","         "+directionX);
                }
                if (directionX >= viewWidth - diameter) {
                    directionX = viewWidth - diameter;
                    Log.d("viewwidth","          "+viewWidth);
                    Log.d("fix positionX 222","         "+directionX);
                }
                if (directionY <= diameter) {
                    directionY = diameter;
                    Log.d("fix positionY 111","         "+directionY);
                }
                if (directionY >= viewHeight - diameter) {
                    directionY = viewHeight - diameter;
                    Log.d("fix positionY 222","         "+directionY);
                }

            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

            viewWidth = w;
            viewHeight = h;
            Log.d("11111111111", w + "44444444444444444444444444444444444444444444444444444444");
//            Log.d("222222222222",h+"22222222222222");

    }

    private float downX;
    private float downY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
//        if(control == 0) {
//            if (this.isEnabled()) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        downX = event.getX();
//                        downY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        final float xDistance = event.getX() - downX;
//                        final float yDistance = event.getY() - downY;
//                        if (xDistance != 0 && yDistance != 0) {
//                            int l = (int) (getLeft() + xDistance);
//                            int r = (int) (getRight() + xDistance);
//                            int t = (int) (getTop() + yDistance);
//                            int b = (int) (getBottom() + yDistance);
////                            Log.d("left",l+" ");
////                            Log.d("right",r+" ");
////                            Log.d("top",t+" ");
////                            Log.d("bottom",b+" ");
////                            this.setDirectionX(l);
////                            this.setDirectionY(t);
//                            this.layout(l, t, r, b);
//
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        setPressed(false);
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        setPressed(false);
//                        break;
//                }
//                return true;
//            }
//        }
        if(control == 0) {
            this.setDirectionX((int) event.getX());
            this.setDirectionY((int) event.getY());
            invalidate();
        }
        return true;
    }


}
