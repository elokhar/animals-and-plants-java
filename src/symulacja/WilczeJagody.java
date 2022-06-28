package symulacja;

import java.awt.Color;

public class WilczeJagody extends Roslina {
    
    public WilczeJagody(int x, int y, Swiat sw) {
        super(x, y, 99, 0.1f, new Color(0,0,80), sw);
    }    
    
    @Override
    public char efektZjedzenia(Organizm zjadajacy) {
        zjadajacy.zabij();
        return 'z';
    }
    
    @Override
    public String gatunek() {
        return "WilczeJagody";
    }
    
    @Override
    protected Organizm potomek(int x, int y) {
        return new WilczeJagody(x,y,swiat);
    }
}
