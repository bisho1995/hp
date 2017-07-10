import java.util.*;
class p2
{
	public static void main(String args[])
	{
		int count=1;
		for(int i=5;i>=1;i--)
		{
			for(int j=1;j<=5;j++)
			{
				if(j<i)System.out.print(" ");
				
			}
			int j;
			for(j=65;j<65+count;j++)
			{
				System.out.print((char)j);
			}
			for(int k=j-2;k>=65;k--)
			{
				System.out.print((char)k);
			}
			count++;
			System.out.println();
		}
	}
}