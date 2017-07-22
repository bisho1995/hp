package the_tinker_project.co.in.broadcast1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by user on 18-07-2017.
 */

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
        {
            Toast.makeText(context,"Outgoing Call",Toast.LENGTH_LONG).show();
        }
    }
}
