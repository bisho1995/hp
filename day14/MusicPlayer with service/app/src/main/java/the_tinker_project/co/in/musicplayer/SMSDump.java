package the_tinker_project.co.in.musicplayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SMSDump extends AppCompatActivity {

    static ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsdump);

        lv= (ListView) findViewById(R.id.sms);

        SQLiteDatabase db = openOrCreateDatabase(Base64.encodeToString("myDb".getBytes(), Base64.DEFAULT),MODE_PRIVATE,null);
        Cursor c=db.rawQuery("SELECT * from sms",null);
        ArrayList<String> ar=new ArrayList<String>();
        while(c.moveToNext())
        {
            //ar.add(c.getString(0)+" "+Base64.decode(c.getString(1),Base64.DEFAULT));
            ar.add(c.getString(0)+" "+c.getString(1));

        }
        ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar);
        lv.setAdapter(adp);
    }
}
