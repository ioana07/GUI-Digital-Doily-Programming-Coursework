import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/*
 * Handles the methods and properties of the drawing area (the doily):
 */
public class DisplayPanel extends JPanel {

	
	//Setting a few initial parameters:
	
	private static final long serialVersionUID = 1L;
	private int penSize = 4;
	private Color color = Color.WHITE;
	private int numberOfSectors = 6;
	private double angle = 360 / numberOfSectors;
	private boolean showSectorLines = true;
	private boolean reflectDrawing = false;
	public BufferedImage paint;
	private Color lastColor;
	private boolean eraser = false;
	

	public Mark pointList = new Mark(this);
	public ArrayList<Mark> marks = new ArrayList<Mark>();
	private ArrayList<Mark> rememberedMarks = new ArrayList<Mark>();

	
	public DisplayPanel() {
		
		super();
		this.setBackground(Color.GRAY);
		paint = new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
		color = Color.WHITE;
		
		/*
		 * Adding mouse listeners so that the drawn points are stored in a point list
		 */

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				addPoint(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			//Once the mouse is released a mark is created using the list of points:
			public void mouseReleased(MouseEvent e) {
				
				marks.add(pointList);
				pointList = new Mark(DisplayPanel.this);
			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		
		/*
		 * Adding mouse motion listener:
		 */

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				addPoint(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});

	}
	
	//Invoke the public void paint (Graphics g) method in the superclass so that the panel can be cleared: 
	public void paint(Graphics g) {
		
		super.paint(g);
	}

	//Painting the doily:
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		Graphics2D graph = (Graphics2D) g;

		//Setting some parameters 
		graph.setColor(Color.BLACK);
		graph.translate(this.getWidth()/2, this.getHeight()/2);
		graph.setStroke(new BasicStroke(penSize));
		graph.setColor(Color.BLACK);

		//Drawing all the marks in the marks array list by calling the drawMark() method:
		for (int i = 0; i < marks.size(); i++) {
			
			marks.get(i).drawMark(graph);
		}
		
		pointList.drawMark(graph);
		
		//Drawing the sector lines if this option is selected:
		if (showSectorLines == true)
			
			for (int i = 0; i < numberOfSectors; i++) {
				
				graph.setStroke(new BasicStroke(1));
				graph.setColor(Color.BLACK);// sets the colour of the sector lines to black
				graph.drawLine(0, 0, 0, -360);
				graph.rotate(Math.toRadians(angle));// rotates the graph so that there is an equal angle between all lines
				
			}
		
		

	}
	
	//Add points to the list of points in a symmetric way:
	public void addPoint(MouseEvent e) {
		
		int positionY = e.getY() - getHeight()/2;
		int positionX = e.getX() - getWidth()/2;
		
		if (numberOfSectors % 2 == 0) 
			
			pointList.addPoint(new Point(-positionX, -positionY));
		 else 
			
			pointList.addPoint(new Point(positionX, positionY));
		
		repaint();
	}
	
	//Make a capture of the doilies:
	public BufferedImage getCapture(JPanel panel) {
		
		BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		panel.paint(image.getGraphics());
		return image;
	}

	//Remove all the marks for this doily:
	public void clear() {
		
		this.removeMarks();
	}
	
	/*
	 * Removes the drawn marks as many times as the undo button is clicked and remembers the marks
	 * in the rememberedMarks ArrayList 
	 */
	
	public void undo() {
		
		int index = marks.size();
		
		if(index > 0) {
			rememberedMarks.add(marks.get(index-1));
			this.removeMarks(index-1);
		}
			
	}
	/*
	* Redraws the undone marks by adding the remembered marks to the initial mark list
	* and removes the redrawn mark from the remembered list
	*/
	public void redo() {
		
		int index = rememberedMarks.size();
		
		if(index > 0) {
			marks.add(rememberedMarks.get(index-1));
			repaint();
			rememberedMarks.remove(index-1);
		}
		
	}
	
	//Clears the marks list for this doily:
	public void removeMarks() {
		this.marks.clear();
	}

	//Removes a specific mark from the list for this doily:
	public void removeMarks(int index) {
		this.marks.remove(index);
	}
	
	/*
	 * Getters and setters:
	 */
	public int getPenSize() {
		
		return penSize;
	}

	public void setPenSize(Integer penSize) {
		
		this.penSize = penSize;
	}

	public Color getColor() {
		
		return color;
	}

	public void setColor(Color color) {
		
		this.color = color;
	}

	public int getNumberOfSectors() {
		
		return numberOfSectors;
	}

	public void setNumberOfSectors(int numberOfSectors) {
		
		this.numberOfSectors = numberOfSectors;
	}

	public double getAngle() {
		
		return angle;
	}

	public void setAngle(double angle) {
		
		this.angle = angle;
	}

	public boolean showSectorLines() {
		
		return showSectorLines;
	}

	public void setSectorLines(boolean showSectorLines) {
		
		this.showSectorLines = showSectorLines;
	}

	public boolean isReflectDrawing() {
		
		return reflectDrawing;
	}

	public void setReflectDrawing(boolean reflectDrawing) {
		
		this.reflectDrawing = reflectDrawing;
	}

	public BufferedImage getPaint() {
		
		return paint;
	}

	public void setPaint(BufferedImage paint) {
		
		this.paint = paint;
	}

	public Mark getPointList() {
		
		return pointList;
	}

	public void setPointList(Mark pointList) {
		
		this.pointList = pointList;
	}

	public void setPointListColour(Color color) {
		
		this.pointList.setColor(color);
	}

	public void setPenSizePointList(int size) {
		
		this.pointList.setPenSize(size);
	}

	public void setRefPointList(boolean reflection) {
		
		this.pointList.setReflected(reflection);
	}

	public ArrayList<Mark> getMarks() {
		
		return marks;
	}

	public void setMarks(ArrayList<Mark> marks) {
		
		this.marks = marks;
	}

	
	public void setLastColor(Color color) {
		
		if(this.color != this.getBackground())
		   this.lastColor = color;
	}
	public Color getLastColor() {
		
		return lastColor;
	}
	
	public boolean isErased () {
		
		return eraser;
	}
	
	public void setErased (boolean eraser) {
		
		this.eraser = eraser;
	}
	
	
	

	
	
	

	

}
