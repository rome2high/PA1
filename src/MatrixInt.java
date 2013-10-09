import java.security.InvalidParameterException;
import java.util.ArrayList;

public class MatrixInt implements Matrix{
	
	private int m_rows, m_cols;
	private ArrayList<String> alist = new ArrayList<String>();
	private Vector[] m_matrix;
	
	public static MatrixInt InitMatrix()
	{
		return new MatrixInt();
	}
	
	public MatrixInt()
	{
//		if ((columns() <= 0))
//		{
//			throw new InvalidParameterException("Invalid amount of rows or columns.");
//		}

		//m_rows = rows;
		//m_cols = columns;

//		m_matrix = new Vector[m_rows];
//		for (int i = 0; i < m_rows; i++)
//		{
//			//m_matrix[i] = new Vector(m_cols);
//		}
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

}
