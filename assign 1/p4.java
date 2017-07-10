import java.util.*;
class p4
{
	static boolean isArm(int n)
	{
		String s=n+"";
		int sum=0;
		int len=s.length();
		for(int i=0;i<len;i++)
		{
			String ch=s.charAt(i)+"";
			int no=Integer.parseInt(ch);
			sum+=(int)Math.pow(no,len);
		}
		if(sum==n)return true;
		return false;
	}
	public static void main(String args[])
	{
		int count=0;
		for(int i=100; ;i++)
		{
			if(count>=4)break;
			//else System.out.println("count is "+count);
			if(isArm(i)==true)
			{
				System.out.println(i);
				count++;
			}
		}
	}
}