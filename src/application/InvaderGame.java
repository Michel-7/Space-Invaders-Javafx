package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InvaderGame extends Pane{
	static int highscore=0;
	private int width=350;
	private int height=350;
	private double t=0;
	private AudioClip bulletShot;
	private AudioClip gameOverSound;
	private AudioClip enemyDestroyedSound;
	AnimationTimer timer;
	boolean isOver=false;
	int score=0;
	Sprite player;
	ArrayList<Sprite> spritesEnemy;
	
	
	public InvaderGame() {
		this.bulletShot = new AudioClip("file:///C:/Users/User/eclipse-workspace/i3305-project/assets/fireBullet.mp3");
		this.gameOverSound=new AudioClip("file:///C:/Users/User/eclipse-workspace/i3305-project/assets/gameOver.mp3");
		this.enemyDestroyedSound=new AudioClip("file:///C:/Users/User/eclipse-workspace/i3305-project/assets/enemyDestroyed.mp3");
		Image playerShip=new Image("C:/Users/User/eclipse-workspace/i3305-project/assets/spaceship.png");
		this.player=new Sprite(width/2,height,"player",playerShip);
		spritesEnemy=new ArrayList<Sprite>();
		this.timer=new AnimationTimer() {
			@Override
			public void handle(long now) {
				setHighScore(score);
				update();
				if(isEmpty(spritesEnemy)) {
					nextLevel();

					getChildren().add(player);
					startApp();
				}
				if(player.dead) {
					this.stop();
					gameOverSound.play();
					Stage g = new GameOver();
					((GameOver) g).display("Game Over!");
					g.setOnHidden(e->{
						if(((GameOver) g).back==2) {
						
							score=0;
							
							nextLevel();
							addNewPlayer();
							System.out.println("Reached back 2;");
							this.start();
						}else {
							isOver=true;
						}
					});
				}
				
			}
		};
		
		nextLevel();
		this.getChildren().add(player);
		startApp();
		timer.start();
	}
//	public void shotFiredSound() {
//		Media shotFired=new Media("https://rr4---sn-a5meknzr.googlevideo.com/videoplayback?expire=1646569720&ei=mFQkYvjWHIOQkgbA4YtI&ip=8.4.122.173&id=o-AOOjbcvVcpj4dfOGYxO6uz1upFlP61iB2GJN2NV0VXey&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ns=Xc6vtKkJgO3k6zQWaKeAMEAG&cnr=14&ratebypass=yes&dur=1.068&lmt=1628951009790776&fexp=24001373,24007246,24162927&c=WEB&txp=6211224&n=qDecX8BdByWHdDbJw&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhANQVHAzaU7QA16niv8IXj6d9nZxUGtguBA1R4dNq7u3HAiEA5lKGxac7015Ffy7naMyZxhdNUulnULYfpCObVKcNoZQ%3D&redirect_counter=1&cm2rm=sn-a5myy7z&req_id=2d32f0b0fd80a3ee&cms_redirect=yes&cmsv=e&mh=wq&mm=34&mn=sn-a5meknzr&ms=ltu&mt=1646548111&mv=m&mvi=4&pl=22&lsparams=mh,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgLfWxT1NVu5ju3Sz98I8o4NTT14h4pQCtmEHr-nBQjTgCIAUxeG--KxgkAEtrwrkIcXz2aplnwufupgqJT13gZREg");
//		MediaPlayer mediaPlayerOnShot=new MediaPlayer(shotFired);
//		
//		mediaPlayerOnShot.play();
//	}
	public void nextLevel() {
		this.getChildren().clear();
		for(int i=1;i<=4;i++) {
			Image ship=new Image("C:/Users/User/eclipse-workspace/i3305-project/assets/enemyship.png");
			Sprite enemy=new Sprite(i*width/4-10,height/4-50,"enemy",ship);
			spritesEnemy.add(enemy);
			this.getChildren().add(enemy);
		}
	}
	public void addNewPlayer() {
		Image ship=new Image("C:/Users/User/eclipse-workspace/i3305-project/assets/spaceship.png");

		player=new Sprite(width/2,height,"player",ship);
		InvaderGame.this.getChildren().add(player);
	}
	public boolean isEmpty(ArrayList<Sprite> spritesEnemy) {
		return spritesEnemy.isEmpty();
	}
	public void shoot(Sprite who) {
		Image bulletImage=new Image("C:/Users/User/eclipse-workspace/i3305-project/assets/bullet.png");
		Sprite bullet=new Sprite((int)who.getTranslateX()+16,(int)who.getTranslateY(),who.type+" bullet",bulletImage);
		this.getChildren().add(bullet);
		
		bulletShot.play();
	}
	public List<Sprite> sprites(){
		return this.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
		
	}
	public void setHighScore(int score) {
		if(score>highscore) {
			highscore=score;
		}
	}
	public void update() {
		t+=0.016;
		sprites().forEach(s->{
			switch(s.type) {
			
			case "enemy bullet":{
//				shotFiredSound();
				s.moveDown();
				if(s.getBoundsInParent().intersects(player.getBoundsInParent())) {
					player.dead=true;
					s.dead=true;
				}
				break;
			}
			case "player bullet":{
				s.moveUp();
				sprites().stream().filter(e->e.type.equals("enemy")).forEach(enemy->{
					if(s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
						enemy.dead=true;
						spritesEnemy.remove(enemy);
						s.dead=true;
					}
				});
				break;
			}
			case "enemy":{
				if(!s.dead) {
				if(t>2 ) {
					if(Math.random()<0.3) {
						shoot(s);
					}
				}
				break;
			}
			}
			}
		});
		this.getChildren().removeIf(n->{
			Sprite s=(Sprite) n;
			if(s.type.equals("enemy") && s.dead==true) {
				score+=1;
				spritesEnemy.remove(s);
				enemyDestroyedSound.play();
			}
			
			return s.dead;
			
		});
		if(t>2) {
			t=0;
		}
	}
	public InvaderGame newGame() {
		this.timer.stop();
		return new InvaderGame();
	}
	public void startApp() {
		this.setOnKeyPressed(e->{
			switch (e.getCode()) {
			case A:{
				
			}
			case LEFT:{
				player.moveLeft();
				break;
			}
			case D:{
				
			}
			case RIGHT:{
				player.moveRight();
				break;
			}
			case SPACE:{
				if(!this.player.dead) {
				shoot(this.player);
				break;
				}
			}
			}
			this.requestFocus();
			
		});
	}
	
	class Sprite extends ImageView{
		public boolean dead=false;
		public String type;
	 public Sprite(int x,int y,String type,Image image) {
		 super(image);
		 this.type=type;
		 setTranslateX(x);
		 setTranslateY(y);
	 }
	 void moveLeft() {
		 if(getTranslateX()<5) {
		 setTranslateX(width+19);
		 }else {
			 setTranslateX(getTranslateX()-5);
		 }
	 }
	 void moveRight() {

		 if(getTranslateX()>width+20) {
		 setTranslateX(0);
		 }else {
			 setTranslateX(getTranslateX()+5);
		 }	 }
	 void moveUp() {
		 setTranslateY(getTranslateY()-5);
	 }
	 void moveDown() {
		 setTranslateY(getTranslateY()+5);
	 }
	}

}
