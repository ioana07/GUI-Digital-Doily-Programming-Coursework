import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

// Defines the marks properties and methods (strokes) drawn on the DisplayPanel
public class Mark {
	
	private ArrayList<Point> listOfPoints;
	private Color color;
	private boolean reflected;
	private int penSize;
	private DisplayPanel dolly;
	
	//Initialise Mark's properties:
	public Mark (DisplayPanel panel) {
		
		listOfPoints = new ArrayList<>();
		this.color = panel.getColor();
		this.reflected = panel.isReflectDrawing();
		this.penSize = panel.getPenSize();
		dolly = panel;

	}
	
	// Draw a mark using Graphics2D:
	public void drawMark (Graphics g) {

		Graphics2D graph = (Graphics2D) g;

		graph.setColor(color);
		
		//For each sector draw the same mark
		for (int i = 0; i < dolly.getNumberOfSectors(); i++) {
			
			graph.setStroke(new BasicStroke(penSize));
			Iterator<Point> iter = listOfPoints.iterator();
			Point firstPoint = null; 
			Point secondPoint = null;
			
			
			if (listOfPoints.size() == 1) {
				
				firstPoint = listOfPoints.get(0);
				graph.drawRect((int) firstPoint.getX(), (int) firstPoint.getY(), dolly.getPenSize(), dolly.getPenSize());
				
				if (reflected) {  
					
					graph.drawRect(-(int) firstPoint.getX(), (int) firstPoint.getY(), dolly.getPenSize(), dolly.getPenSize());
				}
				graph.rotate(Math.toRadians(dolly.getAngle()));
				
			} else if (listOfPoints.size() > 1) { 
				
				firstPoint = iter.next();

				while (iter.hasNext()) {
					secondPoint = iter.next();

					graph.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(), (int) secondPoint.getX(), (int) secondPoint.getY());

					if (reflected) { 
						
						graph.drawLine(-(int) firstPoint.getX(), (int) firstPoint.getY(), -(int) secondPoint.getX(),
								(int) secondPoint.getY());
					}

					firstPoint = secondPoint;
				}
				graph.rotate(Math.toRadians(dolly.getAngle()));
			}
		}

	}
	
	//Add a point to the list of points:
	public void addPoint(Point point) {
		
		this.listOfPoints.add(point);
	}
	
	/*
	 * Getters and setters:
	 */

	public void setPenSize(int penSize) {
		
		this.penSize = penSize;
	}
	public ArrayList<Point> getListOfPoints() {
		
		return listOfPoints;
	}
	
	public Color getColor() {
		
		return color;
	}
	
	public void setColor(Color color) {
		
		this.color = color;
	}
	
	public void setReflected(boolean reflected) {
		
		this.reflected = reflected;
	}





}
