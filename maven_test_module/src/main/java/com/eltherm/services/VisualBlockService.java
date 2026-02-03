/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Samir
 */
public class VisualBlockService extends JPanel {

    private static final int SIZE = 200;     // base panel size
    private static final int PADDING = 6;    // padding around the SVG
    private static final double SCALE_FACTOR = 2.0; // scale the image by 2x

    private final SvgIconService icon;

    public VisualBlockService(String elementName) {
        // Load SVG using SvgIconService
        this.icon = new SvgIconService("/svg/" + elementName + ".svg");

        //Optional: make the panel visible with background and border
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
         
        // Get the bounding rectangle of the SVG
        Rectangle bounds = icon.getNode().getBounds().getBounds();

        // Compute panel size based on SVG size and scale factor, plus padding
        int width = (int) Math.round(bounds.width) + PADDING * 2;
        int height = (int) Math.round(bounds.height) + PADDING * 2;

        // Set preferred size and size so that layout managers or null layout know the size
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Create a copy of Graphics2D for safe rendering
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - PADDING * 2; // available width inside panel
        int h = getHeight() - PADDING * 2; // available height inside panel

        // Get SVG bounds again
        Rectangle bounds = icon.getNode().getBounds().getBounds();

        // Scale SVG to 2x, preserving proportions
        double scaleY = (h / (double) bounds.height) * SCALE_FACTOR;
        double scaleX = scaleY; // keep aspect ratio

        // Compute offset to center SVG inside the panel
        double offsetX = (w - bounds.width * scaleX) / 2.0;
        double offsetY = (h - bounds.height * scaleY) / 2.0;

        // Apply translation and scaling transform
        AffineTransform at = new AffineTransform();
        at.translate(PADDING + offsetX, PADDING + offsetY);
        at.scale(scaleX, scaleY);

        g2.transform(at);

        // Paint the SVG onto the panel
        icon.getNode().paint(g2);

        g2.dispose(); // clean up Graphics2D
    }
}
