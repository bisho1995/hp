package the_tinker_project.co.in.sensors1;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    SensorManager smm;
    List<Sensor> sensor;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smm = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = smm.getSensorList(Sensor.TYPE_ALL);
        lv = (ListView) findViewById (R.id.lv);
        ArrayAdapter adp=new ArrayAdapter<Sensor>(this, android.R.layout.simple_list_item_1,  sensor);
        lv.setAdapter(adp);
        toast(adp.getCount()+"");
    }
    void toast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public  void click(View v)
    {
        toast("Clicked");
    }
}
