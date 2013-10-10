
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
//Michael Dorin
//ICS-462
//
import javax.swing.JOptionPane;


public class Parent extends JFrame implements ActionListener {

	private static JButton button0 = new JButton("Load Matrix A");
	private static JButton button1 = new JButton("Dump Matrix A");
	private static JButton button2 = new JButton("Load Matrix B");
	private static JButton button3 = new JButton("Dump Matrix B");
	private static JButton button4 = new JButton("Execute");
	private static JButton button5 = new JButton("Exit");

	Vector vector1, vector2;
	MatrixInt MatrixA, MatrixB, MatrixC;

	FileHandler fileHandler = new FileHandler();

	public void myMain() {
		setTitle("ICS-462 PA1 Processes with JAVA");
		setLayout(new GridLayout(0,1));
		setSize(400,400);
		setLocationRelativeTo(null);
		add(button0);
		add(button1);
		add(button2);
		add(button3);
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
			
			//need to know rows and cols
			fileHandler = new FileHandler();
			fileHandler.select();
			if(fileHandler.getFile() == null){	//cancel button
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
		}
		else if (arg0.getActionCommand().equals("Dump Matrix B")) { 
			System.out.println("The Matrix B is:");
			System.out.println(MatrixB);
		}
		
		else if (arg0.getActionCommand().equals("Parse")) { 
			ArrayList<String>lines = fileHandler.getLines();
			vector1 = new Vector (lines.get(0));
			vector2 = new Vector (lines.get(1));
			vector1.addVector(vector2);
			System.out.println(vector1);
			System.out.println(vector2);	

		}
		else if (arg0.getActionCommand().equals("Execute")) { 
			//matrix matching
			if(MatrixA != null && MatrixB != null){
				if(MatrixA.columns() == MatrixB.rows()){
					Manager m = new Manager(MatrixA, MatrixB, MatrixC);
					m.store();
					m.execute();
					return;
				}
			}
			JOptionPane.showMessageDialog(this, "Matrices are not match!");
			
		} else if (arg0.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}
}