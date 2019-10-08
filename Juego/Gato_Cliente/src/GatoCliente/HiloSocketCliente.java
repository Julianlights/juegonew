package GatoCliente;

import static GatoCliente.TableroCliente.listaBotones;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.ImageIcon;


/**
 *
 * @author Ivanovich
 */
public class HiloSocketCliente extends Thread {

    String HOST = "localhost";
    static int PUERTO = 5000;
    Socket sc;
    DataOutputStream salida;
    DataInputStream entrada;
    String mensajeRecibido;
    Ganador ganar;
    Ganador perder;
    int mM = 10;
    int mAn = 10;
    int s = 0;
    int mR = 0;
    

    public HiloSocketCliente(String HOST, Ganador ganar, Ganador perder) {
        this.HOST = HOST;
        this.ganar = ganar;
        this.perder = perder;
        
    }

    @Override
    public void run() {
        try {
            sc = new Socket(HOST, PUERTO);
            salida = new DataOutputStream(sc.getOutputStream());
            entrada = new DataInputStream(sc.getInputStream());
            TableroCliente.mensaje.setText("Te conectaste para jugar");
            iniciarWhile();

        } catch (Exception e) {
        }
    }

    public void enviarEntero(int m) {
        try {
            salida.writeInt(m);
        } catch (Exception e) {
        }

    }

    public void setM(int mM) {
        this.mM = mM;
    }

    public void setS() {
        s = 0;
    }

    public void iniciarWhile() {
        while (true) {
            System.out.println("" + mM);
            if (s == 1 && mM != mAn) {
                enviarEntero(mM);
                ganar.setG(mM);
                if(ganar.ganaste()){
                    ganar.resetG();
                    TableroCliente.mensaje2.setText("Has ganado!!");                   
                    
                    break;
                }
                mAn = mM;
                s = 0;
            }
            if (s == 0 && mM == mAn) {
                System.out.println("Escuchando...");
                TableroCliente.mensaje2.setText("Espera!");
                try {
                    mR = entrada.readInt();
                    listaBotones.get(mR).setIcon(new ImageIcon(getClass().getResource("/imagenes/Windows" + ".png")));
                    
                    perder.setG(mR);
                    if(perder.ganaste()){
                        perder.resetG();
                        TableroCliente.mensaje2.setText("Has perdido!!");
                        break;
                    }
                    s = 1;
                    mAn = mM;
                    TableroCliente.mensaje2.setText("Tu turno!");
                } catch (Exception e) {
                }

            }

        }
        System.out.println("TERMINO");
        this.stop();
    }

}
