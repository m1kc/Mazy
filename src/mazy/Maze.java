/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m1kc
 */
public class Maze extends Thread implements Observable {
    
    int size;
    int[][] maze;
    LinkedList<Observer> observers;
    String stage = "idle";
    ArrayList<MazeWall> walls;

    public Maze(int size) {
        this.size = size;
        maze = new int[size][size];
        observers = new LinkedList<>();
        walls = new ArrayList<>();
    }

    @Override
    public void subscribe(Observer x) {
        observers.add(x);
    }

    @Override
    public void ring() {
        for (Observer x : observers)
        {
            x.ring();
        }
    }

    @Override
    public void run() {
        // This algorithm is a randomized version of Prim's algorithm.
        // 1. Start with a grid full of walls.
        stage = "init";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = -1;
            }
        }
        maze[1][0] = 0;
        this.ring();
        
        stage = "generating";
        this.ring();
        
        // 2. Pick a cell, mark it as part of the maze. Add the walls of the cell to the wall list.
        maze[1][1] = 0;
        createWalls(1, 1);
        this.ring();
        
        Random random = new Random();
        // 3. While there are walls in the list:
        while (walls.size() > 0)
        {
            // 3.1. Pick a random wall from the list.
            MazeWall w = walls.get(random.nextInt(walls.size()));
            // If the cell on the opposite side isn't in the maze yet:
            if (maze[w.nextX][w.nextY] == -1)
            {
                // 3.1.1. Make the wall a passage and mark the cell on the opposite side as part of the maze.
                maze[w.x][w.y] = 0;
                maze[w.nextX][w.nextY] = 0;
                // 3.2.2. Add the neighboring walls of the cell to the wall list.
                createWalls(w.nextX, w.nextY);
                
                this.ring();
                sleep();
            }
            // 3.2. Remove the wall from the list.
            walls.remove(w);
        }
        
        stage = "waving";
        this.ring();
        
        maze[1][0] = -2;
        maze[size-2][size-1] = 0;
        maze[1][1] = 1;
        this.ring();
        
        int waveNumber = 2;
        while(maze[size-2][size-2] == 0)
        {
            for (int i = 0; i < size; i++) 
            {
                for (int j = 0; j < size; j++) 
                {
                    if (maze[i][j] == waveNumber - 1)
                    {
                        if (maze[i-1][j] == 0) maze[i-1][j] = waveNumber;
                        if (maze[i+1][j] == 0) maze[i+1][j] = waveNumber;
                        if (maze[i][j-1] == 0) maze[i][j-1] = waveNumber;
                        if (maze[i][j+1] == 0) maze[i][j+1] = waveNumber;
                    }
                }
            }
            
            waveNumber++;
            
            this.ring();
            sleep();
        }
        
        stage = "reverse waving";
        this.ring();
        
        maze[size-2][size-1] = -2;
        maze[size-2][size-2] = -2;
        this.ring();
        
        int currentX = size-2;
        int currentY = size-2;
        waveNumber -= 2;
        
        while(waveNumber >= 1)
        {
            if (maze[currentX-1][currentY] == waveNumber)
            {
                maze[currentX-1][currentY] = -2;
                currentX = currentX-1;
                waveNumber--;
                this.ring();
                sleep();
                continue;
            }
            if (maze[currentX+1][currentY] == waveNumber)
            {
                maze[currentX+1][currentY] = -2;
                currentX = currentX+1;
                waveNumber--;
                this.ring();
                sleep();
                continue;
            }
            if (maze[currentX][currentY-1] == waveNumber)
            {
                maze[currentX][currentY-1] = -2;
                currentY = currentY-1;
                waveNumber--;
                this.ring();
                sleep();
                continue;
            }
            if (maze[currentX][currentY+1] == waveNumber)
            {
                maze[currentX][currentY+1] = -2;
                currentY = currentY+1;
                waveNumber--;
                this.ring();
                sleep();
                continue;
            }
        }
        
        stage = "cleanup";
        this.ring();
        
        for (int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++) 
            {
                if (maze[i][j] > 0) maze[i][j] = 0;
            }
        }
        this.ring();
        
        stage = "done";
        this.ring();
    }

    private void createWalls(int x, int y)
    {
        // left
        if (x >= 2)
        {
            walls.add(new MazeWall(x-1, y, x-2, y));
        }
        // up
        if (y >= 2)
        {
            walls.add(new MazeWall(x, y-1, x, y-2));
        }
        // right
        if (x <= size-3)
        {
            walls.add(new MazeWall(x+1, y, x+2, y));
        }
        // down
        if (y <= size-3)
        {
            walls.add(new MazeWall(x, y+1, x, y+2));
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
