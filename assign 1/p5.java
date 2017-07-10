import java.util.*;
class p5
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int a,b;
		System.out.println("Enter a number");
		int n=sc.nextInt();
		System.out.println(facto(n));
	}
	static int facto(int n)
	{
		if(n==1)return 1;
		return n*facto(n-1);
	}
}