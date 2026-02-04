/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
        
        //delete old components
        //visualBoardPanel.removeAll();
        
       
        visualBoardPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));



        
        // Создаем новый блок с выбранным SVG
        VisualBlockService block = new VisualBlockService(element);

        // Получаем предпочтительный размер блока
        Dimension d = block.getPreferredSize();


        // Добавляем блок на панель
        visualBoardPanel.add(block);

        
        
        visualBoardPanel.revalidate();
        visualBoardPanel.repaint();
        
    }  
}
    