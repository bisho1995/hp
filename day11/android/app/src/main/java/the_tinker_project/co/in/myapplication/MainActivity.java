package the_tinker_project.co.in.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            progressBarManipulation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void progressBarManipulation() throws InterruptedException {
        this.pb= (ProgressBar) findViewById(R.id.progressBar);

        this.pb.setMax(100);
        for(int i=1;i<=101;i++)
        {
            this.pb.setProgress(i);
            Thread.sleep(500);
            //if(i==100)i=1;
        }
    }

}
