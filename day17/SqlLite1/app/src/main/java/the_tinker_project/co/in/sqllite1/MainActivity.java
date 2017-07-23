package the_tinker_project.co.in.sqllite1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView tv;
    EditText id,pwd,fn;
    Button register,showall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("myDb",MODE_PRIVATE,null);
        if(db!=null)
        {
            //tv= (TextView) findViewById(R.id.tv);
            //tv.setText("db created \nPath "+db.getPath()+"\nMaximum size "+db.getMaximumSize()+"\nVersion "+db.getVersion());
            try
            {
                db.execSQL("CREATE TABLE login_master(user_id varchar(45),password varchar(45),fullname varchar(45))");
                Toast.makeText(this, "Table Created Successfully", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                Toast.makeText(this, "Error in creatisng table "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        initialize();
    }
    public void showall(View v)
    {
        startActivity(new Intent(this,SecondActivity.class));
        finish();
    }


    public void register(View v)
    {
        try
        {
            db.execSQL("INSERT INTO login_master values('"+id.getText().toString()+"','"+pwd.getText().toString()+"','"+fn.getText().toString()+"')");
            id.setText("");
            pwd.setText("");
            fn.setText("");
        }catch(Exception e)
        {
            Toast.makeText(this, "Error is "+e, Toast.LENGTH_SHORT).show();
        }
    }

    private void initialize() {
        id= (EditText) findViewById(R.id.id);
        pwd= (EditText) findViewById(R.id.pwd);
        fn= (EditText) findViewById(R.id.fn);
        register= (Button) findViewById(R.id.register);
        showall= (Button) findViewById(R.id.showall);
    }
}
