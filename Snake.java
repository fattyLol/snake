package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();

    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public Snake(int x, int y) {
        GameObject part1 = new GameObject(x, y);
        GameObject part2 = new GameObject(x + 1, y);
        GameObject part3 = new GameObject(x + 2, y);
        snakeParts.add(part1);
        snakeParts.add(part2);
        snakeParts.add(part3);
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();

        int appleX = apple.x;
        int appleY = apple.y;

        int newX = newHead.x;
        int newY = newHead.y;


        if (!checkCollision(newHead)){
            if (newX == appleX && newY == appleY) {
                apple.isAlive = false;
                snakeParts.add(0, newHead);
            } else {
                if (newX < 0 || newX >= SnakeGame.WIDTH || newY >= SnakeGame.HEIGHT || newY < 0) {
                    isAlive = false;
                } else {
                    snakeParts.add(0, newHead);
                    removeTail();
                }
            }
        } else {
            isAlive = false;
        }

    }

    public boolean checkCollision(GameObject gameObject) {

        int x = gameObject.x;
        int y = gameObject.y;

        for (GameObject object : snakeParts) {
            if (object.x == x && object.y == y) {
                return true;
            }
        }

        return false;
    }

    public int getLength() {
    return snakeParts.size();
    }

    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (i == 0) {
                if (isAlive) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.GREEN, 75);
                } else {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }

            } else {
                if (isAlive) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.GREEN, 75);
                } else {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
    }
   /* public void setDirection(Direction direcnew){
        if ( ((direction==Direction.RIGHT) || (direction==Direction.LEFT)) && (snakeParts.get(0).y==snakeParts.get(1).y) && ((direcnew==Direction.DOWN) || (direcnew==Direction.UP)) ) {
            this.direction = direcnew;
        }

        if ( ((direction==Direction.DOWN) || (direction==Direction.UP)) && (snakeParts.get(0).x==snakeParts.get(1).x) && ((direcnew==Direction.RIGHT) || (direcnew==Direction.LEFT)) ) {
            this.direction = direcnew;
        }
    }*/

    public void setDirection(Direction direction) {

        switch (direction) {
            case UP:
                if (this.direction.equals(Direction.DOWN)) {
                    break;
                }
                this.direction = direction;
                break;
            case DOWN:
                if (this.direction.equals(Direction.UP)) {
                    break;
                }
                this.direction = direction;
                break;
            case LEFT:
                if (this.direction.equals(Direction.RIGHT)) {
                    break;
                }
                this.direction = direction;
                break;
            case RIGHT:
                if (this.direction.equals(Direction.LEFT)) {
                    break;
                }
                this.direction = direction;
                break;
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public GameObject createNewHead() {

        GameObject newHead = null;

        switch (direction) {
            case UP:
                newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
                break;
            case DOWN:
                newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
                break;
            case LEFT:
                newHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
                break;
            case RIGHT:
                newHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
                break;
        }
        return newHead;
    }


}