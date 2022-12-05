package com.example.tetris;

import java.util.ArrayList;

public class TetrisState {
    BoardBlock[][] board;
    BoardBlock[][] boardStored;
    int standOutBlocks;
    int rows;
    int columns;
    ArrayList<TetrisFigure> figuresList;
    int figureCnt;
    TetrisFigure activeFig;
    TetrisFigure storedFig;
    boolean alreadyStored;
    boolean paused;
    boolean gameOver;
    int score;

    TetrisState(int r, int c){
        standOutBlocks = 3;
        rows = r+standOutBlocks;
        columns = c;
        board = new BoardBlock[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = new BoardBlock(i, j);
            }
        }

        int storedBoardRows = 4;
        int storedBoardColumns = 3;
        boardStored = new BoardBlock[storedBoardRows][storedBoardColumns];
        for (int i = 0; i < storedBoardRows; i++) {
            for (int j = 0; j < storedBoardColumns; j++) {
                boardStored[i][j] = new BoardBlock(i, j);
            }
        }

        figureCnt = 1;
        figuresList = new ArrayList<>();
        activeFig = new TetrisFigure(TetrisFigure.Figure.getRandomFigure(), figureCnt, this, false);
        figuresList.add(activeFig);
        storedFig = null;
        alreadyStored = false;
        paused = false;
        gameOver = false;
        score = 0;
    }

    private BoardBlock getCoordinateBlock(Point pos) {
        return this.board[pos.y][pos.x];
    }

    public boolean isConflicting(Point pos) {
        if (pos.x < 0 || pos.x >= columns || pos.y < 0 || pos.y >= rows)
            return true;
        return getCoordinateBlock(pos).state == BoardBlock.BlockState.FILLED;
    }

    public boolean rotatingBlockIsConflicting(Point pos) {
        if(pos.y < 0 || pos.y >= rows)
            return true;
        if (pos.x >= 0 && pos.x < columns && pos.y >= 0 && pos.y < rows)
            return getCoordinateBlock(pos).state == BoardBlock.BlockState.FILLED;
        return false;
    }

    public boolean gamePaused(){
        return paused;
    }

    public void pauseGame(boolean b){
        paused = b;
    }

    private boolean validFigureMove(TetrisFigure tf, Point displacement) {
        if(!paused) {
            for (BoardBlock block : tf.figBlocks) {
                if (block.state == BoardBlock.BlockState.FILLED) {
                    Point shifted = Point.add(block.position, displacement);
                    if (isConflicting(shifted)) {
                        return false;
                    }
                    if (displacement.x != 0 && displacement.y == 0) {
                        for (int i = block.position.x + 1; i <= block.position.x + displacement.x; ++i) {
                            if (getCoordinateBlock(new Point(i, block.position.y)).state == BoardBlock.BlockState.FILLED) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private int calculateShift(TetrisFigure tf) {
        int shift = 0;
        for (BoardBlock block : tf.figBlocks) {
            int auxShift = 0;
            if(block.state == BoardBlock.BlockState.FILLED && (block.position.x < 0 || block.position.x >= columns)) {
                if(block.position.x < 0)
                    auxShift = Math.abs(block.position.x);
                else if(block.position.x >= columns)
                    auxShift = (columns-1) - block.position.x;

                if(shift < Math.abs(auxShift)) {
                    shift = auxShift;
                }
            }
        }
        return shift;
    }

    boolean moveActiveFigureDown() {
        if (validFigureMove(activeFig, new Point(0, 1))) {
            activeFig.moveDown();
            return true;
        } else {
            alreadyStored = false;
            return false;
        }

    }

    boolean moveActiveFigureLeft() {
        if (validFigureMove(activeFig, new Point(-1, 0))) {
            activeFig.moveLeft();
            return true;
        } else {
            return false;
        }
    }

    boolean moveActiveFigureRight() {
        if (validFigureMove(activeFig, new Point(1, 0))) {
            activeFig.moveRight();
            return true;
        } else {
            return false;
        }
    }

    boolean rotateActiveFigure() {
        if (paused || activeFig.figType == TetrisFigure.Figure.SQUARE_SHAPED) {
            return false;
        }
        if(activeFig.rotate()) {
            int shift = calculateShift(activeFig);
            System.out.println("SHIFT: " + shift);
            activeFig.setShift(shift);
        }
        return true;
    }

    boolean dropDownActiveFigure() {
        boolean canGoDown = moveActiveFigureDown();
        boolean ret = canGoDown;
        while (canGoDown){
            canGoDown = moveActiveFigureDown();
        }
        return ret;
    }

    void paintActiveFigure() {
        for (BoardBlock block : activeFig.figBlocks) {
            if (block.state == BoardBlock.BlockState.EMPTY)
                continue;
            getCoordinateBlock(block.position).set(block);
        }
    }

    public TetrisFigure getActiveFigure(){
        return activeFig;
    }

    public void storeActiveFigure(){
        if(!alreadyStored && !paused) {
            if (storedFig == null) {
                storedFig = new TetrisFigure(activeFig.figType, figureCnt, activeFig.ts, true);
                addNewFigure();
            } else {
                TetrisFigure auxFigure = new TetrisFigure(activeFig.figType, figureCnt, activeFig.ts, true);
                activeFig = new TetrisFigure(storedFig.figType, figureCnt, storedFig.ts, false);
                storedFig = auxFigure;
            }
            alreadyStored = true;
        }
    }

    public TetrisFigure getStoredFigure(){
        return storedFig;
    }


    public void addNewFigure() {
        ++figureCnt;
        TetrisFigure.Figure randomFigure = TetrisFigure.Figure.getRandomFigure();
        TetrisFigure auxFigure = new TetrisFigure(randomFigure, figureCnt, this, false);
        if(validFigureMove(auxFigure, new Point(0, 0))) {
            activeFig = auxFigure;
            figuresList.add(activeFig);
        }
        else{
            gameOver = true;
        }
    }

    public void removeLines(){
        //  Iteramos todas las lineas del tablero
        for(int i = rows-1; i >= 0; --i){
            if(uniformRow(i, BoardBlock.BlockState.FILLED)){    //  Linea completa
                //  Eliminar linea completada
                for(int j = 0; j < columns; ++j) {
                    board[i][j].removeBlock(new Point(j, i));
                }

                //  Bajar bloques
                for(int i2 = i; i2 > 0; --i2){
                    for(int j = 0; j < columns; ++j) {
                        board[i2][j].set(board[i2-1][j]);
                    }
                }

                //  SIEMPRE que haya bloques en la fila más alta, la limpiamos
                if(!uniformRow(0, BoardBlock.BlockState.EMPTY)){
                    for(int j = 0; j < columns; ++j) {
                        board[0][j].removeBlock(new Point(j, 0));
                    }
                }
                ++i;
                ++score;
            }
        }
    }

    /**
     * Comprueba si todos los bloques de una fila "row" se encuentran en el mismo estado "state"
     * @param row
     * @param state
     * @return
     */
    boolean uniformRow(int row, BoardBlock.BlockState state){
        for(int j = 0; j < columns; ++j){
            if(getCoordinateBlock(new Point(j, row)).state == BoardBlock.BlockState.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean gameOver() {
        return gameOver;
    }

    public int getScore(){
        return score;
    }
}
