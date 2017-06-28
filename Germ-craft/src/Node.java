/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Beez
 */
import java.awt.Graphics;


public class Node {
    int id;
    int x = 100,y = 25;
    int multiplier = 2;
    int numGerm = 1;
    int playerNum = 0;
    int size = 100;
    boolean selected = false;
    
    public Node (int i,int m,int n,int p,int x,int y){
        id = i;
        multiplier =m;
        numGerm =n;
        playerNum = p;
        this.x=x;
        this.y=y;
    }
    public Node(int i){
        id = i;
    }
    public int getId(){
        return id;
    }
    public void setId(int i){
        id = i;
    }
    public int getMultiplier(){
        return multiplier;
    }
    public void setMultiplier(int m){
        multiplier = m;
    }
    public int getNumGerm(){
        return numGerm;
    }
    public void setNumGerm (int n){
        numGerm = n;
    }
    public int getPlayerNum (){
        return playerNum;
    }
    public void setPlayerNum(int p){
        playerNum = p;
    }
    public int getX (){
        return x;
    }
    public void setX (int x){
        this.x = x;
    }
    public int getY (){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int s){
        size = s;
    }
    public boolean isSelected(){
        return selected;
    }
    public void select(){
        selected = true;
    }
    public void notselect(){
        selected = false;
    }
    public void draw(Graphics g){
        g.drawOval(x,y,size,size);
        //g.drawRect(x, y, size, size);
        if ((Integer.toString(numGerm).length()) <2){
            g.drawString(Integer.toString(numGerm),x+(size/2)-3,y+(size/2)+3);
        }
        else if((Integer.toString(numGerm).length())%2==0){
            g.drawString(Integer.toString(numGerm),x+(size/2)-((Integer.toString(numGerm).length())/2)*6,y+(size/2)+3);  
        }
        else {
            g.drawString(Integer.toString(numGerm),x+(size/2)-((Integer.toString(numGerm).length())/2)*8,y+(size/2)+3);  
        }
        
    }
}
