/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Dimension;
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
        visualBoardPanel.removeAll();
        
        // Создаем новый блок с выбранным SVG
        VisualBlockService block = new VisualBlockService(element);

        // Получаем предпочтительный размер блока
        Dimension d = block.getPreferredSize();

        // Центрируем блок на панели
        int panelW = visualBoardPanel.getWidth();
        int panelH = visualBoardPanel.getHeight();
        int x = (panelW - d.width) / 2;
        int y = (panelH - d.height) / 2;

        block.setBounds(x, y, d.width, d.height);

        // Добавляем блок на панель
        visualBoardPanel.add(block);

        
        
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
    