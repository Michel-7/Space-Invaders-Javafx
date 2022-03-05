package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SampleController {
	@FXML
	public void handleBtnControls(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
	   
	    GridPane loader = FXMLLoader.load(getClass().getResource("Controls.fxml"));

	    Scene scene = new Scene(loader,400,400); 
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	}
	@FXML
	public void handleBtnBack(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
	    
	    BorderPane loader = FXMLLoader.load(getClass().getResource("Menu.fxml"));

	    Scene scene = new Scene(loader,400,400); 
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	}
	
	//game operations
	
	private Sprite shoot(Sprite s) {
		Sprite bullet=new Sprite((int)s.getTranslateX()+20,(int)s.getTranslateY(),5,20,s.type+"bullet",Color.GREEN);
		return bullet;
	}
	
	@FXML
	public void handleBtnPlay(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
		//creating main player
		Sprite player =new Sprite(160,360,40,40,"player",Color.BLUE);
	
		
	    Pane loader = FXMLLoader.load(getClass().getResource("Game.fxml"));
		//add enemies and main player
	    loader.getChildren().add(player);
		 for(int i=0;i<4;i++) {
   	Sprite enemy=new Sprite(50+i*100,50,30,30,"enemy",Color.RED);
   	loader.getChildren().add(enemy);
   }
	   
	    Scene scene = new Scene(loader,400,400); 
	    //listen for actions
		scene.setOnKeyPressed(e->{
    	switch(e.getCode()) {
    	case A:
    		player.moveLeft();
    		break;
    	case D:
    		player.moveRight();
    		break;
    	case SPACE:
    		loader.getChildren().add(shoot(player));
    		
    	}
    });
	    
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	    
	}
	//class sprite
	private  class Sprite extends Rectangle{
	boolean dead=false;
	 String type;
	 
	 public Sprite(int x,int y,int w,int h,String type,Color color) {
		 super(w,h,color);
		 this.type=type;
		 setTranslateX(x);
		 setTranslateY(y);
	 }
	 void moveLeft() {
		 setTranslateX(getTranslateX()-5);
	 }
	 void moveRight() {
		 setTranslateX(getTranslateX()+5);
	 }
	 void moveUp() {
		 setTranslateY(getTranslateY()-5);
	 }
	 void moveDown() {
		 setTranslateY(getTranslateY()+5);
	 }
}
	
	
}
