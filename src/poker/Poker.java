/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 *
 * @author Max
 */
public class Poker implements ActionListener{

    private JFrame mainWin;
    private JPanel table;
    private JPanel hand;
    private JPanel bet;
    private JPanel [] tableCards = new JPanel[5];
    private JPanel [] handCards = new JPanel[2];
    private JButton raise;
    private JButton call;
    private JButton fold;
    private Card[] cards = new Card[5];
    private int turn = 0;
    private int [] posX = {20, 110, 200, 290, 380};
    private int posY = 70;
    private int sizeX = 72;
    private int sizeY = 104;
    private boolean [] deckPique = new boolean[13];
    private boolean [] deckTrefle = new boolean[13];
    private boolean [] deckCoeur = new boolean[13];
    private boolean [] deckCarreau = new boolean[13];
    private Deck deck;
    
    public Poker(){
        setUpWindow();
        deck = new Deck();
        deck.showDeck();
        setUpTable();
              
    }
    
    
    
    
    private void setUpWindow(){
        
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 600);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        table = new JPanel();
        table.setSize(mainWin.getWidth(), mainWin.getHeight()/2);
        table.setLayout(null);
        table.setBackground(Color.green);
        hand = new JPanel();
        hand.setBounds(0 , mainWin.getHeight()/2 , mainWin.getWidth()/2,
                mainWin.getHeight()/2);
        hand.setLayout(new FlowLayout());
        hand.setBackground(Color.red);
        
        call = new JButton("Call");
        call.setBounds(20, 200, 200, 50);
        call.addActionListener(this);
        hand.add(call);
        
        
    }
    
    public void setUpTable(){
        
        
        
        for(int i = 0; i < tableCards.length; i++){
            createNewCard(i);
            addFace(i, cards[i].getImage());
                               
        }
        refresh();
        
    }
    public void refresh(){
        mainWin.getContentPane().add(table);
        mainWin.getContentPane().add(hand);
        mainWin.setVisible(true);
    }
    public void putInPlay(int i){
        table.remove(tableCards[i]);
        addCardPanel(i);
        addFace(i, cards[i].setInPlay());
        refresh();
    }
    
    public void createNewCard(int i){
        addCardPanel(i);
        cards[i] = deck.pickCard();
       
    }
            
    public void addCardPanel(int i){
        tableCards[i] = new JPanel();
        tableCards[i].setBounds(posX[i], posY, sizeX, sizeY);
        tableCards[i].setBackground(Color.GREEN);
        tableCards[i].setOpaque(true);
    }
    
    public void addFace(int i, BufferedImage name){
        
        JLabel image = new JLabel(new ImageIcon(name));
        tableCards[i].add(image);
        table.add(tableCards[i]);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == call){
            putInPlay(turn);
            turn++;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Poker();
    }
    
    
    
    
}
