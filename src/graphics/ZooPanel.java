package graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import Factory.AbstractZooFactory;
import Factory.CarnivoreFactory;
import Factory.HerbivoreFactory;
import Factory.OmnivoreFactory;
import animals.Animal;
import animals.Bear;
import animals.Elephant;
import animals.Giraffe;
import animals.Lion;
import animals.Turtle;
import diet.*;
import food.EFoodType;
import plants.Cabbage;
import plants.Lettuce;
import plants.Plant;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * A class that represent a panel on the frame
*/
public class ZooPanel extends JPanel implements ActionListener, Runnable
{
   private static final long serialVersionUID = 1L;
   private static final int MAX_ANIMAL_NUMBER  = 11;
   private final String BACKGROUND_PATH = Animal.PICTURE_PATH+"savanna.jpg";
   private final String MEAT_PATH = Animal.PICTURE_PATH+"meat.gif";
   public final int RESOLUTION = 25; 
   private ZooFrame frame;
   private EFoodType Food;
   private JPanel p1;
   private JButton[] b_num;
   private String[] names = {"Add Animal","Sleep","Wake up","Clear","Food","Info","Exit","Decorator","Duplicate","Save state","Restore state"};
   private caretaker Caretaker;
   private Plant forFood = null;
   private Meat meat=null;
   private JScrollPane scrollPane;
   private boolean isTableVisible;
   private int totalCount;
   private BufferedImage img, img_m;
   private boolean bgr;
   public ZooObserver controller;
   ExecutorService executor = Executors.newFixedThreadPool(5);
   ArrayList<Animal> animals;
   static private volatile  ZooPanel instance = null;
   AbstractZooFactory zoofact=null;
   protected boolean isdead=false;
   
