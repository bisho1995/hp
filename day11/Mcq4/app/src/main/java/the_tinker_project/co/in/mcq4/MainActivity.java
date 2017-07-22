package the_tinker_project.co.in.mcq4;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton rb[] = new RadioButton[8];
    Button submit;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTitleBar();
        setContentView(R.layout.activity_main);
        //dispatchTakeVideoIntent();
        //dispatchTakeVideoIntent();
        Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
        setUpRadioButtons();
        submitClicked();
        initializeProgressBar();
        rbStateChanged();
    }


    public void initializeProgressBar() {
        pb = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void rbStateChanged()
    {
        for(int i=0;i<4;i++)
        {
            rb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(MainActivity.this.pb.getProgress()==50)
                    {
                        MainActivity.this.pb.setProgress(100);
                    }
                    else
                        MainActivity.this.pb.setProgress(50);
                }
            });
        }
        for(int i=4;i<8;i++)
        {
            rb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(MainActivity.this.pb.getProgress()==50)
                    {
                        MainActivity.this.pb.setProgress(100);
                    }
                    else
                        MainActivity.this.pb.setProgress(50);
                }
            });
        }
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            //VideoView mVideoView= (VideoView) findViewById(R.id.videoView);
            //mVideoView.setVideoURI(videoUri);
        }
    }

    public void setUpRadioButtons()
    {
        rb[0]= (RadioButton) findViewById(R.id.op1);
        rb[1]= (RadioButton) findViewById(R.id.op2);
        rb[2]= (RadioButton) findViewById(R.id.op3);
        rb[3]= (RadioButton) findViewById(R.id.op4);
        rb[4]= (RadioButton) findViewById(R.id.op5);
        rb[5]= (RadioButton) findViewById(R.id.op6);
        rb[6]= (RadioButton) findViewById(R.id.op7);
        rb[7]= (RadioButton) findViewById(R.id.op8);
    }

    public void submitClicked()
    {
        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    public void removeTitleBar()
    {
        /**
         * Code to remove the title bar at top
         */
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /**
         * Make app fullscreen
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onClick(View view) {
           if(view.getId()==submit.getId())
           {
               String s="";
               //Toast.makeText(MainActivity.this,"Submit is Clicked",Toast.LENGTH_SHORT).show();
               for(int i=0;i<8;i++)
               {
                   if(rb[i].isChecked())
                   {
                       s+=rb[i].getText()+" ";
                   }
               }
               Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
           }
    }
}
