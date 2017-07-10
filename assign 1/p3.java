import java.util.*;
class p3
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int n;
		System.out.println("Enter a number");
		n=sc.nextInt();
		
		boolean b=false;
		b=isPrime(n);
		
		if(b==true)System.out.println("Prime");
		else System.out.println("Non prime");
		
	}
	static boolean isPrime(int n)
	{
		for(int i=2;i<n/2;i++)
		{
			if(n%i==0)return false;
		}
		return true;
	}
	
}