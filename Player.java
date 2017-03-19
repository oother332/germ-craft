/**
 * Created by George on 3/18/2017.
 */
import java.util.*;
public class Player {
    int id = 0;
    Set<Integer> capNode = new HashSet<Integer>();
    public Player(int i,int node){
        id = i;
        capNode.add(node);
    }
    public void attack(Player aPlayer,Player dPlayer,int nodeAttack,int nodeDefence){
        if(main.nodeList[nodeAttack].numGerm <= main.nodeList[nodeDefence].numGerm) {
            main.nodeList[nodeAttack].numGerm = main.nodeList[nodeAttack].numGerm / 2;
        }
        else{
            dPlayer.capNode.remove(main.nodeList[nodeDefence].id);
            aPlayer.capNode.add(main.nodeList[nodeDefence].id);
            main.nodeList[nodeDefence].numGerm = main.nodeList[nodeAttack].numGerm/2;
            main.nodeList[nodeAttack].numGerm = main.nodeList[nodeAttack].numGerm/2;
            }
        }

    public void move(Player aPlayer,int nodeOne, int nodeTwo){
        aPlayer.capNode.add(main.nodeList[nodeTwo].id);
        main.nodeList[nodeTwo].numGerm = main.nodeList[nodeOne].numGerm/2;
        main.nodeList[nodeOne].numGerm = main.nodeList[nodeOne].numGerm/2;
    }
}
