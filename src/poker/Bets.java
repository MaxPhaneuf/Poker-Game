/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Max
 */
public class Bets {
    
    
    private JFrame mainWin;
    public JPanel bets;
    public JTextField blinds;
    public JLabel blindText;
    public JTextField pot;
    public JLabel potText;
    public JTextField raised;
    public JLabel raisedText;
    
    
    public BigDecimal blindMoney;
    public BigDecimal potMoney;
    public BigDecimal raiseMoney;
    public Bets(JFrame mainWin){
        this.mainWin = mainWin;
        blindMoney = new BigDecimal(0);
        potMoney = new BigDecimal(0);
        raiseMoney = new BigDecimal(0);
        setUpBets(); 
        setUpFields();
        showMoney(); 
    }
    
    
    public void setUpFields(){
        blindText = new JLabel("Blinds : ");
        blindText.setBounds(0, 0, 50 ,20);
        blinds = new JTextField("0$");
        blinds.setBounds(55, 0, 100 ,20);
        blinds.setEditable(false);
        
        raisedText = new JLabel("Raise : ");
        raisedText.setBounds(0, 30, 50, 20);
        raised = new JTextField("0$");
        raised.setBounds(55, 30, 100 ,20);
        raised.setEditable(false);
        
        potText = new JLabel("Pot : ");
        potText.setBounds(0, 60, 50 ,20);
        pot = new JTextField("0$");
        pot.setBounds (55, 60, 100 ,20);
        pot.setEditable(false);
        
        
        
        bets.add(blindText);
        bets.add(blinds);
        bets.add(potText); 
        bets.add(pot);
        bets.add(raisedText);
        bets.add(raised);
    }
    
    public void setUpBets() {
        bets = new JPanel();
        bets.setBounds(mainWin.getWidth() / 2, mainWin.getHeight() / 2,
                mainWin.getWidth() / 2, mainWin.getHeight() / 2);
        bets.setLayout(null);
        
    }
    
    public void showMoney(){
        
        blinds.setText(blindMoney + "$");
        pot.setText(potMoney + "$");
        raised.setText(raiseMoney + "$");
        
    }
    
    
    
      
   
}
