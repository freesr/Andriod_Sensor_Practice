package com.example.sensorexample;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private EditText editTextX,editTextY,editTextZ;
    private EditText grX,grY,grZ;
    private Button button;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    float acclX =0, acclY=0,acclZ=0;
    float gyrX =0, gyrY=0,gyrZ=0;
    private Handler myhandler =new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextX = findViewById(R.id.editTextX);
        editTextY = findViewById(R.id.editTextY);
        editTextZ = findViewById(R.id.editTextZ);
       grX = findViewById(R.id.grX);
        grY = findViewById(R.id.grY);
        grZ = findViewById(R.id.grZ);
        button = findViewById(R.id.button);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        button.setOnClickListener(this);



//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               switch (v.getId()){
//                   case R.id.button:
//                      // sensorManager.registerListener(accelerometer,SensorManager.SENSOR_DELAY_NORMAL)
//                       sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
//                       editTextX.setText("12");
//                       break;
//               }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                   case R.id.button:
                      // sensorManager.registerListener(accelerometer,SensorManager.SENSOR_DELAY_NORMAL)
                       sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
                       sensorManager.registerListener(MainActivity.this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
                       break;
               }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,accelerometer);
        sensorManager.unregisterListener(this,gyroscope);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //event.timestamp
            acclX = event.values[0];
            acclY = event.values[1];
            acclZ = event.values[2];

            AcclTask myTask = new AcclTask(acclX,acclY,acclZ);
            myhandler.post(myTask);

//            editTextX.setText(new Float(acclX).toString());
//            editTextY.setText(new Float(acclY).toString());
//            editTextZ.setText(new Float(acclZ).toString());
        }
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            gyrX = event.values[0];
            gyrY = event.values[1];
           gyrZ = event.values[2];

            GyTask myGyTask = new GyTask(gyrX,gyrY,gyrZ);
            myhandler.post(myGyTask);

//            grX.setText(new Float(gyrX).toString());
//            grY.setText(new Float(gyrY).toString());
//            grZ.setText(new Float(gyrZ).toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class AcclTask implements Runnable{
        float acclxt,acclyt,acclzt;
        public AcclTask(float accx,float accy,float accz){
            this.acclxt = accx;
            this.acclyt = accy;
            this.acclzt = accz;

        }
        @Override
        public void run() {
            editTextX.setText(new Float(acclxt).toString());
            editTextY.setText(new Float(acclyt).toString());
            editTextZ.setText(new Float(acclzt).toString());
        }
    }


    private class GyTask implements Runnable{
        float gylxt,gylyt,gylzt;
        public GyTask(float gyx,float gyy,float gyz){
            this.gylxt = gyx;
            this.gylyt = gyy;
            this.gylzt = gyz;

        }
        @Override
        public void run() {
            grX.setText(new Float(gylxt).toString());
            grY.setText(new Float(gylyt).toString());
            grZ.setText(new Float(gylzt).toString());
        }
    }
}