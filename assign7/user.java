package mypack;

import temperature.conversion;

class user
{
	public static void main(String args[])
	{
		double f=25.2;
		conversion o=new conversion();
		System.out.println(o.f2c(25.2));
	}
}