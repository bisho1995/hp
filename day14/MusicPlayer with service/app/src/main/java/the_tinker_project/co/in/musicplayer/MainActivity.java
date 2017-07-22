package the_tinker_project.co.in.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;
import android.hardware.SensorEventListener;

import static android.R.attr.x;




public class MainActivity extends AppCompatActivity implements SensorEventListener {

    /**
     * instance variables
     */
    static MediaPlayer mp;
    Button start,stop,pause,next,previous;
    static SeekBar sb;
    static SeekBar volume;
    static TextView currentDuration;
    static TextView maxTime;
    static ListView lv;
    static int currentSong;
    static int noOfSongs;
    static String[] songs;
    static String duration;
    static AudioManager am;
    int[] songId;
    static int maxVolume;
    static int[] songIdFromRaw;
    static Intent i;
    static int notificationId;
    private NotificationManager nm;
    static ArrayList<String> path;
    static ArrayList<String> song;
    static int PrevPositionOfSong;
    static int currentSongTime;
    static ToggleButton tb;
    static SensorManager sm;
    static Sensor accSensor;
    static TextView log;
    static SharedPreferences sf;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * utility functions
         */
        i=new Intent(this,MusicPlayerService.class);
        initialize();

        seekbarDrag();

        listViewListener();
        volumeControl();
        seekbarMoveAsSongPlays();

        toggleButtonToggled();

