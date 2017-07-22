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

            if(optn.equals("startSong"))
            {
                startSong();

            }

            if(optn.equals("playNextSong"))
            {
                playNextSong();

            }
            if(optn.equals("songDeterminer"))
            {
                String val=intent.getStringExtra("val");
                playSongDeterminer(Integer.parseInt(val));

            }
            if(optn.equals("stopSong"))
            {
                stopSong();

            }if(optn.equals("pauseSong"))
        {
            pauseSong();

        }
            if(optn.equals("playPrevSong"))
            {
                playPrevSong();

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public  void startSong() {
        //Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
        if(mp==null)
        {
            //Toast.makeText(MainActivity.this,"mp initialized from start",Toast.LENGTH_SHORT).show();
            //mp=MediaPlayer.create(this,MainActivity.songIdFromRaw[MainActivity.currentSong]);
            mp=MediaPlayer.create(this, Uri.parse(MainActivity.path.get(MainActivity.currentSong)));
            MainActivity.duration=getTimeInMinAndSec(mp.getDuration());
            MainActivity.sb.setMax(mp.getDuration());
            mediaPlayerOnCompletionListerner();
            mp.start();
            MainActivity.currentSongTime=0;
            startNotification(MainActivity.song.get(currentSong));
        }
        else
        {
            mp.start();
        }
    }






    public static void stopSong()
    {
        //Toast.makeText(MainActivity.this,"stop",Toast.LENGTH_SHORT).show();
        if(mp!=null)
        {
            mp.stop();
            mp=null;
            MainActivity.sb.setProgress(0);
            MainActivity.currentDuration.setText("00:00");
        }
    }

    public static void pauseSong() {

        //Toast.makeText(MainActivity.this,"pause",Toast.LENGTH_SHORT).show();
        if(mp!=null && mp.isPlaying())
        {
            mp.pause();
        }
    }

    public void playPrevSong()
    {
        //Toast.makeText(MainActivity.this,"previous",Toast.LENGTH_SHORT).show();
        MainActivity.currentSong=(MainActivity.currentSong-1);
        // 0-1
        if(MainActivity.currentSong<0)
        {
            MainActivity.currentSong=MainActivity.noOfSongs+MainActivity.currentSong;
        }
        playSongDeterminer();
    }
    public  void playNextSong() {
        MainActivity.currentSong=(MainActivity.currentSong+1)%MainActivity.noOfSongs;
        playSongDeterminer();
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
     * change song from one song to another
     * @param id the id of the song to be played the first song has id 0 second has id 1 and so on
     */
    void songSwitchProperWay(int id)
    {
        stopSong();

        mp=MediaPlayer.create(this,Uri.parse(MainActivity.path.get(MainActivity.currentSong)));
        //mp=MediaPlayer.create(this,MainActivity.songIdFromRaw[id]);
        mediaPlayerOnCompletionListerner();
        if(mp!=null)
        {
            mp.start();
            MainActivity.sb.setProgress(0);
            startNotification(MainActivity.song.get(MainActivity.currentSong));
            //MainActivity.lv.getChildAt(MainActivity.lv.getSelectedItemPosition()).setBackgroundColor(Color.BLUE);
            MainActivity.duration=getTimeInMinAndSec(mp.getDuration());
            MainActivity.maxTime.setText(""+MainActivity.duration);
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
        MainActivity.currentSong=i;
        songSwitchProperWay(MainActivity.currentSong);
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

        Intent next=new Intent(this,MusicPlayerService.class);
        next.putExtra("optn","playNextSong");

        Intent prev=new Intent(this,MusicPlayerService.class);
        prev.putExtra("optn","playPrevSong");


        Intent play=new Intent(this,MusicPlayerService.class);
        play.putExtra("optn","startSong");

        PendingIntent nextPi=PendingIntent.getService(this,1,next,0);
        PendingIntent prevPi=PendingIntent.getService(this,1,prev,0);
        PendingIntent playPi=PendingIntent.getService(this,1,play,0);

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
                      playNextSong();
                  }
              });
          }

    }






}
