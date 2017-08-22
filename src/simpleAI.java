/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
/**
 *
 * @author george
 */
public class simpleAI {
  int[] aiNodes = new int[50];
  int moveDelay = 0;
  void initialize(int startNode){
    for(int i = 0; i < 50; i++){
        aiNodes[i] = 0;
    }
    aiNodes[startNode] = 1;              //startNode is set by game.java
  }

  void chooseMoves(Node[] nodes){
    moveDelay++;
    Random rand = new Random();
    int target = 0;
    if(moveDelay % 20 == 0){                //So that the AI isnt trying to do moves every tick.
        for(int node : aiNodes){                //Iterating through the local node array
            if(node == 1){                          
                if(nodes[node].getNumGerm() > 50){   //Arbitrary attack condition
                    while(true){                    
                        target = rand.nextInt(50);              // Making sure we are using a neutral node as the target
                         if(target != node){    
                            if(nodes[target].getPlayer() != 1 && nodes[target].getPlayer() != 2)
                                break;
                        }
                    }
                    attack(node,target);
                }
                if(nodes[node].getNumGerm() < 50){          //Arbitrary transfer condition
                    target = rand.nextInt(50);
                    if(target != node){
                        if(nodes[target].getPlayer() != 1 && nodes[target].getPlayer() != 0)
                            break;
                    }
                    transfer(node,target);
                }
            }
        }
    }
  }
  void updateNode(int capturedNode){
      aiNodes[capturedNode] = 1;
  }
  
  void transfer(int startNode, int endNode){
  
  }
      
  void attack(int startNode, int endNode){

  }
}
