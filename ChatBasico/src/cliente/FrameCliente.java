package cliente;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import comun.ClienteBean;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class FrameCliente extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField txtuser;
	private JTextField txtipdestino;
	private JTextField txtmensaje;
	private JButton btnenviar;
	private JTextArea txtmensajes;
	private JTextField txtipserver;
	private JTextField txtipuser;
	private JLabel lblIpDelUsuario;
	private JLabel lblNombreDeUsuario;
	private JLabel lblIpDestino;
	private JLabel lblIpDelServidor;
	private JLabel lblMensaje;
	private JLabel lblMensajesRecibidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameCliente frame = new FrameCliente();
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
	public FrameCliente() {
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
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		btnenviar = new JButton("Enviar");
		btnenviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Socket cli = new Socket(txtipserver.getText().toString(),8080);
					ClienteBean bean=new ClienteBean();
					bean.setNick(txtuser.getText());
					bean.setIp(txtipdestino.getText());
					bean.setMensaje(txtmensaje.getText());
					ObjectOutputStream flujo=new ObjectOutputStream(cli.getOutputStream());
					flujo.writeObject(bean);
					cli.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		txtipuser = new JTextField();
		txtipuser.setEnabled(false);
		txtipuser.setEditable(false);
		txtipuser.setHorizontalAlignment(SwingConstants.CENTER);
		txtipuser.setText(ip);
		GridBagConstraints gbc_txtipuser = new GridBagConstraints();
		gbc_txtipuser.anchor = GridBagConstraints.NORTH;
		gbc_txtipuser.insets = new Insets(0, 0, 5, 5);
		gbc_txtipuser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtipuser.gridx = 0;
		gbc_txtipuser.gridy = 0;
		contentPane.add(txtipuser, gbc_txtipuser);
		txtipuser.setColumns(10);
		
		lblIpDelUsuario = new JLabel("I.P. del usuario");
		GridBagConstraints gbc_lblIpDelUsuario = new GridBagConstraints();
		gbc_lblIpDelUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_lblIpDelUsuario.gridx = 1;
		gbc_lblIpDelUsuario.gridy = 0;
		contentPane.add(lblIpDelUsuario, gbc_lblIpDelUsuario);
		
		txtuser = new JTextField();
		txtuser.setHorizontalAlignment(SwingConstants.CENTER);
		txtuser.setText("Usuario");
		GridBagConstraints gbc_txtuser = new GridBagConstraints();
		gbc_txtuser.insets = new Insets(0, 0, 5, 5);
		gbc_txtuser.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtuser.gridx = 0;
		gbc_txtuser.gridy = 1;
		contentPane.add(txtuser, gbc_txtuser);
		txtuser.setColumns(10);
		
		lblNombreDeUsuario = new JLabel("Nombre de usuario");
		GridBagConstraints gbc_lblNombreDeUsuario = new GridBagConstraints();
		gbc_lblNombreDeUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreDeUsuario.gridx = 1;
		gbc_lblNombreDeUsuario.gridy = 1;
		contentPane.add(lblNombreDeUsuario, gbc_lblNombreDeUsuario);
		
		txtipdestino = new JTextField();
		txtipdestino.setText("I.P. del usuario destino");
		txtipdestino.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtipdestino = new GridBagConstraints();
		gbc_txtipdestino.insets = new Insets(0, 0, 5, 5);
		gbc_txtipdestino.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtipdestino.gridx = 0;
		gbc_txtipdestino.gridy = 2;
		contentPane.add(txtipdestino, gbc_txtipdestino);
		txtipdestino.setColumns(10);
		
		lblIpDestino = new JLabel("I.P. destino");
		GridBagConstraints gbc_lblIpDestino = new GridBagConstraints();
		gbc_lblIpDestino.insets = new Insets(0, 0, 5, 0);
		gbc_lblIpDestino.gridx = 1;
		gbc_lblIpDestino.gridy = 2;
		contentPane.add(lblIpDestino, gbc_lblIpDestino);
		
		txtipserver = new JTextField();
		txtipserver.setText("I.P. del servidor");
		txtipserver.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtipserver = new GridBagConstraints();
		gbc_txtipserver.anchor = GridBagConstraints.NORTH;
		gbc_txtipserver.insets = new Insets(0, 0, 5, 5);
		gbc_txtipserver.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtipserver.gridx = 0;
		gbc_txtipserver.gridy = 3;
		contentPane.add(txtipserver, gbc_txtipserver);
		txtipserver.setColumns(10);
		
		lblIpDelServidor = new JLabel("I.P. del servidor");
		GridBagConstraints gbc_lblIpDelServidor = new GridBagConstraints();
		gbc_lblIpDelServidor.insets = new Insets(0, 0, 5, 0);
		gbc_lblIpDelServidor.gridx = 1;
		gbc_lblIpDelServidor.gridy = 3;
		contentPane.add(lblIpDelServidor, gbc_lblIpDelServidor);
		
		txtmensaje = new JTextField();
		txtmensaje.setText("Mensaje");
		txtmensaje.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtmensaje = new GridBagConstraints();
		gbc_txtmensaje.insets = new Insets(0, 0, 5, 5);
		gbc_txtmensaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtmensaje.gridx = 0;
		gbc_txtmensaje.gridy = 4;
		contentPane.add(txtmensaje, gbc_txtmensaje);
		txtmensaje.setColumns(10);
		
		lblMensaje = new JLabel("Mensaje");
		GridBagConstraints gbc_lblMensaje = new GridBagConstraints();
		gbc_lblMensaje.insets = new Insets(0, 0, 5, 0);
		gbc_lblMensaje.gridx = 1;
		gbc_lblMensaje.gridy = 4;
		contentPane.add(lblMensaje, gbc_lblMensaje);
		GridBagConstraints gbc_btnenviar = new GridBagConstraints();
		gbc_btnenviar.insets = new Insets(0, 0, 5, 5);
		gbc_btnenviar.gridx = 0;
		gbc_btnenviar.gridy = 5;
		contentPane.add(btnenviar, gbc_btnenviar);
		
		txtmensajes = new JTextArea();
		GridBagConstraints gbc_txtmensajes = new GridBagConstraints();
		gbc_txtmensajes.insets = new Insets(0, 0, 0, 5);
		gbc_txtmensajes.fill = GridBagConstraints.BOTH;
		gbc_txtmensajes.gridx = 0;
		gbc_txtmensajes.gridy = 6;
		contentPane.add(txtmensajes, gbc_txtmensajes);
		
		lblMensajesRecibidos = new JLabel("Mensajes Recibidos");
		GridBagConstraints gbc_lblMensajesRecibidos = new GridBagConstraints();
		gbc_lblMensajesRecibidos.gridx = 1;
		gbc_lblMensajesRecibidos.gridy = 6;
		contentPane.add(lblMensajesRecibidos, gbc_lblMensajesRecibidos);
		
		Thread hilo=new Thread(this);
		hilo.start();
	}

	public void run(){
		try{
			ServerSocket serv=new ServerSocket(9090);
			Socket cli;
			ClienteBean bean;
			while (true){
				cli=serv.accept();
				ObjectInputStream flujo=new ObjectInputStream(cli.getInputStream());
				bean=(ClienteBean) flujo.readObject();
				txtmensajes.append("\n"+bean.getNick()+": "+bean.getMensaje());
				cli.close();
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
