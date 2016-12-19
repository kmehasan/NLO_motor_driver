/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor_driver;

import com.fazecast.jSerialComm.SerialPort;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author KME Hasan
 */
public class Motor_driver {

    /**
     * @param args the command line arguments
     */
    static SerialPort chosenPort;
    private static OutputStream Output;
public static boolean port_flag = false;
    
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame window = new JFrame();
		window.setTitle("Arduino controller GUI");
		window.setSize(600, 400);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JComboBox<String> portList_combobox = new JComboBox<String>();
		JLabel no_port_found_label = new JLabel("Please Connect Arduino");
            JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.add(portList_combobox);
		topPanel.add(connectButton);
		window.add(topPanel, BorderLayout.NORTH);
		
                
                SerialPort[] portNames = SerialPort.getCommPorts();
		// populate the drop-down box
            //check for new port available
            Thread thread_port = new Thread(){
                @Override
                public void run() {
                    while(true){
                        
                        SerialPort[] sp = SerialPort.getCommPorts();
                        if(sp.length>0)
                        {   
                            //no port label add
                            if(!port_flag){
                                topPanel.removeAll();
                                topPanel.add(portList_combobox);
                                topPanel.add(connectButton);
                                topPanel.revalidate();
                                topPanel.repaint();
                                portList_combobox.removeAllItems();
                                port_flag = true;
                            
                            }
                            for(SerialPort sp_name : sp)
                            {
                                
                                int l=portList_combobox.getItemCount(),i;
                                for(i=0;i<l;i++)
                                {
                                    //check port name already exist or not
                                    if(sp_name.getSystemPortName().equalsIgnoreCase(portList_combobox.getItemAt(i))){
                                        break;
                                    }
                                }
                                if(i==l){
                                    portList_combobox.addItem(sp_name.getSystemPortName());
                                    
                                }

                            }

                        }
                        else {
                            port_flag = false;
                            topPanel.removeAll();
                            topPanel.add(no_port_found_label);
                            portList_combobox.removeAllItems();
                            
                            topPanel.repaint();

                        }
                        portList_combobox.repaint();
                        

                    }

                }

            };
            thread_port.start();
            
                for(SerialPort sp_name : portNames)
                    portList_combobox.addItem(sp_name.getSystemPortName());
                
                
                JButton Enable_btn = new JButton("ON");
                JButton Clock_speed_submit = new JButton("Set Clock");
                
                JTextPane T = new JTextPane();
                T.setToolTipText("Put Clock Speed here");
                SimpleAttributeSet attribs = new SimpleAttributeSet();
                StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
                T.setParagraphAttributes(attribs, true);
                
                JPanel Conteiner = new JPanel();
                Conteiner.setVisible(false);
                
                JButton direction =new JButton("Clockwise");
                GridLayout gl = new GridLayout();
                gl.setColumns(2);
                gl.setRows(2);
                JPanel bodyPanel = new JPanel(gl);
                bodyPanel.add(T);
                bodyPanel.add(Clock_speed_submit);
                bodyPanel.add(direction);
                bodyPanel.add(Enable_btn);
                Conteiner.add(bodyPanel);
                window.add(Conteiner);
                Clock_speed_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String T_text = T.getText();
                if(T_text!= null && !T_text.equalsIgnoreCase("0")){
                    if(chosenPort.isOpen())
                    {
                        try {
                            Output.write(T_text.getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Motor_driver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            Enable_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Enable_btn.getText().equalsIgnoreCase("OFF")) {
                    
                    // light off
                    if(chosenPort.isOpen())
                    {
                        try {
                            String a="0";
                            Output.write(a.getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Motor_driver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                                        
                    Enable_btn.setText("ON");
                }
                else{
                    // light on
                    if(chosenPort.isOpen())
                    {
                        try {
                            String a="-1";
                            
                            Output.write(a.getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Motor_driver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    Enable_btn.setText("OFF");
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
            
            direction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (direction.getText().equalsIgnoreCase("Clockwise")) {
                    
                    // light off
                    if(chosenPort.isOpen())
                    {
                        try {
                            String a="-2";
                            Output.write(a.getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Motor_driver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                                        
                    direction.setText("Anti-Clockwise");
                }
                else{
                    // light on
                    if(chosenPort.isOpen())
                    {
                        try {
                            String a="-3";
                            Output.write(a.getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Motor_driver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    direction.setText("Clockwise");
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
                connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList_combobox.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("Disconnect");
                                                Output = chosenPort.getOutputStream();
						portList_combobox.setEnabled(false);
                                                Conteiner.setVisible(true);
					}
                                        else {
                                            Conteiner.setVisible(false);
                                        }
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
                                        Conteiner.setVisible(false);
					portList_combobox.setEnabled(true);
					connectButton.setText("Connect");
					
				}
			}
		});
		
		window.setVisible(true);
    }
    
}
