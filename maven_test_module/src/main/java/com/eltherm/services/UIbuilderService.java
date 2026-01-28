/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Samir
 */
public class UIbuilderService {
    
    
    public void build_symbole_panel(JPanel panel){
        Border raised = BorderFactory.createRaisedBevelBorder();
        Border lowered = BorderFactory.createLoweredBevelBorder();
        Border bevel = BorderFactory.createCompoundBorder(raised, lowered);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        panel.setBorder(BorderFactory.createCompoundBorder(bevel, padding));

        panel.setLayout(new GridLayout(4, 2, 5, 5));
        for (int i = 1; i <= 8; i++) {
            JButton button = new JButton();
            try {
                Image img = ImageIO.read(
                        getClass().getResource("/images/" + i + ".png")
                );

                Image scaled = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                panel.add(button);
            } catch (IOException e) {
                System.out.println("Не загрузилось изображение " + i);
            }
        }
        
    }
    
}
