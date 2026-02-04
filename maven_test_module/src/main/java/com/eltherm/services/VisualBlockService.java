/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.apache.batik.gvt.GraphicsNode;

/**
 *
 * @author Samir
 */
public class VisualBlockService extends JPanel {

    private final SvgIconService svgIconService;
    private final GraphicsNode svgNode;
    
    private final double SCALE_FACTOR = 3;

    public VisualBlockService(String elementName) {
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(200,200));
        
        svgIconService = new SvgIconService();
        svgIconService.loadSvg("/svg/" + elementName + ".svg");
        
        svgNode = svgIconService.getNode();
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (svgNode == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Rectangle2D bounds = svgNode.getBounds();
        if (bounds == null || bounds.getWidth() == 0 || bounds.getHeight() == 0) {
            g2.dispose();
            return;
        }
        
        
        
        double svgWidth = svgIconService.getSvgWidth();
        double svgHeight = svgIconService.getSvgHeight();
        
         System.out.println("SvgWidth: " + svgWidth);
        System.out.println("SvgHeight: " + svgHeight);


        System.out.println("Panel size: " + getWidth() + "x" + getHeight());
        System.out.println("SVG bounds: " + bounds.getWidth() + "x" + bounds.getHeight());

        // Рассчитываем масштаб под весь размер панели
        double scaleY = getHeight() / bounds.getHeight() * SCALE_FACTOR;
        double scaleX = scaleY;
       
        
        Rectangle2D primitiveBounds = svgNode.getPrimitiveBounds();
        System.out.println("bounds: " + bounds);
        System.out.println("primitiveBounds: " + primitiveBounds);

        System.out.println("ScaleX: " + scaleX);
        System.out.println("ScaleY: " + scaleY);


        // Центрируем
        double scaledWidth = bounds.getWidth() * scaleX;
        double scaledHeight = bounds.getHeight() * scaleY;
        double tx = (getWidth() - scaledWidth) / 2;
        double ty = (getHeight() - scaledHeight) / 2;

        g2.translate(tx, ty);
        g2.scale(scaleX, scaleY);
        g2.translate(-bounds.getX(), -bounds.getY());
        

        svgNode.paint(g2);
        g2.dispose();
    }
}
