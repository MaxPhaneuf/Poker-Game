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
    public void add(ArrayList<Card> card){
        cards.addAll(card);
    }
    
    public ArrayList<Card> getCopyCard(int card){
        ArrayList<Card> temp = new ArrayList<>();
        temp.add(cards.get(card));
        return temp;
    }
    
    public ArrayList<Card> remove(int nbr){
        ArrayList<Card> temp = new ArrayList<>();
        for(int i = 0; i < nbr; i++){
            temp.add(cards.remove(0));
        }
        return temp;
    }
    
    public void pickUp(int nbrCards){
       
        cards.addAll(Deck.pickCard(nbrCards));
       
    }
    
    
}
