import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class LiarLiar {

	private String fileName;
	private Scanner input;
	private int numAccusers;
	private HashMap<String, ArrayList<String>> accuseesToAccusers;
	private boolean debug;
	
	public LiarLiar(String fileName) throws Exception{
		this.fileName = fileName;
		
		createScanner();
		
		this.numAccusers = 0;
		accuseesToAccusers = new HashMap<String, ArrayList<String>>();
		
		debug = true;
	}
	
	private void createScanner() throws Exception{
		File f = new File(fileName);

		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file name. " +
					"Rerun program with proper text file name");
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}
	
	public void run(){
		parseFile();
		if (debug)
			printHashMap();
	}
	
	private void parseFile(){
		//first read the first line for the number of people you are dealing with
		getNumAccusersFromFirstLine();
		
		//keep reading the next lines, accuser by accuser		
		for (int i=0; i<numAccusers; i++){
			readInAccuser();
		}
	}

	private void getNumAccusersFromFirstLine() {
		String firstLine = "";
		if (input.hasNextLine())
			firstLine = input.nextLine();
		numAccusers = Integer.parseInt(firstLine.trim());
	}
	
	private void readInAccuser(){
		StringTokenizer st = new StringTokenizer(input.nextLine().trim(), " ");
		String accuser = st.nextToken();
		int numAccusees = Integer.parseInt(st.nextToken());
		
		String currentAccusee = "";
		ArrayList<String> tempAL = new ArrayList<String>();
		for (int j=0; j<numAccusees; j++){
			currentAccusee = input.nextLine();
			if (!accuseesToAccusers.containsKey(currentAccusee)){
				tempAL.clear();
				tempAL.add(accuser);
				accuseesToAccusers.put(currentAccusee, tempAL);
			}
			else { //has previously been accused
				tempAL = accuseesToAccusers.get(currentAccusee);
				tempAL.add(accuser);
				accuseesToAccusers.put(currentAccusee, tempAL);
			}
		}
	}
	
	private void printHashMap(){
		AbstractSet<String> keySet = (AbstractSet<String>) accuseesToAccusers.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		String currentAccusee = "";
		ArrayList<String> accusers = new ArrayList<String>();
		while (keySetIterator.hasNext()){
			currentAccusee = keySetIterator.next();
			System.out.print(currentAccusee + "--> (");
			accusers = accuseesToAccusers.get(currentAccusee);
			for (int b=0; b<accusers.size(); b++){
				System.out.print(accusers.get(b));
				if (b<(accusers.size()-1))
					System.out.print(", ");
			}
			System.out.println(")");
		}
	}

}