   private ZooPanel(ZooFrame f)
   {
	   
	    frame = f;
	    Food = EFoodType.NOTFOOD;
	    totalCount = 0;
	    isTableVisible = false;
	    Caretaker = new caretaker();


	    animals = new ArrayList<Animal>();
	    
	    controller = new ZooObserver(this);
	    controller.start();	    
	   
	    setBackground(new Color(255,255,255));
	    
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,7,0,0));
		p1.setBackground(new Color(0,150,255));

		b_num=new JButton[names.length];
		for(int i=0;i<names.length;i++)
		{
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(this);
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}

		setLayout(new BorderLayout());
		add("South", p1);
		
		img = img_m = null;
		bgr = false;
		try { img = ImageIO.read(new File(BACKGROUND_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load background"); }
		try { img_m = ImageIO.read(new File(MEAT_PATH)); } 
		catch (IOException e) { System.out.println("Cannot load meat"); }
		
		//System.out.println(BACKGROUND_PATH);
		//System.out.println(MEAT_PATH);
		
   }	
   
   public static ZooPanel getInstance(ZooFrame frame){
       if (instance == null)
          synchronized(ZooPanel.class){   
              if (instance == null)
                  instance = new ZooPanel(frame);
          }
       return instance;
    }


   public void paintComponent(Graphics g)
   {
	   	super.paintComponent(g);	
	   	
	   	if(bgr && (img!=null))
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);

	   	//if(Food == EFoodType.MEAT)
	   		//g.drawImage(img_m, getWidth()/2-20, getHeight()/2-20, 40, 40, this);
	   	if((Food == EFoodType.MEAT) && (meat != null))
	   		meat.drawObject(g);
	    
	   	if((Food == EFoodType.VEGETABLE) && (forFood != null))
	   		forFood.drawObject(g);

	   	synchronized(this) {
		   	for(Animal an : animals)
		    	an.drawObject(g);
	   	}
   }
   
   public void setBackgr(int num) {
	   switch(num) {
	   case 0:
		   setBackground(new Color(255,255,255));
		   bgr = false; 
		   break;
	   case 1:
		   setBackground(new Color(0,155,0));
		   bgr = false; 
		   break;
	   default:
			bgr = true;   
	   }
	   repaint();
   }
   
   synchronized public EFoodType checkFood()
   {
	   return Food;
   }

   /**
    * CallBack function 
    * @param f
    */
   synchronized public void eatFood(Animal an)
   {
	   if(Food != EFoodType.NOTFOOD)
	   {
		    if(Food == EFoodType.VEGETABLE)
		    	forFood = null;
		   	Food = EFoodType.NOTFOOD;
	   		an.eatInc();
	   		totalCount++;
	   		System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" ate food.");
	   }
	   else
	   {
		   System.out.println("The "+an.getName()+" with "+an.getColor()+" color and size "+an.getSize()+" missed food.");
	   }
   }

   public void addDialog()
   {
	   if(animals.size()==MAX_ANIMAL_NUMBER) {
		   JOptionPane.showMessageDialog(this, "You cannot add more than "+MAX_ANIMAL_NUMBER+" animals");
	   }
	   else {
		   Object[] options = {"Herbivore", "Omnivore", "Carnivore"}; 
		   int n = JOptionPane.showOptionDialog(frame, 
		   		"Type", "Please choose Type", 
		   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
		   		null, options, options[2]);
		   switch(n) {
		   case 0: // Harbivore
			   zoofact=createAnimalFactory("H");
			   break;
		   case 1: // Omnivore
			   zoofact=createAnimalFactory("O");
			   break;
		   default: // Carnivore
			   zoofact=createAnimalFactory("C");

		   }
		   
		   AddAnimalDialog dial = new AddAnimalDialog(zoofact.getClass().toString(),this,"Add an animal to aquarium");
		   dial.setVisible(true);
	   }
   }
   
   public void addAnimal(String animal, int sz, int hor, int ver, String c)
   {
	   
	   Animal an = null;
	   if(animal.equals("Elephant"))
	   		an = zoofact.produceAnimal(animal, sz, hor, ver, c, this);
	   else if (animal.equals("Lion"))
	   		an = zoofact.produceAnimal(animal, sz, hor, ver, c, this);
	   else if (animal.equals("Turtle")) 
	   		an = zoofact.produceAnimal(animal, sz, hor, ver, c, this);
	   else if (animal.equals("Bear"))
	   		an = zoofact.produceAnimal(animal, sz, hor, ver, c, this);
	   else 
	   		an = zoofact.produceAnimal(animal, sz, hor, ver, c, this);
	   animals.add(an);
	   //this.start();
	   executor.execute(an);
   }
   public void addan(Animal an)
   {
	   animals.add(an);
	   executor.execute(an);
   }

	public void start() {
	    for(Animal an : animals)
	    	an.setResume();
   }

 	public void stop() {
	    for(Animal an : animals)
	    	an.setSuspend();
   }
 	/**
 	 * clear from the screen the running animals and remove them from the list of animals and from the tradpool
 	 */
 	synchronized public void clear()
    {
 	   if(animals.size()<6)
 	   {
	 	   for(Animal an : animals){
	 		   if(an.isRun)
	 			   an.interrupt();
	 	   }
	
	 	   Food = EFoodType.NOTFOOD;
	 	   forFood = null;
	 	   totalCount = 0;
 	   }
 	   else
 	   {
 		   for(int i =0 ; i<5 ; i++) 
 		   {
 			   animals.get(0).interrupt();
 			   animals.remove(0);
 		   }
 	   }
 	   repaint();
 	  animals.clear();
    }

   

   synchronized public void preyEating(Animal predator, Animal prey)
   {
	   predator.eatInc();
	   totalCount -= (prey.getEatCount()-1);
   }

   synchronized public void addFood()
   {
	  
	   if(Food == EFoodType.NOTFOOD){
		   Object[] options = {"Meat", "Cabbage", "Lettuce"}; 
		   int n = JOptionPane.showOptionDialog(frame, 
		   		"Please choose food", "Food for animals", 
		   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
		   		null, options, options[2]);
		   switch(n) {
		   case 0: // Meat
			   Food = EFoodType.MEAT;
			   meat = Meat.getInstance(this);
			   break;
		   case 1: // Cabbage
			   Food = EFoodType.VEGETABLE;
			   forFood = Cabbage.getInstance(this);
			   break;
		   default: // Lettuce
			   Food = EFoodType.VEGETABLE;
			   forFood = Lettuce.getInstance(this);
			   break;
		   }
	   }
	   else {
		   Food = EFoodType.NOTFOOD;
		   meat=null;
		   forFood = null;
	   }
	   repaint();
  }
   
   public void info()
   {  	 
	   if(isTableVisible == false)
	   {
		  int i=0;
		  int sz = animals.size();

		  String[] columnNames = {"Animal","Color","Weight","Hor. speed","Ver. speed","Eat counter"};
	      String [][] data = new String[sz+1][columnNames.length];
		  for(Animal an : animals)
	      {
	    	  data[i][0] = an.getName();
	    	  data[i][1] = an.getColor();
	    	  data[i][2] = new Integer((int)(an.getWeight())).toString();
		      data[i][3] = new Integer(an.getHorSpeed()).toString();
		      data[i][4] = new Integer(an.getVerSpeed()).toString();
	    	  data[i][5] = new Integer(an.getEatCount()).toString();
	    	  i++;
	      }
	      data[i][0] = "Total";
	      data[i][5] = new Integer(totalCount).toString();
	      
	      JTable table = new JTable(data, columnNames);
	      scrollPane = new JScrollPane(table);
	      scrollPane.setSize(450,table.getRowHeight()*(sz+1)+24);
	      add( scrollPane, BorderLayout.CENTER );
	      isTableVisible = true;
	   }
	   else
	   {
		   isTableVisible = false;
	   }
	   scrollPane.setVisible(isTableVisible);
       repaint();
   }
   
   public void destroy()
   { 
	  executor.shutdown();
      System.exit(0);
   }
   
   
   public void actionPerformed(ActionEvent e)
   {
	if(e.getSource() == b_num[0]) // "Add Animal"
		addDialog();
	else if(e.getSource() == b_num[1]) // "Sleep"
		stop();
	else if(e.getSource() == b_num[2]) // "Wake up"
		start();
	else if(e.getSource() == b_num[3]) // "Clear"
		clear();
	else if(e.getSource() == b_num[4]) // "Food"
		addFood();
	else if(e.getSource() == b_num[5]) // "Info"
		info();
	else if(e.getSource() == b_num[6]) // "Exit"
		destroy();
	else if(e.getSource() == b_num[7]) // "Decorator"
		decorate();
	else if(e.getSource() == b_num[8]) // "Duplicate"
		Open_Duplicate();
	else if(e.getSource() == b_num[9]) // "Save state"
		save_state();
	else if(e.getSource() == b_num[10]) // "Restore state"
		restore_frame();
	
   }

   


	public boolean isChange() {
		boolean rc = false;
		for(Animal an : animals) {
		    if(an.getChanges()){
		    	rc = true;
		    	an.setChanges(false);
			}
	    }
		return rc;
	}
	
	public AbstractZooFactory createAnimalFactory(String animal_eat) {
		if(animal_eat.equals("C"))
			return new CarnivoreFactory();
		else if(animal_eat.equals("H"))
			return new HerbivoreFactory();
		else 
			return new OmnivoreFactory();
	}
	
	/**
	 * check the color of the animals, if it's natural the boolean variable there_is_natural changed to true
	 */
	public void decorate(){ 
		boolean there_is_natural = false;
		for(Animal an : animals)
		{
			if(an.getColor().equals("Natural"))
				there_is_natural= true;
		}
		if (!there_is_natural)
			JOptionPane.showMessageDialog(this, "You have not animals for decoration");
		else
		{
			DecorateDialog deco=new DecorateDialog(this);
			deco.setVisible(true);
		}
	}
	/**
	 * 
	 * @return the array list of animals
	 */
	public ArrayList<Animal> getanimals(){
		return animals;
	}
	/**
	 * fill the combobox  of animals for decorate
	 * @param list
	 * @param string
	 */
	public void fillComboBox(JComboBox<String> list, String string) {
		for (Animal an : animals)
		{
			int counter = 1;
			if (an.getColor().equals(string) || string.equals("All"))
			{
				String temp= counter + ". " + an.getName() + ", running=" + an.isRun +", weight=" + an.getWeight()+ ", color=" + an.getColor() ;
				list.addItem(temp);
			}
			counter++;
		}
		
		
	}
	/**
	 * 
	 * @param i
	 * @return animal by index
	 */
	public Animal getAnimal(int i) {
		return animals.get(i);
	}
	/**
	 * change for visible the dialog of duplicate
	 */
	public void Open_Duplicate(){
		DuplicateDialog deco=new DuplicateDialog(this);
		deco.setVisible(true);
	}

	@Override
	public void run() {
		
	}
		/**
	 * Function that save which the user request
	 */
	public void save_state()
	{
		stop();
		Caretaker.addMemento(new ZooMemento(this.animals,this.Food, this.forFood  ));
		start();
	}
	/**
	 * return the frame to the saving state
	 */
	public void restore_frame()
	{
		if ( this.Caretaker.list_size()==0 )
				JOptionPane.showMessageDialog(this, "You have not saved states");
		else
		{
			   Object[] options = {"State 1", "State 2", "State 3"}; 
			   int n = JOptionPane.showOptionDialog(frame, 
			   		"Type", "Please choose Type", 
			   		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
			   		null, options, options[2]);
			   switch(n) 
			   {
			   case 0: // State 1
				   System.out.println("case 0");
				   clearall();
				   this.animals.addAll(this.Caretaker.getMemento(0).getSaved_animal_state());
				   for ( int i=0 ; i< this.animals.size() ; i++)
					   executor.execute(this.animals.get(i));
				   addFoodRestore(this.Caretaker.getMemento(0).getSaved_memo_Food() , this.Caretaker.getMemento(0).getSaved_memo_plant());
				   start();
				   break;
			   case 1: // State 2
				   if (this.Caretaker.list_size()>1)
				   {
				   clearall();
				   this.animals.addAll(this.Caretaker.getMemento(1).getSaved_animal_state());
				   for ( int i=0 ; i< this.animals.size() ; i++)
					   executor.execute(this.animals.get(i));
				   addFoodRestore(this.Caretaker.getMemento(1).getSaved_memo_Food() , this.Caretaker.getMemento(1).getSaved_memo_plant());
				   start();
				   }
				   else
					   JOptionPane.showMessageDialog(this, "You haven't saves second state");
				   break;
			   case 2: // State 3
				   if (this.Caretaker.list_size()>2)
				   {
					   clearall();
					   this.animals.addAll(this.Caretaker.getMemento(2).getSaved_animal_state());
					   for ( int i=0 ; i< this.animals.size() ; i++)
						   executor.execute(this.animals.get(i));
					   addFoodRestore(this.Caretaker.getMemento(2).getSaved_memo_Food() , this.Caretaker.getMemento(2).getSaved_memo_plant());
					   start();
				   }
				   else
					   JOptionPane.showMessageDialog(this, "You haven't saves third state");
			   }
			   
		 }
	}
	
	/**
	 * clear all animals on panel 
	 */
		public void clearall()
		{
		 	   for(Animal an : animals)
		 		   if(an.isRun)
		 			   an.interrupt();
		 	   
		 	   Food = EFoodType.NOTFOOD;
		 	   forFood = null;
		 	   totalCount = 0;
		 	   repaint();
		 	   animals.clear();
		 	   System.out.println(executor.isTerminated());

		 }
		/**
		 * @param p
		 * @param f
		 * @return animal by index
		 */
		synchronized public void addFoodRestore(EFoodType f, Plant p)
		   {

					   Food = f;
					   if(Food == EFoodType.MEAT)
						   meat = Meat.getInstance(this);
					   if(Food == EFoodType.VEGETABLE)
					   {
						  
						   
						   if (p.name() == "Cabbage")
							   forFood = Cabbage.getInstance(this);
						   else 
							   forFood = Lettuce.getInstance(this);
					   }
					   if(Food == EFoodType.NOTFOOD)
					   {
						   meat=null;
						   forFood = null;
					   }
					   repaint();
			}

}
