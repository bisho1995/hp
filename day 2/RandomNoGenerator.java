//5 random no between 1 to 100 
class RandomNoGenerator
{
	public static void main(String args[])
	{
		for(int i=1;i<=5;i++)
		{
			double r=Math.random();
			int n=(int)(r*100);
			System.out.println(n);
		}
	}
}