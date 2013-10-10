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
	
	MatrixInt MatrixA;
	MatrixInt MatrixB;
	MatrixInt MatrixC;

	MappedByteBuffer io1, io2, io3;
	
	RandomAccessFile MatrixA_mmFile;
	RandomAccessFile MatrixB_mmFile;
	RandomAccessFile MatrixC_mmFile;
	
	public Manager(MatrixInt _MatrixA, MatrixInt _MatrixB, MatrixInt _MatrixC){
		MatrixA = _MatrixA;
		MatrixB = _MatrixB;
		MatrixC = _MatrixC;
	}

	public void store()  {

		try {
		MatrixA_mmFile = new RandomAccessFile("MatrixA.io", "rw");
		io1 = MatrixA_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 12*4);
		
		MatrixB_mmFile = new RandomAccessFile("MatrixB.io", "rw");
		io2 = MatrixB_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 12*4);
		
		MatrixC_mmFile = new RandomAccessFile("MatrixC.io", "rw");
		io3 = MatrixC_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 12*4);
		}
		catch (Exception e) {
			System.out.println("error");
		}
		
		System.out.println("Store Matrices");

		//ArrayList<String> a = MatrixA.getAlist();
		//ArrayList<String> b = MatrixB.getAlist();
		
		Vector[] vec = MatrixA.getM_matrix();
		int index = 0;
		
		for(int i = 0; i< vec.length; i++){
			Vector v = vec[i];
			for(int j= 0; j < v.vector.length; j++){
				System.out.println(index + "-" +v.vector[j]);
				io1.putInt(index , v.vector[j]);
				index += 4;
			}
			io1.putInt(index, -99999);
			index += 4;
		}
		
		vec = MatrixB.getM_matrix();
		index = 0;
		for(int i = 0; i< vec.length; i++){
			Vector v = vec[i];
			for(int j= 0; j < v.vector.length; j++){
				System.out.println(index + "-" +v.vector[j]);
				io2.putInt(index , v.vector[j]);
				index += 4;
			}
			io2.putInt(index, -99999);
			index += 4;
		}
		
//		for(int i=0;i<a.size();i++) {
//			int[] v = new Vector(a.get(i)).getVector();
//			
//			for(int j=0;j<v.length;j++) {
//				System.out.println(v[j]);
//				io1.putInt(i*4, v[i]);
//				io1_2.putInt(j*4, v[j]);
//			}
//		}
		
//		for(int i=0;i<b.size();i++) {
//			int[] v = new Vector(b.get(i)).getVector();
//			for(int j=0;j<v.length;j++) {
//				System.out.println(v[j]);
//				io2.putInt(j*4, v[j]);
//			}
//		}
//		
//		for(int i=0;i<b.size();i++) {
//			int[] v = new Vector(b.get(i)).getVector();
//			for(int j=0;j<v.length;j++) {
//				io3.putInt(j*4, 0);
//			}
//		}
		
		io1.force();
		io2.force();
		io3.force();
		;
		
	}

	public void execute() {
		
		// my vectors are all 3 in length
		
		try {
			String[] exec = {"java", "-cp", "bin\\", "Worker", "12"};  //java Worker 3 
			proc[0] = Runtime.getRuntime().exec(exec);
			proc[0].waitFor();

		} catch (Exception e) { 
			e.printStackTrace();
		}  

		int exitCode = proc[0].exitValue();

		if (exitCode != 0) { 
			System.out.println("worker error:"+exitCode);
		}

		System.out.println("MatrixC result is:");

		for (int i=0;i<3;i++) {
			int val = io3.getInt(i*4);
			//System.out.println(i+" "+val);
		}
		
		System.out.println("End.........!");
		
 	}

 
}
