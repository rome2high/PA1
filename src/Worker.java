
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
import javax.swing.JOptionPane;

public class Worker {

	RandomAccessFile MatrixA_mmFile;
	RandomAccessFile MatrixB_mmFile;
	RandomAccessFile MatrixC_mmFile;  
        
    MappedByteBuffer io1, io2, io3;
    
    MatrixInt MatrixA, MatrixB, MatrixC;

        public void myMain(String mArows, String mAcols, String mBrows, String mBcols) throws Exception {
                int Arows = 0;
                int Acols = 0;
                int Brows = 0;
                int Bcols = 0;
                
                try {
                	Arows = Integer.parseInt(mArows);
                	Acols = Integer.parseInt(mAcols);
                	Brows = Integer.parseInt(mBrows);
                	Bcols = Integer.parseInt(mBcols);
                }
                catch (Exception e) {
                        System.exit(15);
                }
                try {
                	MatrixA_mmFile = new RandomAccessFile("MatrixA.io", "rw");
                    io1 = MatrixA_mmFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, 100*8);
                    
                    MatrixB_mmFile = new RandomAccessFile("MatrixB.io", "rw");
                    io2 = MatrixB_mmFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, 100*8);
                    
                    MatrixC_mmFile = new RandomAccessFile("MatrixC.io", "rw");
                    io3 = MatrixC_mmFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 100*8);
               
                } catch (Exception e) {
                	System.out.println(e);
                	throw new IOException("RandomAccessFile Faild in Worker");
                }
                
                MatrixA = new MatrixInt(Arows, Acols);
                MatrixA.getFromIO(io1);
                //print(MatrixA.toString());
                
                MatrixB = new MatrixInt(Brows, Bcols);
                MatrixB.getFromIO(io2);
                //print(MatrixB.toString());
                
                MatrixC = new MatrixInt(Arows, Bcols);
                MatrixC = MatrixA.multiply(MatrixB);
                MatrixC.putToIO(io3);
                //print("this MatrixC: \n" + MatrixC.toString());

                io1.force();
                io2.force();
                io3.force();
                MatrixA_mmFile.close();
                MatrixB_mmFile.close();
                MatrixC_mmFile.close();
                System.exit(0);
        }
        
        public static void main(String args[]) {
                Worker worker = new Worker();

                if (args.length == 0) {
                        System.exit(12);
                }

                if (args.length != 4)
                        System.exit(9);

                try {
                    worker.myMain(args[0], args[1], args[2], args[3]);
                } catch (Exception e) {
                         System.exit(2);
                }
        }
        
        //debug use only
        public void print(String s){
        	 JOptionPane.showMessageDialog(null, s);
        }
        public void print(int i){
       	 JOptionPane.showMessageDialog(null, i);
       }
}
