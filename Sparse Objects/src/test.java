public class test{

	private static int num=10;
	private int notStatic=10;
	
	public static void main(String args[]) {

		int x = 10;
		int y;
		y = x;
		System.out.println(y);
		System.out.println(x);
		y = 15;
		
		
	}
	
	public void increment(){
		
		num++;
		notStatic++;
	}

}