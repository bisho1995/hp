package the_tinker_project.co.in.accelerometer1;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.Policy;

import static android.content.DialogInterface.*;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button btnSwitch;

    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Policy.Parameters params;
    MediaPlayer mp;


    private SensorManager senSensorManager;
    private Sensor senAccelerometer;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager= (SensorManager) getSystemService(this.SENSOR_SERVICE);
        senAccelerometer=senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tv= (TextView) findViewById(R.id.textView);


        // flash switch button
        btnSwitch = (Button) findViewById(R.id.btnSwitch);


        // First check if device is supporting flashlight or not
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            //alert.setButton("OK");
            alert.show();
            return;
        }

        // get the camera
        getCamera();

        // displaying button image
        //toggleButtonImage();


        // Switch button click event to toggle flash on/off
        btnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    // turn off flash
                    turnOffFlash();
                } else {
                    // turn on flash
                    turnOnFlash();
                }
            }
        });

    }


    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                //Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
            }
        }
    }


    // Turning On flash
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();

            params = (Policy.Parameters) camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters((Camera.Parameters) params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
            //toggleButtonImage();
        }

    }


    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
            //playSound();

            params = (Policy.Parameters) camera.getParameters();
            params.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters((Camera.Parameters) params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
            //toggleButtonImage();
        }
    }




    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor=sensorEvent.sensor;
        if(mySensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            String s="";
            float val[]=sensorEvent.values;
            for (float value:
                 val)
            {
                s+=value+"\n";
            }
            tv.setText(s);
        }
    }

    //onResume() register the accelerometer for listening the events

    protected void onResume() {

        super.onResume();

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
    public void vibrate(View v)
    {
        Vibrator vibrate =(Vibrator)getSystemService(this.VIBRATOR_SERVICE);
        vibrate.vibrate(1000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
