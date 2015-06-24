import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/*
 
 SparseArray implementation. 
 - Remove methods not implemented completely because of little difficulty in modifying 
   all the keys greater than the removed key from the map by decrementing.
 
 PS: Implementation is not robust and should be used carefully
 */

public class SparseArray {
	
	private
		
		int size;
		Map<Integer,Double> table;
		
	public
	
		SparseArray(){
			
			size=0;
			table= new HashMap<Integer,Double>();
		
		}

		void add(int index,double element){
			
			if(index>=this.size)
				size=index+1;
			
			table.put(index, element);
			
		}

		void add(double element){
			
			table.put(size, element);
			size++;
			
		}

		boolean contains(double element){
			
			if(table.containsValue(element))
				return true;
			
			return false;
		}

		
		double get(int index){
			
			
			
			if(table.containsKey(index)){
				
				return table.get(index);
			
			}
			
			return 0.0;
		}

		boolean isEmpty(){
			
			if(this.size==0)
				return true;
			return false;
			
		}

//		boolean remove(T element){
//			
//			for(int i=0;i<arr.size();i++){
//				
//				if(arr.get(i)==element){
//					
//					Set<Integer> keyset=table.keySet();
//					
//					Integer keyVal=0;
//					
//					for(Integer key:keyset){
//						
//						if(table.get(key)==i){
//							keyVal=key;
//							keyset.remove(key);
//							arr.remove(i);
//							break;
//							
//						}
//						
//					}
//					
//					for(Integer key:keyset){
//						
//						if(table.get(key)>keyVal){
//							
//							
//							
//						}
//						
//					}
//					
//					return true;
//				}
//				
//			}
//			
//			return false;
//		}
//		
//		boolean remove(int pos){
//			
//			return false;
//			
//		}
		
		void set(int pos,double element){
			
			if(table.containsKey(pos))
				table.put(pos,element);
			
		}
		
		int size(){
			
			return size;
			
		}
		
		Set<Integer> getKeySet(){
			
			return table.keySet();
		}
		
}