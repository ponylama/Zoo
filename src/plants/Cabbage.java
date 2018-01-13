package plants;
import graphics.ZooPanel;
/**
 *A Singleton class that represent a food from type Cabbage
 */

public class Cabbage extends Plant {
	private static volatile  Cabbage instance = null;
	/**
	 * ctor
	 * @param pan
	 */
	private Cabbage(ZooPanel pan) {
		super(pan);
		loadImages("cabbage");
	}
	
	/**
	 * 
	 * @param p
	 * @return the instance of the class (if it's already done) or creating new instance by calling the ctor
	 */
	
	public static Cabbage getInstance(ZooPanel p){
	       if (instance == null)
	          synchronized(Lettuce.class){   
	              if (instance == null)
	                  instance = new Cabbage(p);
	          }
	       return instance;
	}
	public String name(){return "Cabbage";}
}
