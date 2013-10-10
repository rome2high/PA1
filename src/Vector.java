import java.util.ArrayList;
//Michael Dorin
//ICS-462
//

public class Vector {
        String line;
        int[] vector;

        public Vector(String _line) {
                line = _line;
                String vals[] = line.split("\\s+");
                vector = new int[vals.length];
                for (int i=0;i<vals.length;i++) {
                        vector[i] = Integer.parseInt(vals[i]);
                }
        }
        
        public Vector(int[] _vector) {
        	vector = _vector;
        }

		public Vector(int m_cols) {
			for(int i = 0; i<m_cols; i++){
				vector[i]= 0;
			}
		}

		public int[] getVector() {
			return vector;
		}
		
		public Vector addVector(Vector _vector1, Vector _vector2) {
			Vector retVal = null;
			int[] v1 = _vector1.getVector();
			int[] v2 = _vector2.getVector();
			
			if (v1.length != v2.length) return null;
			int[] v3 = new int[v1.length];
			
			for (int i=0;i<v1.length;i++) {
				v3[i] = v1[i] + v2[i];
			}
			retVal = new Vector(v3);
			return retVal;
		}
		
		public Vector addVector(Vector _vector2) {
			Vector retVal = null; 
			int[] v2 = _vector2.getVector();
			if (v2.length != vector.length) return null;
			
			int[] v3 = new int[v2.length];
			
			for (int i=0;i<v2.length;i++) {
				v3[i] = vector[i] + v2[i];
			}
			retVal = new Vector(v3);
			return retVal;
		}


		public String toString() {
            String retVal = "The vector is:";
            for (int i=0;i<vector.length;i++)
                    retVal+=vector[i]+",";
            return retVal;
    }

		public void set(int i, int value) {
			vector[i] = value;
		}

}