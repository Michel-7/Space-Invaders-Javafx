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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class InvaderGame extends Pane{
	private int width=350;
	private int height=350;
	private double t=0;
	AnimationTimer timer;
	boolean isOver=false;
	int score=0;
	Sprite player;
	List<Sprite> spritesEnemy=new ArrayList<Sprite>();
	
	
	public InvaderGame() {
		
		this.player=new Sprite(width/2,height,30,30,"player",Color.BLUE);
		this.timer=new AnimationTimer() {
			@Override
			public void handle(long now) {
				
				update();
				if(isEmpty()) {
					nextLevel();
				}
				if(player.dead) {
					this.stop();
					Stage g = new GameOver();
					((GameOver) g).display("Game Over!");
					g.setOnHidden(e->{
						if(((GameOver) g).back==2) {
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
		for(int i=1;i<=4;i++) {
			Sprite enemy=new Sprite(i*width/4-10,height/4-50,30,30,"enemy",Color.RED);
			spritesEnemy.add(enemy);
			this.getChildren().add(enemy);
		}
	}
	public void addNewPlayer() {
		player=new Sprite(width/2,height,30,30,"player",Color.BLUE);
		InvaderGame.this.getChildren().add(player);
	}
	public boolean isEmpty() {
		return spritesEnemy.isEmpty();
	}
	public void shoot(Sprite who) {
		Sprite bullet=new Sprite((int)who.getTranslateX()+15,(int)who.getTranslateY(),7,7,who.type+" bullet",Color.GOLD);
		this.getChildren().add(bullet);
	}
	public List<Sprite> sprites(){
		return this.getChildren().stream().map(n->(Sprite)n).collect(Collectors.toList());
		
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
//				shotFiredSound();
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
				if(t>2 || (t>=0.032 && t<0.064)) {
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
			}
			if(s.dead && (!s.type.equals("enemy bullet") || !s.type.equals("player bullet"))) {
				Media sound=new Media("https://rr4---sn-a5mekn6d.googlevideo.com/videoplayback?expire=1646570595&ei=A1gkYoPmB4_XkgaA8K-4DA&ip=8.4.122.173&id=o-AM8KU6-6C3NwvK4K4TV2zew4Ral7kQoyusYH9h4PBmae&itag=22&source=youtube&requiressl=yes&mh=gw&mm=31%2C26&mn=sn-a5mekn6d%2Csn-o097znz7&ms=au%2Conr&mv=m&mvi=4&pl=22&initcwndbps=1003750&vprv=1&mime=video%2Fmp4&ns=iMpiDponZg3nam3926dPYZoG&ratebypass=yes&dur=0.928&lmt=1540171742075258&mt=1646548611&fvip=4&fexp=24001373%2C24007246&c=WEB&txp=2211222&n=ox_DOIV39rb_k96Bn&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cns%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRQIgP--vmzg3xqKYIp3LM3zzUYl0gEYVtpzkSvpXhxWNpX0CIQCwfmLfGWUb0lani5nNsvB55wdcPYpeXCV7xFucohVTqg%3D%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIgEHKZQ8emucwHlYL8z7Xk4QyWJm2_OJw0QF5p4g8M9tsCIQDDsw-LizUOQ_V01ENX3T_wNJ6I7JtmHP3whjePVH1-NA%3D%3D");
				MediaPlayer msound=new MediaPlayer(sound);
				msound.play();
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
	
	class Sprite extends Rectangle{
		public boolean dead=false;
		public String type;
	 public Sprite(int x,int y,int w,int h,String type,Color color) {
		 super(w,h,color);
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
