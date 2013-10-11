
//**********************************************************
// Assignment: ICS-462-50 PA1 Processes with JAVA
//
// Author: Romeo Mai
//
// Completion time: 36 hours
//
// Honor Code: I pledge that this program represents my own
//   program code with the inspiration from Michael Dorin's works in designing and debugging my program.
//*********************************************************

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class Parent extends JFrame implements ActionListener {

	private static JButton button0 = new JButton("Load Matrix A");
	private static JButton button1 = new JButton("Dump Matrix A");
	private static JButton button2 = new JButton("Load Matrix B");
	private static JButton button3 = new JButton("Dump Matrix B");
	private static JButton button4 = new JButton("Execute");
	private static JButton button5 = new JButton("Exit");
	private JTextField txtInput = new JTextField();

	MatrixInt MatrixA, MatrixB, MatrixC;

	FileHandler fileHandler = new FileHandler();

	public void myMain() {
		
		JLabel labelName = new JLabel("Enter a output result file name below: ");
		JPanel panel = new JPanel();
		panel.add(labelName, "push, align center");		
		
		setTitle("ICS-462 PA1 Processes with JAVA");
		setLayout(new GridLayout(0,1));
		setSize(400,400);
		setLocationRelativeTo(null);
		
		add(button0);
		add(button1);
		add(button2);
		add(button3);
		add(panel);
		add(txtInput);
		add(button4);
		add(button5);
		
		button0.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String args[]) {
		Parent parent = new Parent();
		parent.myMain();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("Load Matrix A")) {
			
			fileHandler = new FileHandler();
			fileHandler.select();
			if(fileHandler.getFile() == null){	// handle cancel button
				return;
			}
			fileHandler.read();
			MatrixA = new MatrixInt(fileHandler.getLines());
		}else if (arg0.getActionCommand().equals("Dump Matrix A")) {
			System.out.println("The Matrix A is:");
			System.out.println(MatrixA);
			
		} else if (arg0.getActionCommand().equals("Load Matrix B")) {
			fileHandler = new FileHandler();
			fileHandler.select();
			if(fileHandler.getFile() == null){
				return;
			}
			fileHandler.read();
			MatrixB = new MatrixInt(fileHandler.getLines());
			
//			File fi = new File("MatrixC.io");
//			if(fi.exists()){
//				fi.delete();
//			}
//			System.out.println(fi.exists());
			
		}
		else if (arg0.getActionCommand().equals("Dump Matrix B")) { 
			System.out.println("The Matrix B is:");
			System.out.println(MatrixB);
		}
		
		else if (arg0.getActionCommand().equals("Execute")) { 
			if(this.txtInput.getText() == null || this.txtInput.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Please enter output file name!");
				return;
			}else if(MatrixA == null || MatrixB == null){
				JOptionPane.showMessageDialog(this, "Please load Matrix A and Matrix B");
				return;
			}
			if(MatrixA != null && MatrixB != null){
				if(MatrixA.columns() == MatrixB.rows()){
					Manager m = new Manager(MatrixA, MatrixB, MatrixC);
					try {
						//m.vectorSize = MatrixA.columns() * MatrixB.rows();
						m.store();
						m.execute();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					File fi = new File(this.txtInput.getText());
					fileHandler = new FileHandler();
					fileHandler.setFile(fi);
					fileHandler.setLines(m.MatrixC.ToArrayList());
					fileHandler.write();
					
					return;
				}
			}
			JOptionPane.showMessageDialog(this, "The columns in Matrix A must equal to rows in Matrix B.\n" 
					+ "Matrix A Columns: " + MatrixA.columns()
					+ "\nMatrix B Rows: " + MatrixB.rows()
					);
			
		} else if (arg0.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}
}