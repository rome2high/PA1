import java.security.InvalidParameterException;
import java.util.ArrayList;


public interface Matrix {
	
	public Matrix multiply(Matrix right) throws InvalidParameterException;
	
	public int rows();
	
	public int columns();
	
	public void setAlist(ArrayList<String> alist);
	
	public boolean isNull();

	public MatrixInt multiply(MatrixInt right) throws InvalidParameterException;

}
