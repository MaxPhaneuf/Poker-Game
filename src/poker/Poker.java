/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 *
 * @author Max
 */
public class Poker implements ActionListener{

    private JFrame mainWin;
    private JPanel table;
    private JPanel butPanel;
    private JPanel bet;
    private JPanel [] tableCards = new JPanel[5];
    private JPanel [] handCards = new JPanel[2];
    private JButton raise;
    private JButton newGame;
    private JButton fold;
    private boolean turn = false;
    private int [] posX = {20, 110, 200, 290, 380};
    private int posY = 20;
    private int sizeX = 72;
    private int sizeY = 102;
    private Hand hand;
    
    public Poker(){
        setUpWindow();
        
        refresh();
        
               
    }
    
    
    
    
    private void setUpWindow(){
        
        mainWin = new JFrame("TexasHold'Em Poker");
        mainWin.setSize(500, 300);
        mainWin.setLocationRelativeTo(null);
        mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWin.setLayout(null);
        table = new JPanel();
        table.setSize(mainWin.getWidth(), mainWin.getHeight()/2);
        table.setLayout(null);
        table.setBackground(Color.green);
        butPanel = new JPanel();
        butPanel.setBounds(0 , mainWin.getHeight()/2 , mainWin.getWidth()/2,
                mainWin.getHeight()/2);
        butPanel.setLayout(new FlowLayout());
        butPanel.setBackground(Color.red);
        
        newGame = new JButton("New Game");
        newGame.setBounds(20, 200, 200, 50);
        newGame.addActionListener(this);
        raise = new JButton("Show deck");
        raise.setBounds(20, 230, 200, 50);
        raise.addActionListener(this);
        
        butPanel.add(newGame);
        butPanel.add(raise);
        
    }
    
    public void setUpTable(){
            
        for(int i = 0; i < 5; i++){
            createNewCard(i);
            addFace(i, hand.cards[i].getImage());
                               
        }
        refresh();
        
    }
    public void refresh(){
        mainWin.getContentPane().add(table);
        mainWin.getContentPane().add(butPanel);
        mainWin.setVisible(true);
    }
    public void putInPlay(int i){
        table.remove(tableCards[i]);
        addCardPanel(i);
        addFace(i, hand.cards[i].setInPlay());
        refresh();
    }
    
    public void createNewCard(int i){
        
        addCardPanel(i);
        //cards[i] = Deck.pickCard();
        
       
    }
            
    public void addCardPanel(int i){
        tableCards[i] = new JPanel();
        tableCards[i].setBounds(posX[i], posY, sizeX, sizeY);
        tableCards[i].setBackground(Color.GREEN);
        tableCards[i].setOpaque(true);
    }
    
    public void addFace(int i, BufferedImage name){
        
        JLabel image = new JLabel(new ImageIcon(name));
        tableCards[i].add(image);
        table.add(tableCards[i]);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGame){
            
            if(!turn){
                hand = new Hand();
                setUpTable();
                turn = true; 
                
            }else{
                putInPlay(0);
                putInPlay(1);
                putInPlay(2);
                putInPlay(3);
                putInPlay(4);
             
            }
            
            
        }else if(e.getSource() == raise){
            Deck.showDeck();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Poker();
    }
    
    
    
    
}
