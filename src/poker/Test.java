/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author Max
 */
public class Test {
    public Table table;
    public int test;
    
    public Test(Table table, int test){
        this.table = table;
        this.test = test;
        test();
    }
    private void test() {
            
        if (test == 0) {
            testHighCard();
        } else if (test == 1) {
            testPair();
        } else if (test == 2) {
            testTwoPair();
        } else if (test == 3) {
            testTriple();
        } else if (test == 4) {
            testStraight();
        } else if (test == 5) {
            testFlush();
        } else if (test == 6) {
            testFullHouse();
        } else if (test == 7) {
            testFour();
        } else if (test == 8) {
            testStraightFlush();
        } else if (test == 9) {
            testRoyalFlush();
        }
    }
    
    public void testPick(int nbr){
        table.draw.add(Deck.pickCard(nbr));
        for (int i = 0; i < nbr; i++) {
            table.createTable(i);
            table.draw.get(i).setInPlay();
        }
    }
    
    public void testHighCard(){
        table.draw = new CardManager();
        Deck.pickAs();
        testPick(1);
    }

    public void testPair() {
        table.draw = new CardManager();
        Deck.pickPair();
        testPick(2);
    }
    
    public void testTwoPair() {
        table.draw = new CardManager();
        Deck.pickTwoPair();
        testPick(4);
    }
    
    public void testTriple() {
        table.draw = new CardManager();
        Deck.pickTriple();
        testPick(3);
    }
    
    
    public void testStraight() {
        
        table.draw = new CardManager();
        Deck.pickStraight();
        testPick(5);
    }
    
    public void testFlush() {
        
        table.draw = new CardManager();
        Deck.pickFlush();
        testPick(5);
        
    }
    
    public void testFullHouse() {
        
        table.draw = new CardManager();
        Deck.pickFullHouse();
        testPick(5);
        
    }
    
    public void testFour(){
        table.draw = new CardManager();
        Deck.pickFour();
        testPick(4);
    }
    
    public void testStraightFlush(){
        table.draw = new CardManager();
        Deck.pickStraightFlush();
        testPick(5);
    }
    
    public void testRoyalFlush(){
        table.draw = new CardManager();
        Deck.pickRoyalFlush();
        testPick(5);
    }
    
}
