package symulacja;

import java.awt.Color;
import java.awt.Point;

public class BarszczSosnowskiego extends Roslina{
    
    public BarszczSosnowskiego(int x, int y, Swiat sw) {
        super(x, y, 10, 0.1f, Color.WHITE, sw);
    }  
    
    @Override
    public void akcja() {
        boolean[] sasiedzi = new boolean[8];
	Point gdzie = new Point();
	String komunikat;
	wpiszSasiadow(sasiedzi, 1);
	Organizm sasiad;
	for (int i = 0; i < 8; i++) {
		if (sasiedzi[i] == true) {
			zamienNaWspolrzedne(i, 1, gdzie);
			sasiad = swiat.getTabAt(x + gdzie.x, y + gdzie.y);
			if (sasiad != null && 
				sasiad instanceof Zwierze &&
				sasiad.czyMartwy()==false) {
				sasiad.zabij();
				komunikat = this.nazwa() + " zabija " + sasiad.nazwa();
				swiat.dodajKomunikat(komunikat);
			}
				
		}
	}
	rozsiej(prawdopodobienstwo);
    }
    
    @Override
    public char efektZjedzenia(Organizm zjadajacy) {
        zjadajacy.zabij();
        return 'z';
    }
    
    @Override
    public String gatunek() {
        return "BarszczSosnowskiego";
    }
    
    @Override
    protected Organizm potomek(int x, int y) {
        return new BarszczSosnowskiego(x,y,swiat);
    }
}
