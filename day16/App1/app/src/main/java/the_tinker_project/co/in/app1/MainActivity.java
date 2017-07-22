package the_tinker_project.co.in.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1= (Button) findViewById(R.id.button);
        b2= (Button) findViewById(R.id.button2);

        registerForContextMenu(b1);
        registerForContextMenu(b2);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.opt1)
        {
            Toast.makeText(this, "Rate the app", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.opt2)
        {
            Toast.makeText(this, "Share this app", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.opt3)
        {
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.opt1)
        {
            Toast.makeText(this, "Rate the app", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.opt2)
        {
            Toast.makeText(this, "Share this app", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.opt3)
        {
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
