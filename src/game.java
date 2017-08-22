
/**
 * Created by Beez & George
 * Most Recently
 * placement is now fully random
 * places as many as possible within reasonable time
 * or however many you place
 *
 * added win Screen and Win condition
 * Unified transfer and attack functions
 * added animation for both(same animation)
 * //whole thing seems real buggy need to click while holding the button or else it doesnt work properly and unsure why
 *
 * Added unit transfer function - hold t to transfer germs between allied nodes
 *
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
import java.util.ArrayList;

public class game extends JFrame implements KeyListener, ActionListener, MouseListener, MouseMotionListener {

    //put variables here plz;
    static int nodeMax = 50;
    static int numOfRows = 1;
    static int startBoxX;
    static int startBoxY;
    static int boxX;
    static int boxY;
    static int tGerms;
    static int defId;
    static int winX = 1200, winY = 600;

    static ArrayList<Integer> nodePos = new ArrayList<Integer>();

    static Double[] xferpos = new Double[4]; //0,1 for final pos 2,3 for initial pos

    static Random rand = new Random();

    static boolean boxClick = false;
    static boolean attack = false;
    static boolean xferring = false;
    static boolean winnerGagne = false;
    static boolean debugNodes = false;
    static boolean actionHappening= false;

    Timer t = new Timer(1000, this);//int is tick interval
    Timer xferT = new Timer(10, this);
    static Node[] nodes = new Node[nodeMax];//array of node
    //static Node node = new Node(1); // single node for testing
    Color background = Color.BLACK;
    Color selectionColor = Color.BLUE;

    //public static Node1[] nodeList = new Node1[nodeMax*nodeMax];
    //Player p1 = new Player(1,0);
    //Player p2 = new Player(2,1);
    private DrawCanvas canvas;

    public game() {
        canvas = new DrawCanvas();                                             //Initializing Game stats
        canvas.setPreferredSize(new Dimension(winX, winY));
        Container cp = getContentPane();
        cp.add(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Game");
        setVisible(true);

        t.setInitialDelay(1000);
        t.start();
        xferT.setInitialDelay(10);
        randomPlacement();
        int count = 0;
        for (int i = 0; i < nodeMax; i++) {                                      //Create the array of nodes
            nodes[i] = new Node(i);
            nodes[i].setX(nodePos.get(count));
            nodes[i].setY(nodePos.get(count + 1));
            count += 2;
        }
        int ran = rand.nextInt(nodeMax);                                              //Set Player 1 Location and their number of Germs
        nodes[ran].setPlayer(1);
        nodes[ran].setNumGerm(100);
        //nodes[ran].setMultiplier(2);
        //gridPlacement(nodes,numOfRows);
        //nodes[1].setX(300);

        addKeyListener(this);                                                   //Initialize I/O
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //System.out.println(g.getColor());
            if (winnerGagne == false) {
                setBackground(background);
                for (int i = 0; i < nodeMax; i++) {
                    if (nodes[i].isSelected() == true) {
                        g.setColor(selectionColor);
                        nodes[i].draw(g);
                    } else {
                        if (nodes[i].getPlayer() == 1) {
                            g.setColor(Color.GREEN.darker());
                        } else {
                            g.setColor(Color.RED);
                        }
                        nodes[i].draw(g);
                    }
                }
                if (xferT.isRunning() == true) {
                    g.setColor(Color.WHITE);
                    int x, y;
                    x = xferpos[2].intValue();
                    y = xferpos[3].intValue();
                    g.fillRect(x, y, 10, 10);
                }
                if (boxClick == true) {
                    g.setColor(Color.GREEN.brighter());
                    int w = boxX - startBoxX;
                    int h = boxY - startBoxY;
                    //System.out.println("w: "+w+" h: "+h);
                    if (w != 0 && h != 0) {
                        if (w < 0 && h < 0) {
                            g.drawRect(boxX, boxY, (-1 * w), (-1 * h));
                        } else if (h < 0) {
                            g.drawRect(startBoxX, boxY, w, (-1 * h));
                        } else if (w < 0) {
                            g.drawRect(boxX, startBoxY, (-1 * w), h);
                        } else {
                            g.drawRect(startBoxX, startBoxY, w, h);
                        }
                    }
                }
                winnerWinnerChickenDinner();
            } else if (winnerGagne == true) {
                setBackground(Color.RED);
                g.drawString("Winner Winner Chicken Dinner", 10, winY / 3);
            }
        }
    }

    public void winnerWinnerChickenDinner() {
        int count = 0;
        for (int i = 0; i < nodeMax; i++) {
            if (nodes[i].getPlayer() == 1) {
                count++;
            }
        }
        if (count == nodeMax) {
            winnerGagne = true;
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        if (boxClick == true) {
            boxX = e.getX();
            boxY = e.getY();
            boxSelect();
            //System.out.println("IT SHOULD BE WORKING");
        }
        repaint();

    }

    public void mousePressed(MouseEvent e) {
         if(e.getButton()==e.BUTTON3&&actionHappening==true){
            actionHappening=false;
            attack=false;
            xferring=false;
        }
         
        if ((e.getButton() == e.BUTTON1) && actionHappening==false) {
            for (int i = 0; i < nodeMax; i++) {
                nodes[i].deselect();
            }
            boxClick = true;
            startBoxX = e.getX();
            startBoxY = e.getY();

            boxX = e.getX();
            boxY = e.getY();
        }
        repaint();

    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == e.BUTTON1) {
            boxClick = false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        //selection code
        if ((e.getButton() == e.BUTTON1) && (attack == false && xferring == false)&&actionHappening==false) {
            //System.out.println("Should not be running");
            for (int i = 0; i < nodeMax; i++) {
                int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
                int nodeXMax = (nodes[i].getX() + nodes[i].getSize()) + 8;///2);
                int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
                int nodeYMax = (nodes[i].getY() + nodes[i].getSize()) + 30;///2);
                if ((e.getX() >= nodeXMin && e.getX() <= nodeXMax) && (e.getY() >= nodeYMin && e.getY() <= nodeYMax) && debugNodes == true) {
                    System.out.println("X: " + nodes[i].getX() + " Y: " + nodes[i].getY() + " Node: " + i);
                }
                if ((e.getX() >= nodeXMin && e.getX() <= nodeXMax) && (e.getY() >= nodeYMin && e.getY() <= nodeYMax) && nodes[i].getPlayer() == 1) {
                    nodes[i].select();
                } else {
                    nodes[i].deselect();
                }
            }
        }
        
        //transferring and attacking
        if (((xferring == true) || (attack == true)) && (e.getButton() == e.BUTTON1)&&actionHappening==true&&xferT.isRunning()==false) {
            for (int i = 0; i < nodeMax; i++) {
                int nodeXMin = (nodes[i].getX());//-nodes[i].getSize()/2);
                int nodeXMax = (nodes[i].getX() + nodes[i].getSize()) + 8;///2);
                int nodeYMin = (nodes[i].getY());//-nodes[i].getSize()/2);
                int nodeYMax = (nodes[i].getY() + nodes[i].getSize()) + 30;///2);

                if ((e.getX() >= nodeXMin && e.getX() <= nodeXMax) && (e.getY() >= nodeYMin && e.getY() <= nodeYMax) && nodes[i].isSelected() == false) {
                    if (nodes[i].getPlayer() == 1) {
                        attack = false;
                    } else {
                        xferring = false;
                    }
                    defId = i;
                    //System.out.println("defId: "+defId);
                    // System.out.println("attack: "+attack);
                    //System.out.println("xferring: "+xferring);
                    if (attack == true || xferring == true) {
                        transferring();
                    }
                    //attack = false;
                    //xferring = false;
                    //System.out.println("clicked on a node");
                }
            }

        }
        //System.out.println("Mouse xferring2: "+xferring);
        repaint();
    }

    public void transferring() {
        tGerms = 0;
        xferpos[0] = 1.0 * (nodes[defId].getX() + nodes[defId].getSize() / 2);
        xferpos[1] = 1.0 * (nodes[defId].getY() + nodes[defId].getSize() / 2);
        //System.out.println("final x: "+xferpos[0]);
        //System.out.println("final y: "+xferpos[1]);
        ArrayList<Integer> aNodes = new ArrayList<Integer>();
        for (int i = 0; i < nodeMax; i++) {
            if (nodes[i].isSelected() == true) {
                tGerms = tGerms + nodes[i].getNumGerm() / 2;
                aNodes.add(i);
                nodes[i].setNumGerm(nodes[i].getNumGerm() / 2);
            }
        }
        int startX = 0, startY = 0, counter = 0;
        for (int i = 0; i < nodeMax; i++) {
            if (nodes[i].isSelected() == true) {
                startX += nodes[i].getX();
                startY += nodes[i].getY();
                counter++;
            }
        }
        startX = (startX / counter);
        startY = (startY / counter);
        startX += nodes[0].getSize() / 2;
        startY += nodes[0].getSize() / 2;
        xferpos[2] = 1.0 * (startX);
        xferpos[3] = 1.0 * (startY);
        xferT.start();

    }

    public void endTransfer() {
        int defNum = nodes[defId].getNumGerm();
        if (attack == true) {
            nodes[defId].setNumGerm(defNum - tGerms);
            if (nodes[defId].getNumGerm() <= 0) {
                nodes[defId].setPlayer(1);
                nodes[defId].setNumGerm(1);
            }
            attack = false;
        } else if (xferring == true) {
            nodes[defId].setNumGerm(defNum + tGerms);
            xferring = false;
        }
        actionHappening=false;
    }

    public void actionPerformed(ActionEvent a) {
        if (a.getSource() == t) {
            for (int i = 0; i < nodeMax; i++) {
                nodes[i].setNumGerm((nodes[i].getNumGerm()) + (nodes[i].getMultiplier()));
            }
        }
        if (a.getSource() == xferT) {
            if (xferpos[0] != xferpos[2]) {
                if (xferpos[0] > xferpos[2]) {
                    xferpos[2]++;
                } else if (xferpos[0] < xferpos[2]) {
                    xferpos[2]--;
                }
            }
            if (xferpos[1] != xferpos[3]) {
                if (xferpos[1] > xferpos[3]) {
                    xferpos[3]++;
                } else if (xferpos[1] < xferpos[3]) {
                    xferpos[3]--;
                }
            }
            if (xferpos[0].equals(xferpos[2]) && xferpos[1].equals(xferpos[3])) {
                endTransfer();
                xferT.stop();

            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {

        for (int i = 0; i < nodeMax; i++) {
            if (nodes[i].isSelected() == true) {
                if ((e.getKeyChar() == 't' || e.getKeyChar() == 'T'&&xferring ==false)&&actionHappening==false) {
                    xferring = true;
                    attack = false;
                    actionHappening = true;
                } else if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A'&&attack==false)&&actionHappening==false) {
                    attack = true;
                    xferring = false;
                    actionHappening=true;
                }

            }
        }
        //actionHappening=false;
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
            debugNodes = true;
        }
        repaint();
    }

    public void keyReleased(KeyEvent e) {

        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {
            debugNodes = false;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new game(); // Let the constructor do the job
            }
        });
    }

    public void boxSelect() {
        int nodeCX, nodeCY;
        for (int i = 0; i < nodeMax; i++) {
            nodeCX = nodes[i].getX() + (nodes[i].getSize() / 2);
            nodeCY = nodes[i].getY() + (nodes[i].getSize() / 2);

            if ((startBoxX < boxX && startBoxY < boxY) && (nodeCX > startBoxX && nodeCX < boxX) && (nodeCY > startBoxY && nodeCY < boxY)) {
                nodes[i].select();
            } else if ((startBoxX > boxX && startBoxY < boxY) && (nodeCX < startBoxX && nodeCX > boxX) && (nodeCY > startBoxY && nodeCY < boxY)) {
                nodes[i].select();
            } else if ((startBoxX < boxX && startBoxY > boxY) && (nodeCX > startBoxX && nodeCX < boxX) && (nodeCY < startBoxY && nodeCY > boxY)) {
                nodes[i].select();
            } else if ((startBoxX > boxX && startBoxY > boxY) && (nodeCX < startBoxX && nodeCX > boxX) && (nodeCY < startBoxY && nodeCY > boxY)) {
                nodes[i].select();
            }
            else{
                nodes[i].deselect();
            }
            if (nodes[i].getPlayer() != 1) {
                nodes[i].deselect();
            }
        }
    }

    public void randomPlacement() {
        int count = 0, ranX, ranY;
        int stopcounter = 0;
        boolean okaySpawn;
        Random ranP = new Random();
        ArrayList<Integer> nodeX = new ArrayList<Integer>();
        ArrayList<Integer> nodeY = new ArrayList<Integer>();
        while (count < nodeMax) {
            stopcounter++;
            if (stopcounter == 999999) {
                nodeMax = count;
                break;
            }
            okaySpawn = true;
            ranX = ranP.nextInt(winX - 100);
            ranY = ranP.nextInt(winY - 100);
            for (int a = 0; a < nodeX.size(); a++) {
                int size = 100;
                if (ranX > (nodeX.get(a) - (size + 5)) && ranX < (nodeX.get(a) + (size + 5)) && ranY > (nodeY.get(a) - (size + 5)) && ranY < (nodeY.get(a) + (size + 5))) {
                    okaySpawn = false;
                } else {

                }
            }
            if (okaySpawn == true) {
                stopcounter = 0;
                //System.out.println("Final placement for node: "+count);
                //System.out.println("X: "+ranX+" Y: "+ranY+" Node: "+count);
                nodePos.add(ranX);
                nodePos.add(ranY);
                //nodes[count].setX(ranX);
                //nodes[count].setY(ranY);
                nodeX.add(ranX);
                nodeY.add(ranY);
                count++;
            }
        }
    }

    public void gridPlacement(Node[] n, int r) {

        if (r == 1) {
            for (int i = 0; i < nodeMax; i++) {
                if (i > 0) {
                    n[i].setX(i * n[i].getSize() + 20 * i);
                } else {
                    n[i].setX(i * n[i].getSize());
                }
            }
        } else if (r == nodeMax) {
            for (int i = 0; i < nodeMax; i++) {
                if (i > 0) {
                    n[i].setY(i * n[i].getSize() + 20 * i);
                } else {
                    n[i].setY(i * n[i].getSize());
                }
            }
        } else {
            int col = 1;
            int row = 0;
            for (int i = 1; i < nodeMax; i++) {
                //if(i>((nodeMax/r)*(a-1))&&i<((nodeMax/r)*(a))){
                //System.out.println("Highest nodenum: "+(nodeMax*a/r));
                n[i].setX(n[i].getX() + col * n[i].getSize() + 20 * col);
                n[i].setY(n[i].getY() + row * n[i].getSize() + 20 * row);
                //System.out.println("row: "+row);
                if (col < nodeMax / r) {
                    col++;
                } else {
                    col = 0;
                    row++;
                }
            }
        }
    }
}
/* tranfer function
    double lDist=0.0;//largest distance between attack and def nodes
    int lNode=-1;
    for (int i =0;i<aNodes.size();i++){
        if(nodes[i].isSelected()==true){
            double temp1 = Math.pow(Math.abs(xferpos[0]-nodes[i].getX()),2.0);
            double temp2 =Math.pow(Math.abs(xferpos[1]-nodes[i].getY()), 2.0);
            double dist=Math.sqrt(temp1+temp2);
            if (dist>lDist){
                lDist = dist;
                lNode=i;
            }
            System.out.println("lDist: "+lDist);
            System.out.println("lNode: "+lNode);
        }
    }
    //xferpos[2]=1.0*(nodes[lNode].getX()+nodes[lNode].getSize()/2);
    //xferpos[3]=1.0*(nodes[lNode].getY()+nodes[lNode].getSize()/2);
 */

/* old attack and transfer functions
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
*/
