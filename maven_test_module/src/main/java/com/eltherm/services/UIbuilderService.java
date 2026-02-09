/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author Samir
 */
public class UIbuilderService {
    
    
    //service used to repaint visualboard and navigator panel
    private final RepaintService repaintService;
    
    //Compound Border for both panels;
    private final CompoundBorder border;
    
    public UIbuilderService(RepaintService repaintService) {
        this.repaintService = repaintService;
        this.border = createBorder();
    }
    
    
    public void build_navigator_panel(JPanel panel){
        panel.setBorder(border);
        
        //set Gridlayout for 8 elements
        panel.setLayout(new GridLayout(4, 2, 5, 5));
        for (int i = 1; i <= 8; i++) {
            //Create button for each element and place it in button as an icon
            JButton button = new JButton();
            try {
                Image img = ImageIO.read(
                        getClass().getResource("/images/" + i + ".png")
                );

                Image scaled = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
                
                //save the element name and pass it to an ActionListener
                String selected_element = Integer.toString(i);
                //Add actionListener for each button to draw element that has been selected
                button.addActionListener((ActionEvent e) -> {
                    repaintService.onElementSelected(selected_element);
                });
                panel.add(button);
            } catch (IOException e) {
                System.out.println("Das Bild wurde nicht gefunden " + i);
            }
        }  
    }
    
    public void build_visual_board_panel(JPanel panel) {
        panel.setLayout(new BorderLayout());
        panel.setBorder(border);
    }
    
    
    private CompoundBorder createBorder() { 
        //create border with some padding 
        Border raised = BorderFactory.createRaisedBevelBorder();
        Border lowered = BorderFactory.createLoweredBevelBorder();
        Border bevel = BorderFactory.createCompoundBorder(raised, lowered);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        
        return BorderFactory.createCompoundBorder(bevel, padding);
    
    }
    
    
   
    
}
