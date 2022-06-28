package symulacja;

import java.awt.Color;
import java.awt.Point;

public abstract class Zwierze extends Organizm{
   
    public Zwierze(int x, int y, int s, int i, Color k, Swiat sw) {
        super(x, y, s, i, k, sw);
    }
            
   protected void ruch() {
       Point wspolrzedne = new Point();
       losujObok(wspolrzedne,1,false);
       swiat.przesun(x + wspolrzedne.x, y + wspolrzedne.y, this);
   } 
   
   public boolean rozmnazanie() {
       Point wspolrzedne = new Point();
       losujObok(wspolrzedne, 1, true);
       if (wspolrzedne.equals(new Point(0,0))) {
           return false;
       }
       else {
           swiat.dodajOrganizm(potomek(x + wspolrzedne.x, y + wspolrzedne.y));
           return true;
       }      
   }
   
   @Override
   public void akcja() {
       ruch();
   }
   
   @Override
   public boolean czyOdbilAtak(Organizm atakujacy) {
       return false;
   }
   
   public boolean ucieczka() {
       return false;
   }
}
