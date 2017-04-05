/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class Player {

    public BigDecimal money;
    public BigDecimal raiseMoney = new BigDecimal(0);
    public boolean blindPaid;
    public int score;
    public JFrame mainWin;
    public JFrame picks;
    public JButton showHand;
    public JButton raise;
    public JButton call;
    public JButton fold;
    public JTextField showMoney;
    public JComboBox raiseChoice;
    public ArrayList<JPanel> handCards = new ArrayList<>();
    public CardManager hand;
    public Bets bets;
    public boolean pressed;
    public boolean raising;
    public boolean folded;
    public boolean played;
    public boolean error = false;
    public boolean endTurn;
    public int playerNbr;
    public int playersTotal;
    public Player nextPlayer = null;
    public Player previousPlayer = null;
    public int[] posX;
    public int[] posY;

    public Player(JFrame mainWin, Bets bets, int nbr) {
        playerNbr = nbr;
        this.mainWin = mainWin;
        posX = new int[]{mainWin.getX() - 230,
            mainWin.getX()+ 25,
            mainWin.getX() + 255,
            mainWin.getX() + mainWin.getWidth() + 10,
            mainWin.getX() + mainWin.getWidth() + 10,
            mainWin.getX() + 255,
            mainWin.getX()+ 25,
            mainWin.getX() - 230};
        posY = new int[]{mainWin.getY(), 
            mainWin.getY() - 260,
            mainWin.getY() - 260,
            mainWin.getY(),
            mainWin.getY() + 260,
            mainWin.getY() + 390,
            mainWin.getY() + 390,
            mainWin.getY() + 260};
        this.bets = bets;

        setUpWindow();
    }

    public Card get(int i) {
        return hand.get(i);
    }

    private void setUpWindow() {
        picks = new JFrame("Player " + (playerNbr + 1));
        picks.setSize(220, 250);
        picks.setLocation(posX[playerNbr], posY[playerNbr]);
        picks.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        picks.setLayout(null);
        picks.setResizable(false);
        picks.setIconImage(addImage("Poker.png"));
        money = new BigDecimal(0);
        setUpButtons();
        setUpText();
        setUpChoice();
        showMoney();

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

    private void setUpButtons() {
        showHand = new JButton("Show");
        showHand.setLocation(110, 140);
        showHand.setSize(75, 20);
        picks.getContentPane().add(showHand);

        raise = new JButton("Raise?");
        raise.setLocation(20, 140);
        raise.setSize(75, 20);
        raise.setEnabled(true);
        picks.getContentPane().add(raise);

        call = new JButton("Call");
        call.setLocation(110, 165);
        call.setSize(75, 20);
        picks.getContentPane().add(call);

        fold = new JButton("Fold");
        fold.setLocation(45, 190);
        fold.setSize(120, 20);
        picks.getContentPane().add(fold);
    }

    private void setUpText() {
        showMoney = new JTextField("0$");
        showMoney.setBounds(70, 4, 75, 20);
        showMoney.setEditable(false);
        picks.getContentPane().add(showMoney);

    }

    private void setUpChoice() {
        raiseChoice = new JComboBox();
        raiseChoice.addItem("10$");
        raiseChoice.addItem("25$");
        raiseChoice.addItem("50$");
        raiseChoice.addItem("100$");
        raiseChoice.addItem("All-in");
        raiseChoice.setLocation(20, 165);
        raiseChoice.setSize(75, 20);
        picks.getContentPane().add(raiseChoice);
    }

    public void setUpHand() {
        hand = new CardManager();
        hand.pickUp(2);
        for (int i = 0; i < hand.size(); i++) {
            createNewHand(i);
            handCards.get(i).add(hand.get(i).getImage());
            picks.getContentPane().add(handCards.get(i));
        }
    }

    public void createNewHand(int i) {
        handCards.add(new JPanel());
        handCards.get(i).setBounds(POSX_PICK[i], 20, SIZEX, SIZEY);
    }

    public void removeHand() {

        while (!handCards.isEmpty()) {
            handCards.get(0).setVisible(false);
            handCards.remove(0);
            hand.remove(0);
        }

    }

    public void showMoney() {

        showMoney.setText(money.toString() + "$");
        picks.getContentPane().add(showMoney);
        picks.getContentPane().add(raiseChoice);

    }

    public void showHand() {
        for (int i = 0; i < hand.cards.size(); i++) {
            hand.cards.get(i).setInPlay();

        }

    }

    public void hideHand() {
        for (int i = 0; i < hand.cards.size(); i++) {
            hand.cards.get(i).setFaceDown();

        }

    }

    public void isShown() {
        if (!pressed) {
            show();
        } else {
            hide();
        }
    }

    public void show() {
        pressed = true;
        showHand();
        showHand.setText("Hide");
    }

    public void hide() {
        pressed = false;
        hideHand();
        showHand.setText("Show");
    }

    public void endTurn() {
        raise.setEnabled(false);
        call.setEnabled(false);
        fold.setEnabled(false);
        showHand.setEnabled(false);
        raiseChoice.setEnabled(false);
        endTurn = true;
        hide();
    }
    
    public void disable(){
        raise.setEnabled(false);
        call.setEnabled(false);
        fold.setEnabled(false);
        showHand.setEnabled(false);
        raiseChoice.setEnabled(false);
        endTurn = true;
    }

    public void startTurn() {
        if (raiseMoney.intValue() == bets.raiseMoney.intValue() && blindPaid) {
            call.setText("Check");
        } else {
            call.setText("Call");
        }
        call.setEnabled(true);
        fold.setEnabled(true);
        showHand.setEnabled(true);
        raiseChoice.setEnabled(true);
        endTurn = false;
    }

    public void call() {
        BigDecimal blind = new BigDecimal(0);
        BigDecimal temp = new BigDecimal(0);
        BigDecimal diff = new BigDecimal(0);
        if (!blindPaid) {
            blind = payBlind(blind); 
        }      
        if (raising) {
            temp = raise();
            
        }
        if (temp.intValue() + blind.intValue() <= money.intValue()) {
            blindPaid = true;
            bets.raiseMoney = bets.raiseMoney.add(temp);
            diff = bets.raiseMoney.subtract(raiseMoney);
            money = money.subtract(diff.add(blind));
            raiseMoney = raiseMoney.add(diff);
            bets.potMoney = bets.potMoney.add(diff.add(blind));
            showMoney();
            bets.showMoney();
            error = false;
            
        }else{
            error = true;
        }

    }

    public BigDecimal raise() {
        BigDecimal temp2 = new BigDecimal(0);
        if (raiseChoice.getSelectedIndex() != 4) {
            String temp = (String) raiseChoice.getSelectedItem();
            temp = temp.substring(0, temp.length() - 1);
            temp2 = new BigDecimal(temp);
        } else {
           
            if(blindPaid){
                temp2 = money;
            }else{
                temp2 = money.subtract(bets.blindMoney);
            }
       
        }
       
        return temp2;
    }
    
    public void fold() {
        folded = true;
       

    }

    private BigDecimal payBlind(BigDecimal temp) {
        temp = temp.add(bets.blindMoney);
        return temp;

    }

    @Override
    public String toString() {
        return "Player " + playerNbr + "end : " + endTurn;
    }
}
