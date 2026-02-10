/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;



/**
 *
 * @author Samir
 */
public class DrawButtonService {
    
    //call this function when draw button is clicked and enable to draw on panel
    public void enableDrawing(DrawPanel panel) {
        panel.setMouseListenerAndMouseMotionListener();
    }
    
}
