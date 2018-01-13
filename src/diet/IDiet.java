package diet;

import animals.Animal;
import food.EFoodType;
import food.IEdible;


public interface IDiet {

	/**
	 * @param food
	 * @return
	 */
	public boolean canEat(IEdible food);

	/**
	 * @param food
	 * @return
	 */
	public boolean eat(Animal animal, IEdible food);
	
	public boolean canEat(EFoodType food_type);

}
