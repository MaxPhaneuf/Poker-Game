/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static poker.Poker.POSX_PICK;
import static poker.Poker.SIZEX;
import static poker.Poker.SIZEY;

/**
 *
 * @author Max
 */
public class Player implements ActionListener{
    public int score;
    public JFrame mainWin;
    public JFrame picks;
    public JButton showHand;
    public JButton raise;
    public JButton call;
    public JButton fold;
    public JTextField raiseBox;
    public ArrayList<JPanel> handCards = new ArrayList<>();
    public CardManager hand;
    public boolean pressed;
    
    public Player(JFrame mainWin) {
       this.mainWin = mainWin;
        setUpWindow();
    }
    
    private void setUpWindow() {
        picks = new JFrame("Your Hand");
        picks.setSize(220, 250);
        picks.setLocationRelativeTo(mainWin);
        picks.setLocation(mainWin.getX() + mainWin.getWidth(), mainWin.getY());
        picks.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        picks.setLayout(null);
        picks.setResizable(false);
        setUpButtons();
        setUpText();
        
    }
    
    private void setUpButtons(){
        showHand = new JButton("Show");
        showHand.setLocation(110, 140);
        showHand.setSize(75,20);
        showHand.addActionListener(this);
        picks.getContentPane().add(showHand);
        
        raise = new JButton("Raise");
        raise.setLocation(20, 140);
        raise.setSize(75,20);
        raise.addActionListener(this);
        picks.getContentPane().add(raise);
        
        call = new JButton("Call");
        call.setLocation(110, 165);
        call.setSize(75,20);
        call.addActionListener(this);
        picks.getContentPane().add(call);
        
        fold = new JButton("Fold");
        fold.setLocation(45, 190);
        fold.setSize(120,20);
        fold.addActionListener(this);
        picks.getContentPane().add(fold);
    }
    
    private void setUpText(){
        raiseBox = new JTextField("0");
        raiseBox.setLocation(20, 165);
        raiseBox.setSize(75, 20);
        picks.getContentPane().add(raiseBox);
    }
            
    public void setUpHand() {
        hand = new CardManager();
        hand.pickUp(2);
        for (int i = 0; i < hand.cards.size(); i++) {
            createNewHand(i);
            handCards.get(i).add(hand.cards.get(i).getImage());
            picks.getContentPane().add(handCards.get(i));
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
    public void showHand(){
        for (int i = 0; i < hand.cards.size(); i++) {
                hand.cards.get(i).setInPlay();
            
         }
        
    }
    
    public void hideHand(){
        for (int i = 0; i < hand.cards.size(); i++) {
                hand.cards.get(i).setFaceDown();
            
         }
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showHand) {
            if(!pressed){
                pressed = true;
                showHand();
                showHand.setText("Hide");
            }else{
                pressed = false;
                hideHand();
                showHand.setText("Show");
            }
            
            
        }
    }
}
