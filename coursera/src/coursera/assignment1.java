package coursera;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

import coursera.LineNumberReaderExample;

public class assignment1 {
	static LineNumberReaderExample lnr ;
	public static void main (String args[]){
		runlogic();
	}
	
	private static void runlogic(){
		lnr = new LineNumberReaderExample("assignment1_data.txt");
		/*
		System.out.println(lnr.readline(11));
		System.out.println(lnr.readline(10));
		System.out.println(lnr.readline(11));
		System.out.println(lnr.readline(5));
		System.out.println(lnr.readline(110000));
		System.out.println(lnr.getCurrentLineNumber());
		lnr.swapLines(4,3);
		
		try {
			lnr.insertStringAfterLine(0, "hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		System.out.println(findInversions(0,100001));
	} 
	
	public static int findInversions(int start , int end){
		
		if(end-start ==0){
			
				return 0;
				
			
		}
		int leftInversions=findInversions(start , (start+end)/2);
		int rightInversions= findInversions((start+end)/2+1, end);
		int mergeInversions=numInversionsInArrays(start , (start+end)/2,(start+end)/2+1, end) ;
		
		return leftInversions + rightInversions + mergeInversions;
	}
	
	public static int numInversionsInArrays(int startA, int endA, int startB , int endB){
		RandomAccessFile file;
	    Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
		File currentDir = new File(".");
		File newFile = null;
		RandomAccessFile filetmp = null ;
		int ran= (int)(Math.random()*10000);
		try {
			newFile = new File(currentDir.getCanonicalFile(),"src/data/tmpfile-"+String.valueOf(ran)+dateFormat.format(date)+".txt");
			filetmp = new RandomAccessFile(newFile, "rw");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int result =0 ; 
		int i= startA , j=startB;
		for ( int k = 0 ; k<= endB -startA; k++){
			int currA=lnr.readlineInt(i);
			int currB= lnr.readlineInt(j);
			if(currA > currB && j <=endB ) {
				result = result + endA-i+1;
				try {
					filetmp.write(String.valueOf(currB).getBytes());
					filetmp.write("\n".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(j==endB){
					while(i<=endA){
						try {
							filetmp.write(String.valueOf(lnr.readlineInt(i)).getBytes());
							filetmp.write("\n".getBytes());
							i++;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					break;
				}
				else{
					j= j+1;
				}
				
			}
			
			if(currB >= currA && i <=endA) {
				
				try {
					filetmp.write(String.valueOf(currA).getBytes());
					filetmp.write("\n".getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i==endA){
					while(j<=endB){
						try {
							filetmp.write(String.valueOf(lnr.readlineInt(j)).getBytes());
							filetmp.write("\n".getBytes());
							j++;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					break;
				}
				else{
					i= i+1;
				}
				
			}
			
		}
		try {
			filetmp.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			lnr.mergeFile(newFile.getPath(), startA, endB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
