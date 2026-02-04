/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.*;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGSVGElement;

/**
 *
 * @author Samir
 */



public class SvgIconService {
    
   private GraphicsNode node;
   private double svgWidth;
   private double svgHeight;
   
   
    // Getter for the built GraphicsNode
    // This allows other classes (like VisualBlockService) to render the SVG
   
 
    public GraphicsNode getNode() {
        return node;
    }
    
    public double getSvgWidth() {
        return svgWidth;
    }

    public double getSvgHeight() {
        return svgHeight;
    }
    
    public void loadSvg(String  resourcePath) {
        try {
            // Get the XML parser class name for parsing SVG files
            String parser = XMLResourceDescriptor.getXMLParserClassName();

            // Create a factory that can parse SVG documents using the parser
            SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);

            // Load the SVG file as an InputStream from the classpath
            InputStream svg_img = getClass().getResourceAsStream(resourcePath);
            if (svg_img == null) {
                throw new IllegalArgumentException("SVG not found: " + resourcePath);
            }

            // Parse the SVG document into an SVGDocument object
            SVGDocument doc = factory.createSVGDocument(null, svg_img);
            
            SVGSVGElement root = (SVGSVGElement) doc.getDocumentElement();
            SVGRect viewBox = root.getViewBox().getBaseVal();
            
            if (viewBox != null && viewBox.getWidth() > 0 && viewBox.getHeight() > 0) {
                svgWidth = viewBox.getWidth();
                svgHeight = viewBox.getHeight();
            } else {
                // fallback: width/height атрибуты
                svgWidth = root.getWidth().getBaseVal().getValue();
                svgHeight = root.getHeight().getBaseVal().getValue();
            }

            // Setup a bridge context, which handles linking SVG elements to graphics nodes
            var context = new BridgeContext(new UserAgentAdapter());

            // Build the GraphicsNode tree from the SVGDocument
            // GraphicsNode is the object we can render in Java2D
            GVTBuilder builder = new GVTBuilder();
            node = builder.build(context, doc);

        } catch (IOException | IllegalArgumentException e) {
            // Wrap exceptions to indicate SVG loading failure
            throw new RuntimeException("Failed to load SVG", e);
        }
        
    }
    
    
    public BufferedImage svgToImage(SvgIconService svgIcon, int width, int height) {

        Rectangle bounds = svgIcon.getNode().getBounds().getBounds();

        BufferedImage image
                = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double scaleX = width / (double) bounds.width;
        double scaleY = height / (double) bounds.height;
        double scale = Math.min(scaleX, scaleY);

        // ❗ ВАЖНО: сначала translate, потом scale
        AffineTransform at = new AffineTransform();
        at.translate(
                (width - bounds.width * scale) / 2.0,
                (height - bounds.height * scale) / 2.0
        );
        at.scale(scale, scale);
        at.translate(-bounds.x, -bounds.y);

        g2.transform(at);

        svgIcon.getNode().paint(g2);
        g2.dispose();

        return image;
    }
}
