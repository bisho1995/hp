
/**
 *
 * @author Bisvarup
 */


interface A
{
    abstract void show();
}

interface B
{
    abstract void display(A ob);
}


/**
 * This class shows the use of anonymous i
 * @author Bisvarup
 */
public class P1 {
    public static void main(String[] args) {
        B ob=new B()
        {
            @Override
            public void display(A ob) {
                System.out.println("hello");
                ob.show();
            }
        };
        
        ob.display(new A()
        {
            @Override
            public void show() {
               System.out.println("Hi");
            }
        });
    }
}
