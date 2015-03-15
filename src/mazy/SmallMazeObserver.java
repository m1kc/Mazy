/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import static mazy.BigMazeObserver.cellSize;

/**
 *
 * @author m1kc
 */
public class SmallMazeObserver implements Observer {

    JPanel canvas;
    Maze maze;
    static int cellSize = 1;

    public SmallMazeObserver(Maze maze, JPanel canvas) {
        this.maze = maze;
        this.canvas = canvas;
    }

    @Override
    public void ring() {
        Graphics gg = canvas.getGraphics();
        Image buffer = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                if (maze.maze[i][j] == -1)
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                }
                if (maze.maze[i][j] == -2)
                {
                    g.setColor(Color.RED);
                    g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                }
            }
        }
        gg.drawImage(buffer, 0, 0, null);
    }
    
}
