package game;

public class TestMain {

	public static void main(String[] args) { 
	         int x = 5, j = 0; 
	         for(int i=0; i<3; )  
	           INNER: do { 
	             i++; x++; 
	             System.out.println("i : " + i + "   x : " + x);
	             if(x > 10) break INNER; 
	             x += 4; 
	             j++; 
	           } while(j <= 2); 
	         System.out.println(x); 
	      } 

}
