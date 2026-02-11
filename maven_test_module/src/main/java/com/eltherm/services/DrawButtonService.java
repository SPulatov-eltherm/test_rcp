/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;



/**
 *
 * @author Samir
 */
public class DrawButtonService {
    
   
    //call this function when draw button is clicked and enable to draw on panel
    public void enableDrawing(DrawPanel panel,Color color) {
        panel.setColor(color);
        panel.setMouseListenerAndMouseMotionListener();
    }
    
    //call this function to disable drawing
    public void disableDrawing(DrawPanel panel) {
        panel.removeMouseListenerAndMouseMotionListener();
    }
    
}
