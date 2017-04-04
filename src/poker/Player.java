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
import javax.swing.JButton;
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
public class Player{
    
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
    public JTextField raiseBox;
    public ArrayList<JPanel> handCards = new ArrayList<>();
    public CardManager hand;
    public Bets bets;

    public boolean pressed;
    public boolean raising;
    public boolean isBlind;
    public boolean folded;

    public boolean endTurn;
    public int playerNbr;
    public int playersTotal;
    public Player nextPlayer = null;
    public int[] posX;
    public int[] posY;
   

    public Player(JFrame mainWin, Bets bets, int nbr) {
        playerNbr = nbr;
        this.mainWin = mainWin;
        posX = new int[]{mainWin.getX() + mainWin.getWidth() + 10,
            mainWin.getX() + mainWin.getWidth() + 10, mainWin.getX() - 230, mainWin.getX() - 230};
        posY = new int[]{mainWin.getY(), mainWin.getY() + 260, mainWin.getY(), mainWin.getY() + 260};
        this.bets = bets;
        
        setUpWindow();

    }

    private void setUpWindow() {
        picks = new JFrame("Player " + (playerNbr + 1));
        picks.setSize(220, 250);
        picks.setLocation(posX[playerNbr], posY[playerNbr]);
        picks.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        picks.setLayout(null);
        picks.setResizable(false);
        money = new BigDecimal(0);
        setUpButtons();
        setUpText();

    }

    private void setUpButtons() {
        showHand = new JButton("Show");
        showHand.setLocation(110, 140);
        showHand.setSize(75, 20);
        //showHand.addActionListener(this);
        picks.getContentPane().add(showHand);

        raise = new JButton("Raise?");
        raise.setLocation(20, 140);
        raise.setSize(75, 20);
        //raise.addActionListener(this);
        picks.getContentPane().add(raise);
        raise.setEnabled(true);

        call = new JButton("Call");
        call.setLocation(110, 165);
        call.setSize(75, 20);
        //call.addActionListener(this);
        picks.getContentPane().add(call);

        fold = new JButton("Fold");
        fold.setLocation(45, 190);
        fold.setSize(120, 20);
        //fold.addActionListener(this);
        picks.getContentPane().add(fold);
    }

    private void setUpText() {
        raiseBox = new JTextField("0$");
        raiseBox.setLocation(20, 165);
        raiseBox.setSize(75, 20);
        //raiseBox.addActionListener(this);
        picks.getContentPane().add(raiseBox);
        showMoney = new JTextField("0$");

        showMoney.setBounds(70, 4, 75, 20);
        showMoney.setEditable(false);
        showMoney();
        picks.getContentPane().add(showMoney);

    }

    public void setUpHand() {
        hand = new CardManager();
        hand.pickUp(2);
        for (int i = 0; i < hand.cards.size(); i++) {
            createNewHand(i);
            handCards.get(i).add(hand.cards.get(i).getImage());
            picks.getContentPane().add(handCards.get(i));
            
        }

    }

    public void createNewHand(int i) {
        handCards.add(new JPanel());
        handCards.get(i).setBounds(POSX_PICK[i], 20, SIZEX, SIZEY);
    }

    public void resetHand() {

        while (!handCards.isEmpty()) {
            handCards.get(0).setVisible(false);
            handCards.remove(0);
        }

    }

    public void discardHand() {
        while (!hand.cards.isEmpty()) {
            Deck.discard(hand.remove(1));
        }
    }

    public void showMoney() {

        showMoney.setText(money.toString());
        picks.getContentPane().add(showMoney);
        raiseBox.setText("0$");
        picks.getContentPane().add(raiseBox);

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
        raiseBox.setEditable(false);
        endTurn = true;
        hide();
    }

    public void startTurn() {
        if (!folded) {
            raise.setEnabled(true);
            call.setEnabled(true);
            fold.setEnabled(true);
            showHand.setEnabled(true);
            raiseBox.setEditable(true);
            endTurn = false;
        } else {
            if (nextPlayer != null) {
                nextPlayer.startTurn();
            }
        }
    }
    
     public void call() {
        BigDecimal blind = new BigDecimal(0);
        BigDecimal temp = bets.raiseMoney;

        if (!blindPaid) {
            blind = payBlind(blind);
        }
        if (raising) {
            raise();
            temp = temp.add(raiseMoney);
            

        }
        if (temp.intValue() <= money.intValue()) {
            money = money.subtract(temp.add(blind));
            bets.raiseMoney = temp;
            raiseMoney = temp;
            bets.potMoney = bets.potMoney.add(temp.add(blind));
            showMoney();
            bets.showMoney();
            if (nextPlayer != null) {
                    nextPlayer.startTurn();
            }
            endTurn();
        }
             
    }

    public void raise() {
        BigDecimal temp = bets.raiseMoney.add(new BigDecimal(parse()));
        raiseMoney = temp;
    }

    private BigDecimal payBlind(BigDecimal temp) {
        blindPaid = true;
        temp = temp.add(bets.blindMoney);
        return temp;

    }

    public String parse() {
        String temp = raiseBox.getText();
        if (!temp.isEmpty()) {
            int i = temp.indexOf("$");
            if (i > -1) {
                temp = temp.substring(0, i);
            }
        } else {
            temp = "0";
        }

        return temp;

    }


    /*@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.showHand) {
            isShown();
        } else {

            if (e.getSource() == this.call) {
                call();
                raising = false;

            } else if (e.getSource() == this.raise) {
                BigDecimal temp = new BigDecimal(parse());
                raise.setText(bets.raiseMoney.add(temp).toString() + "S");
                raising = true;

            } else if (e.getSource() == this.fold) {
                if (nextPlayer != null) {
                    nextPlayer.startTurn();
                }
                fold();
                
                endTurn();

            }
        }
    }*/
    public void fold(){
        folded = true;
        picks.setState(JFrame.ICONIFIED);
        endTurn();
        
    }
    
    public void unFold(){
        folded = false;
        picks.setState(JFrame.NORMAL);
        
    }

    @Override
    public String toString() {
        return "Player " + playerNbr + "end : " + endTurn;
    }
}
