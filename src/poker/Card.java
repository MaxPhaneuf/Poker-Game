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
    public static String [] faces = {"J","Q","K","A"};
    private BufferedImage image;
    private String name;
    
    
    
    public Card(int color, int value){
        setName(color, value);
        setImage();
    }
    
    public String getName(){
        return name;
    }
    public BufferedImage getImage(){
        return image;
    }
    public void setName(int color, int value){
                        
        name = colors[color];
        if(value > 10){
            name = name + faces[value - 10];
        }else{
            name = name + value;
        }
    }
       
    public void setImage(){
        image = addImage(name + ".png");
    }
    
    public BufferedImage addImage(String fileName) {
        System.out.println(fileName);
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

