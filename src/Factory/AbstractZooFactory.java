package Factory;

import animals.Animal;
import food.EFoodType;
import graphics.ZooPanel;
/**
 * An interface that includes a method for making animals by type(Herbivore, Omnivore, Carnivore)
 */
public interface AbstractZooFactory {
	/**
	 * for produce an animal
	 * @param type
	 * @param sz
	 * @param hor
	 * @param ver
	 * @param c
	 * @param p
	 * @return
	 */
	public Animal  produceAnimal(String type,int sz, int hor, int ver, String c, ZooPanel p);
}
