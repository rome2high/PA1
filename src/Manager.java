// MemoryMappedFile Demo 
// Michael Dorin
// ICS-462
// 
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;


public class Manager {

	Process[] proc = new Process[1];
	
	MatrixInt mxA;
	MatrixInt mxB;
	MatrixInt mxC;

	MappedByteBuffer io1, io1_2, io2, io3;
	
	RandomAccessFile matrixA_mmFile;
	RandomAccessFile matrixA_2_mmFile;
	RandomAccessFile matrixB_mmFile;
	RandomAccessFile matrixC_mmFile;
	
	public Manager(MatrixInt _mxA, MatrixInt _mxB, MatrixInt _mxC){
		mxA = _mxA;
		mxB = _mxB;
		mxC = _mxC;
	}

	public void store()  {

		try {
		matrixA_mmFile = new RandomAccessFile("matrixA.io", "rw");
		io1 = matrixA_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		
		matrixA_2_mmFile = new RandomAccessFile("matrixA_2.io", "rw");
		io1_2 = matrixA_2_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		
		matrixB_mmFile = new RandomAccessFile("matrixB.io", "rw");
		io2 = matrixB_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		
		matrixC_mmFile = new RandomAccessFile("matrixC.io", "rw");
		io3 = matrixC_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		}
		catch (Exception e) {
			System.out.println("error");
		}

		ArrayList<String> a = mxA.getAlist();
		ArrayList<String> b = mxB.getAlist();
		
		for(int i=0;i<a.size();i++) {
			int[] v = new Vector(a.get(i)).getVector();
			
			for(int j=0;j<v.length;j++) {
				System.out.println(v[j]);
				io1.putInt(i*4, v[i]);
				io1_2.putInt(j*4, v[j]);
			}
		}
		
		for(int i=0;i<b.size();i++) {
			int[] v = new Vector(b.get(i)).getVector();
			for(int j=0;j<v.length;j++) {
				System.out.println(v[j]);
				io2.putInt(j*4, v[j]);
			}
		}
		
		for(int i=0;i<b.size();i++) {
			int[] v = new Vector(b.get(i)).getVector();
			for(int j=0;j<v.length;j++) {
				io3.putInt(j*4, 0);
			}
		}
		
		io1.force();
		io2.force();
		io3.force();
		;
		
	}

	public void execute() {
		
		// my vectors are all 3 in length
		
		try {
			String[] exec = {"java", "-cp", "bin\\", "Worker", "3"};  //java Worker 3 
			proc[0] = Runtime.getRuntime().exec(exec);
			proc[0].waitFor();

		} catch (Exception e) { 
			e.printStackTrace();
		}  

		int exitCode = proc[0].exitValue();

		if (exitCode != 0) { 
			System.out.println("worker error:"+exitCode);
		}

		System.out.println("Matrix result vector is:");

		for (int i=0;i<3;i++) {
			int val = io3.getInt(i*4);
			System.out.println(i+" "+val);
		}
		
 	}

 
}
