/*
 * uncomment this if you want to read input.
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class ex1 {
    public static void main(String args[] ) throws Exception {
        /*
         * Read input from stdin and provide input before running
	*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        int N = Integer.parseInt(line);
        for (int j = 0; j < N; j++) {
            //reading first test case condition
		String numbers = br.readLine();
		String[] tokens = numbers.split(" ");
		int[] testcase = new int[tokens.length];

		int i = 0;
		for (String token : tokens){
		    testcase[i++] = Integer.parseInt(token); 
		}
		
		// create a hash set
      		HashSet hs = new HashSet();

		//reading pranjal
		String pranjal = br.readLine();
		tokens = pranjal.split(" ");
		int[] pranjalInput = new int[tokens.length];

		i = 0;
		for (String token : tokens){
		    pranjalInput[i++] = Integer.parseInt(token); 
		    hs.add(Integer.parseInt(token));
		}

		//reading milli
		String milli = br.readLine();
		tokens = milli.split(" ");
		int[] milliInput = new int[tokens.length];

		i = 0;
		for (String token : tokens){
		    milliInput[i++] = Integer.parseInt(token); 
		    hs.add(Integer.parseInt(token));
		}
		
		if(hs.size()>= testcase[0] ){
		System.out.println("They can");
		}
		else{
		System.out.println("They can\'t");
		}
        }
	

    }
}

