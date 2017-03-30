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

    public Card[] deck = new Card[52]; 
    
    public boolean [] isCard = new boolean[52];
    
    public Deck() {
        createDeck();
      
    }

    public void createDeck() {
        int color = 0;
        int value = 1;
        for(int i = 0; i < deck.length; i++) {
            deck[i] = new Card(color, value);
            if(value == 13){
                value = 1;
                color++;
            }else{
                value++;
            }
            
        }
    }
    
    public Card pickCard(){
        int num = rdnGenerator52();
        while(this.deck[num].isPicked()){
             num = rdnGenerator52();  
        }
        this.deck[num].pick();
        return this.deck[num];
    }
    
    public int rdnGenerator4(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(4);
        return randomInt;
    }
    
    public int rdnGenerator14(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(13) + 1;
        return randomInt;
    }
    
    public int rdnGenerator52(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(52);
        return randomInt;
    }
    
    public void showDeck(){
        JFrame mainWin;
        mainWin = new JFrame("Deck");
        mainWin.setSize(1100, 500);
        mainWin.setLocationRelativeTo(null);
        mainWin.setLayout(new GridLayout(4, 13, 20, 20));
        JLabel image = null;
        for(int i = 0; i < deck.length; i++){
            JPanel temp = new JPanel();
            
            temp.setOpaque(true);
            System.out.println(deck[i].getName());
            
            if(!deck[i].isPicked()){
                image = new JLabel(new ImageIcon(deck[i].addImage(deck[i].getName())));
            }else{
                image = new JLabel(new ImageIcon("CardBack.png"));
            }
            temp.add(image);
            mainWin.getContentPane().add(temp);
        }
        mainWin.setVisible(true);
    }
}
