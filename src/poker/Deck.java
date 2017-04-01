/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Max
 */
public class Deck {

    public static ArrayList<Card> deck = createDeck();
    public static ArrayList<Card> discard = new ArrayList<>();

    public static void newDeck() {
        deck = createDeck();
    }
    
    public static void clearAll(){
        deck = new ArrayList<Card>();
        discard = new ArrayList<Card>();
    }
    
    public static void clearDiscard(){
        discard = new ArrayList<Card>();
    }

    public static ArrayList<Card> createDeck() {
        int color = rdnGen(4);
        int value = rdnGen(13) + 1;
        ArrayList<Card> deckTemp = new ArrayList<>();
        Card card = null;
        int i = 0;
        while (i < 52) {

            card = new Card(color, value);
            if (!deckTemp.toString().contains(card.toString())) {
                deckTemp.add(card);
                i++;
            }
            color = rdnGen(4);
            value = rdnGen(13) + 1;

        }
        return deckTemp;

    }

    public static ArrayList<Card> createSpecificDeck(ArrayList<Card> deck,
            int color, int value, boolean rdnColor, boolean rdnValue) {

        Card card = null;
        boolean found = false;
        do {
            if (rdnColor) {
                color = rdnGen(4);
            }
            if (rdnValue) {
                value = rdnGen(13) + 1;
            }
            card = new Card(color, value);

            if (!deck.toString().contains(card.toString())) {
                deck.add(card);
                found = true;
            }
        } while (!found);

        return deck;
    }

    public static void saveDeck(ArrayList<Card> deckTemp) {
        deck = deckTemp;
    }

    public static void pickAs(){
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, 1, true, false);
        }
        saveDeck(deckTemp);
    }
    
    public static void pickPair() {
        int num = rdnGen(13) + 1;
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, num, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickTwoPair() {
        int num = rdnGen(13) + 1;
        int num2 = rdnGen(13) + 1;
        while(num2 == num){
           num2 = rdnGen(13) + 1;; 
        }
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0,num, true, false);
        }
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, num2, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickTriple() {
        int num = rdnGen(13) + 1;
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, num, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickStraight() {
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        int start = rdnGen(10) + 1;
        int temp = 0;
        for (int i = 0; i < 5; i++) {
            if(start + i == 14){
                temp = 1;
            }else{
                temp = start + i;
            }
            deckTemp = createSpecificDeck(deckTemp, 0,temp , true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickFlush() {
        int num = rdnGen(4);
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deckTemp = createSpecificDeck(deckTemp, num, 0, false, true);
        }
        saveDeck(deckTemp);
    }
    
    public static void pickFullHouse() {
        int num = rdnGen(13) + 1;
        int num2 = rdnGen(13) + 1;
        while(num2 == num){
           num2 = rdnGen(13) + 1;; 
        }
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0,num, true, false);
        }
        for (int i = 0; i < 3; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, num2, true, false);
        }
        saveDeck(deckTemp);
    }
    
    public static void pickFour() {
        int num = rdnGen(13) + 1;
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, num, true, false);
        }
        saveDeck(deckTemp);
    }
    
    public static void pickStraightFlush() {
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        int start = rdnGen(9) + 1;
        int num = rdnGen(4);
        int temp = 0;
        for (int i = 0; i < 5; i++) {
            if(start + i == 14){
                temp = 1;
            }else{
                temp = start + i;
            }
            deckTemp = createSpecificDeck(deckTemp, num, temp , false, false);
        }
        saveDeck(deckTemp);
    }
    
    public static void pickRoyalFlush() {
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        int start = 10;
        int num = rdnGen(4);
        int temp = 0;
        for (int i = 0; i < 5; i++) {
            if(start + i == 14){
                temp = 1;
            }else{
                temp = start + i;
            }
            deckTemp = createSpecificDeck(deckTemp, num, temp , false, false);
        }
        saveDeck(deckTemp);
    }
    
    

    public static ArrayList<Card> pickCard(int nbr) {
        ArrayList<Card> cards = new ArrayList<>();
        if (deck.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No more cards",
                    "Alert!", JOptionPane.ERROR_MESSAGE);
           System.exit(0);

        } else {
            int i = 0;
            while (!deck.isEmpty() && i < nbr) {
                cards.add(deck.remove(0));
                i++;
            }
        }
        return cards;
    }
    public static void discard(ArrayList<Card> card) {
        discard.addAll(card);
    }

    public static void burn() {
        discard(Deck.pickCard(1));
    }

    public static int rdnGen(int num) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(num);
        return randomInt;
    }

    public static void showDeck() {
        JFrame mainWin;
        mainWin = new JFrame("Deck");
        mainWin.setSize(1300, 525);
        mainWin.setLocationRelativeTo(null);
        mainWin.setLayout(new GridLayout(4, 13, 20, 20));
        JLabel image = null;
        for (int i = 0; i < deck.size(); i++) {
            JPanel temp = new JPanel();
            image = new JLabel(new ImageIcon(deck.get(i).addImage(deck.get(i).getName())));

            temp.add(image);
            mainWin.getContentPane().add(temp);
        }
        mainWin.setVisible(true);
    }

    public static void showDiscard() {
        JFrame mainWin;
        mainWin = new JFrame("Discard");
        mainWin.setSize(1300, 525);
        mainWin.setLocationRelativeTo(null);
        mainWin.setLayout(new GridLayout(4, 13, 20, 20));
        JLabel image = null;
        for (int i = 0; i < discard.size(); i++) {
            JPanel temp = new JPanel();
            image = new JLabel(new ImageIcon(discard.get(i).addImage(discard.get(i).getName())));

            temp.add(image);
            mainWin.getContentPane().add(temp);
        }
        mainWin.setVisible(true);
    }

    public String toString() {
        return getNames();
    }

    private String getNames() {
        String names = "";
        for (Card card : deck) {
            names = names + card.toString() + " ";
        }
        return names;
    }
}
