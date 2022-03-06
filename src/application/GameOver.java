package application;

import application.InvaderGame.Sprite;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameOver extends Stage{
	
	
	int back=0;
	
	public void display(String title) {
		this.initModality(Modality.APPLICATION_MODAL);
		this.setMinHeight(100);
		Label label=new Label();
		label.setText(title);
		Button newGame=new Button("New Game");
		newGame.setOnAction(e->{
			back=2;
			this.close();
		});
		Button closeWindow=new Button("Back to Main Menu");
		closeWindow.setOnAction(e->{
			back=1;
			this.close();
		});
		HBox layout1 = new HBox(10);
		layout1.getChildren().addAll(newGame,closeWindow);
		layout1.setAlignment(Pos.CENTER);
		VBox layout=new VBox(10);
		layout.getChildren().addAll(label,layout1);
		layout.setAlignment(Pos.CENTER);
		this.setScene(new Scene(layout,300,50));
		this.show();
	}
	
	
}
