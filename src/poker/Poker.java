/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Max
 */
public class Poker {

    private JFrame mainWin;
    private JPanel table;
    private JPanel hand;
    private JPanel bet;
    private JPanel [] tableCards = new JPanel[5];
    private JPanel [] handCards = new JPanel[2];
    private JButton raise;
    private JButton call;
    private JButton fold;
    
    public Poker(){
        setUpWindow();
        setUpTable();
        
       
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(4) + 1;
        System.out.println(randomInt);
        new Poker();
    }
    
    
    private void setUpWindow(){
        
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(800, 600);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        table = new JPanel();
        table.setSize(mainWin.getWidth(), mainWin.getHeight()/2);
        table.setLayout(null);
        table.setBackground(Color.green);
        
        hand = new JPanel();
        hand.setBounds(0 , mainWin.getHeight()/2 , mainWin.getWidth()/2,
                mainWin.getHeight()/2);
        hand.setLayout(new FlowLayout());
        hand.setBackground(Color.red);
        
        mainWin.getContentPane().add(table);
        mainWin.getContentPane().add(hand);
        mainWin.setVisible(true);
    }
    
    public void setUpTable(){
        int x = 25;
        int y = 50;
        for(JPanel tableCard: tableCards){
            tableCard = new JPanel();
            tableCard.setBounds(x, y, 125, 200);
            tableCard.setBackground(Color.BLACK);
            tableCard.setOpaque(true);
            table.add(tableCard);
            x = x + 155;
                        
        }
    }
    
    
    
}
