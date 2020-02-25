package Application;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	Point2D X =new Point2D(200,700); 
	Point2D lastPosition = null;
	Point2D clickPoint;
	FlowerBed fl = new FlowerBed(100,100,250,250);
	Flower l = new Flower(X,Color.YELLOW,true);
	
	Garden presentShape;
	boolean inDragMode = false;
	List<Garden> shapes = new ArrayList<Garden>();
	@Override
	public void start(Stage primaryStage) {
			
		
			AnchorPane root = new AnchorPane();
			Scene scene = new Scene(root,1500,1500);
			scene.setFill(Color.SLATEBLUE);
			root.getChildren().addAll(fl.getRectangle(),l.getCircle());
			
			shapes.add(l);
			shapes.add(fl);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		
		
		EventHandler<MouseEvent> mouseHandler= new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent mouseEvent) {
			clickPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
		//S	System.out.println(clickPoint.getX()+" "+clickPoint.getY());
			
			String eventName = mouseEvent.getEventType().getName();
			if(!inDragMode){
				presentShape = getShape();
        	}
			switch(eventName) {
			case("MOUSE_DRAGGED"):
				
				if(shapes!=null&&lastPosition !=null) {
					
					try {
					
					inDragMode = true;
					
					double deltaX = clickPoint.getX()-lastPosition.getX();
					double deltaY = clickPoint.getY()-lastPosition.getY();
					presentShape.move(deltaX, deltaY);}
					
					
					catch(NullPointerException e) {
						
					}
				}
				
			break;
			case("MOUSE_RELEASED"):
				if(shapes!=null && presentShape instanceof Flower){
        			for(Garden shape: shapes){
            			if (shape instanceof FlowerBed && shape.contains(clickPoint)){
            				((FlowerBed)shape).addChild(presentShape);
            				
            				break;
            			}
            			
            			
            		}
			}
			inDragMode = false;
			break;
			}
			lastPosition = clickPoint;
		}
		};
		
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMouseReleased(mouseHandler);
		scene.setOnMousePressed(mouseHandler);
	}
	
	public Garden getShape() {
		presentShape =null;
		for(Garden shape : shapes) {
			if(shape.contains(clickPoint)) {
				presentShape =shape;
			}
		}
		return presentShape;
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}