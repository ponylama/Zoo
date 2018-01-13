package graphics;
import java.util.ArrayList;

import plants.Plant;
import food.EFoodType;
import mobility.Point;
import animals.Animal;
public class ZooMemento {

	ArrayList<Animal> memo_animals;
	EFoodType memo_Food;
	Plant memo_plant;
	public ZooMemento(ArrayList<Animal> memo_animal_to_save , EFoodType Food , Plant forFood)
	{
		//save state
		memo_animals = new ArrayList<Animal>();
		for(int i=0 ; i<memo_animal_to_save.size(); i++)
		{
			memo_animals.add((Animal) memo_animal_to_save.get(i).clone());
			Point temp  = new Point (memo_animal_to_save.get(i).getLocation().getX(),memo_animal_to_save.get(i).getLocation().getY());
			this.memo_animals.get(i).setLocation(temp);
		}
		
		this.memo_Food = Food;
		this.memo_plant =  forFood ;
	}
	
	// return list
	public ArrayList<Animal> getSaved_animal_state() { return memo_animals; }
	public EFoodType getSaved_memo_Food() { return memo_Food; }
	public Plant getSaved_memo_plant() { return this.memo_plant; }
}
