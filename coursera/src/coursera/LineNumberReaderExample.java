package coursera;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.SimpleDateFormat;
import java.util.Date;
 

public class LineNumberReaderExample
{
	String fileName;
	LineNumberReader lineNumberReader;

	public LineNumberReaderExample(String filename) {
		
		try {
			File currentDir = new File(".");
			File newFile = new File(currentDir.getCanonicalFile(),"src/data/"+filename);
			this.lineNumberReader = new LineNumberReader(new FileReader(newFile.toString()));
			this.fileName = newFile.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		File currentDir = new File(".");
		System.out.println(currentDir.getAbsolutePath());
	    
	    File newFile;
		try {
			newFile = new File(currentDir.getCanonicalFile(),"src/data/assignment1_data.txt");
			System.out.println(newFile.getCanonicalPath());
			readFromFile(newFile.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	public int getCurrentLineNumber() {
		return this.lineNumberReader.getLineNumber();
	}

	public void setCurrentLineNumber(int l) {
		int currline=lineNumberReader.getLineNumber();
		String str;
		if(currline == l){
			
		}
		else{
			closeReader(this);
			try {
				this.lineNumberReader = new LineNumberReader(new FileReader(this.fileName));
				lineNumberReader.setLineNumber(0);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				while( this.getCurrentLineNumber() != l && (str=lineNumberReader.readLine())!=null)
				{
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		// Get current line number
		//System.out.println("Line " + lineNumberReader.getLineNumber());
	}
	
	public void swapLines(int m, int n){
		String mLine= this.readline(m);
		String nLine = this.readline(n);
		try {
			insertStringAfterLine(m-1 , nLine);
			insertStringAfterLine(n-1 , mLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertStringAfterLine(int l , String str) throws IOException {
		String line = null;
	    RandomAccessFile file;
	    Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
		File currentDir = new File(".");
		File newFile = new File(currentDir.getCanonicalFile(),"src/data/tmpfile-"+dateFormat.format(date)+".txt");
		
		try {
			RandomAccessFile fileold = new RandomAccessFile(fileName, "rw");
			RandomAccessFile filetmp = new RandomAccessFile(newFile, "rw");
			 
			 int linenumber =0;
			    while((line = fileold.readLine()) != null){
			    	
			        //System.out.println(line);
			        if(linenumber== l){
			            filetmp.write(str.getBytes());
			            filetmp.write("\n".getBytes());
			        }
			        filetmp.write(line.getBytes());
			        filetmp.write("\n".getBytes());
			    }
			    linenumber++;
			    fileold.close();
			    filetmp.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	   
		
		//delete previous file
		try {
			File oldFile= new File (fileName);
		    Files.delete(oldFile.toPath());
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n",currentDir.toPath());
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", currentDir.toPath());
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
		//rename tmp file to original filename
		File file2 = new File(fileName);

		if (file2.exists())
			try {
				throw new java.io.IOException("file exists");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Rename file (or directory)
		boolean success = newFile.renameTo(file2);

		if (!success) {
			throw new java.io.IOException("File was not successfully renamed");
		}
		
	
	    
	}
	
	public void mergeFile(String mergeFileName ,int start, int end ) throws IOException{
		String line = null;
	    RandomAccessFile file;
	    Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss") ;
		File currentDir = new File(".");
		File newFile = new File(currentDir.getCanonicalFile(),"src/data/tmpfile-"+dateFormat.format(date)+".txt");
		
		try {
			RandomAccessFile fileold = new RandomAccessFile(fileName, "rw");
			RandomAccessFile filetmp = new RandomAccessFile(newFile, "rw");
			RandomAccessFile mergefile = new RandomAccessFile(mergeFileName, "rw");
			 int linenumber =0;
			    while((line = fileold.readLine()) != null){
			    	
			        if(linenumber>= start && linenumber <=end ){
			            filetmp.write(mergefile.readLine().getBytes());
			            filetmp.write("\n".getBytes());
			            linenumber++;
			            continue;
			        }
			        linenumber++;
			       // System.out.println(line);
			        filetmp.write(line.getBytes());
			        filetmp.write("\n".getBytes());
			    }
			    fileold.close();
			    filetmp.close();
			    mergefile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	   
		
		//delete previous file
		try {
			File oldFile= new File (fileName);
		    Files.delete(oldFile.toPath());
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n",currentDir.toPath());
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", currentDir.toPath());
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
		//rename tmp file to original filename
		File file2 = new File(fileName);

		if (file2.exists())
			try {
				throw new java.io.IOException("file exists");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Rename file (or directory)
		boolean success = newFile.renameTo(file2);

		if (!success) {
			throw new java.io.IOException("File was not successfully renamed");
		}
		
	}
	
	public int readlineInt(int lineNumber){
		return Integer.parseInt(this.readline(lineNumber));
	}
	
	public String readline(int lineNumber) {
		String line = null;
		this.setCurrentLineNumber(lineNumber);
		try {
			line = lineNumberReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}

	private static void closeReader(LineNumberReaderExample lnr){
		try {
			if (lnr.lineNumberReader != null) {
				lnr.lineNumberReader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private static void readFromFile(String filename) {
		LineNumberReader lineNumberReader = null;
		try {
			// Construct the LineNumberReader object
			lineNumberReader = new LineNumberReader(new FileReader(filename));

			// Print initial line number
			System.out.println("Line " + lineNumberReader.getLineNumber());

			// Setting initial line number
			lineNumberReader.setLineNumber(5);

			// Get current line number
			System.out.println("Line " + lineNumberReader.getLineNumber());

			// Read all lines now; Every read increase the line number by 1
			String line = null;
			while ((line = lineNumberReader.readLine()) != null) {
				System.out.println("Line " + lineNumberReader.getLineNumber() + ": " + line);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// Close the LineNumberReader
			try {
				if (lineNumberReader != null) {
					lineNumberReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}