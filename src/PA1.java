import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;



public class PA1 extends JFrame {
	
	static File   f;
    public PA1() {
    JFileChooser fileChooser = new JFileChooser();
    f=fileChooser.getSelectedFile();

    fileChooser.setDialogTitle("Choose a file");
    this.getContentPane().add(fileChooser);
    fileChooser.setVisible(true);
  }
    static BufferedReader br = null;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		int filesno=2;
		 
		   int x1=0;
		   int y1=0;
		   int p1=0;
		   int q1=0;
		        int[][] matrix1=null;
		        int[][] matrix2=null;
		    while(filesno!=0){
		        

		      JFileChooser chooser=new  JFileChooser();
		      int returnVal = chooser.showOpenDialog(null);
		      if(returnVal == JFileChooser.APPROVE_OPTION) {
		      File f = chooser.getSelectedFile();
		      try {
		           matrix1 = new int[102][102];
		         matrix2 = new int[102][102];             
		                    if(filesno==2)
		                    {
		                        int x=0;
		                        int y=0;
		                   Scanner br= new Scanner(f);
		                   
		                   String line = null;
		                   while ((line = br.nextLine()) != null) {
		                      y=0;
		                      
		                     String[] values = line.split(" ");
		                            
		                     for (String str : values) {
		                  
		                         int str_int = Integer.parseInt(str);
		                         matrix2[x][y]=str_int;
		                         
		                       //System.out.println(str_int);

		                    System.out.println(matrix2[x][y]);

		                    y=y+1;
		                     } 
		                     
		                   x=x+1;
		                   
		                   x1=x;
		                   y1=y;
		                   }
		                

		                    }
		         else 
		               {
		                        int p=0;
		                        int q=0;
		                   Scanner br= new Scanner(f);
		                   
		                   String line = null;
		                   while ((line = br.nextLine()) != null) {
		                      q=0;
		                      
		                     String[] values = line.split(" ");
		                            
		                     for (String str : values) {
		                  
		                         int str_int = Integer.parseInt(str);
		                         matrix2[p][q]=str_int;
		                         
		                       //System.out.println(str_int);

		                         System.out.println(matrix2[p][q]);

		                    q=q+1;
		                     } 
		                     
		                   p=p+1;
		                   q1=q;    
		                   
		                   p1=p; 
		                   }
		                   
		      
		      //System.out.println("The Matrix is:"+p1+""+q1);  
		      
		      
		      
		               
		      }
		                     
		      }
		                    catch(Exception e)
		                {
		                        
		                }
		     
		     
		      }
		      filesno--;
		    }
		 
		   int[][] c = new int[x1][q1];
		   

		    for ( int i = 0; i <x1; i++) {
		        for ( int j = 0; j < q1; j++) {
		        
		            for (int k = 0; k < y1; k++) {
		                
		                                
		                c[i][j] = c[i][j] + matrix1[i][k] * matrix2[k][j];
		            }
		    
		       
		    }
		   

		    
		    }
		    PrintWriter pw =new PrintWriter("c:/test1");
		    System.out.println("The Matrix is:"+x1+""+q1);  
		    for(int i=0; i<x1; i++){  
//		            pw.print(i);  
		        for(int j=0; j<q1; j++){  
//		            System.out.print(matrix[i][j]+" ");  
		              
		            pw.print(c[i][j]); // here trying to print into file  
		                                    // but it's printing in single line  
		        }  
		        System.out.println("");  
		    }  
		    pw.flush();  
		    	
		

	}

}
