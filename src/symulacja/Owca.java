package symulacja;

import java.awt.Color;

public class Owca extends Zwierze{
    
    public Owca(int x, int y, Swiat sw) {
        super(x,y,4,4,Color.LIGHT_GRAY,sw);
    }
    
    @Override
    public String gatunek() {
        return "Owca";
    }
    
    @Override
    public Organizm potomek(int x, int y) {
        return new Owca(x,y,swiat);
    }
}
