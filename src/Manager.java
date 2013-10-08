// MemoryMappedFile Demo 
// Michael Dorin
// ICS-462
// 
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class Manager {

	Process[] proc = new Process[1];
	Vector vector1;
	Vector vector2;

	MappedByteBuffer io1, io2, io3;

	RandomAccessFile vector1MemoryMappedFile;
	RandomAccessFile vector2MemoryMappedFile;
	RandomAccessFile resultMemoryMappedFile;

	public Manager(Vector _vector1, Vector _vector2) {
		vector1 = _vector1;
		vector2 = _vector2;
	}


	public void store()  {

		try {
		vector1MemoryMappedFile = new RandomAccessFile("vector1.io", "rw");
		io1 = vector1MemoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		
		vector2MemoryMappedFile = new RandomAccessFile("vector2.io", "rw");
		io2 = vector2MemoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		
		resultMemoryMappedFile = new RandomAccessFile("vector3.io", "rw");
		io3 = resultMemoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3*4);
		}
		catch (Exception e) {
			System.out.println("error");
		}

		long val = 0;

		int[] v = vector1.getVector();
		for(int i=0;i<v.length;i++) {
			io1.putInt(i*4, v[i]);
		}

		v = vector2.getVector();
		for(int i=0;i<v.length;i++) {
			io2.putInt(i*4, v[i]);
		}

		for(int i=0;i<v.length;i++) {
			io3.putInt(i*4, 0);
		}
		io1.force();
		io2.force();
		io3.force();
		;
		
	}

	public void execute() {
		
		// my vectors are all 3 in length
		
		try {
			String[] exec = {"java", "-cp", "..\\PA1Demo\\bin\\", "Worker1", "3"};  //java Worker 3 
			proc[0] = Runtime.getRuntime().exec(exec);
			proc[0].waitFor();

		} catch (Exception e) { 
			e.printStackTrace();
		}  

		int exitCode = proc[0].exitValue();

		if (exitCode != 0) { 
			System.out.println("worker error:"+exitCode);
		}

		System.out.println("our result vector is:");

		for (int i=0;i<3;i++) {
			int val = io3.getInt(i*4);
			System.out.println(i+" "+val);
		}
		
 	}

 
}
