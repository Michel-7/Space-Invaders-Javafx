package application;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SampleController {
//	Media sound=new Media("https://youtu.be/2ouCipxhCrM");
//	MediaPlayer mSound=new MediaPlayer(sound);
//	
	@FXML
	public void handleBtnControls(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
//		mSound.play();
	    GridPane loader = FXMLLoader.load(getClass().getResource("Controls.fxml"));

	    Scene scene = new Scene(loader,400,400); 
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	}
	@FXML
	public void handleBtnQuit(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();   
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
	

	
	@FXML
	public void handleBtnPlay(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
		BorderPane bp=new BorderPane();
		InvaderGame game=new InvaderGame();
		
		Label label=new Label();
		label.setId("scoreLabel");
		label.setText("Score: 0");
		Label labelHigh=new Label();
		labelHigh.setId("highScoreLabel");
		labelHigh.setText("Highscore: "+InvaderGame.highscore);
		HBox labelContainer=new HBox(10);
		labelContainer.getChildren().addAll(label,labelHigh);
		labelContainer.setAlignment(Pos.CENTER);
		bp.setTop(labelContainer);
		bp.setAlignment(labelContainer, Pos.TOP_LEFT);
		bp.setCenter(game);
		Image img=new Image("https://ak.picdn.net/shutterstock/videos/1055070605/thumb/1.jpg");
		BackgroundImage bgImg=new BackgroundImage(img,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,false));
		Background bg=new Background(bgImg);
		bp.setBackground(bg);
	    Scene scene = new Scene(bp,400,400);    
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	    game.startApp();
	    game.requestFocus();
	    AnimationTimer timer=new AnimationTimer() {

			@Override
			public void handle(long now) {
				label.setText("Score: "+game.score);
				labelHigh.setText("Highscore: "+InvaderGame.highscore);
				if(game.isOver) {
					
					this.stop();
					BorderPane loader;
					try {
						loader = FXMLLoader.load(getClass().getResource("Menu.fxml"));
						 Scene scene1 = new Scene(loader,400,400); 
						    scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
							app_stage.setScene(scene1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				   
				}
			}
	    };
	    timer.start();
	   
	}
	
}
