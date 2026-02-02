/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author SPulatov
 */
public class RepaintService {
    
    private JPanel navigatorPanel;
    private JPanel visualBoardPanel;
    
    private String selectedElement;
    
    public RepaintService(JPanel navigatorPanel,JPanel visualBoardPanel) {
        this.navigatorPanel = navigatorPanel;
        this.visualBoardPanel = visualBoardPanel;
    }
    
    
    //list of selected Elements
    private List<String> selectedElements = new ArrayList<>();
    
    public void onElementSelected(String selected_element) {
        this.selectedElement = selected_element;
        if(!selectedElements.contains(selected_element)) {
            selectedElements.add(selected_element);
        }
        
        //redraw visual board
        redrawVisualBoard(selected_element);
        
    }
    
    
    private void redrawVisualBoard(String element) {
        
        //delete old components
        visualBoardPanel.removeAll();
        
        //remove layout manager
        visualBoardPanel.setLayout(null);
        
        
        JPanel block1 = createBlock(element);
        JPanel block2 = createBlock(element);
        JPanel block3 = createBlock(element);
        
        
        int gap = 5;

        // размеры блоков
        int w1 = block1.getWidth();
        int h1 = block1.getHeight();

        int w2 = block2.getWidth();
        int h2 = block2.getHeight();
        
        
        int panelW = visualBoardPanel.getWidth();
        int panelH = visualBoardPanel.getHeight();

        // центрируем всю группу
        int totalWidth = w1 + gap + w2;
        int totalHeight = Math.max(h1, h2) + gap + block3.getHeight();

        int startX = (panelW - totalWidth) / 2;
        int startY = (panelH - totalHeight) / 2;

        // размещение
        block1.setBounds(startX, startY, w1, h1);
        block2.setBounds(startX + w1 + gap, startY - h1 - gap, w2, h2);
        block3.setBounds(startX + w1 + gap, startY + h1 + gap,
                block3.getWidth(), block3.getHeight());
     
         // добавляем на панель
        visualBoardPanel.add(block1);
        visualBoardPanel.add(block2);
        visualBoardPanel.add(block3);

        
        
        visualBoardPanel.revalidate();
        visualBoardPanel.repaint();
        
    }
    
    
    private JPanel createBlock(String name) {
        
        //default padding for blocks
        int padding = 5;
        
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        //set image Height and Width to 0 by default
        int imgW = 0;
        int imgH = 0;
        
        //default label for block
        JLabel imageLabel = new JLabel("Kein Bild");
        
        
        try {
            Image img = ImageIO.read(
                    getClass().getResource("/images/"+name)
            );
            
            ImageIcon icon = new ImageIcon(img);
            imgW = icon.getIconWidth();
            imgH = icon.getIconHeight();
            imageLabel = new JLabel(icon);
            imageLabel.setBounds(
                padding,
                padding,
                imgW,
                imgH
            );    
        } catch(IOException e) {
            System.out.println("Das Bild wurde nicht gefunden " + e);
        }
        
        int blockW = imgW + padding * 2;
        int blockH = imgH + padding * 2;
        
        panel.setSize(blockW,blockH);
        panel.add(imageLabel);

        return panel;
    }
}
    