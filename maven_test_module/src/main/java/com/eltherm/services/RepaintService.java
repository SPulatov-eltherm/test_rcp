/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author SPulatov
 */
public class RepaintService {
    
    private final JPanel navigatorPanel;
    private final JPanel visualBoardPanel;
    
    
    public RepaintService(JPanel navigatorPanel,JPanel visualBoardPanel) {
        this.navigatorPanel = navigatorPanel;
        this.visualBoardPanel = visualBoardPanel;
    }
    
    
    //list of selected Elements
    private final List<String> selectedElements = new ArrayList<>();
    
    public void onElementSelected(String selected_element) {
        if(!selectedElements.contains(selected_element)) {
            selectedElements.add(selected_element);
        }
        
        //redraw visual board
        redrawVisualBoard(selected_element);
        
    }
    
    
    private void redrawVisualBoard(String element) {
        
     
        visualBoardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        visualBoardPanel.setBackground(Color.white);
        
        // create new block with selected svg
        VisualBlockService block = new VisualBlockService(element);

        // add block to the main panel
        visualBoardPanel.add(block);

        visualBoardPanel.revalidate();
        visualBoardPanel.repaint();
        
    }  
}
    