package com.example.gameball;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int request_code = 1;
   // private  view;
    BallView view1;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    BallView view = null;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getPreferences(MODE_PRIVATE);
        int x = sp.getInt("x", 80);
        int y = sp.getInt("y", 80);
        int control = sp.getInt("control", 0);
        int diameter = sp.getInt("diameter", 30);
        int color = sp.getInt("color",Color.BLACK);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        view = new BallView(this);
        view.setDirectionX(x);
        view.setDirectionY(y);
        view.setControl(control);
        view.setDiameter(diameter);
        view.setColor(color);
        setContentView(R.layout.activity_main);
        RelativeLayout r = findViewById(R.id.main);
        r.addView(view);
        //view1 = findViewById(R.id.ball);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(view.returnControl() == 1) {
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("2222222222","22222222222222222222222222222222");
        }
        else
        {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
            if(view.returnControl() == 1)
            mSensorManager.unregisterListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("color",view.color);
        editor.putInt("x",view.directionX);
        editor.putInt("y",view.directionY);
        editor.putInt("control",view.control);
        editor.putInt("diameter",view.diameter);
        editor.commit();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && view.returnControl() ==1) {
            view.onSensorEvent(event);
            Log.d("333333","33333333333333333333333333333333333333333333333");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting_item:
            {

                Intent intent = new Intent(this, SettingActivity.class);
//                Ball.xD = view.directionX;
//                Ball.yD = view.directionY;
//                Log.d("send intent","           "+Ball.xD+"      " +Ball.yD);
                startActivityForResult(intent, request_code);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == request_code)
        {
            if(resultCode == Activity.RESULT_OK)
            {

                String control = data.getStringExtra("control");
                String color = data.getStringExtra("color");
                if(color.compareTo("Blue") == 0)
                {
                    view.setColor(Color.BLUE);
                }
                if(color.compareTo("Black") == 0)
                {
                    view.setColor(Color.BLACK);
                }
                int diameter = data.getIntExtra("diameter",0);
                if(diameter !=0)
                {
                    if(diameter > 100)
                    {
                        diameter =100;
                    }
                    if(diameter<0)
                    {
                        diameter = 0;
                    }

                    view.setDiameter(diameter);
                }
                if(control.compareTo("Gravity") == 0)
                {
                    view.setControl(1);
                    Log.d("111","1111111111111111111111111111111111111111");
                    //Log.d("after intent","           "+Ball.xD+"      " +Ball.yD);

                }
                if(control.compareTo("Drag") == 0)
                {
                    view.setControl(0);
                }
            }
        }
    }
}
