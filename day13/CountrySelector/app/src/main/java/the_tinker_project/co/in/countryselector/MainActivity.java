package the_tinker_project.co.in.countryselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Spinner country,state,city;
    String countryArray[];
    String[][] stateArray;
    String[][][] cityArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        countrySelected();
        stateSelected();
        citySelected();
    }

    private void citySelected() {
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, country.getSelectedItem()+" "+state.getSelectedItem()+" "+city.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void stateSelected() {
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city.setVisibility(View.VISIBLE);
                city.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,cityArray[country.getSelectedItemPosition()][i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void countrySelected() {
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state.setVisibility(View.VISIBLE);
                state.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_expandable_list_item_1,MainActivity.this.stateArray[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void initialize()
    {
        country= (Spinner) findViewById(R.id.spinner1);
        countryArray=new String[]{"Country1","Country2"};
        ArrayAdapter ad1= new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,countryArray);
        country.setAdapter(ad1);


        state= (Spinner) findViewById(R.id.spinner2);
        stateArray=new String[][]{
                {"State1","State2"},
                {"State3","State4"}
        };
        ArrayAdapter ad2=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,stateArray[0]);
        state.setAdapter(ad2);
        state.setVisibility(View.INVISIBLE);


        city= (Spinner) findViewById(R.id.spinner3);
        cityArray=new String[][][]{


                {
                        {"1","2"},
                        {"3","4"}
                },
                {
                        {"5","6"},
                        {"7","8"}
                }



        };
        ArrayAdapter ad3=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,cityArray[0][0]);
        city.setAdapter(ad3);
        city.setVisibility(View.INVISIBLE);
    }
}
