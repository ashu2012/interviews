import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class gainsight1 {
	
	public static void main(String args[]) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 	String nArr = br.readLine();
	 	int n= Integer.parseInt(nArr);
	 	for (int i =0 ; i< n ; i++){
	 		String input = br.readLine();
	 		int count= CheckNumberOperations (input, true , 0);
	 		System.out.println(count);
	 	}
		
	}

	private static int CheckNumberOperations(String str , boolean start ,int recursionLevel){
		
		int count=0;
		/*
		for (int i =0 ;  i<= recursionLevel ; i++){
			System.out.print("-");
		}
		System.out.print(str);
		System.out.println("");
		*/
		if(str.length()==1 ){
			if (start){
				if(str.charAt(0)=='a'){
					return 0; 
				}
				else{
					return 1;
					
				}	
			}
			else{
				return 0;
			}
			
				
		}
		else if(str.length() ==0){
			return 1;
		}
		else{
			
			for (int i =0 ; i<= str.length()-2;i++){
				if(str.charAt(i)=='a' ){
					if (str.charAt(i+1)=='b'){
						continue;
					}else{
						
						String tmpStr1 = str.substring( i+2);
						int tmpCount1= CheckNumberOperations (tmpStr1 , false,recursionLevel+1);
						
						String tmpStr2 = replaceAtIndex(str, i+1, 'b');
						tmpStr2= tmpStr2.substring(i+1);
						int tmpCount2= CheckNumberOperations (tmpStr2 , false,recursionLevel+1);
						
						if (tmpCount1< tmpCount2){
							return count+ tmpCount1 +1;
						}
						else{
							return count+ tmpCount2 +1;
						}
					}
				}
				else if(str.charAt(i)=='b') {
					if (str.charAt(i+1)=='c'){
						continue;
					}
					else{
						
								String tmpStr1 = str.substring( i+2);
								int tmpCount1= CheckNumberOperations (tmpStr1 , false,recursionLevel+1);
								
								String tmpStr2 = replaceAtIndex(str, i+1, 'c');
								tmpStr2= tmpStr2.substring(i+1);
								int tmpCount2= CheckNumberOperations (tmpStr2 , false,recursionLevel+1);
								
								if (tmpCount1< tmpCount2){
									return count+ tmpCount1 +1;
								}
								else{
									return count+ tmpCount2 +1;
								}
							
							
					}
					
				}
				else if(str.charAt(i)=='c'){
					if ( str.charAt(i+1)=='a'){
						continue;
					}
					else{
						String tmpStr1 = str.substring( i+2);
						int tmpCount1= CheckNumberOperations (tmpStr1 , false,recursionLevel+1);
						
						String tmpStr2 = replaceAtIndex(str, i+1, 'a');
						tmpStr2= tmpStr2.substring(i+1);
						int tmpCount2= CheckNumberOperations (tmpStr2 , false,recursionLevel+1);
						
						if (tmpCount1< tmpCount2){
							return count+ tmpCount1 +1;
						}
						else{
							return count+ tmpCount2 +1;
						}
					}
				}
				else{
					count++;  // replace operation
				}
				//System.out.println(str);
			}
		}
		return count;
		
	}
	
	private static String replaceAtIndex(String str,int index, char ch ){
		String myName = str;
		String newName = myName.substring(0,index)+ch+myName.substring(index+1);
		return newName;
	}
	
	private static String removeAtIndex(String str,int index ){
		String myName = str;
		String newName = myName.substring(0,index)+myName.substring(index+1);
		return newName;
	}
	
	
}
