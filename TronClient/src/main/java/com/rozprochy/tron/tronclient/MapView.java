package com.rozprochy.tron.tronclient;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.rozprochy.tron.troncommon.*;

public class MapView {
    private static int unit = 10;
    private static Canvas canvas;
    private static Color color[] = new Color[4];
    private static GraphicsContext gc;
    
    MapView(Canvas canvas){
        this.canvas = canvas;
        color[0] = Color.BLUE;
        color[1] = Color.RED;
        color[2] = Color.YELLOW;
        color[3] = Color.BLACK;
        gc = canvas.getGraphicsContext2D();
    }
    
    public static void Display(Map map){
        for(int i = 0; i < Map.getxSize(); i++)
            for(int j = 0; j < Map.getySize(); j++)
                if(map.ReadMap(i,j) > -1){
                    gc.setFill(color[map.ReadMap(i, j)]);
                    gc.fillRect(i*unit, j*unit, 2*unit, 2*unit);
                }
    }
}
