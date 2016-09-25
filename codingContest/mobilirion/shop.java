import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import java.util.Scanner;

public class shop {

	public static void main(String args[]) throws IOException {

		HashMap<String, fruit> hm = new HashMap<String, fruit>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 	String nArr = br.readLine();
	 	int n= Integer.parseInt(nArr);
	 	
	 	PriorityQueue<fruit> fruitPricing = new PriorityQueue(n, new FruitComparator());
	 	
	 	for (int i =0 ; i< n ; i++){
	 		String fruitInput = br.readLine();
	 		String[] tokens = fruitInput.split(" ");
	 		int price= Integer.parseInt(tokens[1]);
	 		fruit tmpFruit = new fruit(tokens[0], price);
	 		hm.put(tokens[0], tmpFruit);
	 		fruitPricing.add(tmpFruit);
	 	}
		
	 	//printMap(hm);
	 	String querries = br.readLine();
	 	int numQuerries= Integer.parseInt(querries);
	 	for (int i =0 ; i< numQuerries ; i++){
	 		String input = br.readLine();
	 		String[] tokens = input.split(" ");
	 		if(tokens[0].equals("+")){
	 			fruit tmp = hm.get(tokens[1]);
	 			tmp.quantity =tmp.quantity +1;		
	 		}
	 		else if(tokens[0].equals("-")){
	 			fruit tmp = hm.get(tokens[1]);
	 			tmp.quantity =tmp.quantity +1;	
	 		}
	 		else{
	 			
	 			int currPrice= Integer.parseInt(tokens[1]);
	 			//int ans =numFruitsBasedOnPrice(hm,currPrice);
	 			int ans =numFruitsBasedOnPrice(fruitPricing,currPrice);
	 			System.out.println(ans);
	 		}
	 	}

	 	

	}
	

	public static int numFruitsBasedOnPrice(PriorityQueue< fruit> pq, int price) {
		int count =0;
		Iterator<fruit> iter = pq.iterator();
		while (iter.hasNext()) {
			fruit value = iter.next();
		    // do something with current
			if(value.price > price){
				count=count+value.quantity;
			}
		}
		
		return count;
		
	}
	
	public static int numFruitsBasedOnPrice(HashMap<String, fruit> map, int price) {
		int count =0;
		for (Map.Entry<String, fruit> entry : map.entrySet()) {

			String key = entry.getKey().toString();

			fruit value = entry.getValue();
			if(value.price > price){
				count=count+value.quantity;
			}
			
		}
		return count;
		
	}
	public static void printMap(HashMap<String, fruit> map) {

		/*
		 * Set<fruit> keys = map.keySet();
		 * 
		 * for(fruit p:keys){
		 * 
		 * System.out.println(p+"==>"+map.get(p));
		 * 
		 * }
		 */

		for (Map.Entry<String, fruit> entry : map.entrySet()) {

			String key = entry.getKey().toString();

			fruit value = entry.getValue();

			System.out.println("key:- " + key + " value:- " + value.name +":"+value.price +":"+ value.quantity );

		}

	}

}

class FruitComparator implements Comparator<fruit> {

    public int compare(fruit af, fruit bf) {
    	int a = af.price;
    	int b = bf.price;
    	int cmp = a > b ? +1 : a < b ? -1 : 0;
    	return cmp;
    }
}
class fruit {

	String name;

	int price;

	int quantity;

	public fruit(String itm, int pr) {

		this.name = itm;

		this.price = pr;

		this.quantity = 0;

	}

}