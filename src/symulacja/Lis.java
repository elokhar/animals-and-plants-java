package symulacja;

import java.awt.Color;
import java.awt.Point;

public class Lis extends Zwierze{
    
    public Lis(int x, int y, Swiat sw) {
        super(x,y,3,7,Color.ORANGE,sw);
    }
    
    @Override
    public void ruch() {
        Point dokad = new Point();
        losujObok(dokad,1,false);
        Organizm cel = swiat.getTabAt(x + dokad.x, y + dokad.y);
        if (cel == null || cel.getSila()<sila) {
            swiat.przesun(x + dokad.x, y + dokad.y, this);
        }       
    }
    
    @Override
    public String gatunek() {
        return "Lis";
    }
    
    @Override
    public Organizm potomek(int x, int y) {
        return new Lis(x,y,swiat);
    }
}
