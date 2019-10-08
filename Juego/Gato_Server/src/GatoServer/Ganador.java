
package GatoServer;

/**
 *
 * @author Ivanovich
 */
public final class Ganador {
    int []g = new int[9];
    boolean flag = false;
    public Ganador(){
        resetG();
    }
    
    public void setG(int i){
        g[i] = 1;
    }
    
    public final void resetG(){
        flag = false;
        for (int i = 0; i < g.length; i++) {
            g[i] = 0;
        }
    }
    
    public boolean ganaste(){
        if(g[0] == 1 && g[1] == 1 && g[2] == 1){
            flag = true;
        }
        if(g[3] == 1 && g[4] == 1 && g[5] == 1){
            flag = true;
        }
        if(g[6] == 1 && g[7] == 1 && g[8] == 1){
            flag = true;
        }
        if(g[0] == 1 && g[3] == 1 && g[6] == 1){
            flag = true;
        }
        if(g[1] == 1 && g[4] == 1 && g[7] == 1){
            flag = true;
        }
        if(g[2] == 1 && g[5] == 1 && g[8] == 1){
            flag = true;
        }
        if(g[0] == 1 && g[4] == 1 && g[8] == 1){
            flag = true;
        }
        if(g[2] == 1 && g[4] == 1 && g[6] == 1){
            flag = true;
        }
        
        
        return flag;
    }
    
}
