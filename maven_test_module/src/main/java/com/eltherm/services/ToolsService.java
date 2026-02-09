/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eltherm.services;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author SPulatov
 */
public class ToolsService {
    
    enum Mode {
        DRAW_ON,
        DRAW_OFF,
        CLEAR_ON,
        CLEAR_OFF,
        SHAPES_ON,
        SHAPES_OFF
    }
    
    private Mode currentMode;
    
    private final JPanel visualBoardPanel; 
    private final ClearButtonService clearButtonService;
    
    private List<JButton> toolButtons;
   
    
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
    
    public ToolsService(JPanel visualBoardPanel) {
        this.visualBoardPanel = visualBoardPanel;
        this.clearButtonService = new ClearButtonService();
    }
    
    
    //call relative service to perfrom action
    public void handleAction() {
        switch(currentMode){
            case CLEAR_ON -> {
                clearButtonService.clearPanel(visualBoardPanel,toolButtons);
            }
        }
      
    }
}
