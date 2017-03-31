/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.util.ArrayList;

/**
 *
 * @author Maxime
 */
public class Hand {
    public ArrayList<Card> cards = new ArrayList<>();
    
    public Hand(){
        
    }
    
    public void pickUp(int nbrCards){
        for(int i = 0; i < nbrCards; i++){
            cards.add(Deck.pickCard());
        }
    }
}
