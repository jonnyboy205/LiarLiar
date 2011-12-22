
public class LiarLiarTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			LiarLiar myLiarLiar = new LiarLiar(args[0]);
			myLiarLiar.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
