package Factory;

import animals.Animal;
import animals.Bear;
import animals.Giraffe;
import food.EFoodType;
import graphics.ZooPanel;

public class OmnivoreFactory implements AbstractZooFactory{
	/**
	 * A class that implements the interface AbstractZooFactory that includes a method for making animals by type(Herbivore, Omnivore, Carnivore)
	 */
	public OmnivoreFactory(){
		
	}
	/**
	 * for produce Bear
	 */
	@Override
	public Animal produceAnimal(String type,int sz, int hor, int ver, String c, ZooPanel p) {
		return new Bear(sz,0,0,hor,ver,c,p);
	}



}
