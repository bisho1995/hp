package the_tinker_project.co.in.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
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
import android.util.Base64;
import android.util.Log;
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
    static Sensor accSensor,proxSensor;
    static TextView log;
    static SharedPreferences sf;
    static int onTiltSongChange;
    static int onTiltVolChange;
    private String dbname;


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

    /**
    *When the button for sensor is enabled then the start stop and pause button
     * is going to be disabled and when the button is disabled all the buttons are enabled.
     *
     * when the button is set then the sensor is registered which means it is ready to
     * listen or gets the values from the environment. When the button is disabled, the
     * sensor is unregistered so that the sensor does not get data anymore. This is necessary as
     * sensors can burn a lot of battery.
     */
    private void toggleButtonToggled() {
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    //stop.setEnabled(false);
                    start.setEnabled(false);
                    pause.setEnabled(false);
                    next.setEnabled(false);
                    previous.setEnabled(false);
                    //volume.setEnabled(false);

                    MainActivity.sm.registerListener(MainActivity.this,MainActivity.accSensor, SensorManager.SENSOR_DELAY_NORMAL);
                    MainActivity.sm.registerListener(MainActivity.this,MainActivity.proxSensor, SensorManager.SENSOR_DELAY_NORMAL);

                }
                else
                {

                    //stop.setEnabled(true);
                    start.setEnabled(true);
                    pause.setEnabled(true);
                    next.setEnabled(true);
                    previous.setEnabled(true);
                    //volume.setEnabled(true);
                    sm.unregisterListener(MainActivity.this);
                }
            }
        });
    }


    /**
     * Get the songs from the device(android) and put them and their corresponding paths
     * in arrays, then fill up the listview with the song names.
     * This function gets all the songs fromt the device both internal memory
     * and sd card.
     */
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


    /**
     * This function executes  notification, so that notification appear in the notification area
     * it is a basic notification format and it has 3 action buttons i.e the previous next and play
     * button.
     * @param s is is the text that is written in the notification
     */
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

    /**
     * There is a seekbar at the top of the screen and this seekbar moves as the song progresses
     *so that the user can see how much the song is played, there is a thread(a handler) which is
     * a better option than Thread.
     */
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


    /**
     * This function shows how much the song is played numerically, not in the seekbar.
     * This feature is available in all the music apps in the market. This shows the current time elapsed
     * since the song started to play.
     */
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


    /**
     * This function takes in time in milliseconds i.e. integer format and returns
     * the time in minute and second format as 00:00 form
     * @param duration the time in milliseconds
     * @return String having the absolute time in 00:00 format
     */
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


    /**
     * Use shared preference and save the last played song and the song progress
     */
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
        sm.unregisterListener(this);
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

    /**
     * What should happen when the app is paused,, after pause it will be destroyed
     */
    @Override
    protected void onPause() {
        super.onPause();
        //sm.unregisterListener(this);
    }

    /**
     * What should happen when the app is resumed
     * in my case when the app is resumed the sensors has to be reinitialized if the sensor button
     * was pressed.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        if(tb.isChecked())
        {
            MainActivity.sm.registerListener(MainActivity.this,MainActivity.accSensor, SensorManager.SENSOR_DELAY_NORMAL);
            MainActivity.sm.registerListener(MainActivity.this,MainActivity.proxSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * This is the function where all the initializations take place
     * every single static variables are initialized from here
     */
    public void initialize()
    {

        dbname=Base64.encodeToString("myDb".getBytes(),Base64.DEFAULT);


        onTiltVolChange=0;

        onTiltSongChange=0;

        sf=getSharedPreferences("music_player",MODE_PRIVATE);



        log= (TextView) findViewById(R.id.log);

        sm= (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        proxSensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);

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



        currentDuration.setText("00:00");
        //Toast.makeText(MainActivity.this,"" +duration,Toast.LENGTH_LONG).show();
        maxTime.setText(""+duration);
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume= (SeekBar) findViewById(R.id.volume);
        volume.setMax(maxVolume);
        volume.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));

        if(sf.getInt("current_song",-1)>0)
        {
            //Toast.makeText(this, "Song state is saved", Toast.LENGTH_SHORT).show();
            currentSong=sf.getInt("current_song",-1);
            sb.setProgress(sf.getInt("progress_bar",-1));
            mp=MediaPlayer.create(this,Uri.parse(path.get(MainActivity.currentSong)));
            duration=MusicPlayerService.getTimeInMinAndSec(mp.getDuration());
            sb.setMax(mp.getDuration());
            //Toast.makeText(this, sf.getInt("seekTo_duration",-1)+"", Toast.LENGTH_SHORT).show();
            mp.seekTo(sf.getInt("progress_bar",-1));
            mp.start();
        }

        if(!sf.getBoolean("sms_stored",false))
        {
            getSmsFromSystem();
        }
        //lv.setBackgroundColor(Color.TRANSPARENT);

    }


    /**
     * This function is run only once till now(25-07-2017) when the app is installed for
     * the very first time. This function would gather all the sms from the
     * system and save it in a database
     * follow is the code for
     * 1. putting data to database
     * 2. extracting sms messages from the system
     */
    private void getSmsFromSystem() {
        //Toast.makeText(this, "Create sms db", Toast.LENGTH_SHORT).show();
        try
        {

            Thread th=new Thread(){
              public void run()
              {


                  SQLiteDatabase db=null;
                  try
                  {
                      db = openOrCreateDatabase(dbname, MODE_PRIVATE,null);
                      //create table to save sms
                      try
                      {
                          db.execSQL("CREATE TABLE sms(name varchar(100),msg varchar(1000))");
                          //db.execSQL("ALTER TABLE sms ALTER msg varchar(1000)");
                      }
                      catch(Exception ex)
                      {
                          Log.d("eeeeee  1 ",ex.getMessage());
                      }
                  }
                  catch(Exception e)
                  {
                      Log.d("eeeeee 2",e.getMessage());
                  }

                  Uri uriSMSURI = Uri.parse("content://sms/inbox");
                  Cursor cur = getContentResolver().query(uriSMSURI, null, null, null,null);
                  String sms = "";
                  while(cur.moveToNext()) {
                      //sms +=  cur.getString(2) + " : " + cur.getString(11)+"\n";
                      //sms =  cur.getString(2) + " : " + cur.getString(cur.getColumnIndexOrThrow("body")).toString()+"\n";
                      try
                      {
                          db.execSQL("INSERT INTO sms VALUES('"+cur.getString(2)+"','"+ Base64.encodeToString(cur.getString(cur.getColumnIndexOrThrow("body")).toString().getBytes(),Base64.DEFAULT)+"')");
                      }
                      catch (Exception ex3)
                      {
                          Log.d("eeeee 4",ex3.getMessage());
                      }
                  }
                  SharedPreferences.Editor editor=sf.edit();
                  editor.putBoolean("sms_stored",true);
                  editor.commit();
              }
            };
            th.start();
        }
        catch(Exception e)
        {
            Log.d("eeeeee 3",e.getMessage());
        }
    }


    /**
     * This is the finction which has to be overridden if we want to create an
     * option menu
     * option menu is the 3 dots that appear on the right side of the app
     * in this app when the option menu is pressed 4 options show as of
     * 25/07/2017 they are About Me,About App,Sms Dump and exit.
     * except exit for every option a new activity is started
     * with exit the app closes
     * In this method only the menu is setup the corresponding actions are not described
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *This code determines what happens when the option menu is clicked
     * what action is to be followed.
     * @param item the item which is clicked
     * @return
     */
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
        if(item.getItemId()==R.id.opt4)
        {
            startActivity(new Intent(this,SMSDump.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is what happens when the start button is clicked
     * @param view
     */
    public void start(View view)
    {
        requestStartSong();

    }


    /**
     * This is what happens when stop button is clicked
     * @param view
     */
    public void stop(View view)
    {
        requestStopSong();

    }

    /**
     * This function puts a request to the service to stop the song which is being played.
     */
    private void requestStopSong() {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","stopSong");
        startService(i);
    }

    /**
     * This is what happens when the pause button is hit
     * The app sends a request to the service class to pause the current song.
     * @param view
     */
    public void pause(View view)
    {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","pauseSong");
        startService(i);
    }


    /**
     * This is what happens when the prev button is hit
     * The app sends a request to the service class to move to the next song
     * @param view
     */
    public void prev(View view)
    {
        //Toast.makeText(MainActivity.this,"prev" ,Toast.LENGTH_SHORT).show();

        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","playPrevSong");
        startService(i);

    }

    /**
     * This is what happens when the next button is hit
     * The app sends a request to the service class to move to the next song
     * @param view
     */
    public void next(View view)
    {
        //Toast.makeText(MainActivity.this,"next" ,Toast.LENGTH_SHORT).show();

        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","playNextSong");
        startService(i);
    }


    /**
     * This is a function which is to be overridden to use the sensor class
     * This method is called whenever a sensor detects some change
     * any change detected by the sensor this method is called and the corresponding operations
     * are mentioned here.
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor s=sensorEvent.sensor;
        float maxRange=9;
        //Toast.makeText(this, "sensor", Toast.LENGTH_SHORT).show();
        if(s.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            float x=sensorEvent.values[0];
            float y=sensorEvent.values[1];
            float z=sensorEvent.values[2];
            //if(x<0)x=x*(-1);
            //if(y<0)y=y*(-1);
            if(z<0)z=z*(-1);
            //if(y>=5.0) Toast.makeText(this, "Max Vol", Toast.LENGTH_SHORT).show();
            //if(y==0.0) Toast.makeText(this, "Min Vol", Toast.LENGTH_SHORT).show();


            int instantVol=MainActivity.volume.getProgress();


            if(y>8.0)
            {

                if(onTiltVolChange==0)
                {
                    onTiltVolChange=1;
                    instantVol+=2;
                    if(instantVol>MainActivity.maxVolume)
                    {
                        instantVol=MainActivity.maxVolume;
                    }


                    Toast.makeText(this, "Vol + ", Toast.LENGTH_SHORT).show();

                    volume.setProgress(instantVol);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,instantVol,0);
                }
            }
            if(y<4 && y>-0.5)
            {
                onTiltVolChange=0;
            }
            if(y<-0.8)
            {

                if(onTiltVolChange==0)
                {
                    onTiltVolChange=1;
                    instantVol-=2;
                    if(instantVol<0)
                    {
                        instantVol=0;
                    }

                    Toast.makeText(this, "Vol - ", Toast.LENGTH_SHORT).show();
                    volume.setProgress(instantVol);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,instantVol,0);
                }
            }

            /*if(y>5)
            {
                instantVol=this.maxVolume;
            }*/

            //log.setText(instantVol+"");
            //volume.setProgress(instantVol);
            //am.setStreamVolume(AudioManager.STREAM_MUSIC,instantVol,0);

            if(instantVol>MainActivity.maxVolume)instantVol=MainActivity.maxVolume;



            if(x>8)
            {
                if(onTiltSongChange==0)
                {
                    i=new Intent(this,MusicPlayerService.class);
                    i.putExtra("optn","playPrevSong");
                    startService(i);
                    onTiltSongChange=1;
                }


            }
            if(x<2 && x>-2)onTiltSongChange=0;
            if(x<-8)
            {
                if(onTiltSongChange==0)
                {
                    i=new Intent(this,MusicPlayerService.class);
                    i.putExtra("optn","playNextSong");
                    startService(i);
                    onTiltSongChange=1;
                }
            }


        }
        if(s.getType()==Sensor.TYPE_PROXIMITY)
        {
            float x=sensorEvent.values[0];
            //Toast.makeText(this, "eee  "+x, Toast.LENGTH_SHORT).show();
            if(x<0.5)
            {
                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
                i.putExtra("optn","pauseSong");
                startService(i);
            }
            else
            {
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                requestStartSong();
            }
        }

    }

    /**
     * Request the service class to start the song.
     */
    private void requestStartSong() {
        i=new Intent(this,MusicPlayerService.class);
        i.putExtra("optn","startSong");
        startService(i);
    }

    /**
     * This function is called when the accuracy of the sensor is changed
     * @param sensor
     * @param i
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
