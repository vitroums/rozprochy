package com.rozprochy.tron.tronclient;

import com.rozprochy.tron.troncommon.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
    private final int unit;
    private final int offset;
    private final int xSize;
    private final int ySize;
    private final  Color color[] = new Color[4];
    private final GraphicsContext gc;
    
    public GameView(Canvas canvas, int unit, int xSize, int ySize, int offset){
        color[0] = Color.BLUE;
        color[1] = Color.RED;
        color[2] = Color.YELLOW;
        color[3] = Color.GREEN;
        this.unit = unit;
        this.xSize = xSize;
        this.ySize = ySize;
        this.offset = offset;
        gc = canvas.getGraphicsContext2D();
    }
    public void Display(Map map){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, unit*xSize, unit*ySize + offset);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 15));
        gc.fillText("N - start / new game, P - pause, WSAD - steering", 0, offset - 5);
        gc.setLineWidth(1);
        gc.setStroke(Color.WHITE);
        gc.strokeLine(0, offset - 1, xSize*unit, offset - 1);
        for(int i = 0; i < xSize; i++)
            for(int j = 0; j < ySize; j++)
                if(map.read(i, j) > -1){
                    gc.setFill(color[map.read(i, j)]);
                    gc.fillRect(i*unit, j*unit + offset, unit, unit);
                }
        if(map.isPause())
            print("PAUSE");
        if(map.isWon()){
            String text = "PLAYER ";
            int winner = map.getWinningPlayer();
            text += winner;
            text += " WINS!";
            print(text);
        }
    }
    public void print(String text){
        Color col = new Color(0, 0, 0, 0.5);
        gc.setFill(col);
        gc.fillRect(0, offset, unit*xSize, unit*ySize);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 40));
        gc.fillText(text, unit*xSize/3, unit*ySize/2);
    }
}