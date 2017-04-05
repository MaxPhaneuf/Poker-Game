/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.imageio.ImageIO;
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
    private JFrame levelSelect;
    public Table table;
    public Menu menu;
    public Bets bets;
    public PlayerManager players;
    public int choice = 8;
    public int time = 0;
    public boolean speedUp;
    
    public Poker() {

        setUp();

    }

    private void setUp() {
        setUpWindow();
        menu = new Menu(table, mainWin);
        setAction();
        bets = new Bets(mainWin, 10);
        table = new Table(mainWin);
        players = new PlayerManager(mainWin, bets, table);
        setPlayerButtons();
        refresh();
    }

    private void setUpWindow() {
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 380);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        mainWin.setResizable(false);
        mainWin.setIconImage(addImage("Poker.png"));
    }

    private void levelSelect() {
        levelSelect = new JFrame("New Poker");
        levelSelect.setIconImage(addImage("Poker.png"));
        levelSelect.setSize(500, 380);
        levelSelect.setLocationRelativeTo(null);
        levelSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelSelect.setLayout(null);
        levelSelect.setResizable(false);
    }

    public BufferedImage addImage(String fileName) {

        BufferedImage temp = null;
        try {
            File file = new File(fileName);
            temp = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return temp;
    }

    private void refresh() {
        mainWin.getContentPane().add(table.table);
        mainWin.getContentPane().add(menu.menu);
        mainWin.getContentPane().add(bets.bets);
        mainWin.setVisible(true);

    }

    private void setAction() {
        menu.showCards.addActionListener(this);
        menu.showDeck.addActionListener(this);
        menu.showDiscard.addActionListener(this);
        menu.newDraw.addActionListener(this);
        menu.results.addActionListener(this);
        menu.test.addActionListener(this);
        menu.choice.addActionListener(this);
    }

    private void setPlayerButtons() {
        setCall();
        setRaise();
        setFold();
        setShow();
    }

    private void setCall() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).call.addActionListener(this);
        }
    }

    private void setFold() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).fold.addActionListener(this);
        }
    }

    private void setRaise() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).raise.addActionListener(this);
        }
    }

    private void setShow() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).showHand.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == menu.showCards) {
            if(!speedUp){
                players.showAll();
                speedUp = true;
            }else{
                nextStage();
            }
            
        } else if (e.getSource() == menu.showDeck) {
            Deck.showDeck();
        } else if (e.getSource() == menu.newDraw) {
            newDraw();
        } else if (e.getSource() == menu.showDiscard) {
            Deck.showDiscard();
        } else if (e.getSource() == menu.results) {
            result();
        } else if (e.getSource() == menu.test) {
            setUpTest();
        } else if (e.getSource() == players.inPlay().fold) {
            players.fold();
        } else if (e.getSource() == players.inPlay().call) {
            players.call();
            if (players.allCalled() && players.allPlayed()) {
                nextStage();
            }
        } else if (e.getSource() == players.inPlay().raise) {
            players.raise();
        } else if (e.getSource() == players.inPlay().showHand) {
            players.inPlay().isShown();
        }
    }

    public void newDraw() {
        if (players.size() <= 1) {
            players.nbrPlayers = choice;
        }
        menu.newDraw.setEnabled(false);
        menu.stage.setText("");
        Deck.clearAll();
        Deck.newDeck();
        table.newDraw();
        players.newDraw();
        

    }

    public void lastPlayer() {
        //menu.stage.setText("Player " + (players.get(0).playerNbr + 1) + " wins");
    }

    private void setUpTest() {
        if (!activeTest) {
            menu.results.setVisible(true);
            menu.showCards.setVisible(true);
            menu.showDeck.setVisible(true);
            menu.showDiscard.setVisible(true);
            menu.choice.setVisible(true);
            activeTest = true;
        } else {
            int test = menu.choice.getSelectedIndex();
            table.resetTable();
            players.resetPlayers();
            Deck.clearAll();
            menu.results.setEnabled(true);
            new Test(table, test);
        }
        refresh();

    }

    private void nextStage() {
        switch (time) {
            case 0:
                flop();
                refresh();
                players.startTurn();
                time++;
                break;
            case 1:
                turn();
                players.startTurn();
                time++;
                break;
            case 2:
                river();
                players.startTurn();
                time++;
                break;
            case 3:
                result();
                players.showAll();
                menu.newDraw.setEnabled(true);
                players.get(0).disable();
                time = 0;
                players.eliminate();
                if (players.size() <= 1) {
                    lastPlayer();
                    menu.newDraw.setText("NewGame?");
                }
                break;
        }

    }

    public void flop() {

        for (int i = 0; i < table.size(); i++) {
            table.get(i).setInPlay();
        }
        table.setUpOne(3);

    }

    public void turn() {

        table.get(3).setInPlay();
        table.setUpOne(4);

    }

    public void river() {
        table.get(4).setInPlay();

    }

    public void result() {
        if (!activeTest) {

            if (players.size() > 1) {
                menu.stage.setText(players.getWinner(false));
            } else {
                menu.stage.setText(players.getWinner(true));
            }
        } else {
            Results result = new Results(null, table.draw);
            menu.stage.setText(result.result);
        }
    }

    public static void main(String[] args) {

        new Poker();

    }

}
