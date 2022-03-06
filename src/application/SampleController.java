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
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SampleController {
	Media sound=new Media("https://rr2---sn-a5mekned.googlevideo.com/videoplayback?expire=1646570783&ei=v1gkYsuxI9OpkgaevYToBA&ip=8.4.122.173&id=o-ADQUq8V3-VaG95vVGEKRQOpSmp6Jk_SJT_20g1nOj4VH&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=m6-MAmKdwlr1eRc1KsBt5nsG&cnr=14&ratebypass=yes&dur=0.394&lmt=1615133053759875&fexp=24001373,24007246&c=WEB&txp=6211222&n=mO08kH6nLrt37lM84&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgT6nYtFQ24L19lEL2pu7XX8pv3HeSUS6J5_ax594AQ0UCIDHndPYqg6uCmkzI2bl_oYmT05nOScuk7QFUMNk1nTZm&redirect_counter=1&cm2rm=sn-a5mr776&req_id=a757d9b0a2cea3ee&cms_redirect=yes&cmsv=e&mh=Nd&mm=34&mn=sn-a5mekned&ms=ltu&mt=1646549076&mv=m&mvi=2&pl=22&lsparams=mh,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgOH_bLv4L0_2lyznScdJGYP2AL8XCM3fARBL7ESFs_iICIDtFJTitwbUf9JlvoicVUMnwZQ-w9c9z9hYHX38vBxTK");
	MediaPlayer mSound=new MediaPlayer(sound);
	
	@FXML
	public void handleBtnControls(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
		mSound.play();
	    GridPane loader = FXMLLoader.load(getClass().getResource("Controls.fxml"));

	    Scene scene = new Scene(loader,400,400); 
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); 

	    app_stage.setScene(scene); 

	    app_stage.show(); 
	}
	@FXML
	public void handleBtnQuit(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{mSound.play();
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();   
	}
	@FXML
	public void handleBtnBack(ActionEvent event) throws IOException //This method loads a new scene in a current window
	{
		mSound.play();
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
	{mSound.play();
		BorderPane bp=new BorderPane();
		InvaderGame game=new InvaderGame();
		
		Label label=new Label();
		label.setId("scoreLabel");
		label.setText("Score: 0");
		bp.setTop(label);
		bp.setAlignment(label, Pos.TOP_LEFT);
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
