package GatoServer;

import static GatoServer.TableroServer.listaBotones;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.ImageIcon;

/**
 *
 * @author Ivanovich
 */
public class HiloSocket extends Thread {

    static int PUERTO = 5000;
    ServerSocket sc;
    Socket so;
    DataOutputStream salida;
    DataInputStream entrada;
    int mR;
    int mM;
    int mA;
    int s;
    Ganador ganar;
    Ganador perder;

    public HiloSocket(Ganador ganar, Ganador perder) {
        this.ganar = ganar;
        this.perder = perder;
    }

    @Override
    public void run() {
        try {
            sc = new ServerSocket(PUERTO);
            so = new Socket();
            System.out.println("Esperando...");
            TableroServer.mensaje.setText("Esperando al otro jugador");
            try {
                InetAddress ip = InetAddress.getLocalHost();
                TableroServer.mensaje2.setText("IP:" + ip.getHostAddress().toString());
            } catch (Exception e) {
            }
            so = sc.accept();
            entrada = new DataInputStream(so.getInputStream());
            salida = new DataOutputStream(so.getOutputStream());
            TableroServer.mensaje.setText("Se conecto el otro jugador");
            mR = entrada.readInt();
            System.out.println("" + mR);
            mM = mA = 10;
            if (mR == 1) {
                TableroServer.mensaje3.setText("Espera!");
                mR = entrada.readInt();
                s = 1;
                System.out.println("" + mR);
                listaBotones.get(mR).setIcon(new ImageIcon(getClass().getResource("/imagenes/linux" + ".png")));
                perder.setG(mR);
                TableroServer.mensaje3.setText("Tu turno!");
                iniciarWhile(s);

            } else {
                s = 1;
                TableroServer.mensaje3.setText("Tu Inicias la partida");
                iniciarWhile(s);
            }

        } catch (Exception e) {
        }
    }

    public void iniciarWhile(int ss) throws IOException {
        s = ss;
        while (true) {
            System.out.println("" + mM);
            if (s == 1 && mM != mA) {
                enviar(mM);
                ganar.setG(mM);
                if (ganar.ganaste()) {
                    ganar.resetG();
                    TableroServer.mensaje3.setText("Ganaste!!!");
                    break;
                }
                s = 0;
                mA = mM;
            }
            if (s == 0 && mM == mA) {
                System.out.println("Escuchando...");
                TableroServer.mensaje3.setText("Espera!");
                mR = entrada.readInt();
                listaBotones.get(mR).setIcon(new ImageIcon(getClass().getResource("/imagenes/linux" + ".png")));
                perder.setG(mR);
                if (perder.ganaste()) {
                    perder.resetG();
                    TableroServer.mensaje3.setText("Perdiste!!!");
                    break;
                }
                s = 1;
                mA = mM;
                TableroServer.mensaje3.setText("Tu turno!");
            }
        }
        System.out.println("TERMINO");
        this.stop();
    }

    public void setM(int mMm) {
        mM = mMm;
        System.out.println("" + mM);
    }

    public void enviar(int m) {
        try {
            salida.writeInt(m);
        } catch (Exception e) {
        }
    }

}
