
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
//Michael Dorin
//ICS-462
//


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
			MatrixA = new MatrixInt();
			fileHandler = new FileHandler();
			fileHandler.select();
			fileHandler.read(MatrixA);
		} else if (arg0.getActionCommand().equals("Load Matrix B")) {
			MatrixB = new MatrixInt();
			fileHandler = new FileHandler();
			fileHandler.select();
			fileHandler.read(MatrixB);
		}
		else if (arg0.getActionCommand().equals("Dump Matrix A")) { 
			fileHandler.dump(MatrixA);
		}
		else if (arg0.getActionCommand().equals("Dump Matrix B")) { 
			fileHandler.dump(MatrixB);
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
			Manager m = new Manager(MatrixA, MatrixB, MatrixC);
			m.store();
			m.execute();
			
		} else if (arg0.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
	}
}