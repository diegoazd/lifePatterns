package com.life.pattern;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.charset.StandardCharsets;

public class FindLiveCells {
  
  private boolean isEndOfArray(int currentPosition, int endArray) {
    return currentPosition == endArray - 1;
  }

  private boolean isStartingArray(int currentPosition) {
    return currentPosition == 0;
  }
  
  private boolean isCandidateForLIve(int neighbourCells) {
    return neighbourCells > 1  && neighbourCells  < 4;
  }

  private boolean isCandidateForRevive(int neighbourCells) {
    return neighbourCells == 3;
  }

  public char[][] obtainLifePattern(char cells[][]) {
    char[][] nextGeneration = cells;
    for(int i = 0; i < 10; i++) {
      nextGeneration = findLiveCells(nextGeneration);
    }
    return nextGeneration;
  }

  public char[][] findLiveCells(char cells[][]) {
    char[][] nextGeneration = new char[cells.length][cells[0].length];
    for(int i =0; i < cells.length; i++) {
      for(int j =0; j < cells[i].length; j++) {
        int neigbourLiveCells = findNeighbourLiveCells(formMatrix(cells, i, j));
        nextGeneration[i][j] = returnCurrentCell(cells[i][j], neigbourLiveCells);
      }
    }
    return nextGeneration;
  }

  public int findNeighbourLiveCells(char cells[][]) {
    int neigbourLiveCells = 0;
    cells[1][1] = '.';
    for(int i=0; i < cells.length;i++) 
      for(int j =0; j < cells[i].length; j++) 
        if(cells[i][j] == '*')
          neigbourLiveCells++;
    return neigbourLiveCells;
  }

  public char[][] formMatrix(char cells[][], int positionX, int positionY) {
    int initialX = positionX - 1;
    int initialY = positionY - 1;
    int finalX = positionX + 1;
    int finalY = positionY + 1;
    int carryX = 0;
    int carryY = 0;


    if(isEndOfArray(positionX, cells.length)) {
      finalX = positionX;
      carryX = 0;
    }
    
    if(isEndOfArray(positionY, cells[positionX].length)) {
      finalY = positionY;
      carryY = 0;
    }
    
    if(isStartingArray(positionX)) {
      initialX = 0;
      carryX = 1;
    }
    
    if(isStartingArray(positionY)) {
      initialY = 0;
      carryY = 1;
    }  
      
    return obtainSubArrayWithNeighbours(initialX, initialY, finalX,
        finalY, carryX, carryY, cells);  
  }

  private char[][] obtainSubArrayWithNeighbours(int initialX, int initialY,
      int finalX, int finalY, int carryX, int carryY, char[][] cells) {
    char[][] neighbourCells = new char[3][3];

    int subArrayX = 0 + carryX;
    for(int x = initialX; x <= finalX;x++) {
      int subArrayY = 0 + carryY;
      for(int y = initialY; y <= finalY;y++) {
        neighbourCells[subArrayX][subArrayY] = cells[x][y];    
        subArrayY++;
      }
      subArrayX++;
    }

    return neighbourCells;
  }
  
  public char returnCurrentCell(char currentCell, int neighbourCells) {
    if(currentCell == '*') {
      return cellPopulationRules(neighbourCells);
    }else {
      return reviveCell(neighbourCells); 
    }
  }

  private char cellPopulationRules(int neighbourCells) {
    if (isCandidateForLIve(neighbourCells)) {
      return '*';
    }else {
      return '.';
    }
  }

  private char reviveCell(int neighbourCells) {
    if (isCandidateForRevive(neighbourCells)) {
      return '*';
    }else {
      return '.';
    }
  }
}
