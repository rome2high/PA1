import java.security.InvalidParameterException;

public interface Matrix {
	
	public Matrix multiply(Matrix right) throws InvalidParameterException;
	
	public int rows();
	
	public int columns();
	
	public boolean isNull();

	public MatrixInt multiply(MatrixInt right) throws InvalidParameterException;

}
