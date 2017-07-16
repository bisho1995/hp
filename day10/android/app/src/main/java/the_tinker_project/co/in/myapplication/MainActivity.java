package the_tinker_project.co.in.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl;
    CheckBox cb1,cb2,cb3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl= (RelativeLayout)findViewById(R.id.rl);

        cb1= (CheckBox) findViewById(R.id.checkBox);
        cb2= (CheckBox) findViewById(R.id.checkBox2);
        cb3= (CheckBox) findViewById(R.id.checkBox3);

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cb1.isChecked())
                {
                    rl.setBackgroundResource(R.drawable.pic2);
                    cb2.setChecked(false);
                    cb3.setChecked(false);
                }
            }
        });


        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(cb2.isChecked())
                {
                    rl.setBackgroundResource(R.drawable.pic1);
                    cb1.setChecked(false);
                    cb3.setChecked(false);
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(cb3.isChecked())
                {
                    rl.setBackgroundResource(R.drawable.pic3);
                    cb1.setChecked(false);
                    cb2.setChecked(false);
                }
            }
        });


    }
}