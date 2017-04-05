/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Max
 */
public class Table {
    
    private JFrame mainWin;
    public JPanel table;
    public ArrayList<JPanel> tableCards = new ArrayList<>();
    public CardManager draw;
    public int[] posX = {30, 120, 210, 300, 390};
    public int[] posXPicks = {20, 110};
    public int posY = 20;
    public int sizeX = 72;
    public int sizeY = 102;
    
    public Table(JFrame mainWin){
        this.mainWin = mainWin;
        setUpTable();
        setUpFlop();
    }
    
    public Card get(int i){
        return draw.get(i);
    }
    
    public int size(){
        return draw.size();
    }
    
    public void setUpTable() {
        table = new JPanel();
        table.setSize(mainWin.getWidth(), (mainWin.getHeight() / 3) + 20);
        table.setLayout(null);
        table.setBackground(Color.BLACK);
    }
    
    public void setUpFlop() {
        draw = new CardManager();
        Deck.burn();
        draw.pickUp(3);
        for (int i = 0; i < draw.cards.size(); i++) {
            createTable(i);
        }
    }
    
    public void setUpOne(int card) {
        Deck.burn();
        draw.pickUp(1);
        createTable(card);
        
    }
    
    public void createTable(int i) {
        
        createNewTableCard(i);
        tableCards.get(i).add(draw.cards.get(i).getImage());
        table.add(tableCards.get(i));
        
    }
    
    public void createNewTableCard(int i) {
        
        tableCards.add(new JPanel());
        tableCards.get(i).setBounds(posX[i], posY, sizeX, sizeY);
        tableCards.get(i).setBackground(Color.BLACK);
    }
    
    public void resetTable() {
        
        while (!tableCards.isEmpty()) {
            tableCards.get(0).setVisible(false);
            tableCards.remove(0);
            draw.cards.remove(0);
        }
        
    }
    
    public void newDraw(){
        resetTable();
        setUpFlop();
    }
      
}
