package plants;
import graphics.ZooPanel;


public class Lettuce extends Plant {
	private static volatile  Lettuce instance = null;

	private Lettuce(ZooPanel pan) {
		super(pan);
		loadImages("lettuce");
	}
	/**
	 * 
	 * @param p
	 * @return the instance of the class (if it's already done) or creating new instance by calling the ctor
	 */
	public static Lettuce getInstance(ZooPanel p){
	       if (instance == null)
	          synchronized(Lettuce.class){   
	              if (instance == null)
	                  instance = new Lettuce(p);
	          }
	       return instance;
	}
	public String name(){return "Lettuce";}
}
