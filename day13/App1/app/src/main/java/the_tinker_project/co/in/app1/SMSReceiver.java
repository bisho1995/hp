package the_tinker_project.co.in.app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by user on 19-07-2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Sms received", Toast.LENGTH_SHORT).show();
        /*Bundle bundle = intent.getExtras(); ​
        ​​​​​​​SmsMessage[] msgs = null; ​​​​​​​
        ​String str = "";
        ​​​​​​​​if (bundle != null)
            ​​​​​​​​{ ​​​​​​​​​​​​//---retrieve the SMS message received--​​
             ​​​​​​​​​​Object[] pdus = (Object[]) bundle.get("pdus");
              ​​​​​​​​​​​​msgs = new SmsMessage[pdus.length];
              ​​​​​​​​​​​​for (int i = 0; i < msgs.length; i++) { ​​​​​​​​​​​​​​​​
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                   ​​​​​​​​​​​​​​​​str += "SMS from " + msgs[i].getOriginatingAddress();
                str += " :"; ​​
                  ​​​​​​​​​​​​​​str += msgs[i].getMessageBody().toString();
                   ​​​​​​​​​​​​​​​​str += "\n";
                    ​​​​​​​​​​​​} ​​​​​​​​​​​​
            //---display the new SMS message--​​
            ​​​​​​​​​​Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

        }*/
    }
}
