package the_tinker_project.co.in.sharedpreference1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et,pd;
    CheckBox cb;
    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= (EditText) findViewById(R.id.et);
        cb= (CheckBox) findViewById(R.id.cb);
        pd= (EditText) findViewById(R.id.pd);
        sf=getSharedPreferences("myPref",MODE_PRIVATE);

        if(sf.getString("et","").length()>0)
        {

            Intent i=new Intent(this,SecondActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        putSavedContent();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        putSavedContent();;
    }

    @Override
    protected void onResume() {
        super.onResume();
        putSavedContent();
    }
    void putSavedContent()
    {
        /*if(sf.getBoolean("cb",false)==true)
        {
            et.setText(sf.getString("et",""));
            cb.setChecked(true);
        }*/
    }
    public void login(View view)
    {
        //Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
        if(et.getText().toString().compareTo(pd.getText().toString())==0)
        {

            if(cb.isChecked())
            {
                SharedPreferences.Editor editor=sf.edit();
                editor.putBoolean("cb",true);
                editor.putString("et",et.getText().toString());
                //editor.putString("")
                editor.commit();
            }


            Intent i=new Intent(this,SecondActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(this, "Incorrect username and password", Toast.LENGTH_SHORT).show();
        }
    }
}
