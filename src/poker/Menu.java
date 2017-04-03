/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Max
 */
public class Menu {
   
    public Poker poker;
    public Table table;
    public JFrame mainWin;
    public JPanel menu;
    public JTextField stage;
    public JComboBox choice;
    public JButton showDeck;
    public JButton nextStage;
    public JButton showDiscard;
    public JButton newDraw;
    public JButton results;
    public JButton test;
    
    
    public Menu(Table table, JFrame mainWin){
        this.table = table;
        this.mainWin = mainWin;
        setUpMenu();
        
    }
    
    private void setUpMenu() {
        menu = new JPanel();
        menu.setBounds(0, mainWin.getHeight() / 2, mainWin.getWidth() / 2,
                mainWin.getHeight() / 2);
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu.setAlignmentY(0);
        
        setUpButtons();
        setUpText();
        addToMenu();
        
    }
    
    private void addToMenu() {
        menu.add(showDeck);
        menu.add(showDiscard);
        menu.add(nextStage);
        menu.add(newDraw);
        menu.add(stage);
        menu.add(results);
        menu.add(choice);
        menu.add(test);
    }
    
     private void setUpText() {
        stage = new JTextField();
        stage.setColumns(20);
        stage.setAlignmentX(SwingConstants.CENTER);
        stage.setEditable(false);
        choice = new JComboBox();
        for (String temp : Results.goals) {
            choice.addItem(temp);
        }
        
    }
     
     private void setUpButtons() {
        nextStage = new JButton("Flop");
        showDeck = new JButton("Show deck");
        showDiscard = new JButton("Show Discard");
        newDraw = new JButton("New draw");
        results = new JButton("Results");
        results.setEnabled(false);
        newDraw.setEnabled(false);
        test = new JButton("Test");
        
    }
   
}


