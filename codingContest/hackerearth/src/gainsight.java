import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;


public class gainsight {
	
	static Map<Integer,List<Integer>> hm = new HashMap<Integer,List<Integer>> (); 
	static List<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	static List<ArrayList<Integer>> indexList = new ArrayList<ArrayList<Integer>>();

	public static void main (String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 	String nArr = br.readLine();
	 	int n= Integer.parseInt(nArr);
	 	String numbers = br.readLine();
		String [] tokens = numbers.split(" ");
		
		int [] input = new int  [tokens.length+1];
		int index=1;
		for (String token : tokens){
		    int k = Integer.parseInt(token);
		    input[index] =k;
			index++;
		    
		}
		
		//fillHashMap(arr);
		
		
		
		
		
		int totalCount= 0;
		
		for (int i = 1 ; i< input.length ; i++){
			for (int j = i+1 ; j< input.length ; j++){
				for (int k = j+1 ; k< input.length ; k++){
					if (checkSpecialTuple(input[i], j,input[k])){
						/*
						if (checkCondition(input[i], j,input[k])){
						*/
							ArrayList tmp = new ArrayList<Integer>();
							tmp.add(input[i]);
							tmp.add(j);
							tmp.add(input[k]);
							
							/*
							ArrayList tmp2 = new ArrayList<Integer>();
							tmp2.add(i);
							tmp2.add(j);
							tmp2.add(k);
							*/
							result.add(tmp);
							//indexList.add(tmp2);
							totalCount= (totalCount % ((int)Math.pow(10, 9)+7) +1)% ((int)Math.pow(10, 9)+7) ;
						//}
					}
				}
			}
		}
		//printResult();
		System.out.println(totalCount);
		
	}
	
	private static void printResult(){
		for (ArrayList<Integer> al : result){
			for (Integer  i : al){
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
	
	private static void fillHashMap(ArrayList<Integer> arr){
		int index= 1;
		for (int i : arr){
			if (hm.get(i) != null){
				ArrayList<Integer> al = (ArrayList<Integer>) hm.get(i)  ;
				al.add(index);
			}
			else{
				ArrayList<Integer> al = new ArrayList<Integer>()  ;
				al.add(index);
				hm.put(i, al);
			}
			index++;
		}
	}
	
	private static boolean findLessInHashmap(int A_i , int j){
		// find  i in hashmap  such that a_i in hashmap and i <j
		
		if (hm.get(A_i) != null){
			ArrayList<Integer> al = (ArrayList<Integer>) hm.get(A_i)  ;
			for (int i : al){
				if (i < j){
					return  true;
				}
			}
				
		}
		return false;
	}

	private static boolean findGreaterInHashmap(int A_k , int j){
		// find  k in hashmap  such that a_k in hashmap and j <k
		if (hm.get(A_k) != null){
			ArrayList<Integer> al = (ArrayList<Integer>) hm.get(A_k)  ;
			for (int k : al){
				if (k > j){
					return  true;
				}
			}
				
		}
		return false;
	}
	
	
	
	private static boolean checkCondition(int a , int b ,  int c  ){
		//check if a[i] , j, a[k]) i<j<k
		
		if ((a<b && b>c ) && findLessInHashmap(a,b) && findGreaterInHashmap(c,b)){
			return true;
		}
		 
		if((a>b && b<c) && findLessInHashmap(a,b) && findGreaterInHashmap(c,b)){
			return true;
		}
		
		return false;
		
	}
	
	private static boolean checkSpecialTuple(int a , int b , int c){
		boolean flag = false;
		if ((a<b && b> c) || (a>b && b<c)){
			flag = true;
		}
		 return flag;
	}
}
