package the_tinker_project.co.in.sharedpreference1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        b= (Button) findViewById(R.id.lgut);
    }

    public void logout(View v)
    {
        SharedPreferences sf = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor=sf.edit();
        editor.putBoolean("cb",true);
        editor.putString("et","");
        //editor.putString("")
        editor.commit();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
