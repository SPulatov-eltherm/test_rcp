/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.awt.Component;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author SPulatov
 */
public class ToolsService {
    
    //enum for mode switching 
    enum Mode {
        DRAW_ON,
        DRAW_OFF,
        CLEAR_ON,
        CLEAR_OFF,
        SHAPES_ON,
        SHAPES_OFF
    }
    
    //current mode that user has selected
    private Mode currentMode;
    
    //panel where action is performed
    private final DrawPanel visualBoardPanel; 
    
    //services for clear, draw and shapes button
    private final ClearButtonService clearButtonService;
    private final DrawButtonService drawButtonService;
    private final ShapesButtonService shapesButtonService;
    
    //list of buttons. Keep them to redraw them when clear button is clicked
    private List<JButton> toolButtons;
    
    //save component for shapes button to use it in popup menu
    private Component parent;
    
    
    public void setComponent(Component parent) {
        this.parent = parent;
    }
   
    
    //set mode and perform action based on mode
    public void setMode(Mode mode) {
        currentMode = mode;
        handleAction();
    }
    
    
    public Mode getMode() {
        return currentMode;
    }
    
    public void setToolsButtonList(List<JButton> toolButtons) {
        this.toolButtons = toolButtons;
    }
    
    public ToolsService(DrawPanel visualBoardPanel) {
        this.visualBoardPanel = visualBoardPanel;
        this.clearButtonService = new ClearButtonService();
        this.drawButtonService = new DrawButtonService();
        this.shapesButtonService = new ShapesButtonService();
    }
    
    
    //call relative service to perfrom action
    public void handleAction() {
        switch(currentMode){
            case CLEAR_ON -> {
                clearButtonService.clearPanel(visualBoardPanel,toolButtons);
            }
            case DRAW_ON -> {
                drawButtonService.enableDrawing(visualBoardPanel);
            }
            case DRAW_OFF -> {
                drawButtonService.disableDrawing(visualBoardPanel);
            }
            case SHAPES_ON -> {
                if(parent!=null) {
                    shapesButtonService.showShapesPopup(visualBoardPanel,parent);
                }
            }
        }
      
    }
}
