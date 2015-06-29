import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class SortMap extends Mapper<Text,Text,Text,SortedArrayWritable>{

	private List<Text> list=new ArrayList<Text>();
	private SortedArrayWritable res;
	
	@Override
	public void map(Text key,Text value,Context context){
		
		int i=0;
		
		for(;i<list.size() && (Integer.parseInt(list.get(i).toString().substring(0,list.get(i).toString().indexOf(' ')))<Integer.parseInt(value.toString().substring(0,list.get(i).toString().indexOf(' '))) || (Integer.parseInt(list.get(i).toString().substring(0,list.get(i).toString().indexOf(' ')))==Integer.parseInt(value.toString().substring(0,list.get(i).toString().indexOf(' '))) && key.toString().compareTo(list.get(i).toString().substring(list.get(i).toString().indexOf(' ')+1))>=0 ) );i++);
		
		if(i==list.size())
			list.add(new Text(value.toString()+" "+key.toString()));
		else
			list.add(i,new Text(value.toString()+" "+key.toString()));

		
	}
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException{
		
		res=new SortedArrayWritable(list.toArray(new Text[list.size()]));
		context.write(new Text("BS"),res);
		
	}
	
}
