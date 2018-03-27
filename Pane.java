import java.awt.*;
import javax.swing.*;
/*
 * The Pane acts as a container that accommodates the three main components of this GUI: DisplayPanel, ControlPanel and GalleryPanel 
 */
public class Pane extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Pane (String name) {
		
		super(name);
		this.drawPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 600);
		this.setVisible(true);
		
	}
	
	/*
	 * Creates the three main panels, adds them to the pane and sets their layouts:
	 */
	
	public void drawPane() {
		
		Container pane = this.getContentPane();
		pane.setLayout(new BorderLayout());
		
		GalleryPanel galleryPanel = new GalleryPanel();
		pane.add(galleryPanel, BorderLayout.WEST);
		
		DisplayPanel displayPanel = new DisplayPanel();
		pane.add(displayPanel, BorderLayout.CENTER);
		
		ControlPanel controlPanel = new ControlPanel(displayPanel, pane, galleryPanel);
		pane.add(controlPanel, BorderLayout.EAST);
		
		
		
		
	}

}
