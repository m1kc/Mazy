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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author m1kc
 */
public class BigMazeObserver implements Observer {

    JPanel canvas;
    JLabel status;
    JButton lock;
    Maze maze;
    static int cellSize = 15;

    public BigMazeObserver(Maze maze, JPanel canvas, JLabel status, JButton lock) {
        this.maze = maze;
        this.canvas = canvas;
        this.status = status;
        this.lock = lock;
    }
    
    @Override
    public void ring() {
        status.setText(maze.stage);
        lock.setEnabled(maze.stage.equals("done"));
        
        Graphics gg = canvas.getGraphics();
        Image buffer = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < maze.size; i++) {
            for (int j = 0; j < maze.size; j++) {
                //g.setColor(Color.BLACK);
                //g.drawRect(i*cellSize, j*cellSize, cellSize, cellSize); // todo: throw out
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
                if (maze.maze[i][j] > 0)
                {
                    int whiteness = 255-maze.maze[i][j]*3;
                    if (whiteness < 0) whiteness = 0;
                    g.setColor(new Color(255, 255, whiteness));
                    g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                    g.setColor(Color.RED);
                    g.drawString(String.valueOf(maze.maze[i][j]), i*cellSize, (j+1)*cellSize);
                }
            }
        }
        gg.drawImage(buffer, 0, 0, null);
    }
    
}
