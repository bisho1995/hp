package the_tinker_project.co.in.musicplayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.x;




public class MainActivity extends AppCompatActivity {

    /**
     * instance variables
     */
    MediaPlayer mp;
    Button start,stop,pause,next,previous;
    SeekBar sb,volume;
    TextView currentDuration,maxTime;
    ListView lv;
    int currentSong;
    int noOfSongs;
    String[] songs;
    String duration;
    AudioManager am;
    int[] songId;
    int maxVolume;
    int[] songIdFromRaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * utility functions
         */
        initialize();
        seekbarDrag();
        listViewListener();
        volumeControl();
        seekbarMoveAsSongPlays();
    }

    /**
     * detect when the current song has sfinished playing and move on to the next song
     */
    private void mediaPlayerOnCompletionListerner() {
        this.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNextSong();
            }
        });
    }

    /**
     * control the volume from the seekbar
     */
    private void volumeControl() {
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * change song from one song to another
     * @param id the id of the song to be played the first song has id 0 second has id 1 and so on
     */
    void songSwitchProperWay(int id)
    {
        stopSong();
        mp=MediaPlayer.create(MainActivity.this,songIdFromRaw[id]);
        mediaPlayerOnCompletionListerner();
        mp.start();
        sb.setProgress(0);
        duration=getTimeInMinAndSec(mp.getDuration());
        maxTime.setText(""+duration);
        Toast.makeText(MainActivity.this,songs[this.currentSong],Toast.LENGTH_SHORT).show();
    }

    /**
     * play the proper song
     */
    public void playSongDeterminer()
    {
        songSwitchProperWay(this.currentSong);
    }

    /**
     * play the proper song
     * @param i the ith indexed song
     */
    public void playSongDeterminer(int i)
    {
        MainActivity.this.currentSong=i;
        songSwitchProperWay(this.currentSong);
    }

    /**
     * play the song which the user clicks on
     */
    private void listViewListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainActivity.this,i+"",Toast.LENGTH_SHORT).show();
                playSongDeterminer(i);
            }
        });
    }


    /**
     * when the seekbar is dragged set song position to that position
     */
    private void seekbarDrag() {
        MainActivity.this.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(MainActivity.this.mp!=null)
                {
                    MainActivity.this.mp.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void seekbarMoveAsSongPlays()
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

                        MainActivity.this.sb.setProgress(MainActivity.this.mp.getCurrentPosition());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCurrentDuration();
                            }
                        });
                        //MainActivity.this.setCurrentDuration();
                    }

                    updateHandler.postDelayed(this,1000);
                }

            }
        };
        updateHandler.postDelayed(th3,1000);



        Thread th2=new Thread(){
            public void run()
            {
                while(true)
                {
                    if(mp==null)
                    {
                        sb.setProgress(0);
                    }
                    else
                    {

                        MainActivity.this.sb.setProgress(MainActivity.this.mp.getCurrentPosition());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setCurrentDuration();
                            }
                        });
                        //MainActivity.this.setCurrentDuration();
                    }
                }
            }
        };
        //th2.start();
        Thread th=new Thread(){
            public void run()
            {
                while(true)
                {
                    if(mp==null)
                    {
                        sb.setProgress(0);
                    }
                    else
                    {

                        MainActivity.this.sb.setProgress(MainActivity.this.mp.getCurrentPosition());

                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //setCurrentDuration();
                            }
                        });*/
                        //MainActivity.this.setCurrentDuration();
                    }
                }

            }
        };
        //th.start();

    }

    private void setCurrentDuration() {
        if(mp!=null)
        {
            this.currentDuration.setText(this.getTimeInMinAndSec(this.mp.getCurrentPosition()));
        }
        else
        {
            this.currentDuration.setText("00:00");
        }
    }

    public void initialize()
    {
        this.currentSong=0;
        noOfSongs=3;
        sb= (SeekBar) findViewById(R.id.seekBar);
        mp=MediaPlayer.create(this,R.raw.song1);
        start= (Button) findViewById(R.id.start);
        stop= (Button) findViewById(R.id.stop);
        pause= (Button) findViewById(R.id.pause);
        previous= (Button) findViewById(R.id.prev);
        next= (Button) findViewById(R.id.next);
        duration=getTimeInMinAndSec(mp.getDuration());
        sb.setMax(mp.getDuration());
        currentDuration= (TextView) findViewById(R.id.currentTime);
        maxTime= (TextView) findViewById(R.id.maxDuration);

        songId=new int[]{R.raw.song1,R.raw.song2,R.raw.song3};


        songIdFromRaw=new int[]{R.raw.song1,R.raw.song2,R.raw.song3};


        lv= (ListView) findViewById(R.id.lv);

        songs=new String[]{"Labon Se","Jaane kiu - Dil Chahta Hai","Desi Girl"};
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,songs);
        lv.setAdapter(adapter);



        this.currentDuration.setText("00:00");
        //Toast.makeText(MainActivity.this,"" +duration,Toast.LENGTH_LONG).show();
        maxTime.setText(""+duration);
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        this.maxVolume=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume= (SeekBar) findViewById(R.id.volume);
        volume.setMax(this.maxVolume);
        volume.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));
        //Toast.makeText(MainActivity.this,"initialization done",Toast.LENGTH_SHORT).show();
    }

    private String getTimeInMinAndSec(int duration) {
        String min="",sec="";
        duration=duration/1000;
        min=duration/60+"";
        sec=duration%60+"";
        if(Integer.parseInt(min)<10)min="0"+min;
        if(Integer.parseInt(sec)<10)sec="0"+sec;
        return min+":"+sec;
    }


    public void start(View view)
    {
        Toast.makeText(MainActivity.this,"start",Toast.LENGTH_SHORT).show();
        if(mp==null)
        {
            Toast.makeText(MainActivity.this,"mp initialized from start",Toast.LENGTH_SHORT).show();
            mp=MediaPlayer.create(this,R.raw.song1);
            duration=getTimeInMinAndSec(mp.getDuration());
            sb.setMax(mp.getDuration());
            mediaPlayerOnCompletionListerner();
            mp.start();
        }
        else
        {
            mp.start();
        }

    }
    public void stopSong()
    {
        Toast.makeText(MainActivity.this,"stop",Toast.LENGTH_SHORT).show();
        if(mp!=null)
        {
            mp.stop();
            mp=null;
            MainActivity.this.sb.setProgress(0);
            MainActivity.this.currentDuration.setText("00:00");
        }
    }
    public void stop(View view)
    {

        stopSong();

    }
    public void pause(View view)
    {
        pauseSong();
    }

    private void pauseSong() {

        Toast.makeText(MainActivity.this,"pause",Toast.LENGTH_SHORT).show();
        if(mp!=null && mp.isPlaying())
        {
            mp.pause();
        }
    }

    public void prev(View view)
    {
        //Toast.makeText(MainActivity.this,"previous",Toast.LENGTH_SHORT).show();
        this.currentSong=(this.currentSong-1);
        // 0-1
        if(this.currentSong<0)
        {
            this.currentSong=this.noOfSongs+this.currentSong;
        }
        playSongDeterminer();
    }
    public void next(View view)
    {
        //Toast.makeText(MainActivity.this,"next" ,Toast.LENGTH_SHORT).show();
        playNextSong();
    }

    private void playNextSong() {
        this.currentSong=(this.currentSong+1)%MainActivity.this.noOfSongs;
        playSongDeterminer();
    }
}
