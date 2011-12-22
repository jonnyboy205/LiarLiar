import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;


public class LiarLiar {

	private String fileName;
	private Scanner input;
	private int numAccusers;
	private ArrayList<String> accusers;
	private HashMap<String, ArrayList<String>> accusersToAccusees;
	private HashMap<String, ArrayList<String>> accuseesToAccusers;
	private HashSet<String> truthers;
	private HashSet<String> liars;
	private static final boolean DEBUG = false;
	
	public LiarLiar(String fileName) throws Exception{
		this.fileName = fileName;
		
		createScanner();
		
		this.numAccusers = 0;
		accusers = new ArrayList<String>();
		truthers = new HashSet<String>();
		liars = new HashSet<String>();
		accusersToAccusees = new HashMap<String, ArrayList<String>>();
		accuseesToAccusers = new HashMap<String, ArrayList<String>>();
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
		getTruthers();
		
		while (!isEveryoneDefined()){
			//start looking at truthers, see who they accuse, assume they are liars
			getLiarsFromTruthers();
			getMoreLiars();
			//I need to repeat this cycle until everyone is either a truther or liar
		}
		
		printOutputSizes();
		
		if (DEBUG)
			printDetails();
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
		accusers.add(accuser);
		int numAccusees = Integer.parseInt(st.nextToken());
		
		String currentAccusee = "";
		ArrayList<String> tempAL = new ArrayList<String>();
		ArrayList<String> accusees = new ArrayList<String>(numAccusees);
		for (int j=0; j<numAccusees; j++){
			currentAccusee = input.nextLine();
			accusees.add(currentAccusee);
			
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
		accusersToAccusees.put(accuser, accusees);
	}
	
	private void getTruthers(){
		for (int c=0; c<accusers.size(); c++){
			if (!accuseesToAccusers.containsKey(accusers.get(c)))
				truthers.add(accusers.get(c));
		}
	} 
	
	/**
	 * 
	 * @return
	 */
	private boolean isEveryoneDefined(){
		boolean everyoneDefined = false;
		
		if (truthers.size() + liars.size() == numAccusers)
			everyoneDefined = true;
		
		return everyoneDefined;
	}
	
	/**
	 * Set as liars all people accused by truthers.
	 */
	private void getLiarsFromTruthers(){
		ArrayList<String> temp = new ArrayList<String>();
		Iterator<String> trutherIterator = truthers.iterator();
		while (trutherIterator.hasNext()){
			//if contained with accusers to accusees, which of course it will
			//grab that list, and set all of those guys as liars.
			temp = accusersToAccusees.get(trutherIterator.next());
			for (String l: temp){
				liars.add(l);
			}
		}
	}
	
	/**
	 * Look at liars, and define their accusees as truthers
	 */
	private void getMoreLiars(){
		Iterator<String> liarIterator = liars.iterator();
		String currentLiar = "";
		ArrayList<String> temp = new ArrayList<String>();
		while (liarIterator.hasNext()){
			currentLiar = liarIterator.next();
			//look at who they accuse of being liars
			temp = accusersToAccusees.get(currentLiar);
			for (String currentAccusee: temp){
				truthers.add(currentAccusee);
			}
		}
	}
	
	private void printDetails(){
		System.out.println("Accusers to Accusees:");
		printHashMap(accusersToAccusees);
		
		System.out.println();
		System.out.println("Accusees to Accusers:");
		printHashMap(accuseesToAccusers);
		
		System.out.println();
		printTruthers();
		
		System.out.println();
		printLiars();
	}

	private void printHashMap(HashMap<String, ArrayList<String>> hm) {
		AbstractSet<String> keySet = (AbstractSet<String>) hm.keySet();
		Iterator<String> keySetIterator = keySet.iterator();
		String currentAccusee = "";
		ArrayList<String> accusersPer = new ArrayList<String>();
		while (keySetIterator.hasNext()){
			currentAccusee = keySetIterator.next();
			System.out.print(currentAccusee + "--> (");
			accusersPer = hm.get(currentAccusee);
			for (int b=0; b<accusersPer.size(); b++){
				System.out.print(accusersPer.get(b));
				if (b<(accusersPer.size()-1))
					System.out.print(", ");
			}
			System.out.println(")");
		}
	}
	
	private void printTruthers(){
		System.out.println("Truthers: ");
		Iterator<String> trutherIterator = truthers.iterator();
		while (trutherIterator.hasNext()){
			System.out.println(trutherIterator.next());
		}
	}

	private void printLiars(){
		System.out.println("Liars: ");
		Iterator<String> liarIterator = liars.iterator();
		while (liarIterator.hasNext()){
			System.out.println(liarIterator.next());
		}
	}
	
	private void printOutputSizes(){
		if (truthers.size()>liars.size()){
			System.out.print(truthers.size() + " ");
			System.out.println(liars.size());
		}
		else{ //liars.size()>truthers.size() or ==
			System.out.print(liars.size() + " ");
			System.out.println(truthers.size());
		}
	}
}
