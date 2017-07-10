import java.util.*;
class p6
{
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		int a,b,c,counter=0;
		a=0;
		b=1;
		System.out.println("Enter  n");
		int n=sc.nextInt();
		counter=0;
		do
		{
			c=a+b;
			System.out.print(a+" ");
			a=b;
			b=c;
			counter++;
		}
		while(counter<n);
	}
}