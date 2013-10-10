
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class FileHandler extends JPanel {

	private File file;
	private ArrayList<String> lines = new ArrayList<String>();
	JFileChooser chooser = new JFileChooser();


	public void select() {
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select a Matrix File");
		chooser.showOpenDialog(this);
		file = chooser.getSelectedFile();
	}
	
	public void read() {
		if (file == null)  {
			JOptionPane.showMessageDialog(this, "Unvalid file");
			return;
		}
		try {
			Scanner input = new Scanner(file);
			while(input.hasNext()) {
				String line = input.nextLine().trim();
				if(!line.isEmpty() && line != null){
					lines.add(line);
				}
			}

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Fail to read matrix file!.");
		}
	}

	public void dump() {
		for (int i=0;i<lines.size();i++)
			System.out.println(lines.get(i));
	}

	public ArrayList<String> getLines() {
		return lines;
	}
	
	public void setLines(ArrayList<String> lines) {
		this.lines = lines;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void write() {
		if (file == null)  {
			JOptionPane.showMessageDialog(this, "Unvalid file");
			return;
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
			for(String s : lines){
				writer.println(s);
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}
	
}
