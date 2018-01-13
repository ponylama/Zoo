package graphics;
import animals.Animal;
/**
 * An interface that includes a method for adding new color to the animals
 */
public class ColoredAnimalDecorator implements ColoredAnimal {

	ColoredAnimal coloredanimal;
	/**
	 * for change the color of the animal
	 * @param color
	 */
	
	public ColoredAnimalDecorator(Animal a) {
		this.coloredanimal = a;
	}


	@Override
	public void PaintAnimal(String color) {
		this.coloredanimal.PaintAnimal(color);
		Animal an = (Animal) coloredanimal;
		String nm="";
		switch(an.getName()){
		case "Lion":
			nm="lio";
			break;
		case "Bear":
			nm="bea";
			break;
		case "Elephant":
			nm="elf";
			break;
		case "Giraffe":
			nm="grf";
			break;
		case "Turtle":
			nm="trt";
			break;
		}
		an.setColor(color);
		an.loadImages(nm);
	}

}
