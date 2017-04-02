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
    public static final int[] POSX = {40, 130, 220, 310, 400};
    public static final int[] POSX_PICK = {20, 110};
    public static final int POSY = 20;
    public static final int SIZEX = 72;
    public static final int SIZEY = 102;
    public boolean activeTest = false;
    private JFrame mainWin;
    public Table table;
    public Menu menu;
    public Player player;
    public Bets bets;
    public int time = 0;
        
    public Poker() {
        setUp();
        player.setUpHand();
        //cpu.setUpHand();
        table.setUpFlop();
        menu.nextStage.setEnabled(false);
        refresh();
    }
    
    private void setUp() {
        setUpWindow();
        table = new Table(mainWin);
        menu = new Menu(table, mainWin);
        setAction();
        player = new Player(mainWin);
        //cpu = new Bets(mainWin);
        
    }
       
    private void setUpWindow() {
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 380);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        mainWin.setResizable(false);
    }
    
    public void refresh() {
        mainWin.getContentPane().add(table.table);
        mainWin.getContentPane().add(menu.menu);
      //   mainWin.getContentPane().add(cpu.cpu);
        mainWin.setVisible(true);
        player.picks.setVisible(true);
        player.picks.setAlwaysOnTop(true);
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
                menu.results.setEnabled(true);
                //cpu.reveal();
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
   
    private void test() {
        menu.results.setEnabled(true);
        activeTest = true;
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
            menu.results.setEnabled(false);
            freshStart();
        }
    }
    
    private void resetEveryOne(){
        table.resetTable();
        table.discardDraw();
        player.resetHand();
        player.discardHand();
        //cpu.resetHand();
        //cpu.discardHand();
        refresh();
    }
    private void setUpTest(){
        menu.nextStage.setEnabled(false);
        resetEveryOne();
        Deck.clearAll();
        refresh();
    }
    
    private void restart() {
        menu.nextStage.setText("Flop");
        time = 0;
        resetEveryOne();
        player.setUpHand();
        //cpu.setUpHand();
        table.setUpFlop();
        menu.stage.setText("");
        refresh();
    }
    
    private void freshStart(){
        Deck.newDeck();
        Deck.clearDiscard();
        table.resetTable();
        player.resetHand();
        //cpu.resetHand();
        menu.nextStage.setText("Flop");
        time = 0;
        player.setUpHand();
        //cpu.setUpHand();
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
           resultPress();
        } else if (e.getSource() == menu.test) {
            test();
        }
    }
    
    public void resultPress(){
         if(!activeTest){
                menu.stage.setText(compareScore());
            }else{
                Results result = new Results(player.hand, table.draw);
                menu.stage.setText(result.result);
            }
    }
    public String compareScore(){
        Results playerResult = new Results(player.hand, table.draw);
        //Results playerResult = new Results(cpu.hand, table.draw);
        String winner = "";
        int score = playerResult.getScore();
        int score2 = playerResult.getScore();
        
        if(score > score2){
            winner = "Player wins with " + playerResult.result;
        }else if(score2 > score){
            winner = "House wins with " + playerResult.result;
        }else if(score == score2){
            if(playerResult.handHigh > playerResult.handHigh){
                winner = "Player wins with " + playerResult.result;
            }else if(playerResult.handHigh < playerResult.handHigh){
                winner = "House wins with " + playerResult.result;
            }else{
                winner = "Split pot ";
            }
        }
        return winner;
    }
    
    public static void main(String[] args) {
        
        new Poker();
       
    }
    
}
