package com.example.gameball;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends Activity {

    public String controlR;
    public String colorR;
    public int diameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Spinner control = (Spinner)findViewById(R.id.control);
        final Spinner color = (Spinner)findViewById(R.id.color);
        control.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                controlR = control.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorR = color.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("control", controlR);
        intent.putExtra("color", colorR);
        String diameter = ((EditText)findViewById(R.id.diameter)).getText().toString();
        if(diameter.compareTo("") == 0)
        {
            Toast.makeText(this,"Fill the diameterBox!!!!!!!!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            int d = Integer.parseInt(diameter);
            intent.putExtra("diameter", d);
            setResult(Activity.RESULT_OK, intent);
            finish();
            super.onBackPressed();
        }

    }
}
