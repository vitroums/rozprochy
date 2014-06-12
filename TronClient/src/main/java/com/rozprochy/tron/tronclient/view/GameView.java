package com.rozprochy.tron.tronclient.view;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public final class GameView {

    private final int unit;
    private final int offset;
    private final int xSize;
    private final int ySize;
    private final int x;
    private final int y;
    private final Color color[] = {Color.BLUE, Color.RED, Color.GOLD, Color.GREEN};
    private final GraphicsContext gc;

    public GameView(Canvas canvas, int unit, int x, int y, int offset) {
        this.unit = unit;
        this.xSize = x / unit;
        this.x = x;
        this.y = y;
        this.ySize = (y - offset) / unit;
        this.offset = offset;
        gc = canvas.getGraphicsContext2D();
        print("Waiting for server");
    }

    public void Display(String mapString) 
    {
        int index = 0;
        int[][] map = new int[xSize][ySize];
        for(int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                char c = mapString.charAt(index++);
                if(c == '-')
                {
                    index++;
                    map[i][j] = -1;
                }
                else
                    map[i][j] = c - '0';
            }
        }
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, unit * xSize, unit * ySize + offset);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Verdana", 15));
        gc.fillText("WSAD - steering", 0, offset - 5);
        gc.setLineWidth(1);
        gc.setStroke(Color.WHITE);
        gc.strokeLine(0, offset - 1, xSize * unit, offset - 1);
        if(map == null)
            return;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (map[i][j] > -1) {
                    gc.setFill(color[map[i][j]]);
                    gc.fillRect(i * unit, j * unit + offset, unit, unit);
                }
            }
        }

    }

    public void print(String text) {
        Color col = new Color(0, 0, 0, 0.5);
        gc.setFill(col);
        gc.fillRect(0, offset, unit * xSize, unit * ySize);
        gc.setFill(Color.WHITE);
        Font textFont = Font.font("Verdana", 40);
        FontMetrics metrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(textFont);
        gc.setFont(textFont);
        float w = metrics.computeStringWidth(text);
        float h = metrics.getLineHeight();
        float textY = (y - h) / 2;
        float textX = (x - w) / 2;
        gc.fillText(text, textX, textY);
    }
}
