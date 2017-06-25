/**
 * Created by George on 3/18/2017.
 */

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class game extends JFrame implements KeyListener, ActionListener, MouseListener{
    //put variables here plz;
    static int nodeMax = 3;
    static int count=0;
    static int key;
    static int numOfRows = 3;
    
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
        canvas = new DrawCanvas ();
        canvas.setPreferredSize(new Dimension(700,400));
        Container cp = getContentPane();
        cp.add(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Game");
        setVisible(true);
        
        t.setInitialDelay(1000);
        t.start();
        
        for(int i = 0; i < nodeMax ; i++){
            nodes[i] = new Node(i);
        }
        positionNodes(nodes,numOfRows);
        //nodes[1].setX(300);
        
        addKeyListener(this);
        addMouseListener(this);
        }

    private class DrawCanvas extends JPanel {
        @Override
        public void paintComponent (Graphics g){
            super.paintComponent(g);
            //System.out.println(g.getColor());
            
            setBackground(background);
            /*
            if (node.isSelected() ==true){
                g.setColor(selectionColor);
            }
            else{
                g.setColor(Color.YELLOW);
            }
            node.draw(g);
            */
            for (int i=0;i<nodeMax;i++){
               if (nodes[i].isSelected() ==true){
                    g.setColor(selectionColor);
                    nodes[i].draw(g);
                }
                else{
                 g.setColor(Color.YELLOW);
                 nodes[i].draw(g);
                }                
            }
        }
    }
    public void mousePressed (MouseEvent e){
        /* debug
        System.out.println("MousePressed");
        System.out.println("xMin: "+nodeXMin+" xMax: "+nodeXMax+" yMin: "+nodeYMin+" yMax: "+nodeYMax);
        System.out.println("e.getX: "+e.getX()+" e.getY: "+e.getY());
        count++;
        System.out.println(count);
        
        int nodeXMin = (node.getX()-node.getSize()/2);
        int nodeXMax = (node.getX()+node.getSize()/2);
        int nodeYMin = (node.getY()-node.getSize()/2);
        int nodeYMax = (node.getY()+node.getSize()/2);
        
        if((e.getX()>nodeXMin&&e.getX()<nodeXMax)&&(e.getY()>nodeYMin&&e.getY()<nodeYMax)){
            node.select();
        }
        else{
            node.notselect();
        }
        */
        
        for (int i=0;i<nodeMax;i++){
            int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
            int nodeXMax = (nodes[i].getX()+nodes[i].getSize())+8;///2);
            int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
            int nodeYMax = (nodes[i].getY()+nodes[i].getSize())+30;///2);
            
            if((e.getX()>=nodeXMin&&e.getX()<=nodeXMax)&&(e.getY()>=nodeYMin&&e.getY()<=nodeYMax)){
                nodes[i].select();
            }
            else{
                nodes[i].notselect();
            }
        }
        repaint();
    }
    public void mouseReleased (MouseEvent e){
        
    }
    public void mouseEntered (MouseEvent e){
        
    }
    public void mouseExited (MouseEvent e){
        
    }
    public void mouseClicked (MouseEvent e){
        
    }
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==t){
            for(int i=0;i<nodeMax;i++){
                nodes[i].setNumGerm((nodes[i].getNumGerm())*(nodes[i].getMultiplier()));
            }
            //node.setNumGerm((node.getNumGerm())*(node.getMultiplier()));
        }
            repaint();
    }
    

    public void keyPressed(KeyEvent e){
        /*
        key = e.getKeyCode();
        System.out.println(key);
        if (key == 119||key==87){
                background=Color.RED;
            } else if (key == 97||key==65){
                background=(Color.BLUE);
            } else if (key == 115||key==83){
                background=(Color.YELLOW);
            } else if (key == 100||key==68){
                background=(Color.GREEN);
            }
        */
        repaint();
    }

    public void keyReleased(KeyEvent e){
        
    }
    
    public void keyTyped(KeyEvent e){
       
    }
    public static void main(String[] args){
       
        /*
        while(true){
            for(int j = 0; j < 16; j ++){
                //System.out.println("id = " + nodeList[j].id + "x = " + nodeList[j].x + "y = " + nodeList[j].y);
            }
            if (true){
                System.out.println("xD");
            }
            break;
        }
        */
         SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new game(); // Let the constructor do the job
         }
      });
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
                    n[i].setY(i*n[i].getSize()+10);
                }
            }
        }
        else{
            for (int a=1;a<r;a++){
                for (int i=1;i<nodeMax;i++){
                    n[i].setX(i*n[i].getSize()+20*i);
                    n[i].setY(a*n[i].getSize()+20*a);
                }
            }
        }
    }
}
/* Not this shit!
    private static void rockTheGUI (){
        JFrame frame = new JFrame ("JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
       // panel.setSize(300,300);
        frame.setLocation(400, 200);
        frame.setSize(400,400);
        frame.setVisible(true);
    }
    public void paintComponent (Graphics g){
        
        g.setColor(Color.GREEN);
        g.fillOval(100,100,100,50); 
        super.paintComponent (g);
    }
    */