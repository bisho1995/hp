import java.util.Scanner;

class Test 
{
	int num;
	Test(int num)
	{
		this.num=num;
	}
	int reverse()
	{
		String s=num+"";
		String w="";
		for(int i=s.length()-1;i>=0;i--)
			w+=s.charAt(i)+"";
		return Integer.parseInt(w);
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter a number");
		Test obj=new Test(sc.nextInt());
		System.out.println("Reverse of the number is "+obj.reverse());
	}
}