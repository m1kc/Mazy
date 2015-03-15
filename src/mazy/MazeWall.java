/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazy;

/**
 *
 * @author m1kc
 */
public class MazeWall {
    int x, y;
    int nextX, nextY;

    public MazeWall(int x, int y, int nextX, int nextY) {
        this.x = x;
        this.y = y;
        this.nextX = nextX;
        this.nextY = nextY;
    }
}
