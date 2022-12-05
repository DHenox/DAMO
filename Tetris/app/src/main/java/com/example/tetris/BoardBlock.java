package com.example.tetris;

public class BoardBlock {
    int color;
    enum BlockState {EMPTY, FILLED};
    BlockState state;
    int figureId;
    Point position;

    BoardBlock(int row, int col){
        color = -1;
        figureId = -1;
        state = BlockState.EMPTY;
        position = new Point(row, col);
    }

    BoardBlock(int Color, int figId, Point pos, BlockState bs) {
        color = Color;
        figureId = figId;
        position = pos;
        state = bs;
    }

    void set(BoardBlock b) {
        color = b.color;
        figureId = b.figureId;
        position.y = b.position.y;
        position.x = b.position.x;
        state = b.state;
    }

    void removeBlock(Point pos){
        color = -1;
        figureId = -1;
        position.x = pos.x;
        position.y = pos.y;
        state = BlockState.EMPTY;
    }
}
