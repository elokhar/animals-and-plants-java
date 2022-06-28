package symulacja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;


public abstract class Organizm {
    
    public abstract void akcja();
    public abstract String gatunek();
    
    protected int x;
    protected int y;
    protected int sila;
    protected int inicjatywa;
    protected int wiek = 0;
    protected Color kolor;
    protected boolean martwy = false;
    protected Swiat swiat;
    
    protected abstract Organizm potomek(int x, int y);
            
    public Organizm(int x, int y, int s, int i, Color kolor, Swiat sw/*, int w*/) {
        this.x=x;
        this.y=y;
        this.sila=s;
        this.inicjatywa=i;
        this.kolor = kolor;
        this.swiat=sw;
        //this.wiek=w;
    }
    
    public void kolizja(Organizm drugi){
        if(drugi instanceof Zwierze) {
            if(this.gatunek().equals(drugi.gatunek())) {
                if (this.getWiek() == 0 || drugi.getWiek() == 0) return;
		boolean sukces = ((Zwierze)drugi).rozmnazanie();
		if (sukces) {
                    swiat.dodajKomunikat(this.nazwa() 
                        + " rozmnaza sie z " + drugi.nazwa());
                }				
            }
            else {
                zaatakuj((Zwierze)drugi);
            }
        }
        else if (drugi instanceof Roslina) {
            zjedz((Roslina)drugi);
        }
    }
    
    public boolean czyOdbilAtak(Organizm atakujacy) {
        return false;
    }       
    
    public void narysuj(Graphics2D g2d) {
        double wp = swiat.getWP();
        g2d.setColor(kolor);
        Rectangle2D pole = new Rectangle2D.Double((swiat.getStartX()+x+1)*wp, 
                (swiat.getStartY()+y+1)*wp, wp, wp);
        g2d.fill(pole);
    }
    
    protected void zaatakuj(Zwierze drugi) {
        String komunikat;
	Organizm atakujacy = this;
	Organizm atakowany = drugi;
	komunikat = atakujacy.nazwa() + " atakuje " + atakowany.nazwa() + "!";
	swiat.dodajKomunikat(komunikat);
        
        int celX = drugi.getX();
	int celY = drugi.getY();
        
        if(drugi.ucieczka()) {
            komunikat = drugi.nazwa() + " ucieka z pola (" + celX + ',' 
                    + celY + ")!";
            swiat.dodajKomunikat(komunikat);
            swiat.przesun(celX,celY,this);
            return;
        }
        
        if (drugi.czyOdbilAtak(this)) {
            komunikat = atakowany.nazwa() + " odbija atak!";
            swiat.dodajKomunikat(komunikat);
        }
        else {
            walcz(drugi);
        }            
    }
    
    protected void walcz(Zwierze drugi) {
        String wygrany;
        String komunikat;
        if (this.getSila() >= drugi.getSila()) {
            drugi.zabij();
            swiat.przesun(drugi.getX(), drugi.getY(), this);
            wygrany = this.nazwa();
        }
        else {
            this.zabij();
            wygrany = drugi.nazwa();
        }
        
        komunikat = wygrany + " wygrywa walke";
        swiat.dodajKomunikat(komunikat);
    }
    
    protected void zjedz(Roslina drugi) {
        String komunikat;
        char efekt;
        String efekt_str = "";
        
        komunikat = this.nazwa() + " zjada " + drugi.nazwa(); swiat.dodajKomunikat(komunikat);
        efekt = drugi.efektZjedzenia(this);
        
        if (efekt != '\0') 
	{
		switch (efekt) 
		{
		case 'z': 
			efekt_str = " zabija ";
			break;
		case 'w':
			efekt_str = " wzmacnia ";
			break;
		}
		komunikat = drugi.nazwa() + efekt_str + this.nazwa();
		swiat.dodajKomunikat(komunikat);
	}
        
	drugi.zabij();
	swiat.przesun(drugi.getX(), drugi.getY(), this);
    }
    
    protected void losujObok(Point wspolrzedne, int zasieg, boolean tylkoWolne) {
        int zajete = 0;
	boolean[] sasiedzi = new boolean[8];

	wpiszSasiadow(sasiedzi, zasieg);

	if (tylkoWolne)
		wybierzWolnych(sasiedzi, zasieg);

	int licznik = swiat.generator.nextInt(8);
	int wynik = 0;
	while (licznik >= 0 && zajete<8) 
	{
		wynik = (wynik + 1) % 8;
		if (sasiedzi[wynik] != false) 
		{
			licznik--;
			zajete = 0;
		}		
		else
			zajete++;
	}

	if (zajete >= 8) {
		wspolrzedne.x = 0;
		wspolrzedne.y = 0;
		return;
	}

	zamienNaWspolrzedne(wynik, zasieg, wspolrzedne);	
    }
    
    protected void zamienNaWspolrzedne(int wynik, int zasieg, 
            Point wspolrzedne) {
        
        if (wynik < 3) 
	{
		wspolrzedne.x = (wynik - 1)*zasieg;
		wspolrzedne.y = -zasieg;
	}
	else if (wynik == 3) 
	{
		wspolrzedne.x = -zasieg;
		wspolrzedne.y = 0;
	}
	else if (wynik == 4) 
	{
		wspolrzedne.x = zasieg;
		wspolrzedne.y = 0;
	}
	else 
	{
		wspolrzedne.x = (wynik - 6)*zasieg;
		wspolrzedne.y = zasieg;
	}  
    }
    
    protected void wpiszSasiadow(boolean[] sasiedzi, int zasieg) {
	
	for (int i = 0; i < 8; i++) 
	{
		sasiedzi[i] = true;
	}
	if (this.getX() + 1 - zasieg <= 0)
		sasiedzi[0] = sasiedzi[3] = sasiedzi[5] = false;
	if (this.getX() - 1 + zasieg >= swiat.getSzerokosc() - 1)
		sasiedzi[2] = sasiedzi[4] = sasiedzi[7] = false;
	if (this.getY() + 1 - zasieg <= 0)
		sasiedzi[0] = sasiedzi[1] = sasiedzi[2] = false;
	if (this.getY() - 1 + zasieg >= swiat.getWysokosc() - 1)
		sasiedzi[5] = sasiedzi[6] = sasiedzi[7] = false;		
    }
    
    protected void wybierzWolnych(boolean[] sasiedzi, int zasieg) {
        Point wspolrzedne = new Point();
	for (int i = 0; i < 8; i++) 
	{           
            if (sasiedzi[i] == true) 
            {                   
                zamienNaWspolrzedne(i, zasieg, wspolrzedne);
                if (swiat.getTabAt(x + wspolrzedne.x, y + wspolrzedne.y) != null) {
                    sasiedzi[i] = false;
                }
            }
	}
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getSila() {
        return sila;
    }
    
    public int getWiek() {
        return wiek;
    }
    
    public int getIni() {
        return inicjatywa;
    }
    
    //getter grafika
    
    public boolean czyMartwy() {
        return martwy;
    }
    
    public String nazwa() {
        String nazwa = gatunek() + '(' + x + ',' + y + ')';
        return nazwa;
    }
    
    public void zabij() {
        martwy = true;
    }
    
    public void setX(int n) {
        x = n;
    }
    
    public void setY(int n) {
        y = n;
    }
    
    public void setWiek(int n) {
        wiek = n;
    }
    
    public void setSila(int n) {
        sila = n;
    }
    
    public void zwiekszSila(int n) {
        sila += n;
    }
    
    public void zwiekszWiek(int n) {
        wiek += n;
    }
}
