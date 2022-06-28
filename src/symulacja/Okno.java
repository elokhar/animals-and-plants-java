package symulacja;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Okno extends JFrame{
    
    private final Swiat swiat;
    
    public Okno() {
        super("Piotr Wojciechowski 175757 - symulacja"); 
        swiat = new Swiat(7,7,1,1);
    }
    
    public void start() {
        swiat.dodajOrganizmy();
        setPreferredSize(new Dimension(400,300));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                swiat.rysujSwiat(g);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                repaint();
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        swiat.dodajKomunikat("Wykonano ture");
                        swiat.wykonajTure();              
                        break;
                    case KeyEvent.VK_U:
                        //swiat.gracz.aktywujUmiejetnosc();
                        break;
                    case KeyEvent.VK_Z:
                        //swiat.zapisz();
                        break;
                    case KeyEvent.VK_W:
                        //swiat.wczytaj();
                        break;
                }               
                
                
                swiat.wypiszKomunikaty();
            }
        });
        setVisible(true);
        
        
    }
}
