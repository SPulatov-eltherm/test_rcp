/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author Samir
 */
public class UIbuilderService {
    
    
    //service used to repaint visualboard and navigator panel
    private final RepaintService repaintService;
    
    //service used to handle actions based on button user has clicked (clear,draw,shapes)
    private final ToolsService toolsService;
    
    //List of buttons to redraw
    private final List<JButton> toolButtons = new ArrayList<>();
   
    //flag to add or remove blue blackground from buttons 
    private boolean clicked = false;
    
    //Compound Border for both panels;
    private final CompoundBorder border;
    
    public UIbuilderService(RepaintService repaintService,ToolsService toolsService) {
        this.repaintService = repaintService;
        this.toolsService = toolsService;
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
        panel.setLayout(null);
        panel.setBorder(border);
        
        JButton clearBtn = createOverlayButton("clear.png", "Clear");
        JButton drawBtn = createOverlayButton("pencil.png", "Draw");
        JButton shapeBtn = createOverlayButton("shapes.png", "Shapes");
       
        panel.add(clearBtn);
        panel.add(drawBtn);
        panel.add(shapeBtn);
        
        //add buttons to toolButtons list to redraw them when user chooses to clear panel
        toolButtons.clear();
        toolButtons.add(clearBtn);
        toolButtons.add(drawBtn);
        toolButtons.add(shapeBtn);
        toolsService.setToolsButtonList(toolButtons);
        
        
        
        //wait till visual_board_panel is build
        SwingUtilities.invokeLater(() -> {
            positionToolButtons(panel, clearBtn, drawBtn, shapeBtn);
        });

        // position all tools based on panel size
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                positionToolButtons(panel, clearBtn, drawBtn, shapeBtn);
            }
        });
    }
    
    
    
    private void positionToolButtons(JPanel panel, JButton clearBtn, JButton drawBtn, JButton shapeBtn) {
        int btnSize = 28;
        int gap = 6;
        int x = panel.getWidth() - btnSize - 10;
        int y = 10;

        clearBtn.setBounds(x, y, btnSize, btnSize);
        drawBtn.setBounds(x, y + btnSize + gap, btnSize, btnSize);
        shapeBtn.setBounds(x, y + 2 * (btnSize + gap), btnSize, btnSize);
    }
    
    
    
    private CompoundBorder createBorder() { 
        //create border with some padding 
        Border raised = BorderFactory.createRaisedBevelBorder();
        Border lowered = BorderFactory.createLoweredBevelBorder();
        Border bevel = BorderFactory.createCompoundBorder(raised, lowered);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        
        return BorderFactory.createCompoundBorder(bevel, padding);
    
    } 
    
    
    
    private JButton createOverlayButton(String iconName, String tooltip) {

        JButton btn = new JButton();
        btn.setToolTipText(tooltip);

        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        try {
            Image img = ImageIO.read(
                getClass().getResource("/icons/" + iconName));
            Image scaled = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } catch (IOException e) {
            btn.setText("?");
        }
    
        
        //Add actionListener for each button to perform action
        String toolTipText = btn.getToolTipText();
        
        //change bg for all buttons except clear button
        if(!toolTipText.equals("Clear")) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!clicked) {
                        btn.setBackground(new Color(14, 119, 230));
                        clicked = true;
                    } else {
                        btn.setBackground(Color.white);
                        clicked = false;
                    }
                }
            });
        }
        
        
        
        switch(toolTipText) {
            case "Clear" -> {
                btn.addActionListener((ActionEvent e) -> {
                    toolsService.setMode(ToolsService.Mode.CLEAR_ON);
                });
            }
            case "Draw" -> {
                btn.addActionListener((ActionEvent e) -> {
                    toolsService.setMode(ToolsService.Mode.DRAW_ON);
                });
            }
            case "Shapes" -> {
                btn.addActionListener((ActionEvent e) -> {
                    toolsService.setMode(ToolsService.Mode.SHAPES_ON);
                });
            }
        }
        
        
        
        return btn;
    }

}
