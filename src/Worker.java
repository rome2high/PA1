 
// Michael Dorin
// ICS-462
//

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Worker {

	RandomAccessFile MatrixA_mmFile;
	RandomAccessFile MatrixB_mmFile;
	RandomAccessFile MatrixC_mmFile;  
        
    MappedByteBuffer io1, io2, io3;
    
    MatrixInt MatrixA, MatrixB, MatrixC;


        public void myMain(String _size) throws Exception {
                int vectorSize = 0;

                try {
                        vectorSize = Integer.parseInt(_size);
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
                
                int[] v1_a = new int[vectorSize];
   
                ArrayList<String> alist = new ArrayList<String>();
                String s = "";
                int index = 0;
                
                //handle MatrixA
                for(int i = 0; i<vectorSize; i++){
                	index = i*4;
                	v1_a[i] = io1.getInt(index);
                	s += v1_a[i] + " ";
                	if(v1_a[i] == -99999){
                		s = s.substring(0, s.indexOf("-99999"));
                		s.trim();
                		alist.add(s);
                		s= "";
                	}
                }
                MatrixA = new MatrixInt(alist);
                //print("This is MatrixA: \n" + MatrixA.toString());
                
                //handle MatrixB
                alist = new ArrayList<String>();
                s = "";
                index = 0;
                for(int i = 0; i<vectorSize; i++){
                	index = i*4;
                	v1_a[i] = io2.getInt(index);
                	s += v1_a[i] + " ";
                	if(v1_a[i] == -99999){
                		s = s.substring(0, s.indexOf("-99999"));
                		s.trim();
                		alist.add(s);
                		s= "";
                	}
                }
                MatrixB = new MatrixInt(alist);
                //print("This is MatrixB: \n" + MatrixB.toString());
                
                MatrixC = MatrixA.multiply(MatrixB);
                
                //print("this MatrixC: \n" + MatrixC.toString());
                
                //write MatrixC to io3
                Vector[] vec = MatrixC.getM_matrix();
        		index = 0;
        		
        		for(int i = 0; i < vec.length; i++){
        			Vector v = vec[i];
        			for(int j= 0; j < v.vector.length; j++){
        				io3.putInt(index , v.vector[j]);
        				index += 4;
        			}
        			io3.putInt(index, -99999);
        			index += 4;
        		}
                
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

                if (args.length != 1)
                        System.exit(9);


                try {
                        worker.myMain(args[0]);
                } catch (Exception e) {
                         System.exit(2);
                }
        }
        
        public void print(String s){
        	 JOptionPane.showMessageDialog(null, s);
        }
        public void print(int i){
       	 JOptionPane.showMessageDialog(null, i);
       }
}
