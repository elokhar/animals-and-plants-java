package symulacja;

import java.awt.Color;

public class Mlecz extends Roslina {
    
    public Mlecz(int x, int y, Swiat sw) {
        super(x, y, 0, 0.4f, Color.YELLOW, sw);
    }
    
    @Override
    public void akcja() {
        for(int i=0; i<3; i++) {
            rozsiej(prawdopodobienstwo);
        }
    }
    
    @Override
    public String gatunek() {
        return "Mlecz";
    }
    
    @Override
    protected Organizm potomek(int x, int y) {
        return new Mlecz(x,y,swiat);
    }
}
