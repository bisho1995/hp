class p3
{
	public static void main(String args[])
	{
		int ar1[][]={{1,8,9},{9,8,9},{1,44,7}};
		int ar2[][]={{1,7,8},{9,44,22},{1,44,7}};
		int ar3[][]=new int[3][3];
		
		for(int i=0;i<ar1.length;i++)
		{
			for(int j=0;j<ar1.length;j++)
			{
				ar3[i][j]=ar1[i][j]+ar2[i][j];
			}
		}
		for(int x[]:ar3)
		{
			for(int y:x)
			{
				System.out.print(y+" ");
			}
			System.out.println();
		}
	}
}