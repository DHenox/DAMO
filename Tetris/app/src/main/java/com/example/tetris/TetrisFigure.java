package com.example.tetris;

import java.util.Random;

public class TetrisFigure {
    enum Figure {
        SQUARE_SHAPED,
        LINE_SHAPED,
        T_SHAPED,
        L_SHAPED,
        INV_L_SHAPED,
        Z_SHAPED,
        INV_Z_SHAPED;

        private static final Figure[] VALUES = values();
        private static final int SIZE = VALUES.length;
        private static final Random RANDOM = new Random();

        public static Figure getRandomFigure() {
            return VALUES[RANDOM.nextInt(SIZE)];
        }
    }

    BoardBlock[] figBlocks;
    Figure figType;
    int rotationState;
    TetrisState ts;

    TetrisFigure(Figure f, int figureId, TetrisState tetrisState, boolean stored){
        Point[] points;
        figType = f;
        rotationState = 1;
        ts = tetrisState;

        switch (f) {
            case SQUARE_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(4, 2),
                            new Point(5, 2),
                            new Point(4, 3),
                            new Point(5, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(0, 2),
                            new Point(1, 2),
                            new Point(0, 3),
                            new Point(1, 3)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 1, points);
                break;
            case LINE_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(4, 0),
                            new Point(4, 1),
                            new Point(4, 2),
                            new Point(4, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 0),
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(1, 3)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 2, points);
                break;
            case T_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(4, 1),
                            new Point(4, 2),
                            new Point(4, 3),
                            new Point(5, 2)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(1, 3),
                            new Point(2, 2)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 3, points);
                break;
            case L_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(4, 1),
                            new Point(4, 2),
                            new Point(4, 3),
                            new Point(5, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(1, 3),
                            new Point(2, 3)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 4, points);
                break;
            case INV_L_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(5, 1),
                            new Point(5, 2),
                            new Point(5, 3),
                            new Point(4, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(1, 3),
                            new Point(0, 3)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 5, points);
                break;
            case Z_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(5, 1),
                            new Point(5, 2),
                            new Point(4, 2),
                            new Point(4, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(0, 3),
                            new Point(0, 2)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 6, points);
                break;
            case INV_Z_SHAPED:
                if(!stored) {
                    points = new Point[]{
                            new Point(4, 1),
                            new Point(4, 2),
                            new Point(5, 2),
                            new Point(5, 3)
                    };
                }
                else{
                    points = new Point[]{
                            new Point(1, 1),
                            new Point(1, 2),
                            new Point(2, 2),
                            new Point(2, 3)
                    };
                }
                figBlocks = generateFigureBlocks(figureId, 7, points);
                break;
        }
    }

    private BoardBlock[] generateFigureBlocks(int figId, int color, Point[] points) {
        BoardBlock[] blocks = new BoardBlock[points.length];
        for (int i = 0; i < points.length; i++) {
            blocks[i] = new BoardBlock(color, figId, points[i], BoardBlock.BlockState.FILLED);
        }
        return blocks;
    }

    void moveLeft(){
        for(BoardBlock b : figBlocks){
            --b.position.x;
        }
    }

    void moveRight(){
        for(BoardBlock b : figBlocks){
            ++b.position.x;
        }
    }

    void moveDown(){
        for(BoardBlock b : figBlocks){
            ++b.position.y;
        }
    }

    boolean rotate() {
        boolean rotated = false;
        switch (figType) {
            case SQUARE_SHAPED:
                break;
            case LINE_SHAPED:
                rotated = rotateLineShaped();
                break;
            case T_SHAPED:
                rotated = rotateTShaped();
                break;
            case L_SHAPED:
                rotated = rotateLShaped();
                break;
            case INV_L_SHAPED:
                rotated = rotateInvLShaped();
                break;
            case Z_SHAPED:
                rotated = rotateZShaped();
                break;
            case INV_Z_SHAPED:
                rotated = rotateInvZShaped();
                break;
        }
        rotationState = (rotationState % 4) + 1;
        return rotated;
    }

    public void setShift(int shift) {
        figBlocks[0].position = Point.add(figBlocks[0].position, new Point(shift, 0));
        figBlocks[1].position = Point.add(figBlocks[1].position, new Point(shift, 0));
        figBlocks[2].position = Point.add(figBlocks[2].position, new Point(shift, 0));
        figBlocks[3].position = Point.add(figBlocks[3].position, new Point(shift, 0));
    }

    private boolean rotateLineShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, 2));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-2, -1));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(2, -2));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-2, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(-1, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(1, 2));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(2, -2));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(1, -1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-1, 1));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

    private boolean rotateTShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-1, 1));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-1, -1));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(1, -1));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(1, 1));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

    private boolean rotateLShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-2, 0));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, -2));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(2, 0));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, 2));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

    private boolean rotateInvLShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, -2));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(2, 0));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, 2));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-2, 0));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

    private boolean rotateZShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(0, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(-1, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-1, -2));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, 1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(2, 0));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-1, -2));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, -1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, 1));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(2, 0));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(1, 1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-1, 1));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

    private boolean rotateInvZShaped() {
        Point[] auxRotatedPositions = new Point[4];
        if(rotationState == 1){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, 0));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, -1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(-1, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(-2, -1));
        }
        else if(rotationState == 2){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(0, 2));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(1, 1));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, 0));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(1, -1));
        }else if(rotationState == 3){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(-2, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(-1, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(0, -1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(1, 0));
        }else if(rotationState == 4){
            auxRotatedPositions[0] = Point.add(figBlocks[0].position, new Point(1, -1));
            auxRotatedPositions[1] = Point.add(figBlocks[1].position, new Point(0, 0));
            auxRotatedPositions[2] = Point.add(figBlocks[2].position, new Point(1, 1));
            auxRotatedPositions[3] = Point.add(figBlocks[3].position, new Point(0, 2));
        }
        for(Point auxRotatedPos : auxRotatedPositions){
            if(ts.rotatingBlockIsConflicting(auxRotatedPos))
                return false;
        }
        for(int i = 0; i < auxRotatedPositions.length; ++i){
            figBlocks[i].position = auxRotatedPositions[i];
        }
        return true;
    }

}
