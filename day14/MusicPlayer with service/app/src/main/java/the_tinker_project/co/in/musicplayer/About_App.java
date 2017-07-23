package the_tinker_project.co.in.musicplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class About_App extends AppCompatActivity {

    TextView aboutapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__app);

        aboutapp= (TextView) findViewById(R.id.aboutApp);
        String s="This is a basic music player app, it has all the features of a modern day app such as play pause stop previous song next song. The most attractive feature of this app is that it has genture control. In other words you can control the volume and songs with gestures.";
        aboutapp.setText(s);

    }
}
