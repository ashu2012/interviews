import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ittiam2 {
	private static void printMyGlobalArr(ArrayList<ArrayList<Integer>> resultArr){
		for (ArrayList<Integer> al :resultArr){
			for (int i : al){
				System.out.print(i + ", ");
			}
			System.out.println();
		}
	}

	 public static void main(String args[] ) throws Exception {
		 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 	String numbers = br.readLine();
			String[] tokens = numbers.split(" ");
			int n= Integer.parseInt(tokens[0]);
			int k= Integer.parseInt(tokens[1]);
			
			
			numbers = br.readLine();
			tokens = numbers.split(" ");
			ArrayList<Integer>  arr = new ArrayList<Integer>() ;

			
			for (String token : tokens){
			    arr.add(Integer.parseInt(token));
			    
			}
			
			ArrayList<ArrayList<Integer >> resultArr = new ArrayList<ArrayList <Integer >> ();
			ArrayList<Integer> tmpArr= new ArrayList<Integer>();
			allSubSeq(resultArr, 0, k, arr,tmpArr);
			//printMyGlobalArr(resultArr);
			System.out.println(resultArr.size());
	 }
	 
	 public static void allSubSeq(ArrayList<ArrayList<Integer>> resultArr, int i,int maxVal ,ArrayList<Integer> arr , ArrayList<Integer> tmpArr ){
		 
		 if (tmpArr.size() >0){
			 int currSum =0;
			 for(int a : tmpArr){
				 currSum =currSum+a;
			 }
			 if (currSum <maxVal){
				 resultArr.add(tmpArr);
				 for (int j = i ; j< arr.size() ; j++){
					 ArrayList<Integer> tmpArr2 = new  ArrayList<Integer>(tmpArr);
					 tmpArr2.add(arr.get(j));
					 allSubSeq(resultArr,j+1, maxVal, arr, tmpArr2);
				 }
			 }
			 else{
				 //do Nothing
			 }
			 
		 }
		 else{
			 for (int j = i ; j< arr.size() ; j++){
				 ArrayList<Integer> tmpArr2 = new  ArrayList<Integer>(tmpArr);
				 tmpArr2.add(arr.get(j));
				 allSubSeq(resultArr,j+1, maxVal, arr, tmpArr2);
			 }
		 }
	 }
}
