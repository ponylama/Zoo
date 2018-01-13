package graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import food.EFoodType;
import food.IEdible;
import graphics.IDrawable;
import graphics.ZooPanel;
import mobility.ILocatable;
import mobility.Point;
/**
 *A Singleton class that represent a food from type meat
 */

public class Meat implements IEdible, ILocatable, IDrawable {

	private Point location;
	protected ZooPanel pan;
	protected BufferedImage img;
	private static volatile  Meat instance = null;
	/**
	 * ctor
	 * @param p
	 */
	private Meat(ZooPanel p) {
		pan = p;
		this.location = new Point(pan.getWidth()/2,pan.getHeight()/2);
		loadImages("meat");
	}
	/**
	 * 
	 * @param p
	 * @return the instance of the class (if it's already done) or creating new instance by calling the ctor
	 */
	public static Meat getInstance(ZooPanel p){
	       if (instance == null)
	          synchronized(Meat.class){   
	              if (instance == null)
	                  instance = new Meat(p);
	          }
	       return instance;
	}
	/**
	 * load the image of meat
	 */
	public void loadImages(String nm){
			try { 
				img = ImageIO.read(new File(PICTURE_PATH + nm + ".gif"));
			}
			catch (IOException e) { System.out.println("Cannot load picture"); }
	}
	/**
	 * draw the object
	 */
	public void drawObject(Graphics g) {
		g.drawImage(img, location.getX()-20, location.getY()-20, 40, 40, pan);
	}
	
	public EFoodType getFoodtype() { return EFoodType.MEAT; }
	public String getColor() { return "Red"; }	 
	public Point getLocation() { return null; }
	public boolean setLocation(Point location) { return false; }


}
