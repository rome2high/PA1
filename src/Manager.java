// MemoryMappedFile Demo 
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
	
	public Manager(MatrixInt _MatrixA, MatrixInt _MatrixB, MatrixInt _MatrixC){
		MatrixA = _MatrixA;
		MatrixB = _MatrixB;
		MatrixC = _MatrixC;
	}

	public void store() throws IOException  {

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
		
		Vector[] vec = MatrixA.getM_matrix();
		int index = 0;
		
		for(int i = 0; i< vec.length; i++){
			Vector v = vec[i];
			for(int j= 0; j < v.vector.length; j++){
				System.out.println(index + " - " +v.vector[j]);
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
				System.out.println(index + " - " +v.vector[j]);
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
		
		// my vectors are all 3 in length
		
		try {
			String[] exec = {"java", "-cp", ".", "Worker", "12"};  //java Worker 3 
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
		
		int[] v1_a = new int[12];
		   
        ArrayList<String> alist = new ArrayList<String>();
        String s = "";
        int index = 0;
        
        //handle MatrixC
        for(int i = 0; i<12; i++){
        	index = i*4;
        	v1_a[i] = io3.getInt(index);
        	s += v1_a[i] + " ";
        	if(v1_a[i] == -99999){
        		s = s.substring(0, s.indexOf("-99999"));
        		s = s.trim();
        		alist.add(s);
        		s= "";
        	}
        }
        
        MatrixC = new MatrixInt(alist);
        System.out.println(MatrixC.toString());
		
 	}

 
}
