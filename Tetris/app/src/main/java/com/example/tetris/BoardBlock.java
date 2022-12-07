package com.example.tetris;

public class BoardBlock {
    int color;
    enum BlockState {EMPTY, FILLED};
    BlockState state;
    Point position;

    BoardBlock(int row, int col){
        color = -1;
        state = BlockState.EMPTY;
        position = new Point(row, col);
    }

    BoardBlock(int Color, Point pos, BlockState s) {
        color = Color;
        position = pos;
        state = s;
    }

    void set(BoardBlock b) {
        color = b.color;
        position.y = b.position.y;
        position.x = b.position.x;
        state = b.state;
    }

    void removeBlock(Point pos){
        color = -1;
        position.x = pos.x;
        position.y = pos.y;
        state = BlockState.EMPTY;
    }
}
