/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Max
 */
public class Poker implements ActionListener {
    public static final String[] STAGE = {"Pre-Flop", "Flop", "Turn", "River"};
    public static final int[] POSX = {20, 110, 200, 290, 380};
    public static final int[] POSX_PICK = {20, 110};
    public static final int POSY = 20;
    public static final int SIZEX = 72;
    public static final int SIZEY = 102;
    
    private JFrame mainWin;
    public Table table;
    public Menu menu;
    public Player player;
    public int time = 0;
        
    public Poker() {
        setUp();
        player.setUpHand();
        table.setUpFlop();
        refresh();
    }
    
    private void setUp() {
        setUpWindow();
        table = new Table(mainWin);
        menu = new Menu(table, mainWin);
        setAction();
        player = new Player(mainWin);
    }
       
    private void setUpWindow() {
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 400);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        mainWin.setResizable(false);
    }
    
    public void refresh() {
        mainWin.getContentPane().add(table.table);
        mainWin.getContentPane().add(menu.menu);
        mainWin.getContentPane().add(player.picks);
        mainWin.setVisible(true);
    }
    
    public void setAction(){
        menu.nextStage.addActionListener(this);
        menu.showDeck.addActionListener(this);
        menu.showDiscard.addActionListener(this);
        menu.newGame.addActionListener(this);
        menu.results.addActionListener(this);
        menu.test.addActionListener(this);
        menu.choice.addActionListener(this);
    }
    
    private void flopStage() {
        menu.nextStage.setText("Turn");
        for (int i = 0; i < table.draw.cards.size(); i++) {
            table.draw.cards.get(i).setInPlay();
        }
        deckCheck(1);
        table.setUpOne(3);
        time++;
    }
    
    private void openCard(int card, int check, String msg) {
        menu.nextStage.setText(msg);
        table.draw.cards.get(card).setInPlay();
        deckCheck(check);
        time++;
    }
    
    private void restart() {
        menu.nextStage.setText("Flop");
        time = 0;
        table.resetTable();
        table.discardDraw();
        player.resetHand();
        player.discardHand();
        player.setUpHand();
        table.setUpFlop();
        menu.stage.setText("");
        refresh();
    }
            
    private void nextStage() {
        switch (time) {
            case 0:
                flopStage();
                break;
            case 1:
                openCard(3,1 , "River");
                table.setUpOne(4);
                break;
            case 2:
                openCard(4, 3, "New Draw" );
                break;
            case 3:
                restart();
                break;
        }
    }
    
    public void deckCheck(int cards){
        if(Deck.deck.size() <= cards){
            menu.nextStage.setEnabled(false);
        }
    }
    private void setUpTest(){
        menu.nextStage.setEnabled(false);
        table.resetTable();
        table.discardDraw();
        player.resetHand();
        player.discardHand();
        Deck.clearAll();
        refresh();
    }
    
    private void test() {
        setUpTest();
        int test = menu.choice.getSelectedIndex();
            
        if(test == 0){
            table.testHighCard();
        }else if (test == 1 ) {
            table.testPair();
        }else if (test == 2) {
            table.testTwoPair();
        }else if (test == 3) {
            table.testTriple();
        }else if (test == 4) {
            table.testStraight();
        }else if (test == 5) {
            table.testFlush();
        }else if (test == 6) {
            table.testFullHouse();
        }else if (test == 7) {
            table.testFour();
        }else if (test == 8) {
            table.testStraightFlush();
        }else if (test == 9) {
            table.testRoyalFlush();
        }
    }
        
    private void newGame() {
        int choice = JOptionPane.showConfirmDialog(mainWin, "Are you sure?",
                "New Game?", JOptionPane.OK_CANCEL_OPTION);
        if (choice == 0) {
            menu.nextStage.setEnabled(true);
            freshStart();
        }
    }
    
    private void freshStart(){
        Deck.newDeck();
        Deck.clearDiscard();
        table.resetTable();
        player.resetHand();
        menu.nextStage.setText("Flop");
        time = 0;
        player.setUpHand();
        table.setUpFlop();
        menu.stage.setText("");
        refresh();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == menu.nextStage) {
            nextStage();
        } else if (e.getSource() == menu.showDeck) {
            Deck.showDeck();
        } else if (e.getSource() == menu.newGame) {
            newGame();
        } else if (e.getSource() == menu.showDiscard) {
            Deck.showDiscard();
        } else if (e.getSource() == menu.results) {
            menu.stage.setText(Results.compare(player.hand, table.draw));
        } else if (e.getSource() == menu.test) {
            test();
        }
    }
    
    public static void main(String[] args) {
        
        new Poker();
    }
    
}
