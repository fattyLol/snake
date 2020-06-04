package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    private Snake snake;
    private Apple apple;
    private boolean isGameStopped;
    private int score;
    private static final int GOAL = 28;
    public static final int HEIGHT = 15;
    public static final int WIDTH = 15;
    private int turnDelay;

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        this.snake = new Snake(WIDTH / 2, HEIGHT / 2);
        isGameStopped = false;
        score = 0;
        turnDelay = 300;
        setScore(score);
        setTurnTimer(turnDelay);
        createNewApple();
        drawScene();
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i, j, Color.PINK, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        int x = getRandomNumber(WIDTH);
        int y = getRandomNumber(HEIGHT);

        apple = new Apple(x, y);
        while (snake.checkCollision(apple)) {
            x = getRandomNumber(WIDTH);
            y = getRandomNumber(HEIGHT);
            apple = new Apple(x, y);
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.BLACK, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.BLACK, 75);
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case SPACE:
                if (isGameStopped) {
                    createGame();
                }
        }
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();

    }
}