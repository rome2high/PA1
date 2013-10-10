import java.security.InvalidParameterException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MatrixInt implements Matrix{
	
	private int m_rows, m_cols;
	private ArrayList<String> alist = new ArrayList<String>();
	private Vector[] m_matrix;
	
	public static MatrixInt InitMatrix(ArrayList<String> _lists)
	{
		return new MatrixInt(_lists);
	}
	
	public MatrixInt(int rows, int cols) {
		// TODO Auto-generated constructor stub
		if ((m_rows <= 0) || (m_cols <= 0))
		{
			throw new InvalidParameterException("Invalid amount of rows or columns.");
		}

		m_rows = rows;
		m_cols = cols;

		m_matrix = new Vector[m_rows];
		for (int i = 0; i < m_rows; i++)
		{
			m_matrix[i] = new Vector(m_cols);
			JOptionPane.showMessageDialog(null, "this is " + m_matrix[i]);
		}
		
	}
	
	public MatrixInt(ArrayList<String> _lists)
	{
		int rows = _lists.size();
		int cols = 0;
		
//		if ((rows <= 0) || (columns <= 0))
		if ((rows <= 0))
		{
			JOptionPane.showMessageDialog(null, "Unvalid Data");
			throw new InvalidParameterException("NO matrix data.");
		}
		
		m_rows = rows;
		//m_cols = columns;

		m_matrix = new Vector[m_rows];
		cols =  new Vector(_lists.get(0)).vector.length;	//first row col numb
		m_cols = cols;
		for (int i = 0; i < m_rows; i++)
		{
			m_matrix[i] = new Vector(_lists.get(i));
			alist.add(_lists.get(i));						//redundant
			if(m_matrix[i].vector.length != cols){
				throw new InvalidParameterException("Invalid columns size.");
			}
		}

	}
	
	@Override
	public Matrix multiply(Matrix right) throws InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}
	
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

	public ArrayList<String> getAlist() {
		return alist;
	}

	@Override
	public void setAlist(ArrayList<String> alist) {
		this.alist = alist;
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
		// TODO Auto-generated method stub
		
		//JOptionPane.showMessageDialog(null, right);
		
		MatrixInt MatrixB = right;

//		if (m_cols != MatrixB.m_rows)
//		{
//			throw new InvalidParameterException("The quantity of columns in "
//					+ "multiplicand must be equal to the quantity of rows "
//					+ "int multiplier.");
//		}

		MatrixInt MatrixC = new MatrixInt(m_rows, right.m_cols);
		
		JOptionPane.showMessageDialog(null, "This is MatrixC: \n"+MatrixC);
		
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
		
		
		JOptionPane.showMessageDialog(null, "This is MatrixC: \n"+MatrixC);

		return MatrixC;
	}



}
