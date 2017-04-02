/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Max
 */
public class Table {
    
    private JFrame mainWin;
    public JPanel table;
    public ArrayList<JPanel> tableCards = new ArrayList<>();
    public CardManager draw;
    public int[] posX = {30, 120, 210, 300, 390};
    public int[] posXPicks = {20, 110};
    public int posY = 20;
    public int sizeX = 72;
    public int sizeY = 102;
    
    public Table(JFrame mainWin){
        this.mainWin = mainWin;
        setUpTable();
    }
    
   
    public void setUpTable() {
        table = new JPanel();
        table.setSize(mainWin.getWidth(), (mainWin.getHeight() / 3) + 20);
        table.setLayout(null);
        table.setBackground(Color.BLACK);
    }
    
    public void setUpFlop() {
        draw = new CardManager();
        Deck.burn();
        draw.pickUp(3);
        
        for (int i = 0; i < draw.cards.size(); i++) {
            createTable(i);
        }
    }
    
    public void setUpOne(int card) {
        Deck.burn();
        draw.pickUp(1);
        createTable(card);
        
    }
    /**
     * Create the cards a table
     * @param i card index 
     */
    public void createTable(int i) {
        
        createNewTableCard(i);
        tableCards.get(i).add(draw.cards.get(i).getImage());
        table.add(tableCards.get(i));
        
    }
    
    public void createNewTableCard(int i) {
        
        tableCards.add(new JPanel());
        tableCards.get(i).setBounds(posX[i], posY, sizeX, sizeY);
        tableCards.get(i).setBackground(Color.BLACK);
    }
    
    public void resetTable() {
        while (!tableCards.isEmpty()) {
            tableCards.get(0).setVisible(false);
            tableCards.remove(0);
        }
        
    }
    
    public void discardDraw(){
        while (!draw.cards.isEmpty()) {
            Deck.discard(draw.remove(1));
        }
        
    }
    public void testPick(int nbr){
        draw.add(Deck.pickCard(nbr));
        for (int i = 0; i < nbr; i++) {
            createTable(i);
            draw.cards.get(i).setInPlay();
        }
    }
    
    public void testHighCard(){
        draw = new CardManager();
        Deck.pickAs();
        testPick(1);
    }

    public void testPair() {
        draw = new CardManager();
        Deck.pickPair();
        testPick(2);
    }
    
    public void testTwoPair() {
        draw = new CardManager();
        Deck.pickTwoPair();
        testPick(4);
    }
    
    public void testTriple() {
        draw = new CardManager();
        Deck.pickTriple();
        testPick(3);
    }
    
    
    public void testStraight() {
        
        draw = new CardManager();
        Deck.pickStraight();
        testPick(5);
    }
    
    public void testFlush() {
        
        draw = new CardManager();
        Deck.pickFlush();
        testPick(5);
        
    }
    
    public void testFullHouse() {
        
        draw = new CardManager();
        Deck.pickFullHouse();
        testPick(5);
        
    }
    
    public void testFour(){
        draw = new CardManager();
        Deck.pickFour();
        testPick(4);
    }
    
    public void testStraightFlush(){
        draw = new CardManager();
        Deck.pickStraightFlush();
        testPick(5);
    }
    
    public void testRoyalFlush(){
        draw = new CardManager();
        Deck.pickRoyalFlush();
        testPick(5);
    }
}
