/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

/**
 *
 * @author Maxime
 */
public class Hand {
    public Card[] cards = new Card[5];
    
    public Hand(){
        for(int i = 0; i < cards.length; i++){
            cards[i] = Deck.pickCard();
        }
    }
}
