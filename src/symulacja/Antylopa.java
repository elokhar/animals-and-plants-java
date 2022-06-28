package symulacja;

import java.awt.Color;
import java.awt.Point;

public class Antylopa extends Zwierze{
    
    public Antylopa(int x, int y, Swiat sw) {
        super(x,y,3,7,new Color(189,101,21),sw);
    }
    
    @Override
    public void ruch() {
        Point dokad = new Point();
        losujObok(dokad, 2, false);
        swiat.przesun(x + dokad.x, y + dokad.y, this);
    }
    
    @Override 
    public boolean ucieczka() {
        if (swiat.generator.nextInt(2)==0) {
            return false;
        }
        else {
            Point dokad = new Point();
            losujObok(dokad, 1, true);
            if (dokad.equals(new Point(0,0))) {
                return false;
            }
            else {
                swiat.przesun(x + dokad.x, y + dokad.y, this);
                return true;
            }
        }
    }
    
    @Override
    public String gatunek() {
        return "Antylopa";
    }
    
    @Override
    public Organizm potomek(int x, int y) {
        return new Antylopa(x,y,swiat);
    }
}
