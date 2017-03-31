package poker;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Max
 */
public class Results {

    public static String[] goals = {"High card", "Pair", "Two pairs",
        "Three of a kind", "Straight", "Flush", "FullHouse",
        "Four of a kind", "Straight Flush", "Royal Flush"};

    public static String compare(Hand hand, Hand deck) {
        ArrayList<Integer> value = new ArrayList<>();
        ArrayList<Integer> color = new ArrayList<>();
        Hand all = new Hand();
        for (int i = 0; i < hand.cards.size(); i++) {
            if(hand.cards.get(i).isInPlay()){
                all.add(hand.cards.get(i));
            }
        }
        for (int i = 0; i < deck.cards.size(); i++) {
            if(deck.cards.get(i).isInPlay()){
               all.add(deck.cards.get(i));
            }
        }
        for (int i = 0; i < all.cards.size(); i++) {
            value.add(all.cards.get(i).getValue());
        }
        for (int i = 0; i < all.cards.size(); i++) {
            color.add(all.cards.get(i).getColor());
        }

        return getSameValue(value, all);
    }

    public static String getSameValue(ArrayList<Integer> value, Hand all) {
        int[] results = new int[15];
        boolean isPair = false;
        boolean isTwoPairs = false;
        boolean isTriple = false;
        boolean isFullHouse = false;
        boolean isFour = false;
        boolean isStraight = false;
        String temp = null;
        for (int i = 0; i < value.size(); i++) {
            if(value.get(i) != 1){
                results[value.get(i)]++;
            }else{
                results[value.get(i)]++;
                results[value.get(i)+ 13]++;
            }
        }
        
        for (int i = 2; i < results.length; i++) {
            System.out.print(results[i] + " ");
        }
        System.out.println();
        for (int i = 2; i < results.length; i++) {
            
            switch (results[i]) {
                case 1:
                    if (!isPair && !isTriple && !isTwoPairs && !isFullHouse 
                            && !isFour && !isStraight) {
                        temp = goals[0];
                    }
                    break;
                case 2:
                    if (!isPair && !isTriple && !isTwoPairs && !isFour && !isStraight) {
                        temp = goals[1];
                        isPair = true;
                    } else if (!isTriple && !isTwoPairs && !isFour && !isStraight) {
                        temp = goals[2];
                        isTwoPairs = true;
                    }
                    if (isTriple) {
                        temp = goals[6];
                        isFullHouse = true;
                    }

                    break;

                case 3:
                    if (!isTriple && !isFullHouse && !isFour && !isStraight) {
                        temp = goals[3];
                        isTriple = true;
                    }
                    if (isPair) {
                        temp = goals[6];
                        isFullHouse = true;
                    }
                    break;

                case 4:
                    isFour = true;
                    temp = goals[7];
                    
                    break;

            }

        }
        int num = 0;
        for (int i = 1; i < results.length; i++){
            if(results[i] > 0){
                num++;
                if(num == 5 && !isFullHouse && !isFour){
                    isStraight = true;
                    temp = goals[4];
                }
            }else{
                num = 0;
            }
        }
        return temp;
    }
}
