package com.pong;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application{
	//Variables
	private static final int width= 800;
	private static final int height = 600;
	private static final int PLAYER_HEIGHT = 100;
	private static final int PLAYER_WIDTH = 15;
	private static final double BALL_R = 15;
	private int ballYSpeed = 1;
	private int ballXSpeed = 1;
	private double playerOneYPos = height/2;
	private double playerTwoYPos = height/2;
	private double ballXPos = width/2;
	private double ballYPos = width/2;
	private int scoreP1 = 0;
	private int scoreP2 = 0;
	private boolean gameStarted;
	private double playerOneXPos = 0;
	private double playerTwoXPos = width - PLAYER_WIDTH;

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		stage.setTitle("P-O-N-G");
		Canvas canvas = new Canvas(width, height);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e->run(gc)));
		t1.setCycleCount(Timeline.INDEFINITE);
		
		//mouse control
		canvas.setOnMouseMoved(e-> playerOneYPos = e.getY());
		canvas.setOnMouseClicked(e -> gameStarted = true);
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.show();
		t1.play();
		
		
		
		
		
		
	}
private void run(GraphicsContext gc) {
	//set background color
	gc.setFill(Color.BLACK);
	gc.fillRect(0, 0, width, height);
	
	// set text color
	gc.setFill(Color.WHITE);
	gc.setFont(Font.font(25));
	
	if(gameStarted) {
		//set ball movement
		ballXPos+= ballXSpeed;
		ballYPos += ballYSpeed;
	
	
	// Computer play
		if(ballXPos < width - width/4) {
			playerTwoYPos = ballYPos - PLAYER_HEIGHT/2;
		}else {
			playerTwoYPos = ballYPos > playerTwoYPos + PLAYER_HEIGHT / 2
					?playerTwoYPos += 1: playerTwoYPos-1;
		}
	
	// make ball
		gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
	
		} else {
			//set the Start Text
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			gc.strokeText("Start On Click", width/2, height/2);
			
			//Reset the ball
			ballXPos = width/2;
			ballYPos = height/2;
			
			
			//reset speed and direction
			ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
			ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;	
	}
	
	//ball Stays on Canvas
	if(ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
	
	//Computer gets a point
	if(ballXPos < playerOneXPos - PLAYER_WIDTH) {
		scoreP2++;
		gameStarted = false;
	}
	
	//Player gets a point
	if(ballXPos > playerTwoXPos + PLAYER_WIDTH) {
		scoreP1++;
		gameStarted = false;
	}
	
	//increase ball speed
	if( ((ballXPos + BALL_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + PLAYER_HEIGHT)||
	((ballXPos < playerOneXPos + PLAYER_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + PLAYER_HEIGHT)) {
		ballYSpeed += 1 *Math.signum(ballYSpeed);
		ballXSpeed += 1 *Math.signum(ballXSpeed);
		ballXSpeed *= -1;
		ballYSpeed *= -1;
	}
	
	
	//Draw Score
	gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t " + scoreP2, width/2, 100);
	
	//Draw players
	gc.fillRect(playerTwoXPos,playerTwoYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
	gc.fillRect(playerOneXPos,playerOneYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
}

public static void main(String[] args) {
	launch(args);
}
	
	
}
