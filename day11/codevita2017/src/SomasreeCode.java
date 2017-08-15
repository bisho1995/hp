
import java.util.Scanner;

public class SomasreeCode {
    static int M,N, v[][];
    static char m[][];
    
    
    static void solve(int x, int y, int mark)
    {
        if(x<0 || x>=M || y<0 || y>=N)return;
        if(m[x][y]=='W')return;

        if(mark>=v[x][y])return;

        v[x][y]=mark;
        //System.out.println(v[x][y]);
        for(int i=-1;i<=1;i++)
        {
            for(int j=-1;j<=1;j++)
                solve(x+i,y+j,mark+1);
        }
    }
    static void prtV()
    {
        System.out.println("\nPrt V\n");
        for(int i=0;i<M;i++)
        {
            for(int j=0;j<N;j++)
            {
                System.out.print(v[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        v=new int[25][25];
        m=new char[25][25];
        
        M=sc.nextInt();
        N=sc.nextInt();
        
        int p,q;
        p=sc.nextInt()-1;
        q=sc.nextInt()-1;
        
        for(int i=0;i<M;i++)
        {
            for(int j=0;j<N;j++)
            {
                m[i][j]=sc.next().charAt(0);
            }
        }
        
        for(int i=0;i<M;i++)
        {
            for(int j=0;j<N;j++)
            {
                v[i][j]=20;
            }
        }
        
        
        
        solve(p,q,1);

        int maxm = -650;
        for(int i=0;i<M;i++)
            for(int j=0;j<N;j++)
                if(v[i][j]>maxm && m[i][j]!='W')
                    maxm=v[i][j];
        
        System.out.println(maxm);
        
        prtV();
        
        
    }
}

/*
w 5 4 3 2 1 
6 5 4 3 2 w 
w w w w w 3 
8 7 6 w 4 w 
w 7 6 5 5 w
8 7 6 6 6 6 
*/