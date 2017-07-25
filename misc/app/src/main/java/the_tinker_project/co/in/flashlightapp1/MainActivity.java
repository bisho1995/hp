package the_tinker_project.co.in.flashlightapp1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Camera cam;
    static int isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        isFlashOn=0;
    }

    void toast(String s)
    {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
    public void flashLight(View view) {
        //toast("Flashlight on");
        if(isFlashOn==0)
        {
            turnFlashOn();
            isFlashOn=1;
        }
        else
        {
            turnFlashOff();
            isFlashOn=0;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cam.release();
        cam.unlock();
        cam=null;
    }

    private void turnFlashOff() {
        try {
            if (getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam.stopPreview();
                cam.release();
                cam = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOff",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void turnFlashOn() {
        try {
            if (getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam = Camera.open();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Exception flashLightOn()",
                    Toast.LENGTH_SHORT).show();
        }
    }



    /*private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }*/
    }
