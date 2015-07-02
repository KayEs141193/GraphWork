package tfidfweight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class redd extends Reducer<Text,Text,Text,FloatWritable>{

	private float idf=0;
	
	@Override
	public void reduce(Text key,Iterable<Text> values,Context context) throws IOException, InterruptedException{
		
//		List<Text> tempVal=new ArrayList<Text>();

		List<String> temp= new ArrayList<String>();
		
		int sum=0;
		for(Text val:values){

			String s=val.toString();
			sum++;
			if(s.indexOf(" ")==-1)
				{idf=Float.parseFloat(s);}
			else{temp.add(s);}
			
		}		

		//context.write(new Text("FUCK"),new FloatWritable((float)(sum)));
//			else
//				{tempVal.add(val);}
//		}
		//context.write(key, new Text(Float.toString(idf)));
		
		for(String val:temp)
			context.write(new Text(val.substring(0,val.indexOf(' '))+" "+key.toString()),new FloatWritable((Float.parseFloat(val.substring(val.indexOf(' ')+1))*idf)));

//		
//		for(Text val:tempVal){
//			if(val.toString().indexOf(' ')!=-1)
//				context.write(new Text(val.toString().substring(0,val.toString().indexOf(' '))+" "+key.toString()+" "),new Text(Double.toString(Double.parseDouble(val.toString().substring(val.toString().indexOf(' ')+1))*idf)));
//		}

//		for(Text val:values)
//			context.write(key,val);
		
	}
	
}
