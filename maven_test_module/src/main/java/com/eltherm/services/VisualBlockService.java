/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.batik.gvt.GraphicsNode;

/**
 *
 * @author Samir
 */
public class VisualBlockService extends JPanel {

    private final SvgIconService svgIconService;
    private final GraphicsNode svgNode;

    // Optional factor to slightly enlarge the SVG rendering
    private final double SCALE_FACTOR = 4;
    
    private boolean hovered = false; // flag to track mouse hover
        
    final static float dash1[] = {10.0f}; // flag to build dashed stroke
    
    private Point dragOffset; // mouse position inside panel when drag starts



    
    
    
    public VisualBlockService(String elementName) {
        setOpaque(true);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 200));

        // Load the SVG using the service
        svgIconService = new SvgIconService();
        svgIconService.loadSvg("/svg/" + elementName + ".svg");

        // Cache the GraphicsNode for painting
        svgNode = svgIconService.getNode();
        
        
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Remember where inside the panel the mouse was pressed
                    dragOffset = e.getPoint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragOffset == null) {
                    return;
                }

                // Current mouse position on screen
                Point mouseOnScreen = e.getLocationOnScreen();

                // Parent location on screen
                Point parentOnScreen = getParent().getLocationOnScreen();

                // Calculate new panel position
                int newX = mouseOnScreen.x - parentOnScreen.x - dragOffset.x;
                int newY = mouseOnScreen.y - parentOnScreen.y - dragOffset.y;

                setLocation(newX, newY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragOffset = null;
            }
        };
        
        
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        
        
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (svgNode == null) {
            return; // Nothing to draw
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the bounding box of the SVG content
        Rectangle2D bounds = svgNode.getBounds();
        if (bounds == null || bounds.getWidth() == 0 || bounds.getHeight() == 0) {
            g2.dispose();
            return; // Avoid division by zero
        }

        // Calculate scale to fit SVG inside panel, optionally enlarge
        double scaleY = (getHeight() / bounds.getHeight()) * SCALE_FACTOR;
        double scaleX = scaleY; // Preserve aspect ratio

        // Center the SVG in the panel
        double tx = (getWidth() - bounds.getWidth() * scaleX) / 2;
        double ty = (getHeight() - bounds.getHeight() * scaleY) / 2;

        AffineTransform oldTransform = g2.getTransform();

        /* ==== SVG DRAWING ==== */
        g2.translate(tx, ty);
        g2.scale(scaleX, scaleY);
        g2.translate(-bounds.getX(), -bounds.getY());
        svgNode.paint(g2);

        /* ==== RESET TRANSFORM ==== */
        g2.setTransform(oldTransform);

        /* ==== BORDER DRAWING ==== */
        g2.setColor(hovered ? new Color(37, 150, 190) : Color.white);
        g2.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f));
        g2.drawRect(1, 1, getWidth() - 3, getHeight() - 3);

        // Paint the SVG
        svgNode.paint(g2); 
        g2.dispose();
    }
}
