import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LiarLiar {

	String fileName;
	Scanner input;
	int numAccusers;
	
	public LiarLiar(String fileName) throws Exception{
		this.fileName = fileName;
		
		createScanner();
		
		this.numAccusers = 0;
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
	
	public void parseFile(){
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
		
	}

}
