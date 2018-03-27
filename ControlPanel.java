import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

// ControlPanel class handles the JPanel responsible with the control of the doily

public class ControlPanel extends JPanel {
	
	
	private static final long serialVersionUID = 1L;
	private DisplayPanel doily;
	private Container pane;
	private GalleryPanel gallery;
	
	public ControlPanel (DisplayPanel doily, Container pane, GalleryPanel gallery) {
		
		this.gallery = gallery;
		this.pane = pane;
		this.doily = doily;
		
		this.setLayout(new GridLayout(5, 1, 5, 5));
		this.setBorder(new EmptyBorder(new Insets(15, 20, 15, 20)));
		
		Box box1 = Box.createVerticalBox();// all buttons clear, undo and redo are within box1
		this.add(box1);
		
	   
		/* 
		 * Creates clear button and adds action listener:
		 */
		
		JButton clearButton = new JButton ("Clear");
		box1.add(clearButton);
		box1.add(Box.createVerticalStrut(10));
		clearButton.addActionListener(new ActionListener() {
			 
			@Override
			public void actionPerformed (ActionEvent e) {
				
				doily.clear(); //once the button is pressed, the clear method in DisplayPanel is called
				doily.repaint(); //repaint the doily to reflect the changes
			}
		});
		
		/*
		 * Creates undo button and adds action listener so that the undo method for this doily is called:
		 */
		
		JButton undoButton = new JButton ("Undo");
		box1.add(undoButton);
		box1.add(Box.createVerticalStrut(10));
		undoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (ActionEvent e) {
				
				doily.undo();
				doily.repaint();
			}
		});
		
		/*
		 * Creates redo button and adds action listener so that the redo method for this doily is called:
		 */
		JButton redoButton = new JButton ("Redo");
		box1.add(redoButton);
		box1.add(Box.createVerticalStrut(10));
		redoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				doily.redo();
				doily.repaint();
			}
		});
		
		/*
		 * Creates colour chooser button and sets the pen colour for this doily to the chosen one:
		 */
		
		JButton colour = new JButton("Change colour");
		
		
		colour.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Color myColour = JColorChooser.showDialog(pane, "Choose colour", pane.getBackground());
				
				if(myColour != null) {
					
					doily.setErased(false);
					doily.setColor(myColour);
					doily.setPointListColour(myColour);
					doily.repaint();
				}
			}
		});
		
		Box box2 = Box.createVerticalBox();
		this.add(box2);
		/*
		 * Creates a combo box that allows the user to change the size of the pen:
		 */
		
		JComboBox <String> changeSize = new JComboBox<String>();
		box2.add(changeSize);
		box2.add(Box.createVerticalStrut(10));
		changeSize.addItem("Change size of pen");
		/*
		 * Adds 50 possible values for the pen size to the combo box:
		 */
		for(int i=1; i<=50; i++) {
			String item = ""+i;
			changeSize.addItem(item);
		}
			
		
		changeSize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (ActionEvent e) {
				
				 JComboBox cb = (JComboBox)e.getSource();
			     String string = (String)cb.getSelectedItem();
			     
			     if(string.equals("Change size of pen") == false) { //selecting the title of the combo box should not be a valid choice
			    	 
			    	 int size = Integer.parseInt(string);
			    	 doily.setPenSize(size);
			    	 doily.setPenSizePointList(size); 
			    	 doily.repaint();
			     }
			}
		});
		
		/*
		 * Creates a combo box that allows the user to change the number of sectors:
		 */
		JComboBox <String> changeSectors = new JComboBox<String>();
		box2.add(changeSectors);
		changeSectors.addItem("Change number of sectors");
		for(int i=2; i<=12; i++) {// Because we want each sector to have at least 30 degrees, the user can choose up to 12 sectors
			String item = ""+i;
			changeSectors.addItem(item);
		}
		/*
		 * The action listener attached to changeSectors combo box will update the angle
		 *  value and the number of sectors in the DisplayPanel class
		 */
		changeSectors.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed (ActionEvent e) {
				
				 JComboBox cb = (JComboBox)e.getSource();
			     String string = (String)cb.getSelectedItem();
			     
			     if(string.equals("Change number of sectors") == false) { //selecting the title of the combo box should not be a valid choice
			    	 
			    	 int number = Integer.parseInt(string);
			    	 doily.setNumberOfSectors(number);
			    	 double angle = 360/number;
			    	 doily.setAngle(angle);
			    	 doily.repaint();
			     }
			}
		});
		
		
		
		Box box3 = Box.createVerticalBox();// creating a new box to accommodate both check boxes
		this.add(box3);
		
		/*
		 * Creates a check box that is selected by default and allows the user to toggle
		 * between showing sector lines or not:
		 */
		
		JCheckBox showSectorLines = new JCheckBox("Show sector lines");
		box3.add(showSectorLines);
		
		showSectorLines.setSelected(true); //the show sector line option is selected by default
		
		/*
		 * The action listener attached to the showSectorLines variable will update the value of the corresponding
		 * member variable in DisplayPanel:
		 */
		
		showSectorLines.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 boolean selected = showSectorLines.isSelected();
				    if (selected) {
				      doily.setSectorLines(true);
				      doily.repaint();
				    } else {
				      doily.setSectorLines(false);
				      doily.repaint();
				    }
			}
		});
		/*
		 * Creates a a check box for getting the reflection preference:
		 */
		
		JCheckBox reflect = new JCheckBox("Reflect drawn points");
		box3.add(reflect);
		reflect.setSelected(false);
		reflect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 
				    if (reflect.isSelected()) {
				    	
				      doily.setReflectDrawing(true);
				      doily.repaint();
				    } 
				    else {
				    	
				      doily.setReflectDrawing(false);
				      doily.repaint();
				    }
			}
		});
		
		JPanel drawing = new JPanel (); // creating a new panel to accommodate the eraser and pen icon
		this.add(drawing);
		
		JCheckBox pen = new JCheckBox("Pen");
		pen.setIcon(new ImageIcon ((new ImageIcon("src/pen2.png").getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		pen.setSelected(true); //pen is selected by default
		drawing.add(pen);
		
		JCheckBox eraser = new JCheckBox("Eraser");
		eraser.setIcon(new ImageIcon ((new ImageIcon("src/eraser.png").getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		eraser.setSelected(false);
		drawing.add(eraser);
		
		/*
		 * If pen is selected after the eraser was selected, the colour is reset to the previous one by
		 * calling the getLastColor method in Display Panel
		 */
		
		pen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(pen.isSelected()) {
					
					if(doily.isErased()) {
					doily.setPointListColour(doily.getLastColor());
					doily.setColor(doily.getLastColor());
					eraser.setSelected(false); // pen and eraser cannot be selected at the same time
					doily.repaint();
					}
					doily.setErased(false);
				}
				else
					eraser.setSelected(true);
			}
		});
		
		/*
		 * If eraser is selected, the pen colour is set to the background colour. To avoid erasing the sector lines, 
		 * the sector lines are drawn after the marks
		 */
		eraser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(eraser.isSelected()) {
					
					doily.setErased(true);
					doily.repaint();
					doily.setLastColor(doily.getColor());
					doily.setPointListColour(doily.getBackground());
					doily.setColor(doily.getBackground()); 
					pen.setSelected(false);
					doily.repaint();
				}
				else
					pen.setSelected(true);
				
			}
		});
		
		Box box4 = Box.createVerticalBox(); // box 4 will accommodate button responsible with changing the pen colour and the save button
		this.add(box4);
		JButton save = new JButton("Save to Gallery");
		box4.add(save);
		box4.add(Box.createVerticalStrut(10));
		box4.add(colour);
		/*
		 * Adding an action listener to the save button, so that once the button is clicked
		 * the a screen shot of the doily is taken and the image is added to the gallery:
		 */
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BufferedImage capture = doily.getCapture(doily);
				gallery.addImage(capture);
				
			
				
			}
		});
		
		}
	

	
	

}
