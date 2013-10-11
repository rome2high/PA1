
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

	private void clearIOfile() throws IOException{
		byte[] clearbuffer;
		try{
		clearbuffer = new byte[io1.limit()];
		io1.put(clearbuffer);
		io1.force();
		io1.clear();
		clearbuffer = new byte[io2.limit()];
		io2.put(clearbuffer);
		io2.force();
		io2.clear();
		clearbuffer = new byte[io3.limit()];
		io3.put(clearbuffer);
		io3.force();
		io3.clear();
		}catch (Exception e){
			throw new IOException("Fail to clear .io files!");
		}
	}

	public void store() throws IOException  {

		try {
			
		MatrixA_mmFile = new RandomAccessFile("MatrixA.io", "rw");
		io1 = MatrixA_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 100*8);
		
		MatrixB_mmFile = new RandomAccessFile("MatrixB.io", "rw");
		io2 = MatrixB_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 100*8);
		
		MatrixC_mmFile = new RandomAccessFile("MatrixC.io", "rw");
		io3 = MatrixC_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 100*8);
		}
		catch (Exception e) {
			System.out.println("error");
		}
		
		//clearIOfile();
		
		MatrixA.putToIO(io1);
		MatrixB.putToIO(io2);
		MatrixC = new MatrixInt(MatrixA.rows(), MatrixB.columns());
		MatrixC.putToIO(io3);
		
		io1.force();
		io2.force();
		io3.force();
		MatrixA_mmFile.close();
		MatrixB_mmFile.close();
		MatrixC_mmFile.close();
	}

	public void execute() throws IOException {
		
		try {
			String[] exec = {"java", "-cp", "bin\\", "Worker", Integer.toString(MatrixA.rows()), Integer.toString(MatrixA.columns()), Integer.toString(MatrixB.rows()), Integer.toString(MatrixB.columns())};	//using this for Eclipse Debugging
			//String[] exec = {"java", "-cp", ".", "Worker", Integer.toString(MatrixA.rows()), Integer.toString(MatrixA.columns()), Integer.toString(MatrixB.rows()), Integer.toString(MatrixB.columns())};  		//java Worker vector size =12
			proc[0] = Runtime.getRuntime().exec(exec);
			proc[0].waitFor();

		} catch (Exception e) { 
			e.printStackTrace();
		}  

		int exitCode = proc[0].exitValue();

		if (exitCode != 0) { 
			System.out.println("worker error:"+exitCode);
		}
		
		MatrixC.getFromIO(io3);
		System.out.println("MatrixC result is:");
		System.out.println(MatrixC);
 	}
	
}
