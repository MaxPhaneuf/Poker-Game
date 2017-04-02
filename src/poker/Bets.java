/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static poker.Poker.POSX_PICK;
import static poker.Poker.SIZEX;
import static poker.Poker.SIZEY;

/**
 *
 * @author Max
 */
public class Bets {
    
    
    private JFrame mainWin;
    public JPanel bets;
    public ArrayList<JPanel> cpuCards = new ArrayList<>();
    public CardManager hand;
    public int[] posX = {30, 120, 210, 300, 380};
    public int[] posXPicks = {20, 110};
    public int posY = 20;
    public int sizeX = 72;
    public int sizeY = 102;
    public JTextField blinds;
    public JLabel blindText;
    public JTextField pot;
    public JLabel potText;
    public JLabel raised;
    
    public Bets(JFrame mainWin){
        this.mainWin = mainWin;
         setUpFields();
    }
    
    
    public void setUpFields(){
        blindText = new JLabel("Blinds : ");
        blindText.setSize(75,20);
        blinds = new JTextField("0");
        blinds.setSize(75,20);
        bets.add(blindText);
        
        
       // blindText.setBounds(20, 20, 75, 20);
        
        
    }
    public void setUpBets() {
        bets = new JPanel();
        bets.setBounds(mainWin.getWidth() / 2, mainWin.getHeight() / 2,
                mainWin.getWidth() / 2, mainWin.getHeight() / 2);
        bets.setLayout(new FlowLayout(FlowLayout.CENTER));
        
    }
    
      
   
}
