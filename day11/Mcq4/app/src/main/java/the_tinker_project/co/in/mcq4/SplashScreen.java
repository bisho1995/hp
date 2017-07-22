package the_tinker_project.co.in.mcq4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            Thread.sleep(2000);
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
