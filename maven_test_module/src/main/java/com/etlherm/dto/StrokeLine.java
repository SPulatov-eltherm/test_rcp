/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etlherm.dto;

import java.awt.Color;
import java.awt.geom.Path2D;

/**
 *
 * @author Samir
 */
public class StrokeLine {
    
    Path2D path;
    Color color;
    float width;
    
    public StrokeLine(Color color, float width) {
        this.path = new Path2D.Double();
        this.color = color;
        this.width = width;
    }
    
    public Path2D getPath(){
        return path;
    }
    
    public Color getColor() {
        return color;
    }
    
    public float getWidth() {
        return width;
    }
    
}
