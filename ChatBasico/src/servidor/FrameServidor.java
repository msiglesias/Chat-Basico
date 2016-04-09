package servidor;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import comun.ClienteBean;

import java.awt.GridBagLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class FrameServidor extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextArea txtmensajes;
	private JTextField txtIP;
	private JLabel lblIpDelServidor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameServidor frame = new FrameServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameServidor() {
		String ip=null;
		try {
			ip=InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtIP = new JTextField();
		txtIP.setText(ip);
		txtIP.setEnabled(false);
		txtIP.setEditable(false);
		txtIP.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.insets = new Insets(0, 0, 5, 5);
		gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIP.gridx = 0;
		gbc_txtIP.gridy = 0;
		contentPane.add(txtIP, gbc_txtIP);
		txtIP.setColumns(10);
		
		lblIpDelServidor = new JLabel("I.P. del servidor");
		GridBagConstraints gbc_lblIpDelServidor = new GridBagConstraints();
		gbc_lblIpDelServidor.insets = new Insets(0, 0, 5, 0);
		gbc_lblIpDelServidor.gridx = 1;
		gbc_lblIpDelServidor.gridy = 0;
		contentPane.add(lblIpDelServidor, gbc_lblIpDelServidor);
		
		txtmensajes = new JTextArea();
		GridBagConstraints gbc_txtmensajes = new GridBagConstraints();
		gbc_txtmensajes.gridwidth = 2;
		gbc_txtmensajes.insets = new Insets(0, 0, 0, 5);
		gbc_txtmensajes.gridheight = 2;
		gbc_txtmensajes.fill = GridBagConstraints.BOTH;
		gbc_txtmensajes.gridx = 0;
		gbc_txtmensajes.gridy = 1;
		contentPane.add(txtmensajes, gbc_txtmensajes);
		
		Thread hilo=new Thread(this);
		hilo.start();
	}
	
	public void run(){
		try{
			ServerSocket serv=new ServerSocket(8080);
			Socket cli;
			String nick, ip, mensaje;
			ClienteBean bean;
			while (true){
				cli=serv.accept();
				ObjectInputStream flujo=new ObjectInputStream(cli.getInputStream());
				bean=(ClienteBean) flujo.readObject();
				nick=bean.getNick();
				ip=bean.getIp();
				mensaje=bean.getMensaje();
				txtmensajes.append("\n"+nick + " le dice a "+ ip+ ": "+mensaje);
				
				
				Socket clienvia=new Socket(ip, 9090);
				ObjectOutputStream flujoenvia=new ObjectOutputStream(clienvia.getOutputStream());
				flujoenvia.writeObject(bean);
				clienvia.close();
				
				cli.close();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


}
