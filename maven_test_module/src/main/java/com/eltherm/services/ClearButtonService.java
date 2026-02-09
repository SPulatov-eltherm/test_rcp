/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Component;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author SPulatov
 */
public class ClearButtonService {
    
    
    public void clearPanel(JPanel panel,List<JButton> toolButtons) {
        
        //clear all components except for buttons
        for (Component c : panel.getComponents()) {
            if (!toolButtons.contains(c)) {
                panel.remove(c);
            } else {
            }
        }
        panel.revalidate();
        panel.repaint();
    
    }
    
}
