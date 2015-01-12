/*


*/
import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.sax.WriteOutContentHandler;
public class TikaHW_ExtraCredit{

	List<String> keywords;
	PrintWriter logfile;
	int num_keywords, num_files, num_fileswithkeywords;
	Map<String,Integer> keyword_counts;
	Date timestamp;

	Stopwords s= new Stopwords();
	/**
	 * constructor
	 * DO NOT MODIFY
	 */
	public TikaHW_ExtraCredit() {
		keywords = new ArrayList<String>();
		num_keywords=0;
		num_files=0;
		num_fileswithkeywords=0;
		keyword_counts = new HashMap<String,Integer>();
		timestamp = new Date();
		try {
			logfile = new PrintWriter("log_ExtraCredit.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * destructor
	 * DO NOT MODIFY
	 */
	protected void finalize() throws Throwable {
		try {
			logfile.close();
	    } finally {
	        super.finalize();
	    }
	}

	/**
	 * main() function
	 * instantiate class and execute
	 * DO NOT MODIFY
	 */
	public static void main(String[] args) {
		TikaHW_ExtraCredit instance = new TikaHW_ExtraCredit();
		instance.run();
	}

	/**
	 * execute the program
	 * DO NOT MODIFY
	 */
	private void run() {

		// Open input file and read keywords
		try {
			BufferedReader keyword_reader = new BufferedReader(new FileReader("keywords.txt"));
			String str;
			while ((str = keyword_reader.readLine()) != null) {
				keywords.add(str);
				num_keywords++;
				keyword_counts.put(str, 0);
			}
			keyword_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Open all pdf files, process each one
		File pdfdir = new File("./vault");
		File[] pdfs = pdfdir.listFiles(new PDFFilenameFilter());
		for (File pdf:pdfs) {
			num_files++;
			processfile(pdf);
		}

		// Print output file
		try {
			PrintWriter outfile = new PrintWriter("output_ExtraCredit.txt");
			outfile.print("Keyword(s) used: ");
			if (num_keywords>0) outfile.print(keywords.get(0));
			for (int i=1; i<num_keywords; i++) outfile.print(", "+keywords.get(i));
			outfile.println();
			outfile.println("No of files processed: " + num_files);
			outfile.println("No of files containing keyword(s): " + num_fileswithkeywords);
			outfile.println();
			outfile.println("No of occurrences of each keyword:");
			outfile.println("----------------------------------");
			for (int i=0; i<num_keywords; i++) {
				String keyword = keywords.get(i);
				outfile.println("\t"+keyword+": "+keyword_counts.get(keyword));
			}
			outfile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Process a single file
	 * 
	 * Here, you need to:
	 *  - use Tika to extract text contents from the file
	 *  - (optional) check OCR quality before proceeding
	 *  - search the extracted text for the given keywords
	 *  - update num_fileswithkeywords and keyword_counts as needed
	 *  - update log file as needed
	 * 
	 * @param f File to be processed
	 */
	private void processfile(File f) {

		/***** YOUR CODE GOES HERE *****/
		// to update the log file with a search hit, use:
		// 	updatelog(keyword,f.getName());
		boolean file_count=false;
		try {
			PDFParser parser= new PDFParser();
			InputStream fis = new FileInputStream(f);
			StringWriter writer = new StringWriter();
			ContentHandler handler = new WriteOutContentHandler(writer);

			Metadata metadata = new Metadata();
			parser.parse(fis, handler, metadata, new ParseContext());
        
			String content = writer.toString();    
			String content_remove=s.removeStopwords(content);
			content_remove.replaceAll(",|!|\"|\'|\\.", "");
        
        
			boolean contains=false;
			for(int i=0;i<keywords.size();i++)
			{
				String keyword = keywords.get(i);
				Pattern p = Pattern.compile("\\b"+keyword+"\\b",Pattern.CASE_INSENSITIVE);
			    Matcher m = p.matcher(content_remove);
			    if(m.find())
			    {	    	
			    	contains=true;
			    	file_count=true;
			    	break;
			    }
			}
		
        content_remove.replaceAll("\n", " ");
		if(contains){
			String[] content_dd = content_remove.split(" ");
			
			for(int i=0;i<keywords.size();i++)
			{	
				String keyword = keywords.get(i);
				if(keywords.get(i).contains(" ")) //biwords
				{
					String[] content_bi=new String[content_dd.length];
					int k=0;
					for(int j=0;j<content_dd.length-1;j++) //creat biwords array
					{	
						content_bi[k]=content_dd[j]+" "+content_dd[j+1];			
						k++;
					}
					
					for(int j=0;j<content_bi.length-1;j++) 
					{	
						
						if(content_bi[j].length()!=0)
						{
							int dis=getLevenshteinDistance(keyword,content_bi[j]);
							int con_length=content_bi[j].length();
							if(dis==0){
								writeTolog(keyword,f);
							}
							else if(con_length>8&&dis<=2)
							{
									writeTolog(keyword,f);
							}
								
						}
					}
				
				}
				
				else{
					for(int j=0;j<content_dd.length;j++)
					{			
						if(content_dd[j].length()!=0)
						{
							int dis=getLevenshteinDistance(keyword,content_dd[j]);
							int con_length=content_dd[j].length();
							if(dis==0){
								writeTolog(keyword,f);
							}
							else if(con_length<=8 && dis==1){
								writeTolog(keyword,f);
							}
							else if(con_length>8&&dis<=2)
							{
								writeTolog(keyword,f);
							}		
						}
					}
				}	
				
							
			}
		}
	
		if(file_count==true)
			num_fileswithkeywords++;
		}

		catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

		  
       }

	private void  writeTolog(String keyword,File f){
		updatelog(keyword,f.getName());
		keyword_counts.put(keyword, (keyword_counts.get(keyword))+1);
	}
	
	
	
	/**
	 * Update the log file with search hit
	 * Appends a log entry with the system timestamp, keyword found, and filename of PDF file containing the keyword
	 * DO NOT MODIFY
	 */
	private void updatelog(String keyword, String filename) {
		timestamp.setTime(System.currentTimeMillis());
		logfile.println(timestamp + " -- \"" + keyword + "\" found in file \"" + filename +"\"");
		logfile.flush();
	}

	/**
	 * Filename filter that accepts only *.pdf
	 * DO NOT MODIFY 
	 */
	static class PDFFilenameFilter implements FilenameFilter {
		private Pattern p = Pattern.compile(".*\\.pdf",Pattern.CASE_INSENSITIVE);
		public boolean accept(File dir, String name) {
			Matcher m = p.matcher(name);
			return m.matches();
		}
	}
	 public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
	        if (s == null || t == null) {
	            throw new IllegalArgumentException("Strings must not be null");
	        }

	        /*
	           The difference between this impl. and the previous is that, rather
	           than creating and retaining a matrix of size s.length() + 1 by t.length() + 1,
	           we maintain two single-dimensional arrays of length s.length() + 1.  The first, d,
	           is the 'current working' distance array that maintains the newest distance cost
	           counts as we iterate through the characters of String s.  Each time we increment
	           the index of String t we are comparing, d is copied to p, the second int[].  Doing so
	           allows us to retain the previous cost counts as required by the algorithm (taking
	           the minimum of the cost count to the left, up one, and diagonally up and to the left
	           of the current cost count being calculated).  (Note that the arrays aren't really
	           copied anymore, just switched...this is clearly much better than cloning an array
	           or doing a System.arraycopy() each time  through the outer loop.)

	           Effectively, the difference between the two implementations is this one does not
	           cause an out of memory condition when calculating the LD over two very large strings.
	         */

	        int n = s.length(); // length of s
	        int m = t.length(); // length of t

	        if (n == 0) {
	            return m;
	        } else if (m == 0) {
	            return n;
	        }

	        if (n > m) {
	            // swap the input strings to consume less memory
	            final CharSequence tmp = s;
	            s = t;
	            t = tmp;
	            n = m;
	            m = t.length();
	        }

	        int p[] = new int[n + 1]; //'previous' cost array, horizontally
	        int d[] = new int[n + 1]; // cost array, horizontally
	        int _d[]; //placeholder to assist in swapping p and d

	        // indexes into strings s and t
	        int i; // iterates through s
	        int j; // iterates through t

	        char t_j; // jth character of t

	        int cost; // cost

	        for (i = 0; i <= n; i++) {
	            p[i] = i;
	        }

	        for (j = 1; j <= m; j++) {
	            t_j = t.charAt(j - 1);
	            d[0] = j;

	            for (i = 1; i <= n; i++) {
	                cost = s.charAt(i - 1) == t_j ? 0 : 1;
	                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
	                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
	            }

	            // copy current distance counts to 'previous row' distance counts
	            _d = p;
	            p = d;
	            d = _d;
	        }

	        // our last action in the above loop was to switch d and p, so p now
	        // actually has the most recent cost counts
	        return p[n];
	    }

}
