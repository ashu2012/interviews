/*
 * uncomment this if you want to read input.
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class ex2 {
    public static void main(String args[] ) throws Exception {
        /*
         * Read input from stdin and provide input before running
	*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int N = Integer.parseInt(line);
        for (int j = 0; j < N; j++) {
            //reading first test case condition
		int Q = Integer.parseInt(br.readLine());
		for (int i = 0; i < Q; i++) {
			String currentNumber = br.readLine();
        		int n = Integer.parseInt(currentNumber);

			if(specialCurrent(n)){
			System.out.println(0);
			}
			else{
				int specialLess=specialLess(n);
				int specialGreater=specialGreater(n);
			
				int absdiff1=Math.abs(n-specialLess);
				int absdiff2=Math.abs(n-specialGreater);
				if(absdiff1<absdiff2){
				System.out.println(absdiff1);
				}
				else{
				System.out.println(absdiff2);
				}
			}		
				
		}
		

        }
	

    }

	public static boolean specialCurrent(int n){
		
			if (checkSpecial(n)){
			return true;		
			}
			else{
			return false;
			}
		
	}

	public static int specialLess(int n){
		n= n-1;
		//boolean result= False;
		while(n>1){
			if (checkSpecial(n)){
			return n;		
			}
			else{
			n= n-1;
			}
		}

		return 0;
	}

	public static int specialGreater(int n){
		n= n+1;
		int upperlimit=n+10;
		//boolean result= False;
		while(true){
			if (checkSpecial(n)){
			return n;		
			}
			else{
			n= n+1;
			}
		}

		
	}

	 public static boolean checkSpecial(int n){
		// create a hash set
		HashMap<Integer, Integer>  hm = new HashMap<Integer, Integer>();	
      		
		int i=(int)Math.ceil(Math.sqrt(n));
		//System.out.println(n+" ");
		int Number=n;
		while(i>1 && i<= (int)Math.ceil(Math.sqrt(Number)))
		  {
			double exponent =  Math.log(Number) / Math.log(i);
			int approxNumber= (int)Math.pow(i,(int)exponent);
		  	 if (approxNumber ==Number) {
			    // special number
				return true;
			}
			i--;		
		  }
		
		return false;	
		
		
	}
}

