/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

/**
 *
 * @author SPulatov
 */
public class DraggableShape extends JComponent {
    
    enum ShapeType {
        RECTANGLE,
        CIRCLE,
        TRIANGLE
    }
    
    //currentColor that user has selected
    private final Color currentColor;
    
    private final ShapeType shapeType;
    private Point dragOffSet;
    
    public DraggableShape(ShapeType type,Color color) {
        this.shapeType = type;
        this.currentColor = color;
        setSize(120,120);
        setOpaque(false);
        
        MouseAdapter handler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragOffSet = e.getPoint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                int newX = getX() + e.getX() - dragOffSet.x;
                int newY = getY() + e.getY() - dragOffSet.y;
                setLocation(newX,newY);
            }
        };
        
        addMouseListener(handler);
        addMouseMotionListener(handler);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(currentColor);
        g2.setStroke(new BasicStroke(2));
        
        switch(shapeType) {
            case RECTANGLE -> g2.drawRect(20, 20, 100, 100);
            case CIRCLE -> g2.drawOval(20, 20, 100, 100);
            case TRIANGLE -> {
                int[] x = {60,10,100};
                int[] y = {10,110,110};
                g2.drawPolygon(x,y,3);
            }
        }
        
        g2.dispose();
    }
    
}
