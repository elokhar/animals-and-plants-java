package symulacja;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class Swiat {
    
    private final int szerokosc;
    private final int wysokosc;
    private final int startX;
    private final int startY;
    private final double wp = 20; //wielkosc jednego pola
    private char wejscie;
    private final List<Organizm> lista;
    private final List<String> komunikaty;
    private Organizm[][] tab;
    private Czlowiek gracz;
    
    public Random generator;
        
    public Swiat(int a, int b, int x, int y) {
        this.komunikaty = new LinkedList<>();
        this.lista = new LinkedList<>();
            szerokosc = a;
            wysokosc = b;
            startX = x;
            startY = y;          
            stworzTablice();
            generator = new Random();
        }
       
    public void rysujSwiat(Graphics g) {
           Graphics2D g2d = (Graphics2D) g;
           rysujRamke(g2d);
           lista.forEach((org) -> {
               org.narysuj(g2d);
        });
    }
    
    
    
    public void dodajKomunikat(String komunikat) {
        komunikaty.add(komunikat);
    }
       
    public void wypiszKomunikaty() {
        komunikaty.forEach((x) -> System.out.println(x));
        komunikaty.clear();
    }
       
    public void wykonajTure() {
           List<Organizm> stara_lista = new LinkedList<>(lista);
           stara_lista.sort(new Sorter());
           List<Organizm> martweOrganizmy = new LinkedList<>();
           stara_lista.forEach((org) -> {
               if (org.czyMartwy()) {
                   martweOrganizmy.add(org);
               }
               else {
                   org.akcja();
                   org.zwiekszWiek(1);
               }
            });
            martweOrganizmy.forEach((org) -> {
               usun(org);
            });
       }
       
    public void dodajOrganizm(Organizm org) {
            Organizm cel = tab[org.getX()][org.getY()];
            if (cel == null /*&& cel.czyMartwy()==false*/) {
                //cel.zabij();
                lista.add(org);                
                tab[org.getX()][org.getY()] = org;
            }
            
    }

    public void dodajOrganizmy() {
        for(int i=0; i<2; i++) {
            this.dodajOrganizm(new Owca (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Wilk (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Lis (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Zolw (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Antylopa (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Mlecz (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Trawa (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new Guarana (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new WilczeJagody (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
            this.dodajOrganizm(new BarszczSosnowskiego (generator.nextInt(szerokosc),
                    generator.nextInt(wysokosc),this));
        
        }
        /*this.dodajOrganizm(new Owca (2,2,this));
        this.dodajOrganizm(new Owca (3,2,this));
        //this.dodajOrganizm(new Mlecz (1,2,this));
        //this.dodajOrganizm(new Mlecz (2,0,this));
        this.dodajOrganizm(new Wilk(3,3,this));
        this.dodajOrganizm(new Wilk(0,1,this));*/
    }
    
    public void usun(Organizm org) {
        lista.remove(org);
        tab[org.getX()][org.getY()] = null;
    }
    
    public void przesun(int x, int y, Organizm org) {
        if(org.getX() == x && org.getY() == y) return;
        if (tab[x][y] == null || tab[x][y].czyMartwy()) {
            tab[x][y] = org;
            tab[org.getX()][org.getY()] = null;
            org.setX(x);
            org.setY(y);
        }
        else org.kolizja(tab[x][y]);
    }
    
    public int getWysokosc() {
        return wysokosc;
    }
    
    public int getSzerokosc() {
        return szerokosc;
    }
    
    public int getStartX() {
        return startX;
    }
    
    public int getStartY() {
        return startY;
    }
    
    public double getWP() {
        return wp;
    }
    
    public Organizm getTabAt(int x, int y) {
        return tab[x][y];
    }
    
    private void rysujRamke(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        Rectangle2D pole;
        for (int i = 0; i < szerokosc; i++) 
	{
                pole = new Rectangle2D.Double(startX*wp, (startY + i+1)*wp, wp, wp);
                g2d.fill(pole);
		//narysujZnak(startX, startY + i + 1, '#');
                pole = new Rectangle2D.Double((startX + wysokosc + 1)*wp, (startY + i + 1)*wp, wp, wp);
		//narysujZnak(startX + wysokosc + 1, startY + i + 1, '#');
                g2d.fill(pole);
	}
	for (int i = 0; i < wysokosc + 2; i++) 
	{
                pole = new Rectangle2D.Double((startX + i)*wp, startY*wp, wp, wp);
                g2d.fill(pole);
		pole = new Rectangle2D.Double((startX + i)*wp, (startY + szerokosc + 1)*wp, wp, wp);
		//narysujZnak(startX + i, startY + szerokosc + 1, '#');
                g2d.fill(pole);
	}
    }
    
    private void stworzTablice() {
        tab = new Organizm[szerokosc][wysokosc];
        for (int i = 0; i < wysokosc; i++) {
            for (int j = 0; j < szerokosc; j++) {
                tab[i][j] = null;
            }
	}
    }
}
