


package the_tinker_project.co.in.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * This is a basic calculator, it has  two textboxes and 5 buttons namely add,subtract,multiply,divide and mod.
 * It calculates the result from the textboxes and gives the output.
 * Tested  from api 19 or kitkat
 * @author Bisvarup Mukherjee
 / @version 14/07/2017
 */
public class MainActivity extends AppCompatActivity {

    /**
     * number1 and number2 are the two variables which store the two numbers from the textboxes which
     * are supplied by the user
     */
    public int number1,number2;
    TextView tv;

    /**
     * This function gets the numbers from the two textboxes and assigns them to instance variables of the class
     * namely number1 and number2
     */
    public void getDataFromTextboxes()
    {
        /**
         * Get the respective objects from the textfields and the textview so that we can operate on them
         */
        EditText num1=(EditText)findViewById(R.id.num1);
        EditText num2=(EditText)findViewById(R.id.num2);
        this.tv=(TextView)findViewById(R.id.display);


        String numString1,numString2;

        numString1=num1.getText().toString();
        numString2=num2.getText().toString();

        if(numString1=="")
        {
            numString1="0";
        }
        if(numString2=="")
        {
            numString2="0";
        }


        /**
         * Get the actual data from the objects and store them in these instance variables
         */


        //handle the situations when the user doesnot supply witha  number but still
        //hit the add or any other button
        try
        {
            this.number1=Integer.parseInt(numString1);
        }
        catch (Exception e)
        {
            this.number1=0;
        }
        try
        {
            this.number2=Integer.parseInt(numString2);
        }
        catch(Exception e)
        {
            this.number2=0;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Clear all the textfields, so that the user gets a fresh start
     * @param view
     */
    public void clear(View view)
    {
        /**
         * Get the respective objects from the textfields and the textview so that we can operate on them
         */
        EditText num1=(EditText)findViewById(R.id.num1);
        EditText num2=(EditText)findViewById(R.id.num2);
        this.tv=(TextView)findViewById(R.id.display);


        /**
         * clear all fields
         */
        try{

            num1.setText("");
            num2.setText("");
            this.tv.setText("");
        }
        catch(Exception e)
        {
            System.err.println("Error in clear method "+e);
        }


    }

    /**
     * This function adds two numbers from two textboxes and gives the output.
     * @param view
     */
    public void add(View view)
    {
        this.getDataFromTextboxes();
        try
        {
            String s="Result = "+(this.number1+this.number2)+"";
            this.tv.setText(s);
        }
        catch(Exception e)
        {
            this.tv.setText(" Error is "+e);
        }

    }

    /**
     * This function find the difference between two numbers, it is always first number minus second number
     * @param view
     */
    public void sub(View view)
    {
        this.getDataFromTextboxes();
        String s="Result = "+(this.number1-this.number2)+"";
        this.tv.setText(s);

    }

    /**
     * This functions divides first number by the second number.
     * @param view
     */
    public void div(View view)
    {
        this.getDataFromTextboxes();
        try
        {
            String s="Result = "+(this.number1/this.number2)+"";
            this.tv.setText(s);
        }catch(Exception e)
        {
            this.tv.setText("NAN");
        }

    }//end of div

    /**
     * This function multiplies two numbers from the textboxes and gives the output.
     * @param view
     */
    public void mul(View view)
    {
        this.getDataFromTextboxes();


        try
        {
            String s="Result = "+(this.number1*this.number2)+"";
            this.tv.setText(s);
        }catch(Exception e)
        {
            this.tv.setText("NAN");
        }

    }//end of mul

    /**
     * This function takes two numbers from the text boxes and finds the modulus of the two numbers in the form of
     * first_number%second_number
     * @param view
     */
    public void mod(View view)
    {
        this.getDataFromTextboxes();

        try
        {
            String s="Result = "+(this.number1%this.number2)+"";
            this.tv.setText(s);
        }catch(Exception e)
        {
            this.tv.setText("NAN");
        }

    }//end of mod

}//end of class
