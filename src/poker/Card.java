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

/**
 *
 * @author Max
 */
public class Card {
    public static String [] colors = {"Trefle", "Pique", "Coeur", "Carreau"};  
    public static String [] faces = {null, "J","Q","K"};
    private BufferedImage image = addImage("CardBack.png");
    private String name;
    private int color;
    private int value;
    private boolean picked = false;
    
    public Card(int color, int value){
        this.color = color;
        this.value = value;
        setName(color, value);
        
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
    public BufferedImage getImage(){
        return image;
    }
    
    public boolean isPicked(){
        return picked;
    }
    
    public void pick(){
        picked = true;
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
       // System.out.println(name);
    }
    
    public BufferedImage setInPlay(){
        setImage();
        return image;
    }
    
    
       
    public void setImage(){
        image = addImage(name);
    }
    
    public BufferedImage addImage(String fileName) {
        
        BufferedImage image = null;
        try {
            File file = new File(fileName);
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return image;
    }
    
}

