/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.io.IOException;
import java.io.InputStream;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.*;

/**
 *
 * @author Samir
 */



public class SvgIconService {
    
   private final GraphicsNode node;
   
    public SvgIconService(String resourcePath) {
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

    // Getter for the built GraphicsNode
    // This allows other classes (like VisualBlockService) to render the SVG
    public GraphicsNode getNode() {
        return node;
    }
}
