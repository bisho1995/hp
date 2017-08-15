package the_tinker_project.co.in.musicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import static the_tinker_project.co.in.musicplayer.MainActivity.currentSong;
import static the_tinker_project.co.in.musicplayer.MainActivity.mp;

public class MusicPlayerService extends Service {
    public MusicPlayerService() {
    }

    void toast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //toast("Service Starting");
        if(intent!=null)
        {
            String optn=intent.getStringExtra("optn");
            //get the optn from the extras

            if(optn.equals("startSong"))
            {
                startSong();
                //start song
            }

            if(optn.equals("playNextSong"))
            {
                playNextSong();
                //play song
            }
            if(optn.equals("songDeterminer"))
            {
                String val=intent.getStringExtra("val");
                playSongDeterminer(Integer.parseInt(val));
                //play a particular song

            }
            if(optn.equals("stopSong"))
            {
                stopSong();
                //stop the song

            }if(optn.equals("pauseSong"))
            {
            pauseSong();
            //pause the song

            }
            if(optn.equals("playPrevSong"))
            {
                playPrevSong();
                //play the previous song
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     *
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * start playing the current song
     */
    public  void startSong() {
        //Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        if(mp==null)//do everything only if the mp object is not null
        {
            //Toast.makeText(MainActivity.this,"mp initialized from start",Toast.LENGTH_SHORT).show();
            //mp=MediaPlayer.create(this,MainActivity.songIdFromRaw[MainActivity.currentSong]);
            mp=MediaPlayer.create(this, Uri.parse(MainActivity.path.get(MainActivity.currentSong)));
            //create media player object with the current song's path
            MainActivity.duration=getTimeInMinAndSec(mp.getDuration());
            //get duration of the song
            MainActivity.sb.setMax(mp.getDuration());
            //set the max duration
            mediaPlayerOnCompletionListerner();
            //do something on the completion of the current song playing
            mp.start();
            //start song
            MainActivity.currentSongTime=0;
            //set current song time to zero
            startNotification(MainActivity.song.get(currentSong));
            //set the notification of the current song
        }
        else
        {
            mp.start();
        }
    }


    /**
     * Stop the current song..
     */
    public static void stopSong()
    {
        //Toast.makeText(MainActivity.this,"stop",Toast.LENGTH_SHORT).show();
        if(mp!=null)
        {
            mp.stop();//stop song
            mp=null;//make it null
            MainActivity.sb.setProgress(0);//set the progress zero
            MainActivity.currentDuration.setText("00:00");//current song duration is also zero
        }
    }

    /**
     * pause the current song
     */
    public static void pauseSong() {

        //Toast.makeText(MainActivity.this,"pause",Toast.LENGTH_SHORT).show();
        if(mp!=null && mp.isPlaying())//only if the mp is not null and song is really playing then pause the song
        {
            mp.pause();
        }
    }

    /**
     * play the previous song
     */
    public void playPrevSong()
    {
        //Toast.makeText(MainActivity.this,"previous",Toast.LENGTH_SHORT).show();
        MainActivity.currentSong=(MainActivity.currentSong-1);
        //the previous song so current song -1


        //if the current song is < 0 song will be max no of song
        if(MainActivity.currentSong<0)
        {
            MainActivity.currentSong=MainActivity.noOfSongs+MainActivity.currentSong;
        }
        playSongDeterminer();
        //play the correct song
    }
    public  void playNextSong() {
        MainActivity.currentSong=(MainActivity.currentSong+1)%MainActivity.noOfSongs;
        playSongDeterminer();
    }


    /**
     * get integer date and convert it to 00:00 time
     * @param duration the raw time
     * @return
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
     * change song from one song to another
     * @param id the id of the song to be played the first song has id 0 second has id 1 and so on
     */
    void songSwitchProperWay(int id)
    {
        stopSong();//stop the song first which has been already playing

        mp=MediaPlayer.create(this,Uri.parse(MainActivity.path.get(MainActivity.currentSong)));
        //create the mediaplayer object with the absolute path of the song
        //mp=MediaPlayer.create(this,MainActivity.songIdFromRaw[id]);
        mediaPlayerOnCompletionListerner();//execute the onCompleteListener with the current song


        if(mp!=null)//make sure the mp is not null
        {
            mp.start();
            //start the music player
            MainActivity.sb.setProgress(0);
            //new song so seekbar is set to zero
            startNotification(MainActivity.song.get(MainActivity.currentSong));
            //set the notification
            //MainActivity.lv.getChildAt(MainActivity.lv.getSelectedItemPosition()).setBackgroundColor(Color.BLUE);
            MainActivity.duration=getTimeInMinAndSec(mp.getDuration());
            //set the duration of the current song as every song will have a different duration
            MainActivity.maxTime.setText(""+MainActivity.duration);
            //set the duration
        }

        //Toast.makeText(this,MainActivity.songs[MainActivity.currentSong],Toast.LENGTH_SHORT).show();
    }

    /**
     * play the proper song
     */
    public void playSongDeterminer()
    {
        songSwitchProperWay(MainActivity.currentSong);
    }

    /**
     * play the proper song
     * @param i the ith indexed song
     */
    public  void playSongDeterminer(int i)
    {
        MainActivity.currentSong=i;//set the song to current song
        songSwitchProperWay(MainActivity.currentSong);//play that song with necessary actions
    }

    public void startNotification(String s)
    {
        //Toast.makeText(this, "notification bar", Toast.LENGTH_SHORT).show();
        //notificationId=1;
        NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder nb=new Notification.Builder(this);
        nb.setTicker(s);
        nb.setContentText(s);
        nb.setContentTitle("Music Player");
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setOngoing(true);
        //nb.setDefaults(Notification.AUDIO_ATTRIBUTES_DEFAULT);
        nb.setAutoCancel(false);
        /**
         * Set the default entries of the notification object
         * the ticker text, content text, title icon etc
         */

        Intent next=new Intent(this,MusicPlayerService.class);
        next.putExtra("optn","playNextSong");//set the intent so that the specific option is executed

        Intent prev=new Intent(this,MusicPlayerService.class);
        prev.putExtra("optn","playPrevSong");


        Intent play=new Intent(this,MusicPlayerService.class);
        play.putExtra("optn","startSong");

        PendingIntent nextPi=PendingIntent.getService(this,1,next,0);
        PendingIntent prevPi=PendingIntent.getService(this,1,prev,0);
        PendingIntent playPi=PendingIntent.getService(this,1,play,0);

        /**
         * add the action buttons, the play previous and next buttons
         */
        nb.addAction(android.R.drawable.ic_media_previous,"Prev",prevPi);
        nb.addAction(android.R.drawable.ic_media_play,"Play",playPi);
        nb.addAction(android.R.drawable.ic_media_next,"Next",nextPi);

        Notification n=nb.build();
        nm.notify(1,n);

        //nm.cancel(notificationId);

    }









    /**
     * detect when the current song has sfinished playing and move on to the next song
     */
      void mediaPlayerOnCompletionListerner() {
          if(MainActivity.mp!=null)
          {
              MainActivity.mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                  @Override
                  public void onCompletion(MediaPlayer mediaPlayer) {
                      playNextSong();//when the current song is dont start playing the next song
                  }
              });
          }

    }






}
