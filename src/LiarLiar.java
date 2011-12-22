import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LiarLiar {

	String fileName;
	Scanner input;
	
	public LiarLiar(String fileName) throws Exception{
		this.fileName = fileName;
		createScanner();
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
		
	}

}
