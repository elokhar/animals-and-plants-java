package symulacja;

import java.util.Comparator;

public class Sorter implements Comparator<Organizm> {
    
    @Override
    public int compare(Organizm a, Organizm b) {
        if(a.getIni() != b.getIni()) {
            return b.getIni() - a.getIni();
        }
        else {
            return b.getWiek() - a.getWiek();
        }
    }
}
