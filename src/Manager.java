
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

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Manager {

	Process[] proc = new Process[1];
	
	MatrixInt MatrixA;
	MatrixInt MatrixB;
	MatrixInt MatrixC;

	MappedByteBuffer io1, io2, io3;
	
	RandomAccessFile MatrixA_mmFile;
	RandomAccessFile MatrixB_mmFile;
	RandomAccessFile MatrixC_mmFile;
	
	int vectorSize = 15;
	
	public Manager(MatrixInt _MatrixA, MatrixInt _MatrixB, MatrixInt _MatrixC){
		MatrixA = _MatrixA;
		MatrixB = _MatrixB;
		MatrixC = _MatrixC;
	}

	public void store() throws IOException  {

		try {
			
			MatrixA_mmFile.setLength(0);
			MatrixB_mmFile.setLength(0);
			MatrixC_mmFile.setLength(0);
			
			io1.force();
			io2.force();
			io3.force();
			MatrixA_mmFile.close();
			MatrixB_mmFile.close();
			MatrixC_mmFile.close();
			
		MatrixA_mmFile = new RandomAccessFile("MatrixA.io", "rw");
		io1 = MatrixA_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, vectorSize*4);
		
		MatrixB_mmFile = new RandomAccessFile("MatrixB.io", "rw");
		io2 = MatrixB_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, vectorSize*4);
		
		MatrixC_mmFile = new RandomAccessFile("MatrixC.io", "rw");
		io3 = MatrixC_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, vectorSize*4);
		}
		catch (Exception e) {
			System.out.println("error");
		}
		
		//write MatrixA RandomAccessFile
		Vector[] vec = MatrixA.getM_matrix();
		int index = 0;
		for(int i = 0; i< vec.length; i++){
			Vector v = vec[i];
			for(int j= 0; j < v.vector.length; j++){
				io1.putInt(index , v.vector[j]);
				index += 4;
			}
			io1.putInt(index, -99999);
			index += 4;
		}
		
		//write MatrixB RandomAccessFile
		vec = MatrixB.getM_matrix();
		index = 0;
		for(int i = 0; i< vec.length; i++){
			Vector v = vec[i];
			for(int j= 0; j < v.vector.length; j++){
				io2.putInt(index , v.vector[j]);
				index += 4;
			}
			io2.putInt(index, -99999);
			index += 4;
		}
		
		io1.force();
		io2.force();
		io3.force();
		MatrixA_mmFile.close();
		MatrixB_mmFile.close();
		MatrixC_mmFile.close();
	}

	public void execute() {
		try {
			//String[] exec = {"java", "-cp", "bin\\", "Worker", Integer.toString(vectorSize)};	//using this for Eclipse Debugging
			String[] exec = {"java", "-cp", ".", "Worker", Integer.toString(vectorSize)};  		//java Worker vector size =12
			proc[0] = Runtime.getRuntime().exec(exec);
			proc[0].waitFor();

		} catch (Exception e) { 
			e.printStackTrace();
		}  

		int exitCode = proc[0].exitValue();

		if (exitCode != 0) { 
			System.out.println("worker error:"+exitCode);
		}

		int[] arrInt = new int[vectorSize];
		   
        ArrayList<String> alist = new ArrayList<String>();
        String s = "";
        int index = 0;
        
        //read MatrixC RandomAccessFile
        for(int i = 0; i<vectorSize; i++){
        	index = i*4;
        	arrInt[i] = io3.getInt(index);
        	s += arrInt[i] + " ";
        	if(arrInt[i] == -99999){
        		s = s.substring(0, s.indexOf("-99999"));
        		s = s.trim();
        		alist.add(s);
        		s= "";
        	}
        }
        
        if(alist.size() <= 0){
        	System.out.println("RandomAccessFile Fail! \n Can't retrive data for output matrix ");
        	throw new InvalidParameterException("Invalid number of rows");
        }
        
        MatrixC = new MatrixInt(alist);
        System.out.println("MatrixC result is:");
        System.out.println(MatrixC.toString());
 	}
	
}
