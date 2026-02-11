/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import com.etlherm.dto.StrokeLine;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Samir
 */
public class DrawPanel extends JPanel {
    
    private final List<StrokeLine> lines = new ArrayList<>();
    private StrokeLine currentLine;
    
    private Color color = Color.black;
    
    public void setColor(Color color) { this.color = color; }
    
    private final MouseAdapter mouseHandler;
    
    public DrawPanel(){
        
        mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!SwingUtilities.isLeftMouseButton(e)){
                    return;
                }
                //set start point for drawing line
                currentLine = new StrokeLine(color,3f);
                currentLine.getPath().moveTo(e.getX(), e.getY());
                lines.add(currentLine);
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if(currentLine != null) {
                    //move line to some point(drawing process)
                    currentLine.getPath().lineTo(e.getX(), e.getY());
                    repaint();
                }
            }
            
            
            @Override
            public void mouseReleased(MouseEvent e){
                currentLine = null;
            }
        
        };
        
    }
    
    //function to enable drawing
    public void setMouseListenerAndMouseMotionListener(){
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }
    
    
    public void removeMouseListenerAndMouseMotionListener(){
        removeMouseListener(mouseHandler);
        removeMouseMotionListener(mouseHandler);
    }
    
    
    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            for (StrokeLine line : lines) {
                g2.setColor(line.getColor());
                g2.setStroke(new BasicStroke(
                        line.getWidth(),
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                ));
                g2.draw(line.getPath());
            }
        } finally {
            g2.dispose();
        }
    }
    
    //function to clear lines. We call it then in ClearButtonService
    public void clearLines(){
        lines.clear();
    }
}
