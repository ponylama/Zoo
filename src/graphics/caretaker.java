package graphics;
import java.util.ArrayList;

public class caretaker {
    // Where all mementos are saved
	ArrayList<ZooMemento> SavedState;
	
	/**
	 * caretaker ctor
	 */
	
	public caretaker()
	{
		System.out.println("caretaker ctor");
		SavedState = new ArrayList<ZooMemento>();
	}
	
	/**
	 * add zoomomento object to SavedState ArrayList
	 */
    public void addMemento(ZooMemento m) 
    { 
    	if(list_size()<3)
    		SavedState.add(m);
    	
    	System.out.println("added to SavedState in addMemento");
    }
    
	/**
	 * return savedstate object
	 */
    
    // Gets the memento requested from the ArrayList
    public ZooMemento getMemento(int index) { return SavedState.get(index); }
    
	/**
	 * return savedstate len
	 */
    
    public int list_size() { return SavedState.size(); }
}
