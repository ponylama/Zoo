package Factory;

import animals.Animal;
import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Turtle;
import food.EFoodType;
import graphics.ZooPanel;
/**
 * A class that implements the interface AbstractZooFactory that includes a method for making animals by type(Herbivore, Omnivore, Carnivore)
 */
public class HerbivoreFactory implements AbstractZooFactory {

	/**
	 * for produce Elephant, Giraffe or Turtle
	 */

	@Override
	public Animal produceAnimal(String type,int sz,int hor, int ver, String c, ZooPanel p) {
		if(type.equals("Elephant"))
			return new Elephant(sz,0,0,hor,ver,c,p);
		else if(type.equals("Giraffe"))
			return new Giraffe(sz,0,0,hor,ver,c,p);
		else
			return new Turtle(sz,0,0,hor,ver,c,p);


	}



}
