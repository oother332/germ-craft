/**
 * Created by George on 3/18/2017.
 */
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class main {
    static int nodeMax = 4;
    static int count=0;
    public static Node1[] nodeList = new Node1[nodeMax*nodeMax];
    Player p1 = new Player(1,0);
    Player p2 = new Player(2,16);

/*
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == Key.Event.VK_W){

        } else if (key == Key.Event.VK_A){

        } else if (key == Key.Event.VK_S){

        } else if (key == Key.Event.VK_D){

        }
    }

    public void keyReleased(KeyEvent e){

    }
*/

    public static void main(String[] args){
        for(int i = 0; i < nodeMax ; i++){
            for(int k = 0; k < nodeMax; k++) {
                nodeList[count] = new Node1(count, 1, 0, 200*i, 200*k);
                count++;
            }
        }

        while(true){
            for(int j = 0; j < 16; j ++){
                System.out.println("id = " + nodeList[j].id + "x = " + nodeList[j].x + "y = " + nodeList[j].y);
            }
            if (){
                System.out.println("xD");
            }
            break;
        }
    }
}
