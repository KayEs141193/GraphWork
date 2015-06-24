import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 
 SparseArray implementation. 
 - Remove methods not implemented completely because of little difficulty in modifying 
   all the keys greater than the removed key from the map by decrementing.
 
 PS: Implementation is not robust and should be used carefully
 */

public class SparseArray {
	
	private
		/**
		 * The size of the sparse array
		 */
		int size;
		/**
		 * A map object to implement the sparse array
		 */
		Map<Integer,Double> table;
		
	public
		/**
		 * Constructor to create a sparse array
		 */
	
		SparseArray(){
			
			size=0;
			table= new HashMap<Integer,Double>();
		
		}
	
		/**
		 * Add an element to the sparse array at the give position
		 * @param index
		 * @param element
		 */

		void add(int index,double element){
			
			if(index>=this.size)
				size=index+1;
			
			table.put(index, element);
			
		}

		/**
		 * Add the element at the back of the array
		 * @param element
		 */
		
		void add(double element){
			
			table.put(size, element);
			size++;
			
		}

		/**
		 * Check if the array contains the give element
		 * @param element
		 * @return
		 */
		
		boolean contains(double element){
			
			if(table.containsValue(element))
				return true;
			
			return false;
		}

		/**
		 * Get the element at the give index
		 * @param index
		 * @return
		 */
		
		double get(int index){
			
			
			
			if(table.containsKey(index)){
				
				return table.get(index);
			
			}
			
			return 0.0;
		}

		/**
		 * Check if the array is empty
		 * @return
		 */
		
		boolean isEmpty(){
			
			if(this.size==0)
				return true;
			return false;
			
		}
		
		/**
		 * Set the value of the element at the give position
		 * @param pos
		 * @param element
		 */
		
		void set(int pos,double element){
			
			if(table.containsKey(pos))
				table.put(pos,element);
			
		}
		
		/**
		 * Get the size of the array
		 * @return
		 */
		
		int size(){
			
			return size;
			
		}
		
		/**
		 * Get the keyset associated with sparse array
		 * @return
		 */
		
		Set<Integer> getKeySet(){
			
			return table.keySet();
		}
		
}	