/**
 *
 * @author Beez
 */
import java.awt.Graphics;


public class Node {
    int id;
    int x = 25,y = 25;
    int multiplier = 0;
    int numGerm = 5;
    int player = 0;
    int size = 100;
    int attack = 0;
    boolean selected = false;
    
    public Node (int i,int m,int n,int p,int x,int y){
        id = i;
        multiplier =m;
        numGerm =n;
        player = p;
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
    public int getPlayer (){
        return player;
    }
    public void setPlayer(int p){
        player = p;
        if(p!=0){
            this.multiplier =2;
        }
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
    public void deselect(){
        selected = false;
    }
    //attacking 1 is offense 2 is defense maybe something else tho
    /*
    public void attacking(int a){
        attack = a;
    }
*/
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
