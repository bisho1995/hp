package the_tinker_project.co.in.sqllite1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    static ArrayList<String> ar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ar=new ArrayList<String>();
        ArrayList<String> ar2=new ArrayList<String>();
        lv= (ListView) findViewById(R.id.lv);
        SQLiteDatabase db = openOrCreateDatabase("myDb", MODE_PRIVATE, null);
        Cursor c=db.rawQuery("SELECT * FROM login_master",null);
        while(c.moveToNext())
        {
            ar.add(c.getString(0)+" "+c.getString(1)+" "+c.getString(2));
            ar2.add(c.getString(0));
        }
        ArrayAdapter adp=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar2);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SecondActivity.this);
                builder.setMessage(SecondActivity.ar.get(i)).show();
            }
        });
    }
}
