/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Max
 */
public class Poker implements ActionListener {
    
    private JFrame mainWin;
    private JPanel table;
    private JPanel menu;
    private JPanel picks;
    private JTextField stage;
    private JComboBox choice;
    private ArrayList<JPanel> tableCards = new ArrayList<>();
    private ArrayList<JPanel> handCards = new ArrayList<>();
    private JButton showDeck;
    private JButton nextStage;
    private JButton showDiscard;
    private JButton newGame;
    private JButton results;
    private JButton test;
    private boolean turn = false;
    private int[] posX = {20, 110, 200, 290, 380};
    private int[] posXPicks = {20, 110};
    private int posY = 20;
    private int sizeX = 72;
    private int sizeY = 102;
    private Hand draw;
    private Hand hand;
    private int time = 0;
    private String[] textStage = {"Pre-Flop", "Flop", "Turn", "River"};
    
    public Poker() {
        setUp();
        setUpHand();
        setUpFlop();
        refresh();
        
    }
    
    private void setUp() {
        setUpWindow();
        setUpTable();
        setUpMenu();
        setUpPicks();
    }
    
   
    
    private void setUpWindow() {
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 400);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
    }
    
    private void setUpTable() {
        table = new JPanel();
        table.setSize(mainWin.getWidth(), mainWin.getHeight() / 2);
        table.setLayout(null);
        table.setBackground(Color.green);
    }
    
    private void setUpMenu() {
        menu = new JPanel();
        menu.setBounds(0, mainWin.getHeight() / 2, mainWin.getWidth() / 2,
                mainWin.getHeight() / 2);
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.setBackground(Color.red);
        setUpButtons();
        setUpText();
        addToMenu();
        
    }
    
    private void addToMenu() {
        menu.add(nextStage);
        menu.add(showDeck);
        menu.add(showDiscard);
        menu.add(newGame);
        menu.add(stage);
        menu.add(results);
        menu.add(choice);
        menu.add(test);
    }
    
    private void setUpText() {
        stage = new JTextField();
        stage.setColumns(10);
        stage.setAlignmentX(SwingConstants.CENTER);
        stage.setEditable(false);
        stage.setText(textStage[time]);
        choice = new JComboBox();
        for (String temp : Results.goals) {
            choice.addItem(temp);
        }
        choice.addActionListener(this);
    }
    
    private void setUpPicks() {
        picks = new JPanel();
        picks.setBounds(mainWin.getWidth() / 2, mainWin.getHeight() / 2,
                mainWin.getWidth() / 2, mainWin.getHeight() / 2);
        picks.setLayout(null);
        picks.setBackground(Color.GREEN);
    }
    
    private void setUpButtons() {
        nextStage = new JButton("Next stage");
        nextStage.addActionListener(this);
        
        showDeck = new JButton("Show deck");
        showDeck.addActionListener(this);
        
        showDiscard = new JButton("Show Discard");
        showDiscard.addActionListener(this);
        
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        
        results = new JButton("Results");
        results.addActionListener(this);
        
        test = new JButton("Test");
        test.addActionListener(this);
    }
    
    public void refresh() {
        mainWin.getContentPane().add(table);
        mainWin.getContentPane().add(menu);
        mainWin.getContentPane().add(picks);
        mainWin.setVisible(true);
    }
    
    public void setUpFlop() {
        draw = new Hand();
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
    
    public void setUpHand() {
        hand = new Hand();
        hand.pickUp(2);
        for (int i = 0; i < hand.cards.size(); i++) {
            createNewHand(i);
            handCards.get(i).add(hand.cards.get(i).getImage());
            hand.cards.get(i).setInPlay();
            picks.add(handCards.get(i));
        }
        
    }
    
    public void createTable(int i) {
        
        createNewTableCard(i);
        tableCards.get(i).add(draw.cards.get(i).getImage());
        table.add(tableCards.get(i));
        
    }
    
    public void createNewHand(int i) {
        handCards.add(new JPanel());
        handCards.get(i).setBounds(posXPicks[i], 20, sizeX, sizeY);
        handCards.get(i).setBackground(Color.GREEN);
    }
    
    public void createNewTableCard(int i) {
        
        tableCards.add(new JPanel());
        tableCards.get(i).setBounds(posX[i], posY, sizeX, sizeY);
        tableCards.get(i).setBackground(Color.GREEN);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == nextStage) {
            nextStage();
        } else if (e.getSource() == showDeck) {
            Deck.showDeck();
        } else if (e.getSource() == newGame) {
            newGame();
        } else if (e.getSource() == showDiscard) {
            Deck.showDiscard();
        } else if (e.getSource() == results) {
            stage.setText(Results.compare(hand, draw));
        } else if (e.getSource() == test) {
            test();
        }
    }
    
    private void test() {
        resetPanel();
        discardAll();
        Deck.clearAll();
        refresh();
        Object test = choice.getSelectedItem();
        if (test.toString() == Results.goals[1]) {
            testPair();
        }else if (test.toString() == Results.goals[2]) {
            testTwoPair();
        }else if (test.toString() == Results.goals[3]) {
            testTriple();
        }else if (test.toString() == Results.goals[4]) {
            testStraight();
        }
        
        
        
    }
    
    private void testPair() {
        draw = new Hand();
        Deck.pickPair();
        draw.add(Deck.pickCard(2));
        for (int i = 0; i < 2; i++) {
            createTable(i);
            draw.cards.get(i).setInPlay();
        }
    }
    
    private void testTwoPair() {
        draw = new Hand();
        Deck.pickTwoPair();
        draw.add(Deck.pickCard(4));
        for (int i = 0; i < 4; i++) {
            createTable(i);
            draw.cards.get(i).setInPlay();
        }
    }
    
    private void testTriple() {
        draw = new Hand();
        Deck.pickTriple();
        draw.add(Deck.pickCard(3));
        for (int i = 0; i < 3; i++) {
            createTable(i);
            draw.cards.get(i).setInPlay();
        }
    }
    
    
    private void testStraight() {
        
        draw = new Hand();
        Deck.pickStraight();
        draw.add(Deck.pickCard(5));
        for (int i = 0; i < 5; i++) {
            createTable(i);
            draw.cards.get(i).setInPlay();
        }
    }
    
    private void testFlush() {
        
        draw = new Hand();
        for (int i = 1; i < 6; i++) {
            Deck.pickStraight();
            draw.add(Deck.pickCard(5));
            createTable(i - 1);
            draw.cards.get(i - 1).setInPlay();
        }
        
    }
    
    private void nextStage() {
        switch (time) {
            case 0:
                flopStage();
                break;
            case 1:
                lastStage(3);
                break;
            case 2:
                lastStage(4);
                break;
            case 3:
                time = 0;
                restartSameDeck();
                break;
        }
    }
    
    private void flopStage() {
        for (int i = 0; i < draw.cards.size(); i++) {
            draw.cards.get(i).setInPlay();
        }
        time++;
        stage.setText(textStage[time]);
    }
    
    private void lastStage(int card) {
        setUpOne(card);
        draw.cards.get(card).setInPlay();
        time++;
        stage.setText(textStage[time]);
    }
    
    private void restartSameDeck() {
        discardAll();
        resetPanel();
        setUpHand();
        setUpFlop();
        refresh();
    }
    
    private void discardAll() {
        while (!hand.cards.isEmpty()) {
            Deck.discard(hand.remove(1));
        }
        while (!draw.cards.isEmpty()) {
            Deck.discard(draw.remove(1));
        }        
    }
    
    private void resetPanel() {
        
        while (!tableCards.isEmpty()) {
            tableCards.get(0).setVisible(false);
            tableCards.remove(0);
            
        }
        while (!handCards.isEmpty()) {
            handCards.get(0).setVisible(false);
            handCards.remove(0);
            
        }
    }
    
    private void resetTableOnly() {
        while (!tableCards.isEmpty()) {
            tableCards.get(0).setVisible(false);
            tableCards.remove(0);
        }
    }
    
    private void newGame() {
        int choice = JOptionPane.showConfirmDialog(mainWin, "Are you sure?",
                "New Game?", JOptionPane.OK_CANCEL_OPTION);
        if (choice == 0) {
            this.mainWin.dispose();
            Deck.newDeck();
            new Poker();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Poker();
    }
    
}
