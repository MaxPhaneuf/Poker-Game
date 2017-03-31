/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Max
 */
public class Card {
    public static String [] colors = {"Trefle", "Pique", "Coeur", "Carreau"};  
    public static String [] faces = {null, "J","Q","K"};
    private JLabel image;
    private ImageIcon show;
    private ImageIcon hide; 
   
    private String name;
    private int color;
    private int value;
    
    
    public Card(int color, int value){
        this.color = color;
        this.value = value;
        setName(color, value);
        setImage();
        
    }
    
    public int getColor(){
        return color;
    }
    
    public int getValue(){
        return value;
    }
    public String getName(){
        return name;
    }
    public JLabel getImage(){
        return image;
    }
    
    public ImageIcon getHide(){
        return hide;
    }
       
    public void setName(int color, int value){
                        
        
        name = colors[color];
        if(value > 10){
            name = name + faces[value - 10];
        }else if(value == 1){
            name = name + "A";
        }else{
            name = name + value;
        }
        name = name + ".png";
    }
    
    public void setInPlay(){
        show = new ImageIcon(addImage(name));
        image.setIcon(show);
        
    }
           
    private void setImage(){
        hide = new ImageIcon(addImage("CardBack.png"));
        image = new JLabel(hide);
        
    }
           
    public BufferedImage addImage(String fileName) {
        
        BufferedImage temp = null;
        
        try {
            File file = new File(fileName);
            temp = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return temp;
    }
    @Override
    public String toString(){
        return name;
    }
    
    @Override
    public boolean equals(Object o){
        return this.name == o.toString();
    }
    
}

