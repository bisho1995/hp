import java.util.*;
class p2
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int a,b;
		System.out.println("Enter a and b");
		a=sc.nextInt();
		b=sc.nextInt();
		String s="";
		if(a>=b)s=a+" is greater";
		else s=b+" is greater";
		System.out.println(s);
	}
}