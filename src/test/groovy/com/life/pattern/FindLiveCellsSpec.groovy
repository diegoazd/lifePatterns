package com.life.pattern;

import spock.lang.*;

class FindLiveCellsSpec extends spock.lang.Specification{
  @Shared char[][]cells = new char[4][4]
  @Shared char[][]slimCells = new char[3][3]
  @Shared char[][] matrix = new char[5][5]
  def setup(){
    cells[0][0] = '.'
    cells[0][1] = '*'
    cells[0][2] = '.'
    cells[0][3] = '.'
    cells[1][0] = '*'
    cells[1][1] = '*'
    cells[1][2] = '.'
    cells[1][3] = '.'
    cells[2][0] = '.'
    cells[2][1] = '.'
    cells[2][2] = '.'
    cells[2][3] = '.'
    cells[3][0] = '.'
    cells[3][1] = '.'
    cells[3][2] = '.'
    cells[3][3] = '.'
    
    slimCells[0][0] = '*'
    slimCells[0][1] = '.'
    slimCells[0][2] = '*'
    slimCells[1][0] = '*'
    slimCells[1][1] = '*'
    slimCells[1][2] = '*'
    slimCells[2][0] = '.'
    slimCells[2][1] = '.'
    slimCells[2][2] = '.'
    
    for(int i=0; i < matrix.size(); i++) {
      for(int j=0; j < matrix[i].size(); j++) 
        matrix[i][j] = '.'
    }
  }

  def void "testing live cells"() {
  setup:
    FindLiveCells findLiveCells = new FindLiveCells()
  when:
    def liveCells = findLiveCells.findLiveCells(cells)
  then:
    assert true
  }

  def void "find neighbour live cells"() {
  setup:
    FindLiveCells findLiveCells = new FindLiveCells()
  when:
    def countCells = findLiveCells.findNeighbourLiveCells(slimCells)
  then:
    assert countCells == 4
  }

  def void "should form matrix with minum values"() {
  setup:
    char[][] minimumMatrix = new char[1][1]
    minimumMatrix[0][0] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def neighbourCells = findLiveCells.formMatrix(minimumMatrix,0,0)
  then:
    neighbourCells[1][1] == '*'
    !neighbourCells[0][0]
    !neighbourCells[0][1]
    !neighbourCells[0][2]
    !neighbourCells[1][0]
    !neighbourCells[1][2]
    !neighbourCells[2][0]
    !neighbourCells[2][1]
    !neighbourCells[2][2]
  }
  
  def void "should form matrix since initial array"() {
  setup:
    matrix[0][1] = '*'
    matrix[1][0] = '*'
    matrix[0][0] = '*'
    matrix[1][1] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def neighbourCells = findLiveCells.formMatrix(matrix,0,0)
  then:
    !neighbourCells[0][0]
    !neighbourCells[0][1]
    !neighbourCells[0][2]
    !neighbourCells[1][0]
    neighbourCells[1][2] == '*'
    !neighbourCells[2][0]
    neighbourCells[2][1] == '*'
    neighbourCells[2][2] == '*'
    neighbourCells[1][1] == '*'
  }
  
  def void "should form matrix since right side"() {
  setup:
    matrix[0][3] = '*'
    matrix[0][4] = '*'
    matrix[1][3] = '*'
    matrix[1][4] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def neighbourCells = findLiveCells.formMatrix(matrix,0,4)
  then:
    !neighbourCells[0][0]
    !neighbourCells[0][1]
    !neighbourCells[0][2]
    neighbourCells[1][0] == '*'
    !neighbourCells[1][2]
    neighbourCells[2][0] == '*'
    neighbourCells[2][1] == '*'
    !neighbourCells[2][2]
    neighbourCells[1][1] == '*'
  }
  
  def void "should form matrix since right bottom corner"() {
  setup:
    matrix[3][3] = '*'
    matrix[4][4] = '*'
    matrix[4][3] = '*'
    matrix[3][4] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def neighbourCells = findLiveCells.formMatrix(matrix,4,4)
  then:
    neighbourCells[0][0] == '*'
    neighbourCells[0][1] == '*'
    !neighbourCells[0][2]
    neighbourCells[1][0] == '*'
    !neighbourCells[1][2]
    !neighbourCells[2][0]
    !neighbourCells[2][1]
    !neighbourCells[2][2]
    neighbourCells[1][1] == '*'
  }
  
  def void "should form matrix since left bottom corner"() {
  setup:
    matrix[4][0] = '*'
    matrix[4][1] = '*'
    matrix[3][0] = '*'
    matrix[3][1] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def neighbourCells = findLiveCells.formMatrix(matrix,4,0)
  then:
    !neighbourCells[0][0]
    neighbourCells[0][1] == '*'
    neighbourCells[0][2] == '*'
    !neighbourCells[1][0]
    neighbourCells[1][2] == '*'
    !neighbourCells[2][0]
    !neighbourCells[2][1]
    !neighbourCells[2][2]
    neighbourCells[1][1] == '*'
  }

  def void "should determinate cell rules"() {
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def currentCell = findLiveCells.cellPopulationRules(neighbourCells)
  then:
    currentCell == cell
  where:
    neighbourCells      |       cell
      1                 |       '.'
      2                 |       '*'
      3                 |       '*'
      4                 |       '.'
  }

  def void "should determinate if a cell revive"() {
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def currentCell = findLiveCells.reviveCell(neighbourCells)
  then:
    currentCell == cell
  where:
    neighbourCells      |       cell
      2                 |       '.'
      3                 |       '*'
      4                 |       '.'
  }

  def void "should get current cell applying rules"() {
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def currentCell = findLiveCells.returnCurrentCell(cell, neighbourCells)
  then:
    currentCell == response
  where:
    cell        |       neighbourCells      |       response
    '*' as char |       2                   |       '*'
    '*' as char |       3                   |       '*'
    '*' as char |       4                   |       '.'
    '.' as char |       3                   |       '*'
    '.' as char |       2                   |       '.'
  }

  def void "should execute life patterns"() {
  setup:
    char[][] cells = new char[10][10]
    for(int i=0; i < 10; i++) {
      for(int j=0; j < 10; j++) {
        cells[i][j] = '.'
      }
    }
    cells[0][9] = '*'
    cells[1][1] = '*'
    cells[1][3] = '*'
    cells[1][7] = '*'
    cells[3][2] = '*'
    cells[3][4] = '*'
    cells[3][9] = '*'
    cells[4][1] = '*'
    cells[4][4] = '*'
    cells[4][8] = '*'
    cells[5][9] = '*'
    cells[7][5] = '*'
    cells[7][8] = '*'
    cells[8][1] = '*'
    cells[8][6] = '*'
    cells[9][5] = '*'
    cells[9][6] = '*'
  when:
    FindLiveCells findLiveCells = new FindLiveCells()
    def lifePatternCells = findLiveCells.obtainLifePattern(cells)
  then:
    assert lifePatternCells
    assert lifePatternCells[1][3] == '*'
    assert lifePatternCells[2][2] == '*'
    assert lifePatternCells[2][4] == '*'
    assert lifePatternCells[3][2] == '*'
    assert lifePatternCells[3][4] == '*'
    assert lifePatternCells[4][3] == '*'
  }
}
