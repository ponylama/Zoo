package graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import mobility.Point;
import animals.Animal;
/**
 *  * A class that duplicate an animal
 */

public class DuplicateDialog extends JDialog implements ActionListener {
private ZooPanel panel;
private JPanel mainPanel, selectPanel, speedPanel;
private Animal an, clone;
private JComboBox<String> list;
private JLabel lbl_hor, lbl_ver;
private JSlider sl_hor, sl_ver;
private JButton ok;
/**
 * ctor
 * @param pan
 */
    public DuplicateDialog(ZooPanel pan)
    {
    	super(new JFrame(),"Duplicate an animal",true);
    	panel = pan;
    	an = clone = null;
 
    	setSize(750,250);
    	setResizable(false);
	
	setBackground(new Color(100,230,255));
   	mainPanel = new JPanel();
	add(mainPanel);
		
	mainPanel.setLayout(new GridLayout(1,2));
		
	selectPanel = new JPanel();
	selectPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Select Animal to clone"), 
			BorderFactory.createEmptyBorder(5,5,5,5)));
	selectPanel.setLayout(new BorderLayout());
 
	list = new JComboBox<String>();
	list.addItem("No animal");
	panel.fillComboBox(list,"All");
	list.addActionListener(this);
	selectPanel.add("North",list);
		
	ok = new JButton("OK");
	ok.addActionListener(this);
	selectPanel.add("South", ok);
	mainPanel.add(selectPanel);
		
	speedPanel = new JPanel();
	speedPanel.setLayout(new GridLayout(4,1));
	speedPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Speed may be changed..."), 
			BorderFactory.createEmptyBorder(5,5,5,5)));
		
	lbl_hor = new JLabel("Horizontal speed",JLabel.CENTER);
	speedPanel.add(lbl_hor);
 
	sl_hor = new JSlider(0,10);
	sl_hor.setMajorTickSpacing(2);
	sl_hor.setMinorTickSpacing(1);
	sl_hor.setPaintTicks(true);
	sl_hor.setPaintLabels(true);
	speedPanel.add(sl_hor);
		
	lbl_ver = new JLabel("Vertical speed",JLabel.CENTER);
	speedPanel.add(lbl_ver);
		
	sl_ver = new JSlider(0,10);
	sl_ver.setMajorTickSpacing(2);
	sl_ver.setMinorTickSpacing(1);
	sl_ver.setPaintTicks(true);
	sl_ver.setPaintLabels(true);
	speedPanel.add(sl_ver);
		
	mainPanel.add(speedPanel);		
    }
    /**
	 * function that operate the buttons of the dialog
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == ok) {
	    	if(an!=null) {
	    		clone = (Animal) an.clone();
	    		clone.setLocation(new Point(0,0));
	    		clone.setHorSpeed(sl_hor.getValue());
	    		clone.setVerSpeed(sl_ver.getValue());
	    		panel.addan(clone);
	    	}
    		setVisible(false);
    	}
    	else {
	    	int index;
	    	if((index = list.getSelectedIndex()) != 0) {
	    		try { an = panel.getAnimal(index-1); } 
	    		catch (Exception e1) { System.out.println("Duplicate error!."); an = null; }
		    	if(an!=null) {
		    		sl_hor.setValue(an.getHorSpeed());
		    		sl_ver.setValue(an.getVerSpeed());
		    	}
	    	}		
    	}
		
	}

}
