package GatoServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Ivanovich
 */

public class TableroServer extends JFrame implements ActionListener {

    public static ArrayList<Boton> listaBotones;
    JButton accionar;
    JButton accionar2;
    public static JLabel mensaje;
    public static JLabel mensaje2;
    public static JLabel mensaje3;
    static int PUERTO = 5000;
    Socket socket;
    DataOutputStream salida;
    DataInputStream entrada;
    String mensajeRecibido;
    
    int mR ;
    Ganador ganar = new Ganador();
    Ganador perder = new Ganador();
    HiloSocket hilo;
     

    public TableroServer() {
        listaBotones = new ArrayList<>();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 360);
        this.setLayout(null);
       hilo = new HiloSocket(ganar, perder);
        mensaje = new JLabel("Hola bienvenido");
        mensaje3 = new JLabel("::");
        mensaje.setBounds(340, 10, 250, 20);
        mensaje.setForeground(Color.red);
        mensaje2 = new JLabel("Here2");
        mensaje2.setBounds(340, 35, 250, 20);
        accionar = new JButton("Iniciar Servidor");
        accionar.setBounds(340, 60, 140, 50);
        accionar.setBackground(Color.ORANGE);
        accionar.addActionListener(this);
        accionar2 = new JButton("Iniciar Primero");
        accionar2.setBounds(340, 110, 140, 50);
        mensaje2.setFont(new Font("Arial",Font.PLAIN,20));
        mensaje3.setFont(new Font("Arial", Font.PLAIN, 20));
        mensaje3.setBounds(340, 250, 300, 45);
        mensaje3.setForeground(Color.red);
        accionar2.setBackground(Color.ORANGE);
        accionar2.addActionListener(this);
        this.add(accionar2);
        this.add(accionar);
        this.add(mensaje);
        this.add(mensaje2);
        this.add(mensaje3);
        
        this.setTitle("Jugador - Servidor");
        for (int i = 0; i < 9; i++) {
            Boton b = new Boton();
            b.addActionListener(this);
            listaBotones.add(b);
        }

        int col = 10, fil = 10;
        for (int i = 0; i < listaBotones.size(); i++) {
            listaBotones.get(i).setBounds(col, fil, 100, 100);
            listaBotones.get(i).setBackground(Color.DARK_GRAY);
            this.add(listaBotones.get(i));
            if (col < 210) {
                col = col + 100;
            } else {
                col = 10;
                fil = fil + 100;
            }

        }
    }

    public void initServer() {  
       hilo.start();
    }
    
    int con = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        con++;
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == listaBotones.get(i)) {
                    listaBotones.get(i).setIcon(new ImageIcon(getClass().getResource("/imagenes/Windows" + ".png")));
                    hilo.setM(i);
                    listaBotones.get(i).removeActionListener(this);
            }
        }
        
        if(e.getSource() == accionar){
                initServer();
                System.out.println("Inicio Server");
            }
    }
}
