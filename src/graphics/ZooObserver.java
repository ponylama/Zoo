package graphics;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executors;

import animals.Animal;
/**
 *  * A class that responsible of manage threads
 */
public class ZooObserver extends Thread  implements Observer {

	ZooPanel pan;
	/**
	 * ctor
	 * @param zooPanel
	 */
	public ZooObserver(ZooPanel zooPanel) {
	this.pan = zooPanel;
	}
	/**
	 * A thread will use this method to awake another waiting thread
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		notify();
	}
	/**
	 * operate the threads
	 */
	public void run() {
		while(true) {
			synchronized(this) {
				
				try {
					this.wait(20);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pan.repaint();
				checkeat();
		}
		}
		
	}

		
	
	/**
	 * check if the animals can eat another animal
	 */
	public void checkeat()
	{
		boolean prey_eaten = false;

		synchronized(this) {
			//System.out.println(pan.animals.size());
			for(Animal predator : pan.animals) {
				for(Animal prey : pan.animals) {
					if(predator != prey && predator.getDiet().canEat(prey) && predator.getWeight()/prey.getWeight() >= 2 &&
					   (Math.abs(predator.getLocation().getX() - prey.getLocation().getX()) < prey.getSize()) &&
					   (Math.abs(predator.getLocation().getY() - prey.getLocation().getY()) < prey.getSize())) {
							pan.preyEating(predator,prey);
							System.out.print("The "+predator+" cought up the "+prey+" ==> ");
							//
							//prey.isdead=true;
							pan.animals.remove(prey);	
							prey.interrupt();
							pan.repaint();
							//JOptionPane.showMessageDialog(frame, ""+prey+" killed by "+predator);
							prey_eaten = true;
							break;
					}
				}
				if(prey_eaten)
					break;
			}
		}
		try {
			Thread.sleep(1000/pan.RESOLUTION);
		} catch (InterruptedException e) {
			return;
		}
	}
}
