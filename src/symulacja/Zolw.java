/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symulacja;

import java.awt.Color;

/**
 *
 * @author Piotrek
 */
public class Zolw extends Zwierze{
    
    public Zolw(int x, int y, Swiat sw) {
        super(x,y,2,1,Color.CYAN,sw);
    }
    
    @Override
    public void akcja() {
        if (swiat.generator.nextInt(4) < 1) ruch();
    }
    
    @Override
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return atakujacy.getSila()<5;
    } 
    
    @Override
    public String gatunek() {
        return "Zolw";
    }
    
    @Override
    public Organizm potomek(int x, int y) {
        return new Zolw(x,y,swiat);
    }
}
