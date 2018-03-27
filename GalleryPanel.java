import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

//Handles the JPanel responsible with displaying the saved images (the gallery):
public class GalleryPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> captures = new ArrayList<JLabel>();
    private Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
    private Border redBorder = BorderFactory.createLineBorder(Color.RED, 3);

    //Initialise the properties of the GalleryPanel:
	public GalleryPanel() {
		
		super();
		this.setLayout(new BorderLayout());
		JPanel images = new JPanel();
		this.add(images);
		images.setLayout(new GridLayout(12, 1));
		JButton delete = new JButton ("Delete");
		this.add(delete, BorderLayout.SOUTH);
		
		
		//Creating 12 JLabels and adding them to the captures list:
		for(int i=1; i<=12; i++) {
			
			JLabel jb = new JLabel();
			jb.setBorder(blackBorder);
			captures.add(jb);
		}
		
		//For each JLabel in the captures list, attach mouse listener:
		for(JLabel jb: captures) {
			
			images.add(jb);
			
			 jb.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                	
	                	if(jb.getIcon() != null) {// the user should not be able to select a null icon
	                		
	                		LineBorder border = (LineBorder)jb.getBorder();
	                		
	                		if(border.getLineColor() == Color.RED)
	                		
	                			jb.setBorder(blackBorder); //if the border is alredy red (it was selected before), then unselect it
	                		
	                		else 
	                			
	                			jb.setBorder(redBorder);// if the border is not selected, then select it
	                				
	                	}
	                    
	                	
	                }

	            });
		}
		

        /*
         * Attach action listener to the delete button so that once clicked, it deleted all selected (red-border) labels
         */
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Icon> selectedLabels = new ArrayList<Icon>();
				
				//Iterate over all the captures and add the red-border ones to the selected list:
				for(JLabel capture: captures) {

					if(capture.getIcon()!= null) {

						LineBorder border = (LineBorder)capture.getBorder();

						if(border.getLineColor() == Color.RED) {
							selectedLabels.add(capture.getIcon());
						}
					}
				}
				
				
				/*
				 * For each icon in the selectedLabels that corresponds to an icon in the captures list,
				 * delete that icon from the captures list and rearrange the list:
				 */
				for(Icon icon : selectedLabels) {
					for(JLabel capture : captures) {
							if(icon.equals(capture.getIcon())) {
								capture.setIcon(null);
								capture.setBorder(blackBorder);
								rearrange();
							}
							else
								capture.setBorder(blackBorder);
					}

					
				}
				
				images.repaint();
			}});

	}

	// Rearrange method overwrites the deleted icons with the following ones:
	public void rearrange() {
		
		int size = captures.size();
		for(int i=0; i<size; i++) {
			
			if(captures.get(i).getIcon() == null) {// if the icon at index i is null, then move all icons to the left by one position
				
				for(int j=i; j<size-1; j++) {
					captures.get(j).setIcon(captures.get(j+1).getIcon());
					
				}
			}
		}

		captures.get(size-1).setIcon(null);
		 
	}
	
	// Add captures to the JLabels:
	 public void addImage(BufferedImage capture) {

	        BufferedImage image = resize(capture,120,100);
            for(JLabel jl : this.captures){

	            if(jl.getIcon() == null){
	            	
	            	jl.setIcon(new ImageIcon(image));
	            	jl.setBorder(blackBorder);
	            	
	            break;
	            }
	         }
	    }
	 
	 //Resize the capture:
	 public static BufferedImage resize(BufferedImage image, double width, double height) {

	       
	        Image tempImage = image.getScaledInstance((int)width,(int) height,Image.SCALE_SMOOTH);
	        BufferedImage resizedImg = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = resizedImg.createGraphics();
	        g2d.drawImage(tempImage, 0, 0, null);
	        g2d.dispose();

	        return resizedImg;
	    }
}