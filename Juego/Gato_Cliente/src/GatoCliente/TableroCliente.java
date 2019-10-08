package GatoCliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Ivanovich
 */

public class TableroCliente extends JFrame implements ActionListener {
    
    public static ArrayList<Boton> listaBotones;
    JButton accionar;
    JButton accionar2;
    JButton accionar3;
    JTextField ip;
    public static JLabel mensaje;
    public static JLabel mensaje2;
    static String HOST = "localhost";
    static int PUERTO = 5000;
    Socket sc;
    DataOutputStream salida;
    DataInputStream entrada;
    String mensajeRecibido;
    HiloSocketCliente hilo;
    Ganador ganar = new Ganador();
    Ganador perder = new Ganador();
    Icon defaultIcon;
        

    public TableroCliente() {
        listaBotones = new ArrayList<>();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 360);
        this.setLayout(null);
        mensaje = new JLabel("Ingrese IP del servidor:");
        mensaje2 = new JLabel("::");
        ip = new JTextField();
        mensaje.setBounds(340, 10, 300, 20);
        mensaje2.setFont(new Font("Arial", Font.PLAIN, 20));
        mensaje2.setBounds(340, 250, 300, 45);
        mensaje2.setForeground(Color.red);
        ip.setBounds(340, 30, 100, 25);
        accionar = new JButton("Conectar");
        defaultIcon = mensaje2.getIcon();
        accionar.setBounds(340, 70, 140, 50);
        accionar.setBackground(Color.GREEN);
        accionar.addActionListener(this);
        accionar2 = new JButton("Iniciar Primero");
        accionar2.setBounds(340, 120, 140, 50);
        accionar2.setBackground(Color.ORANGE);
        accionar2.addActionListener(this);
        accionar3 = new JButton("Iniciar Segundo");
        accionar3.setBounds(340, 170, 140, 50);
        accionar3.setBackground(Color.ORANGE);
        accionar3.addActionListener(this);
        this.add(accionar3);
        this.add(accionar2);
        this.add(accionar);
        this.add(mensaje);
        this.add(mensaje2);
        this.add(ip);

        this.setTitle("Jugador - Cliente");
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
        
        if (!ip.getText().isEmpty()) {
            hilo = new HiloSocketCliente(ip.getText(), ganar, perder);
            hilo.start();
        } else {
            mensaje.setText("Ingrese IP del servidor:Primero ingrese IP");
        }

    }
    

    int con = 0;
    boolean flag = false;

    @Override
    public void actionPerformed(ActionEvent e) {

        con++;
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == listaBotones.get(i)) {
                
                listaBotones.get(i).setIcon(new ImageIcon(getClass().getResource("/imagenes/linux" + ".png")));
                listaBotones.get(i).removeActionListener(this);
                if (flag == false) {
                    hilo.enviarEntero(i);
                    ganar.setG(i);
                    flag = true;
                }
                hilo.setM(i);
                System.out.println("" + i);

            }

        }

        if (e.getSource() == accionar) {
            initServer();
        }

        if (e.getSource() == accionar2) {
            hilo.enviarEntero(1);
            mensaje2.setText("Tu inicias!!");
        }

        if (e.getSource() == accionar3) {
            hilo.enviarEntero(2);
            mensaje2.setText("Tu eres el segundo!!");
            hilo.setS();
            flag = true;
        }

    }

}
