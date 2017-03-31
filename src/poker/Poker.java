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
import javax.swing.JButton;
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
    private JPanel[] tableCards = new JPanel[5];
    private JPanel[] handCards = new JPanel[2];
    private JButton showDeck;
    private JButton nextStage;
    private JButton showDiscard;
    private JButton newGame;
    private boolean turn = false;
    private int[] posX = {20, 110, 200, 290, 380};
    private int[] posXPicks = {20, 110};
    private int posY = 20;
    private int sizeX = 72;
    private int sizeY = 102;
    private Hand draw;
    private Hand hand;
    private int time = 0;
    private String[] textStage = {"Pre-Flop", "Flop", "Pre-Turn", "Turn", "Pre-River", "River"};

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
        menu.setLayout(new FlowLayout());
        menu.setBackground(Color.red);
        setUpButtons();
        stage = new JTextField();
        stage.setColumns(10);
        stage.setAlignmentX(SwingConstants.CENTER);
        stage.setEditable(false);
        stage.setText(textStage[time]);

        menu.add(nextStage);
        menu.add(showDeck);
        menu.add(showDiscard);
        menu.add(newGame);
        menu.add(stage);

    }

    private void setUpPicks() {
        picks = new JPanel();
        picks.setBounds(mainWin.getWidth() / 2, mainWin.getHeight() / 2, mainWin.getWidth() / 2,
                mainWin.getHeight() / 2);
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
            createNewTableCard(i);

            tableCards[i].add(draw.cards.get(i).getImage());
            table.add(tableCards[i]);
        }
    }

    public void setUpTurn() {
        Deck.burn();
        draw.pickUp(1);

        createNewTableCard(3);
        tableCards[3].add(draw.cards.get(3).getImage());
        table.add(tableCards[3]);
        refresh();

    }
    
    public void setUpRiver() {
        Deck.burn();
        draw.pickUp(1);

        createNewTableCard(4);
        tableCards[4].add(draw.cards.get(4).getImage());
        table.add(tableCards[4]);
        refresh();
    }

    public void setUpHand() {
        hand = new Hand();
        hand.pickUp(2);
        for (int i = 0; i < hand.cards.size(); i++) {
            createNewHand(i);

            handCards[i].add(hand.cards.get(i).getImage());
            hand.cards.get(i).setInPlay();
            picks.add(handCards[i]);
        }

    }

    public void createNewHand(int i) {
        handCards[i] = new JPanel();
        handCards[i].setBounds(posXPicks[i], 20, sizeX, sizeY);
        handCards[i].setBackground(Color.GREEN);
    }

    public void createNewTableCard(int i) {

        tableCards[i] = new JPanel();
        tableCards[i].setBounds(posX[i], posY, sizeX, sizeY);
        tableCards[i].setBackground(Color.GREEN);
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
        }
    }

    private void nextStage() {
        switch(time){
            case 0:
                for (int i = 0; i < draw.cards.size(); i++) {
                    draw.cards.get(i).setInPlay();
                }
                time++;
                stage.setText(textStage[time]);
                break;
            case 1:
                setUpTurn();
                time++;
                stage.setText(textStage[time]);
                break;
            case 2:
                draw.cards.get(3).setInPlay();
                time++;
                stage.setText(textStage[time]);
                break;
            case 3:
                setUpRiver();
                time++;
                stage.setText(textStage[time]);
                break;
            case 4:
                draw.cards.get(4).setInPlay();
                time++;
                stage.setText(textStage[time]);
                break;
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
