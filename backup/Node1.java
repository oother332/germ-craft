/**
 * Created by George on 3/18/2017.
 */
public class Node1 {
    int multiplier;
    int numGerm;
    int id;
    int x,y;
    public Node1(int i,int m,int n,int xloc,int yloc){
        id = i;
        multiplier = m;
        numGerm = n;
        x = xloc;
        y = yloc;
    }
    public int getMultiplier(){
        return multiplier;
    }
    void setMultiplier(int multiplier){
        this.multiplier = multiplier;
    }
    public int getNumGerm(){
        return numGerm;
    }
    void setNumGerm(int numGerm){
        this.numGerm = numGerm;
    }
    public int getId(){
        return id;
    }
    void setId(int id){
        this.id = id;
    }
}

