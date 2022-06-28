package symulacja;

import java.awt.Color;

public class Wilk extends Zwierze{
    
    public Wilk(int x, int y, Swiat sw) {
        super(x,y,9,5,Color.DARK_GRAY,sw);
    }
    
    @Override
    public String gatunek() {
        return "Wilk";
    }
    
    @Override
    public Organizm potomek(int x, int y) {
        return new Wilk(x,y,swiat);
    }
}
