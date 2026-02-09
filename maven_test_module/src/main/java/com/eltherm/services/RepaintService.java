/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author SPulatov
 */
public class RepaintService {
    
    private final JPanel navigatorPanel;
    private final JPanel visualBoardPanel;
    
    
    
    //constructor to redraw navigator and visualboard panels
    public RepaintService(JPanel navigatorPanel,JPanel visualBoardPanel) {
        this.navigatorPanel = navigatorPanel;
        this.visualBoardPanel = visualBoardPanel;
    }
    
    
    //list of selected Elements
    private final List<String> selectedElements = new ArrayList<>();
    
    
    //selected_element is an index of element that user has selected. That is 1 or 2 and etc.
    public void onElementSelected(String selected_element) {
        if(!selectedElements.contains(selected_element)) {
            selectedElements.add(selected_element);
        }
        
        //redraw visual board
        redrawVisualBoard(selected_element);
        
        //redraw navigator panel 
        redrawNavigatorPanel(selected_element);
        
    }
    
    
    private void redrawNavigatorPanel(String element) {
        if(navigatorPanel != null) {
            navigatorPanel.removeAll();

            // get list of an elements that are could be used with previous selected one
            List<String> possible_combos = switch (element) {
                case "1", "2", "3", "4", "5", "6", "7", "8" -> {
                    yield List.of("9", "10", "11");
                }
                case "9", "10", "11" -> {
                    yield List.of("1", "2", "3", "4", "5", "6", "7", "8");
                }
                default -> {
                    yield List.of("");
                }
            };

            int length = possible_combos.size();
            int cols = 2;
            int rows = (int) Math.ceil((double) length / cols);

            navigatorPanel.setLayout(new GridLayout(rows, cols, 5, 5));

            for (int i = 0; i < length; i++) {

                String index = possible_combos.get(i);

                //Create button for each element and place it in button as an icon
                JButton button = new JButton();
                try {
                    Image img = ImageIO.read(
                            getClass().getResource("/images/" + index + ".png")
                    );

                    Image scaled = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaled));

                    //save the element name and pass it to an ActionListener
                    String selected_element = index;
                    //Add actionListener for each button to draw element that has been selected
                    button.addActionListener((ActionEvent e) -> {
                        this.onElementSelected(selected_element);
                    });
                    navigatorPanel.add(button);
                } catch (IOException e) {
                    System.out.println("Das Bild wurde nicht gefunden " + index);
                }
            }

            navigatorPanel.revalidate();
            navigatorPanel.repaint();
        }  
    }
    
    
    
    
    private void redrawVisualBoard(String element) {
        if(visualBoardPanel != null) {
            visualBoardPanel.setLayout(null);
            visualBoardPanel.setBackground(Color.white);

            // create new block with selected svg
            VisualBlockService block = new VisualBlockService(element);

            Dimension size = block.getPreferredSize();

            Point p = findFreePosition(size);

            block.setBounds(p.x, p.y, size.width, size.height);

            // add block to the main panel
            visualBoardPanel.add(block);

            visualBoardPanel.revalidate();
            visualBoardPanel.repaint();
        } 
    }  
    
    
    
    private Point findFreePosition(Dimension size) {

        // Current size of the main board panel
        int panelW = visualBoardPanel.getWidth();
        int panelH = visualBoardPanel.getHeight();

        // Fallback size if panel is not yet visible or not laid out
        // (getWidth / getHeight may return 0 before first layout)
        if (panelW == 0 || panelH == 0) {
            panelW = 800;
            panelH = 600;
        }

        // Rectangle representing the new block to be placed
        Rectangle newRect = new Rectangle();

        // Try multiple random positions to find a free spot
        for (int attempt = 0; attempt < 100; attempt++) {

            // Generate random position inside panel bounds
            int x = (int) (Math.random() * (panelW - size.width));
            int y = (int) (Math.random() * (panelH - size.height));

            // Define bounds of the new block at this position
            newRect.setBounds(x, y, size.width, size.height);

            boolean intersects = false;

            // Check intersection with all existing components
            for (Component c : visualBoardPanel.getComponents()) {
                if (c.getBounds().intersects(newRect)) {
                    intersects = true;
                    break;
                }
            }

            // If no intersection found, this position is free
            if (!intersects) {
                return new Point(x, y);
            }
        }

        // Fallback: if no free position was found after all attempts
        // (panel is probably too crowded)
        return new Point(10, 10);
    }

}
    