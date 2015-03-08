import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

/**
A WordCount method to find the frequency of words in all files in directory wc_input
The program combines all files into a single stream then counts each word from a combined single file
 **/

public class WordCount {
	
	private static File inputfolder= new File("wc_input");
	private static Vector<InputStream> inputstreams = new Vector<InputStream>(5);

	public WordCount(){
		
	    //constructor
		
	}
	
        /**
 	This method combines all files in a single stream then calls the OutputFile() method
	**/   
	public static void CallCounter(File folder) throws IOException{
		
	
		try
		{
			File[] files = folder.listFiles();

			int temp;
			Arrays.sort(files);
		
			for (File file: files)
			{
				if (file.isDirectory()) {
					CallCounter(file);
		        } else {
		    	   if(!file.getName().equals(".DS_Store"))
		    	   {
		    	   inputstreams.add(new FileInputStream(file));
		    	   }
		        }
			}
			
			Enumeration<InputStream> enumeration = inputstreams.elements();
			SequenceInputStream sis = new SequenceInputStream(enumeration);
			FileOutputStream outstream = new FileOutputStream("wc_output/OutputStream2.txt");
		
			while( ( temp = sis.read() ) != -1) 
			{ 
				outstream.write(temp);	// to write to file
			}
			
			outstream.close();
			sis.close();

			Outputfile();
		
		}
		catch(Exception e)
		{
			System.out.println("no files");
		}

	
		
	}
	
        /**
        This method takes the single stream file and gets the word frequency of each word 
	It returns a hashmap with the word as the key and the frequency as the value
        **/
	public static Map<String,Integer> WordFrequency() throws IOException{
		try
		{
			// Create a BufferedReader to read files from the single stream.
			BufferedReader reader = new BufferedReader(new FileReader(
				"wc_output/OutputStream2.txt"));
			Map<String,Integer> mapwords = new HashMap<String, Integer>(5);
			
		
			
			while(true)
			{
				String line = reader.readLine();
				if(line == null)
				{
					break;
				}
				String symword ="";
				Scanner wordscan = new Scanner(line);
				
				while (wordscan.hasNext())
				{
					String word ="";
					symword = wordscan.next();
					symword= symword.toLowerCase();

					//remove unnecessary characters from word
					for (int i=0; i<symword.length(); i++)
					{
						char c = symword.charAt(i);
						if ((int)c>=97 && (int)c<=122)
						{
							word = word+ Character.toString(c);
						}
					}
					
					if(!(word.length()==0))
					{
						if(!mapwords.containsKey(word))
						{
							
							mapwords.put(word, 1);
						}
						else
						{
							mapwords.put(word, mapwords.get(word)+1);
						}
					}
					
				}
			}
			
			reader.close();
			return mapwords;
			
			
			
			
			
		}
		catch(Exception e)
		{
			System.out.println("error");
			return null;
		}
	}
	
        /**
	This method takes map<String,Integer> returned by WordFrequency() and sorts the map alphabetically using the key(word).
	Then it writes the word and its frequency in a file called wc_result.txt
        **/
	public static void Outputfile() throws IOException
	{
		Map<String,Integer> mapwords = WordFrequency();
		File outputfile = new File("wc_output/wc_result.txt");
		outputfile.createNewFile();
		FileWriter writer = new FileWriter(outputfile); 
		
		if(!(mapwords==null))
		{
			
			Map<String, Integer> sortedmap = new TreeMap<String, Integer>(mapwords);
			String space = "                      ";
			for (Map.Entry<String, Integer> entry : sortedmap.entrySet()) {
			     writer.write(entry.getKey() + (space+entry.getValue()).substring(entry.getKey().length())+ "\n");
			}
			writer.close();
			
		}
		
		else
		{
			System.out.println("no words found");
		}
		
	}
	
        /**
        Main method that invokes the CallCounter(File) method
        **/
	public static void main (String[] args) throws IOException
        {
        
	    CallCounter(inputfolder);
	}
}
