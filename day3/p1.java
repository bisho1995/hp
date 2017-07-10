class Parent
{
	static{
		System.out.println("Static block of parent");
	}
	Parent()
	{
		System.out.println("This is constructor of parent");
	}
	public void display()
	{
		System.out.println("This is display from parent");
	}
}

class Child extends Parent
{
	static
	{
		System.out.println("Static block of child");
	}
	
	
	Child()
	{
		System.out.println("This is constructor of child");
	}
	public static void main(String args[])
	{
		new Child();
	}
}