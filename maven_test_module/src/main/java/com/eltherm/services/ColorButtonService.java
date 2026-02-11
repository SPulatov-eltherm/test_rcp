/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import javax.swing.JColorChooser;

/**
 *
 * @author SPulatov
 */
public class ColorButtonService {
    
    //set blue as default color
    private Color selectedColor = Color.BLUE;
    
    public Color showColorDialog(DrawPanel panel) {
        selectedColor = JColorChooser.showDialog(
                panel,
                "Choose Color",
                selectedColor
        );
        
        return selectedColor;
        
    }
    
    
    
}
