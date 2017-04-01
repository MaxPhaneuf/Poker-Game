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
    
    public static int score = 0;
    public static int high;
    public static int highestPair;
    public static boolean isHighCard = false;
    public static boolean isPair = false;
    public static boolean isTwoPairs = false;
    public static boolean isTriple = false;
    public static boolean isStraight = false;
    public static boolean isFlush = false;
    public static boolean isFullHouse = false;
    public static boolean isFour = false;
    public static boolean isStraightFlush = false;
    public static boolean isRoyalFlush = false;
    public static CardManager all = new CardManager();
    public static String[] goals = {"High card", "Pair", "Two pairs",
        "Three of a kind", "Straight", "Flush", "FullHouse",
        "Four of a kind", "Straight Flush", "Royal Flush"};

    public static String compare(CardManager hand, CardManager deck) {
        score = 0;
        isHighCard = false;
        isPair = false;
        isTwoPairs = false;
        isTriple = false;
        isStraight = false;
        isFlush = false;
        isFullHouse = false;
        isFour = false;
        isStraightFlush = false;
        isRoyalFlush = false;
        all = new CardManager();
        all = getCards(hand, deck);

        return getResults(getAllColor(), getAllValue());
    }

    private static CardManager getCards(CardManager hand, CardManager deck) {

        for (int i = 0; i < hand.cards.size(); i++) {
            if (hand.cards.get(i).isInPlay()) {
                all.add(hand.getCopy(i));
            }
        }
        for (int i = 0; i < deck.cards.size(); i++) {
            if (deck.cards.get(i).isInPlay()) {
                all.add(deck.getCopy(i));
            }
        }
        return all;
    }

    private static ArrayList<Integer> getAllValue() {
        ArrayList<Integer> value = new ArrayList<>();
        for (int i = 0; i < all.cards.size(); i++) {
            value.add(all.cards.get(i).getValue());
        }
        return value;
    }

    private static ArrayList<Integer> getAllColor() {
        ArrayList<Integer> color = new ArrayList<>();
        for (int i = 0; i < all.cards.size(); i++) {
            color.add(all.cards.get(i).getColor());
        }
        return color;
    }

    private static int[] getValueTab(ArrayList<Integer> value) {
        int[] results = new int[15];
        String temp = null;
        for (int i = 0; i < value.size(); i++) {
            if (value.get(i) != 1) {
                results[value.get(i)]++;
            } else {
                results[value.get(i)]++;
                results[value.get(i) + 13]++;
            }
        }
        return results;
    }

    private static int[] getColorTab(ArrayList<Integer> color) {
        int[] results = new int[4];
        String temp = null;
        for (int i = 0; i < color.size(); i++) {
            results[color.get(i)]++;
        }
        return results;
    }

    public static boolean isHighCard(int test) {
        return test == 1  && !isHighCard;
    }

    public static boolean isPair(int test) {
        return test == 2;

    }
    public static boolean isTriple(int test) {
        return test == 3;

    }

    public static boolean isStraight(int count) {
        return count == 5;
    }

    public static boolean isFlush(int test) {
        return test == 5;
    }

    public static boolean isFullHouse(int test) {
        return isPair && isTriple && (test == 2 || test == 3);
    }

    public static boolean isFour(int test) {
        return test == 4;
    }

    public static boolean isStraightFlush() {
        return isStraight && isFlush;
    }
    
    public static boolean isRoyalFlush(int[] value){
        boolean isBestCards = false;
        for(int i = 10; i < value.length; i++){
            isBestCards = (value[i] > 0);
        }
        return isBestCards && isStraightFlush;
    }
        
    public static int countStraight(int test, int count) {
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

    private static void testAllFirst(int test) {
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

    private static void testAllLast(int test) {
        if (isFullHouse(test)) {
            isFullHouse = true;
        }
        if (isFour(test)) {
            isFour = true;
        }
    }

    private static void testUltimate(int[] value) {
        isStraightFlush = isStraightFlush();
        isRoyalFlush = isRoyalFlush(value);
    }
    private static void getHighest(int [] value){
        int temp = 0;
        for(int i = 1; i < value.length; i++){
            if(value[i] >0){
                temp = i;
            }
        }        
        high = temp;
    }
    
    private static void setHighestPair(int [] value){
        int temp = 0;
        for(int i = 1; i < value.length; i++){
            if(value[i] == 2){
                temp = i;
            }
        }  
        if(temp > 10){
            
        }
        highestPair = temp;
    }
    
    public static String showHighestPair(){
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
    public static String showHigh(){
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
    
    
    
    public static String getResults(ArrayList<Integer> color,
            ArrayList<Integer> value) {
        String temp = null;
        int[] resultsValue = getValueTab(value);
        int[] resultsColor = getColorTab(color);
        int countStr = 0;
        int start = 1;
        int last = resultsColor[0];
        for (int i = 2; i < resultsValue.length; i++) {
            if (resultsValue[i] > 0) {
                testAllFirst(resultsValue[i]);
                testAllLast(resultsValue[i]);
            }
            countStr = countStraight(resultsValue[start], countStr);
            start++;
        }
        countStr = countStraight(resultsValue[start], countStr);
        for (int results : resultsColor) {
            if(!isFlush && isFlush(results)){
                isFlush = true;
            }
        }
        testUltimate(resultsValue);
        getHighest(resultsValue);
        score = high;
        if(isPair || isTwoPairs){
            setHighestPair(resultsValue);
        }
        return showResults();
    }
    public static int getScore(){
        return score;
    }
    
    public static int getHighestPair(){
        return highestPair;
    }
           
    private static String showResults() {
        String temp = "";
        if (isHighCard) {
            temp = goals[0] + " with " + showHigh();
        }
        if (isPair) {
            score = score + 20;
            temp = goals[1] + " of " + showHighestPair();
        }
        if (isTwoPairs) {
            score = score + 40;
            temp = goals[2] + " best " + showHighestPair();
        }
        if (isTriple) {
            score = score + 60;
            temp = goals[3];
        }
        if (isStraight) {
            score = score + 80;
            temp = goals[4];
        }
        if (isFlush) {
            score = score + 100;
            temp = goals[5];
        }
        if (isFullHouse) {
            score = score + 120;
            temp = goals[6];
        }
        if (isFour) {
            score = score + 140;
            temp = goals[7];
        }
        if (isStraightFlush) {
            score = score + 160;
            temp = goals[8];
        }
        if (isRoyalFlush) {
            score = score + 180;
            temp = goals[9];
        }
        return temp;
    }
}