        startNotification(song.get(currentSong));
    }

    private void toggleButtonToggled() {
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    stop.setEnabled(false);
                    start.setEnabled(false);
                    pause.setEnabled(false);
                    next.setEnabled(false);
                    previous.setEnabled(false);
                    volume.setEnabled(false);

                    MainActivity.sm.registerListener(MainActivity.this,MainActivity.accSensor,sm.SENSOR_DELAY_NORMAL);

                }
                else
                {

                    stop.setEnabled(true);
                    start.setEnabled(true);
                    pause.setEnabled(true);
                    next.setEnabled(true);
                    previous.setEnabled(true);
                    volume.setEnabled(true);
                    sm.unregisterListener(MainActivity.this);
                }
            }
        });
    }

    void getSongsFromSdCard()
    {
        path=new ArrayList();
        song=new ArrayList();

        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection=MediaStore.Audio.Media.IS_MUSIC+" != 0";
        String projection[]={MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DATA};
        Cursor c=getContentResolver().query(uri,projection,selection,null,null);
        while(c.moveToNext())
        {
            if(c.getString(0).endsWith(".mp3"))
            {
                long tmp=new File(c.getString(1)).length();
                if(tmp>1024*1024*2 && tmp<1024*1024*9)
                {
                    song.add(c.getString(0));
                    path.add(c.getString(1));
                }
            }
        }

        ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,song);
        lv.setAdapter(adp);
    }




    public void startNotification(String s)
    {
        //Toast.makeText(this, "notification bar", Toast.LENGTH_SHORT).show();
        notificationId=1;
        nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder nb=new Notification.Builder(this);
        nb.setTicker(s);
        nb.setContentText(s);
        nb.setContentTitle("Music Player");
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setOngoing(true);
        nb.setDefaults(Notification.DEFAULT_ALL);
        nb.setAutoCancel(false);

        Intent next=new Intent(this,MusicPlayerService.class);
        next.putExtra("optn","playNextSong");

        Intent prev=new Intent(this,MusicPlayerService.class);
        prev.putExtra("optn","playPrevSong");


        Intent play=new Intent(this,MusicPlayerService.class);
        play.putExtra("optn","startSong");

        PendingIntent nextPi=PendingIntent.getService(this,notificationId,next,0);
        PendingIntent prevPi=PendingIntent.getService(this,notificationId,prev,0);
        PendingIntent playPi=PendingIntent.getService(this,notificationId,play,0);

        nb.addAction(android.R.drawable.ic_media_previous,"Prev",prevPi);
        nb.addAction(android.R.drawable.ic_media_play,"Play",playPi);
        nb.addAction(android.R.drawable.ic_media_next,"Next",nextPi);

        Notification n=nb.build();
        nm.notify(notificationId,n);

        //nm.cancel(notificationId);

    }

    public  void seekbarMoveAsSongPlays()
    {
        //Toast.makeText(MainActivity.this,"seekbarMoveAsSongPlays",Toast.LENGTH_SHORT).show();

        final Handler updateHandler=new Handler();
        Runnable th3=new Runnable() {
            @Override
            public void run() {
                //while(true)
                {
                    if(mp==null)
                    {
                        sb.setProgress(0);
                    }
                    else
                    {

                        sb.setProgress(mp.getCurrentPosition());
                        //sb.setProgress(currentSongTime*1000);
                        currentSongTime++;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCurrentDuration();
                            }
                        });
                        //MainActivity.this.setCurrentDuration();
                    }

                    updateHandler.postDelayed(this,2000);
                }

            }
        };
        updateHandler.postDelayed(th3,2000);



    }


    public  void setCurrentDuration() {
        if(mp!=null)
        {
            MainActivity.currentDuration.setText(getTimeInMinAndSec(MainActivity.mp.getCurrentPosition()));
        }
        else
        {
            MainActivity.currentDuration.setText("00:00");
        }
    }

    protected static String getTimeInMinAndSec(int duration) {
        String min="",sec="";
        duration=duration/1000;
        min=duration/60+"";
        sec=duration%60+"";
        if(Integer.parseInt(min)<10)min="0"+min;
        if(Integer.parseInt(sec)<10)sec="0"+sec;
        return min+":"+sec;
    }



    /**
     * control the volume from the seekbar
     */
    public static void volumeControl() {
        MainActivity.volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC,volume.getProgress(),0);
            }
        });
    }


    /**
     * play the song which the user clicks on
     */
    public  void listViewListener() {
        MainActivity.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PrevPositionOfSong=i;

                MainActivity.i=new Intent(MainActivity.this,MusicPlayerService.class);
                MainActivity.i.putExtra("optn","songDeterminer");
                MainActivity.i.putExtra("val",""+i);
                startService(MainActivity.i);

            }
        });
    }


    @Override
    protected void onDestroy() {
        //stopService(i);
        super.onDestroy();
        nm.cancel(notificationId);
        SharedPreferences.Editor editor=sf.edit();
        editor.putInt("current_song",MainActivity.currentSong);
        editor.putInt("progress_bar",MainActivity.sb.getProgress());
        editor.putInt("seekTo_duration",mp.getCurrentPosition());
        editor.commit();
    }

    /**
     * when the seekbar is dragged set song position to that position
     */
    public  void seekbarDrag() {


        MainActivity.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                /*if(mp!=null)
                {
                    mp.seekTo(i);
                }*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(tb.isChecked())
        {
            MainActivity.sm.registerListener(MainActivity.this,MainActivity.accSensor,sm.SENSOR_DELAY_NORMAL);
        }
    }

    public void initialize()
    {
        sf=getSharedPreferences("music_player",MODE_PRIVATE);



        log= (TextView) findViewById(R.id.log);

        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tb= (ToggleButton) findViewById(R.id.tb);
        currentSongTime=0;
        PrevPositionOfSong=0;


        sb= (SeekBar) findViewById(R.id.seekBar);

        lv= (ListView) findViewById(R.id.lv);
        getSongsFromSdCard();

        mp=MediaPlayer.create(this,Uri.parse(path.get(MainActivity.currentSong)));

        noOfSongs=song.size();

        //mp=MediaPlayer.create(this,R.raw.song1);
        start= (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);
        pause= (Button) findViewById(R.id.pause);
        previous= (Button) findViewById(R.id.prev);
        next= (Button) findViewById(R.id.next);
        duration=MusicPlayerService.getTimeInMinAndSec(mp.getDuration());
        sb.setMax(mp.getDuration());
        currentDuration= (TextView) findViewById(R.id.currentTime);
        maxTime= (TextView) findViewById(R.id.maxDuration);

        //songId=new int[]{R.raw.song1,R.raw.song2,R.raw.song3};


        //songIdFromRaw=new int[]{R.raw.song1,R.raw.song2,R.raw.song3};




        //songs=new String[]{"Labon Se","Jaane kiu - Dil Chahta Hai","Desi Girl"};
        //ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,songs);
        //lv.setAdapter(adapter);



        this.currentDuration.setText("00:00");
        //Toast.makeText(MainActivity.this,"" +duration,Toast.LENGTH_LONG).show();
        maxTime.setText(""+duration);
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        this.maxVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume= (SeekBar) findViewById(R.id.volume);
        volume.setMax(this.maxVolume);
        volume.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));

        if(sf.getInt("current_song",-1)>0)
        {
            //Toast.makeText(this, "Song state is saved", Toast.LENGTH_SHORT).show();
            this.currentSong=sf.getInt("current_song",-1);
            this.sb.setProgress(sf.getInt("progress_bar",-1));
            mp=MediaPlayer.create(this,Uri.parse(path.get(MainActivity.currentSong)));
            duration=MusicPlayerService.getTimeInMinAndSec(mp.getDuration());
            sb.setMax(mp.getDuration());
            //Toast.makeText(this, sf.getInt("seekTo_duration",-1)+"", Toast.LENGTH_SHORT).show();
            mp.seekTo(sf.getInt("progress_bar",-1));
            mp.start();
        }


        lv.setBackgroundColor(Color.TRANSPARENT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.opt1)
        {
            startActivity(new Intent(this,About_App.class));
        }
        if(item.getItemId()==R.id.opt2)
        {
            startActivity(new Intent(this,About_Me.class));
        }
        if(item.getItemId()==R.id.opt3)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void start(View view)
    {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","startSong");
        startService(i);

    }



    public void stop(View view)
    {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","stopSong");
        startService(i);

    }
    public void pause(View view)
    {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","pauseSong");
        startService(i);
    }



    public void prev(View view)
    {
        //Toast.makeText(MainActivity.this,"prev" ,Toast.LENGTH_SHORT).show();

        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","playPrevSong");
        startService(i);

    }
    public void next(View view)
    {
        //Toast.makeText(MainActivity.this,"next" ,Toast.LENGTH_SHORT).show();

        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","playNextSong");
        startService(i);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor s=sensorEvent.sensor;
        float maxRange=9;
        //Toast.makeText(this, "sensor", Toast.LENGTH_SHORT).show();
        if(s.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[0];
            float z=sensorEvent.values[0];
            if(x<0)x=x*(-1);
            if(y<0)y=y*(-1);
            if(z<0)z=z*(-1);
            //if(y>=5.0) Toast.makeText(this, "Max Vol", Toast.LENGTH_SHORT).show();
            //if(y==0.0) Toast.makeText(this, "Min Vol", Toast.LENGTH_SHORT).show();
            int instantVol=(int)(z*this.maxVolume/maxRange);

            /*if(y>5)
            {
                instantVol=this.maxVolume;
            }*/

            //log.setText(instantVol+"");
            volume.setProgress(instantVol);
            am.setStreamVolume(AudioManager.STREAM_MUSIC,instantVol,0);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
