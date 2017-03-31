/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Max
 */
public class Deck {

    public static ArrayList<Card> deck = createDeck(); 
    
    
    public static void newDeck(){
        deck = createDeck();        
    }
    public static ArrayList<Card> createDeck() {
        int color = rdn4();
        int value = rdn14();
        ArrayList<Card> deckTemp = new ArrayList<>();
        Card card = null;
        int i = 0;
        while(i < 52) {
            
            card = new Card(color, value);
            if(!deckTemp.toString().contains(card.toString())){
                deckTemp.add(card);
                i++;
            }
            color = rdn4();
            value = rdn14();    
            
        }
        return deckTemp;
        
    }
    
    public static Card pickCard(){
        return deck.remove(0);
    }
    
    public static int  rdn4(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(4);
        return randomInt;
    }
    
    public static int rdn14(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(13) + 1;
        return randomInt;
    }
    
    public int rdn52(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(52);
        return randomInt;
    }
    
    public static void showDeck(){
        JFrame mainWin;
        mainWin = new JFrame("Deck");
        mainWin.setSize(1300, 525);
        mainWin.setLocationRelativeTo(null);
        mainWin.setLayout(new GridLayout(4, 13, 20, 20));
        JLabel image = null;
        for(int i = 0; i < deck.size(); i++){
            JPanel temp = new JPanel();
            
            temp.setOpaque(true);
            //System.out.println(deck.get(i).getName());
            image = new JLabel(new ImageIcon(deck.get(i).addImage(deck.get(i).getName())));
            
            temp.add(image);
            mainWin.getContentPane().add(temp);
        }
        mainWin.setVisible(true);
    }
    public String toString(){
        return getNames();
    }
    
    private String getNames(){
        String names = "";
        for(Card card: deck){
            names = names + card.toString() + " ";
        }
        return names;
    }
}
