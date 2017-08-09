package com.email.client;
import javax.swing.JOptionPane;

public class MyMessageBox {
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	 
	 public static int confirmBox(String message)
	 {
		 return JOptionPane.showConfirmDialog(null, message);
	 }
}
