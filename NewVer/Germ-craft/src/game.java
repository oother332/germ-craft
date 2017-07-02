/**
 * Created by Beez & George
 * Most Recently
 * 
 * Added unit transfer function - hold t to transfer germs between allied nodes
 * Added attacking functionality - code in mouseclicked keypressed and keyreleased
 * as well as boolean gates in a lot of the selection code.
 * attacking works while holding 'A'
 * 
 * added MouseMotionListener
 * added code into mouse pressed,released, and dragged for multiple selection
 * added visual code for multiple selection
 * added new function boxSelect() to for multiple selection
 * 
 * moved single selection code to mouse clicked as to not interfere with multiple selection
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class game extends JFrame implements KeyListener, ActionListener, MouseListener, MouseMotionListener{
    //put variables here plz;
    static int nodeMax = 6;
    static int count=0;
    static int numOfRows = 3;
    static int startBoxX;
    static int startBoxY;
    static int boxX;
    static int boxY;
    static int selectedN = -1;
    
    static Random rand = new Random();
    
    static boolean boxClick=false;
    static boolean attack = false;
    static boolean justAttacked=false;
    static boolean selected = false;
    
    Timer t = new Timer(1000,this);
    static Node[] nodes = new Node[nodeMax];//array of node
    //static Node node = new Node(1); // single node for testing
    Color background = Color.BLACK;
    Color selectionColor = Color.BLUE;
    
    //public static Node1[] nodeList = new Node1[nodeMax*nodeMax];
    //Player p1 = new Player(1,0);
    //Player p2 = new Player(2,1);
    private DrawCanvas canvas;
    public game(){
        canvas = new DrawCanvas ();                                             //Initializing Game stats
        canvas.setPreferredSize(new Dimension(700,400));
        Container cp = getContentPane();
        cp.add(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Game");
        setVisible(true);
        
        t.setInitialDelay(1000);
        t.start();
        
        for(int i = 0; i < nodeMax ; i++){                                      //Create the array of nodes
            nodes[i] = new Node(i);
        }
        int ran = rand.nextInt(6);                                              //Set Player 1 Location and their number of Germs
        nodes[ran].setPlayer(1);
        nodes[ran].setNumGerm(100);
        positionNodes(nodes,numOfRows);
        //nodes[1].setX(300);
        
        addKeyListener(this);                                                   //Initialize I/O
        addMouseListener(this);
        addMouseMotionListener(this);
        }

    private class DrawCanvas extends JPanel {
        @Override
        public void paintComponent (Graphics g){
            super.paintComponent(g);
            //System.out.println(g.getColor());
            
            setBackground(background);
            for (int i=0;i<nodeMax;i++){
               if (nodes[i].isSelected() ==true){
                    g.setColor(selectionColor);
                    nodes[i].draw(g);
                }
                else{
                    if(nodes[i].getPlayer()==1){
                       g.setColor(Color.GREEN.darker());
                    }
                    else{
                       g.setColor(Color.RED);
                   }
                nodes[i].draw(g);
                }                
            }
            if(boxClick ==true){
                g.setColor(Color.GREEN.brighter());
                int w=boxX-startBoxX;
                int h = boxY-startBoxY;
                //System.out.println("w: "+w+" h: "+h);
                if(w!=0&&h!=0){
                    if(w<0&&h<0){
                        g.drawRect(boxX, boxY, (-1*w), (-1*h));                        
                    }
                    else if(h<0){
                        g.drawRect(startBoxX, boxY, w, (-1*h));
                    }
                    else if(w<0){
                        g.drawRect(boxX, startBoxY, (-1*w), h);
                    }
                    else{
                        g.drawRect(startBoxX,startBoxY,w,h);
                    }
                }
            }
        }
    }
    public void mouseMoved(MouseEvent e){
        
    }
    public void mouseDragged(MouseEvent e){
        if(boxClick==true){
            boxX=e.getX();
            boxY=e.getY();
            boxSelect();
            //System.out.println("IT SHOULD BE WORKING");
        }
        repaint();
        
    }
    
    public void mousePressed (MouseEvent e){
        if((e.getButton()==e.BUTTON1)&&(attack==false)){
        for(int i=0;i<nodeMax;i++){
            nodes[i].deselect();
        }
        boxClick=true;
        startBoxX=e.getX();
        startBoxY=e.getY();
        
        boxX=e.getX();
        boxY=e.getY();
        }
        repaint();
        
    }
    public void mouseReleased (MouseEvent e){
        if(e.getButton()==e.BUTTON1){
        boxClick=false;
        justAttacked=false; 
        repaint();
        }
    }
    public void mouseEntered (MouseEvent e){
        
    }
    public void mouseExited (MouseEvent e){
        
    }
    public void mouseClicked (MouseEvent e){
        if((e.getButton()==e.BUTTON1)&&(attack==false)&&selected == false){
            for (int i=0;i<nodeMax;i++){
                int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
                int nodeXMax = (nodes[i].getX()+nodes[i].getSize())+8;///2);
                int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
                int nodeYMax = (nodes[i].getY()+nodes[i].getSize())+30;///2);
            
                if((e.getX()>=nodeXMin&&e.getX()<=nodeXMax)&&(e.getY()>=nodeYMin&&e.getY()<=nodeYMax)&&nodes[i].getPlayer()==1){
                    nodes[i].select();    
                    selectedN = i;
                }
                else{
                    nodes[i].deselect();
                }
            }
        }
        if(selected == true && e.getButton() == e.BUTTON1 && justAttacked == false){
            for (int i=0;i<nodeMax;i++){
                int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
                int nodeXMax = (nodes[i].getX()+nodes[i].getSize())+8;///2);
                int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
                int nodeYMax = (nodes[i].getY()+nodes[i].getSize())+30;///2);
                
                if((e.getX()>=nodeXMin&&e.getX()<=nodeXMax)&&(e.getY()>=nodeYMin&&e.getY()<=nodeYMax)&&(nodes[i].getPlayer()==1)&&selectedN >= 0){
                    nodes[i].select();
                    transferUnits(i);
                }
        }
        }
        if((attack==true)&&(e.getButton()==e.BUTTON1)&&(justAttacked==false)) {
            for (int i=0;i<nodeMax;i++){
                int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
                int nodeXMax = (nodes[i].getX()+nodes[i].getSize())+8;///2);
                int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
                int nodeYMax = (nodes[i].getY()+nodes[i].getSize())+30;///2);
            
                if((e.getX()>=nodeXMin&&e.getX()<=nodeXMax)&&(e.getY()>=nodeYMin&&e.getY()<=nodeYMax)&&nodes[i].getPlayer()!=1){
                    attacking(i);
                    //System.out.println("clicked on a node");
                }
        }
        
    }
        repaint();
    }

    public void attacking (int defId){
        if(justAttacked==false){
        int aGerms = 0;
        for (int i =0;i<nodeMax;i++){
            if (nodes[i].isSelected()==true){
                aGerms=aGerms+nodes[i].getNumGerm();
                nodes[i].setNumGerm(nodes[i].getNumGerm()/2);
            }
        }
        System.out.println("aGerms: "+aGerms);
        nodes[defId].setNumGerm((nodes[defId].getNumGerm()-aGerms));
        
        if(nodes[defId].getNumGerm()<=0){
            nodes[defId].setPlayer(1);
            nodes[defId].setNumGerm(1);
        }
        justAttacked=true;
        }

    }
    
    public void transferUnits(int dId){
        int tGerms = 0;
        tGerms = nodes[selectedN].getNumGerm()/2;
        nodes[selectedN].setNumGerm(tGerms);
        for(int i= 0; i < nodeMax; i++){
            if(nodes[i].isSelected() && i == dId){
                nodes[i].setNumGerm(nodes[i].getNumGerm() + tGerms);

            }
        }

    }
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==t){
            for(int i=0;i<nodeMax;i++){
                nodes[i].setNumGerm((nodes[i].getNumGerm())+(nodes[i].getMultiplier()));
            }
            //node.setNumGerm((node.getNumGerm())*(node.getMultiplier()));
        }
            repaint();
    }
    

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar()=='a'||e.getKeyChar()=='A'){
            for (int i =0;i<nodeMax;i++){
                if((nodes[i].isSelected()==true)&&justAttacked==false){
                    attack=true;
                    //System.out.println("ATTACK");
                }
                
            }
        }
        if(e.getKeyChar()=='t'||e.getKeyChar()=='T'){
            for (int i =0;i<nodeMax;i++){
                if((nodes[i].isSelected()==true)&&justAttacked==false){
                    selected=true;
                }
                
            }
        }
        repaint();
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyChar()=='a'||e.getKeyChar()=='A'){
            attack=false;
            justAttacked=false;
        }
        if(e.getKeyChar()=='t'||e.getKeyChar()=='T'){
            selected = false;
        }

        
        repaint();
    }
    
    public void keyTyped(KeyEvent e){
       
    }
    public static void main(String[] args){

         SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new game(); // Let the constructor do the job
         }
      });
    }
    public void boxSelect(){
        int nodeCX,nodeCY;
        for (int i=0;i<nodeMax;i++){
            nodeCX=nodes[i].getX()+(nodes[i].getSize()/2);
            nodeCY=nodes[i].getY()+(nodes[i].getSize()/2);
            
            if((startBoxX<boxX&&startBoxY<boxY)&&(nodeCX>startBoxX&&nodeCX<boxX)&&(nodeCY>startBoxY&&nodeCY<boxY)){
                nodes[i].select();
            }
            else if((startBoxX>boxX&&startBoxY<boxY)&&(nodeCX<startBoxX&&nodeCX>boxX)&&(nodeCY>startBoxY&&nodeCY<boxY)){
                nodes[i].select();
            }
            else if((startBoxX<boxX&&startBoxY>boxY)&&(nodeCX>startBoxX&&nodeCX<boxX)&&(nodeCY<startBoxY&&nodeCY>boxY)){
                nodes[i].select();
            }
            else if((startBoxX>boxX&&startBoxY>boxY)&&(nodeCX<startBoxX&&nodeCX>boxX)&&(nodeCY<startBoxY&&nodeCY>boxY)){
                nodes[i].select();
            }
            if (nodes[i].getPlayer()!=1){
                nodes[i].deselect();
                selectedN = -1;
            }
        }
    }
    public void positionNodes(Node[] n,int r){
        
        if(r==1){
            for (int i = 0;i<nodeMax;i++){
                if(i>0){
                    n[i].setX(i*n[i].getSize()+20*i);
                }
                else{
                    n[i].setX(i*n[i].getSize());
                }
            }
        }
        else if(r==nodeMax){
            for (int i = 0;i<nodeMax;i++){
                if(i>0){
                    n[i].setY(i*n[i].getSize()+20*i);
                }
                else{
                    n[i].setY(i*n[i].getSize());
                }
            }
        }
        
        else{
            int col=1;
            int row=0;
                for (int i=1;i<nodeMax;i++){
                    //if(i>((nodeMax/r)*(a-1))&&i<((nodeMax/r)*(a))){
                        //System.out.println("Highest nodenum: "+(nodeMax*a/r));
                        n[i].setX(n[i].getX()+col*n[i].getSize()+20*col);
                        n[i].setY(n[i].getY()+row*n[i].getSize()+20*row);
                        //System.out.println("row: "+row);
                        if(col<nodeMax/r){
                            col++;
                    }
                        else{
                            col=0;
                            row++;
                        }
                }
        }
    }
}
