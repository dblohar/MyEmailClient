package com.email.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.temporal.IsoFields;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.wb.swing.FocusTraversalOnArray;

public class MainWindow {

	private JFrame MainWin;
	private JTextField textFieldFrom;
	private JPasswordField passwordField;
	private JTextField textFieldTo;
	private JTextField textFieldSubject;

	private JLabel lblSubject;
	private JLabel lblAttachment;

	JFileChooser fileChooser = new JFileChooser();

	File attachmentFile = new File("jfldas");
	boolean isAttachment = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.MainWin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MainWin = new JFrame();
		MainWin.getContentPane().setBackground(new Color(153, 204, 204));
		MainWin.setBackground(Color.BLACK);
		MainWin.setResizable(false);
		MainWin.setTitle("Quick Mail");
		MainWin.setBounds(100, 100, 823, 481);
		MainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainWin.getContentPane().setLayout(null);
		MainWin.setLocationRelativeTo(null);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFrom.setBounds(10, 11, 46, 14);
		MainWin.getContentPane().add(lblFrom);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(10, 37, 68, 14);
		MainWin.getContentPane().add(lblPassword);

		textFieldFrom = new JTextField();
		textFieldFrom.setText("");
		textFieldFrom.setBounds(88, 8, 249, 20);
		MainWin.getContentPane().add(textFieldFrom);
		textFieldFrom.setColumns(10);
		textFieldFrom.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

		passwordField = new JPasswordField();
		passwordField.setBounds(88, 34, 248, 20);
		MainWin.getContentPane().add(passwordField);
		passwordField.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

		JLabel lblTo = new JLabel("To");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTo.setBounds(10, 66, 46, 14);
		MainWin.getContentPane().add(lblTo);

		textFieldTo = new JTextField();
		textFieldTo.setBounds(88, 59, 248, 20);
		MainWin.getContentPane().add(textFieldTo);
		textFieldTo.setColumns(10);
		textFieldTo.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

		lblSubject = new JLabel("Subject");
		lblSubject.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSubject.setBounds(10, 89, 57, 14);
		MainWin.getContentPane().add(lblSubject);

		textFieldSubject = new JTextField();
		textFieldSubject.setBounds(10, 105, 797, 20);
		MainWin.getContentPane().add(textFieldSubject);
		textFieldSubject.setColumns(10);
		textFieldSubject.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));

		JLabel lblBody = new JLabel("Body");
		lblBody.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblBody.setBounds(11, 134, 46, 14);
		MainWin.getContentPane().add(lblBody);

		JTextArea textAreaBody = new JTextArea();
		textAreaBody.setBounds(10, 151, 797, 236);
		textAreaBody.setBorder(BorderFactory.createLineBorder(Color.decode("#2C6791")));
		MainWin.getContentPane().add(textAreaBody);

		JButton btnAttachment = new JButton("Attachment");
		btnAttachment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isAttachment == false) {
					isAttachment = true;
					int returnVal = fileChooser.showOpenDialog((Component) e.getSource());
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						attachmentFile = fileChooser.getSelectedFile();
						try {
							lblAttachment.setText(attachmentFile.toString());
							btnAttachment.setText("Remove");
						} catch (Exception ex) {
							System.out.println("problem accessing file" + attachmentFile.getAbsolutePath());
						}
					} else {
						System.out.println("File access cancelled by user.");
					}
				}
				else
				{
					isAttachment = false;
					lblAttachment.setText("No Attachment");
					btnAttachment.setText("Attachment");
				}
			}
		});
		btnAttachment.setBounds(9, 395, 122, 23);
		MainWin.getContentPane().add(btnAttachment);

		lblAttachment = new JLabel("No Attachment");
		lblAttachment.setBounds(141, 398, 666, 14);
		MainWin.getContentPane().add(lblAttachment);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!Validation.isInternetAvailable()) {
					MyMessageBox.infoBox("Internet disconnected", "No internet conncection");
					return;
				}
			String fromMail = textFieldFrom.getText();
			if(fromMail.isEmpty())
			{
				MyMessageBox.infoBox("From mail is empty", "Required Field");
				return;
			}
			if(!Validation.isValidEmail(fromMail))
			{
				MyMessageBox.infoBox("From mail is not valid", "Invalid Email");
				textFieldFrom.setText("");
				textFieldFrom.requestFocus();
				return;
			}
				String password = passwordField.getText();
				if (password.isEmpty()) {
					MyMessageBox.infoBox("Enter Password", "Required Field");
					return;
				}
				String toMail = textFieldTo.getText();
				
				if (toMail.isEmpty()) {
					MyMessageBox.infoBox("To mail is empty", "Required Field");
					return;
				}
				ArrayList<String> toMailArrayList = new ArrayList<String>();
				if(!Validation.isValidEmail(toMail))
				{
					MyMessageBox.infoBox("To mail is not valid", "Invalid Email");
					textFieldTo.setText("");
					textFieldTo.requestFocus();
					return;
				}
				toMailArrayList.add(toMail);
				String body = textAreaBody.getText();
				if (body.isEmpty()) {
					MyMessageBox.infoBox("You cant send email without body", "Required Field");
					return;
				}
				String subject = textFieldSubject.getText();
				int doISendMsg = -2;
				if (subject.isEmpty()) {
					doISendMsg = MyMessageBox.confirmBox("Should I send Message?");
					System.out.println("Do I send Message : " + doISendMsg);
					if (doISendMsg != 0) {
						return;
					}
				}

				SendMail objSendMail = new SendMail();

				String attachment;
				String fromEmail = textFieldFrom.getText();
				if (attachmentFile.isFile()) {
					attachment = attachmentFile.toString();
					if (objSendMail.sendMail(fromEmail, password, toMailArrayList, subject, body, attachment)) {
						MyMessageBox.infoBox("Email Send Successfully", "Email Send");
					} else {
						MyMessageBox.infoBox("Unable to send email\nCheck All field are correct", "Error");
					}
				} else {
					// If Not attachment
					if (objSendMail.ifNotAttachmentSendMail(fromEmail, password, toMailArrayList, subject, body)) {
						MyMessageBox.infoBox("Email Send Successfully", "Email Send");
					} else {
						MyMessageBox.infoBox("Unable to send email\nCheck All field are correct", "Error");
					}
				}

			}
		});
		btnSend.setBounds(10, 421, 121, 23);
		MainWin.getContentPane().add(btnSend);

		JLabel lblDeepakLohar = new JLabel("Deepak Lohar");
		lblDeepakLohar.setFont(new Font("Elephant", Font.PLAIN, 17));
		lblDeepakLohar.setBounds(668, 6, 139, 20);
		MainWin.getContentPane().add(lblDeepakLohar);
		MainWin.setFocusTraversalPolicy(
				new FocusTraversalOnArray(new Component[] { textFieldFrom, passwordField, textFieldTo }));

		if (!Validation.isInternetAvailable()) {
			MyMessageBox.infoBox("Internet disconnected", "No internet conncection");
			return;
		}
	}
}
