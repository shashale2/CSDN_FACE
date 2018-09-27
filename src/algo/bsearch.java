package algo;
//¶þ·Ö²éÕÒ11111
public class bsearch {

	public  static void main (String [] args) throws InterruptedException
	{
		int [] num = {1,3,5,6,7,8,9};
		int tar = 4;
		
		System.out.println(bsearch(num,tar));
		
	}
	
	public static boolean bsearch(int [] num,int tar) throws InterruptedException
	{
		int low =0; int  high = num.length-1; int middle = (low+high)/2;
		while (num[middle]!=tar)
		{
			Thread.sleep(100);
			int key =num[middle];
			
			System.out.println(key);
			if (key>tar)
			{
				high = middle;
				middle =(low+high)/2;
				System.out.println("high"+high+"low"+low+"middle"+middle);
			}
			else
			{
				low = middle ;
				middle =(low+high)/2;
				System.out.println("high"+high+"low"+low+"middle"+middle);
			}
			if (high-low<=1&&tar!=num[low]&&tar!=num[high])
			{
				return false;
			}
				
		}
		return true;
		
	}
}
