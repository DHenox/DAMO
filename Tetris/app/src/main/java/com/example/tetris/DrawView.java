package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    int yOffset;
    Paint paint;
    TetrisState ts;
    float boundaryWidth;
    float gridWidth;

    int blockWidth;
    int minX, maxX, minY, maxY;
    int firstBlockPos;

    int blockWidthStored;
    int minXStored, maxXStored, minYStored, maxYStored;
    int firstBlockPosStored;

    public DrawView(Context context, TetrisState tetrisState) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        yOffset = 225;
        ts = tetrisState;
        boundaryWidth = 7f;
        gridWidth = 3f;

        blockWidth = 70;
        minX = 190; maxX = 890;
        minY = 0; maxY = 1120;
        firstBlockPos = minX + ((maxX-minX)/ts.columns);

        blockWidthStored = 48;
        minXStored = 746; maxXStored = 890;
        minYStored = 18; maxYStored = 210;
        firstBlockPosStored = minXStored + ((maxXStored-minXStored)/ts.boardStored[0].length);
    }

    private int getBlockColorCode(int color) {
        switch (color) {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.rgb(255, 165, 0);      //  ORANGE
            case 5:
                return Color.BLUE;
            case 6:
                return Color.RED;
            case 7:
                return Color.GREEN;
            default:
                return Color.TRANSPARENT;
        }

    }

    private void drawUpdatedMatrix(BoardBlock[][] matrix, Canvas canvas) {
        for (int i = 0+ts.standOutBlocks; i < ts.rows; i++) {
            for (int j = 0; j < ts.columns; j++) {
                if (matrix[i][j].state == BoardBlock.BlockState.EMPTY)
                    continue;

                int color = getBlockColorCode(matrix[i][j].color);
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        (minX+gridWidth) + j * blockWidth,
                        yOffset + i * blockWidth + gridWidth - ts.standOutBlocks*blockWidth,
                        (firstBlockPos-gridWidth) + j * blockWidth,
                        yOffset + (i + 1) * blockWidth - gridWidth - ts.standOutBlocks*blockWidth,
                        p
                );
            }
        }
    }

    private void clear(BoardBlock[][] matrix, Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        for (int i = 0+ts.standOutBlocks; i < ts.rows; i++) {
            for (int j = 0; j < ts.columns; j++) {
                canvas.drawRect(
                        (minX+gridWidth) + j * blockWidth,
                        yOffset + i * blockWidth + gridWidth - ts.standOutBlocks*blockWidth,
                        (firstBlockPos-gridWidth) + j * blockWidth,
                        yOffset + (i + 1) * blockWidth - gridWidth - ts.standOutBlocks*blockWidth,
                        p
                );
            }
        }
    }

    private void drawNewFigure(TetrisFigure tetrisFigure, Canvas canvas) {
        for (BoardBlock block : tetrisFigure.figBlocks) {
            if(block.position.y >= ts.standOutBlocks) {
                int color = getBlockColorCode(block.color);
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        (minX + gridWidth) + block.position.x * blockWidth,
                        yOffset + block.position.y * blockWidth + gridWidth - ts.standOutBlocks * blockWidth,
                        (firstBlockPos - gridWidth) + block.position.x * blockWidth,
                        yOffset + (block.position.y + 1) * blockWidth - gridWidth - ts.standOutBlocks * blockWidth,
                        p
                );
            }
            /*else{ //  muestra bloques que sobresalen por encima del tablero
                int color = getBlockColorCode(block.color);
                Paint p = new Paint();
                p.setColor(Color.BLACK);
                canvas.drawRect(
                        (minX + gridWidth) + block.position.x * blockWidth,
                        yOffset + block.position.y * blockWidth + gridWidth - ts.standOutBlocks * blockWidth,
                        (firstBlockPos - gridWidth) + block.position.x * blockWidth,
                        yOffset + (block.position.y + 1) * blockWidth - gridWidth - ts.standOutBlocks * blockWidth,
                        p
                );
            }*/
        }
    }

    private void boundary(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(boundaryWidth);
        canvas.drawLine(minX, yOffset, minX, yOffset + maxY, paint);
        canvas.drawLine(maxX, yOffset, maxX, yOffset + maxY, paint);
        canvas.drawLine(minX, yOffset, maxX, yOffset, paint);
        canvas.drawLine(minX, yOffset + maxY, maxX, yOffset + maxY, paint);
    }

    private void grid(Canvas canvas) {
        paint.setStrokeWidth(gridWidth);
        for (int i = firstBlockPos; i < maxX; i = i + blockWidth) {
            canvas.drawLine(i,yOffset, i, yOffset + maxY, paint);
        }
        for (int j = blockWidth; j < maxY; j = j + blockWidth) {
            canvas.drawLine(minX, yOffset + j, maxX, yOffset + j, paint);
        }
    }

    private void boundaryStored(Canvas canvas){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(boundaryWidth);
        canvas.drawLine(minXStored, minYStored, minXStored, maxYStored, paint);
        canvas.drawLine(maxXStored, minYStored, maxXStored, maxYStored, paint);
        canvas.drawLine(minXStored, minYStored, maxXStored, minYStored, paint);
        canvas.drawLine(minXStored, maxYStored, maxXStored, maxYStored, paint);
    }

    private void gridStored(Canvas canvas) {
        paint.setStrokeWidth(gridWidth);
        for (int i = firstBlockPosStored; i < maxXStored; i = i + blockWidthStored) {
            canvas.drawLine(i,minYStored, i, maxYStored, paint);
        }
        for (int j = blockWidthStored; j < maxYStored; j = j + blockWidthStored) {
            canvas.drawLine(minXStored, minYStored + j, maxXStored, minYStored + j, paint);
        }
    }

    private void clearStored(BoardBlock[][] matrix, Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        for (int i = 0; i < ts.boardStored.length; i++) {
            for (int j = 0; j < ts.boardStored[0].length; j++) {
                canvas.drawRect(
                        (minXStored+gridWidth) + j * blockWidthStored,
                        minYStored + i * blockWidthStored + gridWidth ,
                        (firstBlockPosStored-gridWidth) + j * blockWidthStored,
                        minYStored + (i + 1) * blockWidthStored - gridWidth,
                        p
                );
            }
        }
    }

    private void drawStoredFigure(TetrisFigure tetrisFigure, Canvas canvas) {
        if(tetrisFigure != null) {
            for (BoardBlock block : tetrisFigure.figBlocks) {
                int color = getBlockColorCode(block.color);
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(
                        (minXStored + gridWidth) + block.position.x * blockWidthStored,
                        minYStored + block.position.y * blockWidthStored + gridWidth,
                        (firstBlockPosStored - gridWidth) + block.position.x * blockWidthStored,
                        minYStored + (block.position.y + 1) * blockWidthStored - gridWidth,
                        p
                );
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        boundary(canvas);
        grid(canvas);
        clear(ts.board, canvas);
        drawNewFigure(ts.getActiveFigure(), canvas);
        drawUpdatedMatrix(ts.board, canvas);

        boundaryStored(canvas);
        gridStored(canvas);
        clearStored(ts.boardStored, canvas);
        drawStoredFigure(ts.getStoredFigure(), canvas);
        /*drawStoredMatrix(ts.getStoredFigure(), canvas);*/
    }
}
