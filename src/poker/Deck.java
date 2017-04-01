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
        discard = new ArrayList<Card>();
    }
    
    public static void clearAll(){
        deck = new ArrayList<Card>();
        discard = new ArrayList<Card>();
    }

    public static ArrayList<Card> createDeck() {
        int color = rdn4();
        int value = rdn13();
        ArrayList<Card> deckTemp = new ArrayList<>();
        Card card = null;
        int i = 0;
        while (i < 52) {

            card = new Card(color, value);
            if (!deckTemp.toString().contains(card.toString())) {
                deckTemp.add(card);
                i++;
            }
            color = rdn4();
            value = rdn13();

        }
        return deckTemp;

    }

    public static ArrayList<Card> createSpecificDeck(ArrayList<Card> deck,
            int color, int value, boolean rdnColor, boolean rdnValue) {

        Card card = null;
        boolean found = false;
        do {
            if (rdnColor) {
                color = rdn4();
            }
            if (rdnValue) {
                value = rdn13();
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

    public static void pickPair() {

        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, 1, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickTwoPair() {

        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, 1, true, false);
        }
        for (int i = 0; i < 2; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, 11, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickTriple() {

        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, 7, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickStraight() {
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, i + 1, true, false);
        }
        saveDeck(deckTemp);
    }

    public static void pickFlush() {
        deck = new ArrayList<Card>();
        ArrayList<Card> deckTemp = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deckTemp = createSpecificDeck(deckTemp, 0, i + 1, true, false);
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

    public static Card pickRandomCard() {
        Card card = new Card(rdn4(), rdn13());
        int temp = deck.indexOf(card);
        return deck.remove(temp);
    }

    public static Card pickSpecificCard(int color, int value) {
        Card card = new Card(color, value);

        return card;
    }

    public static Card pickSpecificCardValue(int value) {
        Card card = new Card(rdn4(), value);

        return card;
    }

    public static Card pickSpecificCardColor(int color) {
        Card card = new Card(color, rdn13());

        return card;
    }

    public static void discard(ArrayList<Card> card) {
        discard.addAll(card);
    }

    public static void burn() {
        discard(Deck.pickCard(1));
    }

    public static int rdn4() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(4);
        return randomInt;
    }

    public static int rdn13() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(13) + 1;
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
