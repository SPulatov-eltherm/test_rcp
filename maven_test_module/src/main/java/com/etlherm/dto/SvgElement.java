/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.etlherm.dto;

import com.eltherm.services.SvgIconService;

/**
 *
 * @author Samir
 */
public class SvgElement {
    
    public final SvgIconService svg_icon;
    public final int x,y;
    
    public SvgElement(SvgIconService svg_icon, int x,int y){
        this.svg_icon = svg_icon;
        this.x = x;
        this.y = y;
    }
    
}
