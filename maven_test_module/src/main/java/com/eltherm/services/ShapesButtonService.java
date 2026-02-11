/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import com.eltherm.services.DraggableShapeService.ShapeType;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author SPulatov
 */
public class ShapesButtonService {
    
    
    public void showShapesPopup(DrawPanel panel,Component parent){
        JPopupMenu popup = new JPopupMenu();
        popup.setLayout(new GridLayout(3,1));
        
        JMenuItem rectItem = createShapeItem("rectangle.png");
        JMenuItem circleItem = createShapeItem("circle.png");
        JMenuItem triangleItem = createShapeItem("triangle.png");
    
        popup.add(rectItem);
        popup.add(circleItem);
        popup.add(triangleItem);
        
        rectItem.addActionListener(e -> addShape(panel,ShapeType.RECTANGLE));
        circleItem.addActionListener(e -> addShape(panel,ShapeType.CIRCLE));
        triangleItem.addActionListener(e -> addShape(panel,ShapeType.TRIANGLE));
        
        
        popup.pack(); // calculate width and height

        int popupWidth = popup.getPreferredSize().width;

        //top right under buttons
        int x = parent.getWidth() - popupWidth;
        int y = parent.getHeight() + 5;

        popup.show(parent, x, y);
    }
    
    
    private JMenuItem createShapeItem(String iconFile) {
        
        JMenuItem item = new JMenuItem();
         try{
            Image img = ImageIO.read(
                    getClass().getResource("/icons/" + iconFile));
            Image scaled = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            item.setIcon(new ImageIcon(scaled));
        } catch(IOException e) {
            System.out.println("Das Bild wurde nicht gefunden");
        }
         
        return item;
    }
    
    private void addShape(DrawPanel panel,ShapeType type) {
        DraggableShapeService shapeService = new DraggableShapeService(type);
        
        int x = (panel.getWidth() - shapeService.getWidth()) /2;
        int y = (panel.getHeight() - shapeService.getHeight()) /2;
        
        shapeService.setLocation(x,y);
        panel.add(shapeService);
        panel.repaint();
    }
    
}
