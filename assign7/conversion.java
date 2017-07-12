package temperature;

public class conversion
{
	public double f2c(double f)
	{
		//System.out.println("F i got is "+f);
		//double c=(0.55556)*(f);
		double c=(5.0/9.0)*(f);
		//System.out.println("c = "+c);
		return (c-32.0);
	}
}