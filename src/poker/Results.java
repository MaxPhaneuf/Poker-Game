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
    
    public int score = 0;
    public int high;
    public int highestPair;
    public int handHigh;
    public int[] valueTab;
    public int[] colorTab;
    public int[] handTab;
    public ArrayList<Integer> allValue;
    public ArrayList<Integer> allColor;
    public ArrayList<Integer> handValue;
    public String result;
    public boolean isHighCard = false;
    public boolean isPair = false;
    public boolean isTwoPairs = false;
    public boolean isTriple = false;
    public boolean isStraight = false;
    public boolean isFlush = false;
    public boolean isFullHouse = false;
    public boolean isFour = false;
    public boolean isStraightFlush = false;
    public boolean isRoyalFlush = false;
    public CardManager all = new CardManager();
    public CardManager hand = new CardManager();
    public static final String[] goals = {"High card", "Pair", "Two pairs",
        "Three of a kind", "Straight", "Flush", "FullHouse",
        "Four of a kind", "Straight Flush", "Royal Flush"};

    public Results(CardManager hand, CardManager deck){
        getHandCards(hand);
        getCards(hand, deck);
        getAllValue();
        getAllColor();
        getResults();
    }
    
    private void getHandCards(CardManager temp) {  
        CardManager temp2 = new CardManager();
        for (int i = 0; i < temp.cards.size(); i++) {
           
                hand.add(temp.getCopy(i));
          
        }
    } 
    private void getCards(CardManager temp, CardManager deck) {

        for (int i = 0; i < temp.cards.size(); i++) {
            
                all.add(temp.getCopy(i));
           
        }
        for (int i = 0; i < deck.cards.size(); i++) {
           
                all.add(deck.getCopy(i));
          
        }
    }
    private void getHandValue(){
        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 0; i < hand.cards.size(); i++) {
            value.add(hand.cards.get(i).getValue());
        }
        handValue = value;
    }
    private void getAllValue() {
        getHandValue();
        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 0; i < all.cards.size(); i++) {
            value.add(all.cards.get(i).getValue());
        }
        allValue = value;
    }

    private void getAllColor() {
        ArrayList<Integer> color = new ArrayList<>();
        for (int i = 0; i < all.cards.size(); i++) {
            color.add(all.cards.get(i).getColor());
        }
        allColor = color;
    }

    private void getHandTab(){
        int[] results = new int[15];
        String temp = null;
        for (int i = 0; i < handValue.size(); i++) {
            if (handValue.get(i) != 1) {
                results[handValue.get(i)]++;
            } else {
                results[handValue.get(i)]++;
                results[handValue.get(i) + 13]++;
            }
        }
        handTab = results;
    }
    private void getValueTab() {
        getHandTab();
        int[] results = new int[15];
        String temp = null;
        for (int i = 0; i < allValue.size(); i++) {
            if (allValue.get(i) != 1) {
                results[allValue.get(i)]++;
            } else {
                results[allValue.get(i)]++;
                results[allValue.get(i) + 13]++;
            }
        }
        valueTab = results;
    }

    private void getColorTab() {
        int[] results = new int[4];
        String temp = null;
        for (int i = 0; i < allColor.size(); i++) {
            results[allColor.get(i)]++;
        }
        colorTab = results;
    }

    public boolean isHighCard(int test) {
        return test == 1  && !isHighCard;
    }

    public boolean isPair(int test) {
        return test == 2;

    }
    public boolean isTriple(int test) {
        return test == 3;

    }

    public boolean isStraight(int count) {
        return count == 5;
    }

    public boolean isFlush(int test) {
        return test == 5;
    }

    public boolean isFullHouse(int test) {
        return isPair && isTriple && (test == 2 || test == 3);
    }

    public boolean isFour(int test) {
        return test == 4;
    }

    public boolean isStraightFlush() {
        return isStraight && isFlush;
    }
    
    public boolean isRoyalFlush(){
        boolean isBestCards = false;
        for(int i = 10; i < valueTab.length; i++){
            isBestCards = (valueTab[i] > 0);
        }
        return isBestCards && isStraightFlush;
    }
        
    public int countStraight(int test, int count) {
        if (test > 0) {
            count++;
            if (isStraight(count) && !isStraight) {
                isStraight = true;
            }
        } else {
            count = 0;
        }
        return count;
    }

    private void testAllFirst(int test) {
        if (isHighCard(test)){
            isHighCard = true;
        }
        if (isPair(test) && !isPair) {
            isPair = true;
        } else if (isPair(test) && isPair) {
            isTwoPairs = true;
        }
        if (isTriple(test)) {
            isTriple = true;
        }
    }

    private void testAllLast(int test) {
        if (isFullHouse(test)) {
            isFullHouse = true;
        }
        if (isFour(test)) {
            isFour = true;
        }
    }

    private void testUltimate() {
        isStraightFlush = isStraightFlush();
        isRoyalFlush = isRoyalFlush();
    }
    private void getHighest(){
        int temp = 0;
        for(int i = 1; i < valueTab.length; i++){
            if(valueTab[i] >0){
                temp = i;
            }
        }        
        high = temp;
    }
    
    private void getHandHigh(){
        int temp = 0;
        for(int i = 1; i < handTab.length; i++){
            if(handTab[i] >0){
                temp = i;
            }
        }        
        handHigh = temp;
        
    }
    
    private void setHighestPair(){
        int temp = 0;
        for(int i = 1; i < valueTab.length; i++){
            if(valueTab[i] == 2){
                temp = i;
            }
        }  
        if(temp > 10){
            
        }
        highestPair = temp;
    }
    
    public String showHighestPair(){
        String temp = "";
        if(highestPair == 14){
            temp = Card.FACES[0];
        }else if(highestPair > 10){
            temp = Card.FACES[highestPair - 10];
        }else{
            temp = "" + highestPair;
        }
        return temp;
    }
    public String showHigh(){
        String temp = "";
        if(high == 14){
            temp = Card.FACES[0];
        }else if(high > 10){
            temp = Card.FACES[high - 10];
        }else{
            temp = "" + high;
        }
        return temp;
    }
    
    
    
    public void getResults() {
        String temp = null;
        getValueTab();
        getColorTab();
        
        int countStr = 0;
        int start = 1;
        for (int i = 2; i < valueTab.length; i++) {
            if (valueTab[i] > 0) {
                testAllFirst(valueTab[i]);
                testAllLast(valueTab[i]);
            }
            countStr = countStraight(valueTab[start], countStr);
            start++;
        }
        countStr = countStraight(valueTab[start], countStr);
        for (int i = 0; i < colorTab.length; i++) {
            if(!isFlush && isFlush(colorTab[i])){
                isFlush = true;
            }
        }
        testUltimate();
        getHighest();
        getHandHigh();
        if(isPair || isTwoPairs){
            setHighestPair();
        }
        result = showResults();
    }
    public int getScore(){
        return score;
    }
    
    public int getHighestPair(){
        return highestPair;
    }
           
    private String showResults() {
        String temp = "";
        if (isHighCard) {
            score = high;
            temp = goals[0] + " with " + showHigh();
        }
        if (isPair) {
            score = 20 + high;
            temp = goals[1] + " of " + showHighestPair();
        }
        if (isTwoPairs) {
            score = 40 + high;
            temp = goals[2] + " best " + showHighestPair();
        }
        if (isTriple) {
            score = 60 + high;
            temp = goals[3];
        }
        if (isStraight) {
            score = 80 + high;
            temp = goals[4];
        }
        if (isFlush) {
            score = 100 + high;
            temp = goals[5];
        }
        if (isFullHouse) {
            score = 120 + high;
            temp = goals[6];
        }
        if (isFour) {
            score = 140 + high;
            temp = goals[7];
        }
        if (isStraightFlush) {
            score = 160 + high;
            temp = goals[8];
        }
        if (isRoyalFlush) {
            score = 180 + high;
            temp = goals[9];
        }
        return temp;
    }
}
