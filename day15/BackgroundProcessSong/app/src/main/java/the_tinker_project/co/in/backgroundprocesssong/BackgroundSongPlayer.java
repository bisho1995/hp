package the_tinker_project.co.in.backgroundprocesssong;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.Toast;

public class BackgroundSongPlayer extends Service {

    MediaPlayer mp;

    public BackgroundSongPlayer() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Thread th=new Thread(){
          public void run()
          {
              while(true)
              {
                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }
        };
        //th.start();
        mp=MediaPlayer.create(this,R.raw.song0);
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
