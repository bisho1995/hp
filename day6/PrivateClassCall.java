class PrivateClassCall
{
	private Test()
	{
		
	}
	
	static PrivateClassCall getObject()
	{
		return new PrivateClassCall();
	}
}