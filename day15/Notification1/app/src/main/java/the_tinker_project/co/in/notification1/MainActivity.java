package the_tinker_project.co.in.notification1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listFiles();
    }
    void toast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public void notifyMe(View v)
    {
        int id=(int)System.currentTimeMillis();
        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder nb=new Notification.Builder(this);
        nb.setTicker("Ticker Text");
        nb.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        nb.setAutoCancel(true);
        nb.setContentText("Content Text");
        nb.setSubText("Sub Text");
        nb.setContentTitle("Content Title");
        nb.setDefaults(Notification.DEFAULT_ALL);
        Intent i=new Intent(this,SecondActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,id,i,0);
        nb.setContentIntent(pi);
        nb.setAutoCancel(true);

        Intent next=new Intent(this,MyService.class);
        next.putExtra("optn","next");
        PendingIntent nextPi=PendingIntent.getService(this,id,next,0);
        nb.addAction(android.R.drawable.ic_media_next,"Prev",nextPi);
        Notification n=nb.build();
        nm.notify(id,n);

    }

    void listFiles()
    {
        ListView lv= (ListView) findViewById(R.id.lv);
        //File f= Environment.getExternalStorageDirectory();
        File f0= new File("/storage/sdcard0/");
        //File f= new File("/storage/"+f0.list()[0]);

        String list[]=f0.list();
        ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adp);
    }

}
