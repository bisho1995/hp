package the_tinker_project.co.in.funkydropdown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String[] country;
    public String[][] state;
    public String[][][] city;
    public Spinner spCountry,spState,spCity;
    public TextView tv;
    public int iCountry,jState,kCity;

    public void initialize()
    {
        this.country= new String[]{"Country1", "Country2", "Country3"};
        this.state=new String[][]{{"State1","State2","State3"},{"State4","State5","State6"},{"State7","State8","State9"}};
        this.city=new String[][][]{{{"city1","city2"},{"city1","city2"},{"city1","city2"}},{{"city1","city2"},{"city1","city2"},{"city1","city2"}},{{"city1","city2"},{"city1","city2"},{"city1","city2"}}};
        this.spCountry= (Spinner) findViewById(R.id.country);
        this.spState= (Spinner) findViewById(R.id.state);
        spCity= (Spinner) findViewById(R.id.city);
        tv= (TextView) findViewById(R.id.display);
        iCountry=0;
        jState=0;
        kCity=0;
    }


    public void initializeSpinners()
    {
        initializeCountrySpinner();
        spCountry.setVisibility(View.VISIBLE);
        spState.setVisibility(View.INVISIBLE);
        spCity.setVisibility(View.INVISIBLE);
    }

    public void initializeCountrySpinner()
    {
        ArrayAdapter countryAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,this.country);
        spCountry.setAdapter(countryAdapter);
    }

    public void initializeStateSpinner(int i)
    {
        ArrayAdapter countryAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,this.state[i]);
        spCountry.setAdapter(countryAdapter);
    }

    public void initializeCitySpinner(int i,int j)
    {
        ArrayAdapter countryAdapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,this.city[i][j]);
        spCountry.setAdapter(countryAdapter);
    }
    public void stateSelected()
    {
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                jState=i;
                tv.setText(state[iCountry][jState]);
                spCity.setVisibility(View.VISIBLE);
                initializeCitySpinner(iCountry,jState);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void countrySelected()
    {
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                iCountry=i;
                tv.setText(country[i]);
                spCountry.setVisibility(View.VISIBLE);
                spState.setVisibility(View.VISIBLE);
                spCity.setVisibility(View.INVISIBLE);
                //initializeStateSpinner(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeSpinners();
        countrySelected();
        //stateSelected();
    }
}
