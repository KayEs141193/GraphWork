import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Text;


public class SortedArrayWritable extends ArrayWritable{

	public SortedArrayWritable(){
		
		super(Text.class);
		
	}
	
	public SortedArrayWritable(Text[] values){
		
		super(Text.class,values);
		
	}
	
}
