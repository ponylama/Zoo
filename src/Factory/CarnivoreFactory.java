package Factory;

import animals.Animal;
import animals.Bear;
import animals.Lion;
import food.EFoodType;
import graphics.ZooPanel;

public class CarnivoreFactory implements AbstractZooFactory {
	/**
	 * A class that implements the interface AbstractZooFactory that includes a method for making animals by type(Herbivore, Omnivore, Carnivore)
	 */
	@Override
	public Animal produceAnimal(String type, int sz, int hor, int ver, String c, ZooPanel p) {
		
		/**
		 * for produce Lion
		 */
		
		return new Lion(sz,0,0,hor,ver,c,p);
	}

	

}
