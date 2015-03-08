import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.SequenceInputStream;
import java.util.*;

/**
A program to find the running medium of all lines of all files in directory wc_input.
If there is more than one file in the directory, all the files are combined to a single stream alphabetically and each line is read from this single stream.
 **/

public class RunningMedian {

	
	private static File foldername = new File("wc_input");
	private static Vector<InputStream> inputstreams = new Vector<InputStream>(5);
	
	public RunningMedian(){
	    //constructor
		
	}

/**
This method combines all files in the wc_input directory and writes it to another file called OutputStream.txt
Then it calls the CalculateMedian() method to calculate the running median
 **/	
public static void RunningMedian(File folder) throws IOException {
	try
	{
		File[] files = folder.listFiles();
		int temp;

		//sort files alphabetically
		Arrays.sort(files);
	
		for (File file: files)
		{
		   if (file.isDirectory()) {
	           RunningMedian(file);
	        }  else {
		 
		       if(!file.getName().equals(".DS_Store"))   //.DS_Store file is a hidden file that doesn't need to be considered
	    	   {
	    	   inputstreams.add(new FileInputStream(file));
	    	   }
	        }
		}
		
		Enumeration<InputStream> enumeration = inputstreams.elements();
		SequenceInputStream sis = new SequenceInputStream(enumeration);
		FileOutputStream outstream = new FileOutputStream("wc_output/OutputStream.txt");
	
		while( ( temp = sis.read() ) != -1) 
		{ 
			
			outstream.write(temp);	// to write to file
			
		}
		
		outstream.close();
		sis.close();

		//call method to calculate running median
		CalculateMedian();
	
	}
	catch(Exception e)
	{
		System.out.println("no files");
	}
	
		
		
	
}

/**
Method to calculate running median of each line
The running median is then written in med_result.txt
**/	
public static void CalculateMedian() throws IOException{
	
	
	try
	{
	        // Create a BufferedReader from a FileReader.
		BufferedReader reader = new BufferedReader(new FileReader(
			"wc_output/OutputStream.txt"));
		File outputfile = new File("wc_output/med_result.txt");
		FileWriter writer = new FileWriter(outputfile); 
		List<Integer> vectorofmedians = new Vector<Integer>(5);

		outputfile.createNewFile();

		// Loop over lines in the file and print them.
		while (true) {
		    //each line got read
		    String line = reader.readLine();
		  
		    if (line == null) {
			break;
		    }
		    
		    Scanner wordscan = new Scanner(line);
		    String word ="";
		    double median = 0;
		    int totalwords =0;
		    double medianitem=0;
		    double mid1=0;
		    double mid2=0;
		    Integer miditem1 =0;
		    Integer miditem2=0;
		    
		    while (wordscan.hasNext())
		    {
		    	word = wordscan.next();
		    	totalwords = totalwords+1;
		    
		    }

		    vectorofmedians.add(totalwords);
		    Collections.sort(vectorofmedians);
		    
		    if(vectorofmedians.size()==1)
		    {
		    	writer.write(Double.toString((double)totalwords)+ "\n");	
		    }
		    else
		    {
		    	
		    	if(vectorofmedians.size()%2==0 )
		    	{
		    		medianitem = (((double)vectorofmedians.size()+1.0)/2.0);
		    		
		    		mid1 = medianitem -0.5;
		    		mid2 = medianitem +0.5;
		    	
		    		miditem1 = vectorofmedians.get((int)mid1-1);
		    		miditem2= vectorofmedians.get((int)mid2-1);
		    	
		    		median = ((double)miditem1+(double)miditem2)/2;
		    		
		    		writer.write(Double.toString(median)+"\n");
		    	
		    	}
		    	else 
		    	{
		    		medianitem = (((double)vectorofmedians.size()+1.0)/2.0);
		    		median = (double)vectorofmedians.get((int)medianitem-1);
		    		
		    		writer.write(Double.toString(median)+"\n");
		    	}
		    	
		    	
		    }
		  
		}
		

		// Close the BufferedReader and output file.
		reader.close();
		writer.close();
		
	}
	catch(Exception e)
	{
		System.out.println("error");
	}
	    
}

/**
Main method
**/
public static void main (String[] args) throws IOException
	    {
	        
	        
	        RunningMedian(foldername);
	    }
}
