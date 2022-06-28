package symulacja;

import java.awt.Color;

public class Trawa extends Roslina{
    
    public Trawa(int x, int y, Swiat sw) {
        super(x, y, 0, 0.4f, Color.GREEN, sw);
    }    
    
    @Override
    public String gatunek() {
        return "Trawa";
    }
    
    @Override
    protected Organizm potomek(int x, int y) {
        return new Trawa(x,y,swiat);
    }
}
