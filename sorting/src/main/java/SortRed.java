import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SortRed extends Reducer<Text,SortedArrayWritable,Text,Text>{
	
	private Text[] temp;
	private List<Text> prev= new ArrayList<Text>();
	private List<Text> cur= new ArrayList<Text>();
	
	@Override
	public void reduce(Text key,Iterable<SortedArrayWritable> values,Context context){
		
		
		for(SortedArrayWritable val:values){
		
			temp=(Text[]) val.toArray();
			
			int tempPos=0;
			int resPos=0;

			
			while(tempPos<temp.length && resPos<prev.size()){
				
				if(Integer.parseInt(temp[tempPos].toString().substring(0,temp[tempPos].toString().indexOf(' ')))<Integer.parseInt(prev.get(resPos).toString().substring(0,prev.get(resPos).toString().indexOf(' '))) || (Integer.parseInt(temp[tempPos].toString().substring(0,temp[tempPos].toString().indexOf(' ')))==Integer.parseInt(prev.get(resPos).toString().substring(0,prev.get(resPos).toString().indexOf(' '))) && temp[tempPos].toString().substring(temp[tempPos].toString().indexOf(' ')+1).compareTo(prev.get(resPos).toString().substring(prev.get(resPos).toString().indexOf(' ')+1))<0 ))
					cur.add(temp[tempPos++]);
				else
					cur.add(prev.get(resPos++));
			
			}
			
			while(tempPos<temp.length)
				cur.add(temp[tempPos++]);
			
			while(resPos<prev.size())
				cur.add(prev.get(resPos));
			
			prev=new ArrayList<Text>(cur);
			cur=new ArrayList<Text>();
			
		}
		
		
	}
	
	
	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException{
		
		for(Text val:prev){
			
			int space=val.toString().indexOf(' ');
			context.write(new Text(val.toString().substring(space+1)),new Text(val.toString().substring(0, space)));
			
		}
		
	}
	
}
