
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

import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MatrixInt implements Matrix{
	
	private int m_rows, m_cols;
	private Vector[] m_matrix;
	
	public static MatrixInt InitMatrix(ArrayList<String> _lists)
	{
		return new MatrixInt(_lists);
	}
	
	public MatrixInt(int rows, int cols) {
		
		if ((rows <= 0) || (cols <= 0))
		{
			throw new InvalidParameterException("Invalid amount of rows or columns.");
		}

		m_rows = rows;
		m_cols = cols;

		m_matrix = new Vector[m_rows];
		
		for (int i = 0; i < m_rows; i++)
		{
			m_matrix[i] = new Vector(m_cols);
		}
		
	}
	
	public MatrixInt(ArrayList<String> _lists)
	{
		int rows = _lists.size();
		int cols = 0;

		if ((rows < 0))
		{
			//JOptionPane.showMessageDialog(null, "NO Matrix Data Row");
			throw new InvalidParameterException("NO Matrix Data Row");
		}
		
		m_rows = rows;

		m_matrix = new Vector[m_rows];
		cols =  new Vector(_lists.get(0)).vector.length;	//first row col numb
		m_cols = cols;
		for (int i = 0; i < m_rows; i++)
		{
			//JOptionPane.showMessageDialog(null, i);
			m_matrix[i] = new Vector(_lists.get(i));
			//alist.add(_lists.get(i));						//redundant
			if(m_matrix[i].vector.length != cols){
				JOptionPane.showMessageDialog(null, "MatrixB Error!");
				throw new InvalidParameterException("Invalid columns size.");
			}
		}

	}
	
//	@Override
//	public Matrix multiply(Matrix right) throws InvalidParameterException {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public int rows() {
		// TODO Auto-generated method stub
		return m_rows;
	}

	@Override
	public int columns() {
		// TODO Auto-generated method stub
		return m_cols;
	}

	public Vector[] getM_matrix() {
		return m_matrix;
	}

	public void setM_matrix(Vector[] m_matrix) {
		this.m_matrix = m_matrix;
	}
	
	@Override
	public String toString() {
		String retVal ="";
		for(int i = 0; i < m_rows; i++){
			Vector v = m_matrix[i];
			for(int in: v.vector){
				retVal += in + ",";
			}
			retVal = retVal.substring(0, retVal.length()-1) + "\n";
		}
		return retVal;
	}
	
	@Override
	public boolean isNull(){
		return m_matrix.length <= 0;
	}
	
	public void set(int i, int j, int v)
	{
		m_matrix[i].set(j, v);
	}

	@Override
	public MatrixInt multiply(MatrixInt right) throws InvalidParameterException {
		MatrixInt MatrixB = right;

		if (m_cols != MatrixB.m_rows)
		{
			throw new InvalidParameterException("The columns in Matrix A must equal to rows in Matrix B.");
		}
		
		MatrixInt MatrixC = new MatrixInt(m_rows, MatrixB.m_cols);
		
		for (int i = 0; i < MatrixC.m_rows; i++)
		{
			for (int j = 0; j < MatrixC.m_cols; j++)
			{
				for (int k = 0; k < m_cols; k++)
				{
					int v = MatrixC.m_matrix[i].vector[j]
							+ m_matrix[i].vector[k] * MatrixB.m_matrix[k].vector[j];
					MatrixC.m_matrix[i].set(j, v);
				}
			}
		}

		return MatrixC;
	}
	
	public ArrayList<String> ToArrayList(){
		ArrayList<String> arrS = new ArrayList<String>();
		for(Vector v : m_matrix){
			arrS.add(v.line);
		}
		return arrS;
	}

}
