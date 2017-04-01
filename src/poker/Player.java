/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static poker.Poker.POSX_PICK;
import static poker.Poker.SIZEX;
import static poker.Poker.SIZEY;

/**
 *
 * @author Max
 */
public class Player {
    public int score;
    public JPanel picks;
    public JLabel yourHand;
    private JFrame mainWin;
    public ArrayList<JPanel> handCards = new ArrayList<>();
    public CardManager hand;
    
    public Player(JFrame mainWin) {
        this.mainWin = mainWin;
        setUpPicks();
    }
    
    public void setUpPicks() {
        picks = new JPanel();
        picks.setBounds(mainWin.getWidth() / 2, mainWin.getHeight() / 2,
                mainWin.getWidth() / 2, mainWin.getHeight() / 2);
        picks.setLayout(null);
        yourHand = new JLabel("Your hand");
        yourHand.setBounds(70, 0, 100,20);
        picks.add(yourHand);
    }
    
    public void setUpHand() {
        hand = new CardManager();
        hand.pickUp(2);
        for (int i = 0; i < hand.cards.size(); i++) {
            createNewHand(i);
            handCards.get(i).add(hand.cards.get(i).getImage());
            hand.cards.get(i).setInPlay();
            picks.add(handCards.get(i));
        }
        
    }
    
    public void createNewHand(int i) {
        handCards.add(new JPanel());
        handCards.get(i).setBounds(POSX_PICK[i], 20, SIZEX, SIZEY);
    }
    
    public void resetHand() {
           
        while (!handCards.isEmpty()) {
            handCards.get(0).setVisible(false);
            handCards.remove(0);
        }
        
    }
    
    public void discardHand(){
        while (!hand.cards.isEmpty()) {
            Deck.discard(hand.remove(1));
        }
    }
}
