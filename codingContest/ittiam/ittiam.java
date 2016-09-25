import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

class MyComparator implements Comparator< BigInteger> 
{
    public int compare ( BigInteger o1, BigInteger o2) 
    {
        

        return o1.compareTo(o2);
    }
}

class Node2 {
	int a,b,c;
	public Node2 (int a, int b,int c){
		this.a=a;
		this.b=b;
		this.c=c;
	}
}
public class ittiam {

	private static ArrayList<Integer> findPrimes(int maxSize){
		ArrayList<Integer> al = new ArrayList <Integer>();
		int i =2;
		while(al.size()==0 || al.get(al.size()-1)<2000 ){
			Boolean flag= true;
			Iterator<Integer> it = al.iterator();
			while(it.hasNext()){
				int tmp =it.next();
				if (tmp > Math.sqrt(i))break;
				
				if (i% tmp==0){
					flag= false;
					break;
				}
				
				
			}
			if (flag){
				al.add(i);
				//System.out.println(i);
			}
			i++;
		}
		
		return al;
		
	}
	public static void main(String args[]) throws IOException
    {	
		ArrayList<BigInteger> al= new ArrayList<BigInteger>();
		 HashMap<Integer,Node2 > hm = new HashMap<Integer, Node2>();
		 ArrayList<Integer> pmList= new ArrayList<Integer>();
		 pmList= findPrimes (2000);
		 Integer primeNums[]= new Integer[pmList.size()];
		 primeNums=	pmList.toArray(primeNums);
		 for (int i = 0 ; i< primeNums.length ; i++){
			 for (int j =i+1 ; j< primeNums.length ; j++){
				 for (int k =j+1 ; k<primeNums.length ;k++){
					 BigInteger tmp =BigInteger.valueOf(primeNums[i]).multiply(BigInteger.valueOf(primeNums[j]).multiply(BigInteger.valueOf(primeNums[k])));
					 if(tmp.intValue()<0){
						 //System.out.print(primeNums[i] +","+primeNums[j]+","+primeNums[k]);
						 //System.out.println();
					 }
					 else{
					 
					 }
					 al.add(tmp);
					 //Node2 n =  new Node2(primeNums[i],primeNums[j],primeNums[k]);
					 //hm.put(primeNums[i]*primeNums[j]*primeNums[k], n);
				 }
			 }
		 }

		 Collections.sort(al,new  MyComparator() );
		 //Collections.sort(hm,new  MyComparator() );
		 /*
		 for (BigInteger item : al){
			 System.out.println(item);
			 if( item.intValue()> 1000) break; 
		 }
		 */
		 //System.out.println(al.size());
		 //BigInteger tmp =al.get(5);
		 //System.out.println(tmp);
		 
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 String line = br.readLine();
		 int numTestCases = Integer.parseInt(line);
		 for (int i =0 ; i<numTestCases; i++){
			 line = br.readLine();
			 int tmp1 = Integer.parseInt(line);
			 //System.out.println(tmp);
			 System.out.println(al.get(tmp1-1).intValue());
		 }
		 
    }
}
