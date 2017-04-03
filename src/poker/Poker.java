/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Max
 */
public class Poker implements ActionListener {

    public static final String[] STAGE = {"Pre-Flop", "Flop", "Turn", "River"};
    public static final int[] POSX = {40, 130, 220, 310, 400};
    public static final int[] POSX_PICK = {30, 110};
    public static final int POSY = 20;
    public static final int SIZEX = 72;
    public static final int SIZEY = 102;
    public boolean activeTest = false;
    private JFrame mainWin;
    public Table table;
    public Menu menu;
    public Bets bets;
    public ArrayList<Player> players = new ArrayList<Player>();
    public int nbrPlayers = 4;
    public int playerInPlay = 0;
    public int blindPlayer = 1;
    public int time = 0;

    public Poker() {
        setUp();

        table.setUpFlop();

        bets.blindMoney = bets.blindMoney.add(new BigDecimal(10));
        bets.showMoney();
        addPlayers();
        refresh();
    }

    public void addPlayers() {
        Player temp;
        Player next;
        for (int i = 0; i < nbrPlayers; i++) {

            players.add(new Player(mainWin, bets, i));
            players.get(i).setUpHand();
            players.get(i).money.money = players.get(i).money.money.add(new BigDecimal(1000));
            players.get(i).showMoney();
            players.get(i).picks.setVisible(true);

        }
        for (int i = 0; i < nbrPlayers - 1; i++) {

            players.get(i).nextPlayer = players.get(i + 1);
            if (i != 0) {
                players.get(i).endTurn();
            }
        }
        players.get(players.size() - 1).endTurn();

    }

    private void setUp() {
        setUpWindow();
        table = new Table(mainWin);
        menu = new Menu(table, mainWin);
        setAction();
        bets = new Bets(mainWin);

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
        mainWin.getContentPane().add(bets.bets);
        mainWin.setVisible(true);

    }

    public void setAction() {
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

    private boolean allPlayed() {
        boolean temp = false;
        for (Player player : players) {
            temp = player.endTurn;
        }

        return temp;
    }

    private void startTurn() {
        bets.raiseMoney = new BigDecimal(0);
        bets.showMoney();
        players.get(blindPlayer - 1).startTurn();

    }

    public void preflop() {
        players.get(blindPlayer - 1).money.money
                = players.get(blindPlayer - 1).money.money.subtract(bets.blindMoney);
    }

    public void showAll() {
        for (Player player : players) {
            player.showHand();
        }
    }

    private void nextStage() {
        if (allPlayed()) {
            switch (time) {
                case 0:
                    flopStage();
                    startTurn();
                    break;
                case 1:
                    openCard(3, 1, "River");
                    table.setUpOne(4);
                    startTurn();
                    break;
                case 2:
                    openCard(4, 3, "Show cards");
                    menu.results.setEnabled(true);
                    
                    break;
                case 3:
                    
                    showAll();
                    //restart();
                    //blindPlayer++;
                    //startTurn();
                    break;
            }
        }
    }

    public void deckCheck(int cards) {
        if (Deck.deck.size() <= cards) {
            menu.nextStage.setEnabled(false);
        }
    }

    private void test() {
        menu.results.setEnabled(true);
        activeTest = true;
        setUpTest();
        int test = menu.choice.getSelectedIndex();

        if (test == 0) {
            table.testHighCard();
        } else if (test == 1) {
            table.testPair();
        } else if (test == 2) {
            table.testTwoPair();
        } else if (test == 3) {
            table.testTriple();
        } else if (test == 4) {
            table.testStraight();
        } else if (test == 5) {
            table.testFlush();
        } else if (test == 6) {
            table.testFullHouse();
        } else if (test == 7) {
            table.testFour();
        } else if (test == 8) {
            table.testStraightFlush();
        } else if (test == 9) {
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

    private void resetEveryOne() {
        table.resetTable();
        table.discardDraw();
        for (Player player : players) {
            player.resetHand();
            player.discardHand();
        }

        refresh();
    }

    private void setUpTest() {
        menu.nextStage.setEnabled(false);
        resetEveryOne();
        Deck.clearAll();
        refresh();
    }

    private void restart() {
        menu.nextStage.setText("Flop");
        time = 0;
        resetEveryOne();
        for (Player player : players) {
            player.setUpHand();
        }

        table.setUpFlop();
        menu.stage.setText("");
        refresh();
    }

    private void freshStart() {
        Deck.newDeck();
        Deck.clearDiscard();
        table.resetTable();
        for (Player player : players) {
            player.resetHand();
            player.setUpHand();
        }
        menu.nextStage.setText("Flop");
        time = 0;
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

    public void nextPlayer() {
        if (playerInPlay < nbrPlayers - 1) {
            players.get(playerInPlay + 1).startTurn();
            playerInPlay++;
        } else {
            players.get(0).startTurn();
            playerInPlay = 0;
        }
    }

    public void resultPress() {
        if (!activeTest) {
            menu.stage.setText(getWinner());
        } else {
            Results result = new Results(null, table.draw);
            menu.stage.setText(result.result);
        }
    }

    public ArrayList<Results> resultTab() {
        ArrayList<Results> temp = new ArrayList<>();
        for (Player player : players) {
            temp.add(new Results(player.hand, table.draw));
        }
        return temp;
    }

    public ArrayList<Integer> scoreTab(ArrayList<Results> results) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Results result : results) {
            temp.add(result.getScore());
        }
        return temp;
    }

    public int compareScore(ArrayList<Integer> score) {
        int win = -1;
        int j = 0;
        int i = 1;
        boolean isHigh = false;
        boolean found = false;
        do {
            do {
                if (j != i) {
                    isHigh = score.get(j) > score.get(i);
                } else {
                    isHigh = true;
                }
                i++;

            } while (isHigh && i < score.size());
            found = isHigh;
            if (!found) {
                j++;
                i = 0;
            } else {
                win = j;
            }
        } while (!found && j < (score.size()));

        return win;
    }

    public String getWinner() {
        int win = compareScore(scoreTab(resultTab()));
        String winner = "";
        winner = "Player " + (win + 1) + " wins with " + resultTab().get(win).result;
        
        players.get(win).money.money = players.get(win).money.money.add(bets.potMoney);
        bets.potMoney = new BigDecimal(0);
        return winner;
    }

    public static void main(String[] args) {

        new Poker();

    }

}
