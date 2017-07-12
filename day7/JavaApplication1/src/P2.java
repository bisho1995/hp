class P2Demo
{
	void show()
	{
		int a = 9;
		class MethodLocalInnerClass
		{
			void display()
			{
				System.out.println("Display of MethodLocalInnerClass");
			}
		}
		MethodLocalInnerClass obj=new MethodLocalInnerClass();
		obj.display();
	}
}

public class P2
{
	public static void main(String args[])
	{
		new P2Demo().show();
	}
}