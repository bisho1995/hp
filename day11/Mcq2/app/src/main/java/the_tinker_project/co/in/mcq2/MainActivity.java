package the_tinker_project.co.in.mcq2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl;
    Button b;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        initializeDisplay();

    }

    public void initializeDisplay()
    {
        rl=new RelativeLayout(this);
        rl.setBackgroundColor(Color.parseColor("#ffffff"));


        b=new Button(this);
        b.setId(1);
        b.setBackgroundColor(Color.parseColor("#000000"));
        b.setTextColor(Color.parseColor("#ffffff"));
        b.setText("Login");
        RelativeLayout.LayoutParams buttonDetails=new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);


        et=new EditText(this);
        et.setTextColor(Color.parseColor("#555589"));
        RelativeLayout.LayoutParams editTextDetails=new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        editTextDetails.addRule(RelativeLayout.ABOVE,b.getId());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;
        et.setWidth((int) (0.8*screenWidth));

        editTextDetails.setMargins(5,5,5,50);


        rl.addView(b,buttonDetails);
        rl.addView(et,editTextDetails);

        setContentView(rl);
    }






}
