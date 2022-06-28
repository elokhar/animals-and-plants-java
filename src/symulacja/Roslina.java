package symulacja;

import java.awt.Color;
import java.awt.Point;

public abstract class Roslina extends Organizm {
    
    protected float prawdopodobienstwo;
    
    public Roslina (int x, int y, int s, float p, Color k, Swiat sw) {
        super(x,y,s,0,k,sw);
        prawdopodobienstwo = p;
    }
    
    @Override
    public void akcja() {
        rozsiej(prawdopodobienstwo);
    }
    
    public void rozsiej(float p) {
        if (wiek > 0) 
	{
	int n = swiat.generator.nextInt(100)+1;
	if (n < (p * 100)) 
            {
                Point wspolrzedne = new Point();
                losujObok(wspolrzedne, 1, true);
                Organizm pot = potomek(x + wspolrzedne.x, y + wspolrzedne.y);
                swiat.dodajOrganizm(pot);
            }
	}
    }
    
    public char efektZjedzenia(Organizm zjadajacy) {
        return '\0';
    }
    
    
    
    
}
