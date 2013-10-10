
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

public interface Matrix {
	
	public int rows();
	
	public int columns();
	
	public boolean isNull();

	public MatrixInt multiply(MatrixInt right) throws InvalidParameterException;

}
