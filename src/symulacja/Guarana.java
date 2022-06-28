package symulacja;

import java.awt.Color;

public class Guarana extends Roslina {
    
    public Guarana(int x, int y, Swiat sw) {
        super(x, y, 0, 0.2f, Color.RED, sw);
    }    
    
    @Override
    public char efektZjedzenia(Organizm zjadajacy) {
        zjadajacy.zwiekszSila(3);
        return 'w';
    }
    
    @Override
    public String gatunek() {
        return "Guarana";
    }
    
    @Override
    protected Organizm potomek(int x, int y) {
        return new Guarana(x,y,swiat);
    }
}
