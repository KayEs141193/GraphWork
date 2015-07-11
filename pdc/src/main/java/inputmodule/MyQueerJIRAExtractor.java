package inputmodule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyQueerJIRAExtractor implements MyExtractor{

	MyQueerJIRAParser jiraParser;
	MyMapParser mapParser;
	String[] opFormat=null;
	String dbPath=null;
	String mapPath=null;
	Integer issueToSee;
	
	Map<String,Integer> map=new HashMap<String,Integer>(); //mapping various attributes of the given table to array index number
	Map<String,Integer> projectsInfo=new HashMap<String,Integer>(); //Contains project_key:total_numberof_issues
	List<String[]> currentIssues=new ArrayList<String[]>(); // All issues for a project
	
	Map<String,Integer> epicMapping=new HashMap<String,Integer>();
	Map<String,Boolean> epicSeen=new HashMap<String,Boolean>();
	
	Map<String,Integer> storyMapping=new HashMap<String,Integer>();
	Map<String,Boolean> storySeen=new HashMap<String,Boolean>();
	
	Map<String,Integer> taskMapping=new HashMap<String,Integer>();
	Map<String,Boolean> taskSeen=new HashMap<String,Boolean>();

	Integer indexCI;
	
	private void reset() throws IOException{
		
		jiraParser.close();
		jiraParser = new MyQueerJIRAParser(dbPath);
		
	}
	
	private boolean setNextProject() throws IOException{
		
		String[] row=jiraParser.readNext();
		if(row==null)
			return false;
		
		String project_key=row[map.get("Key")].substring(0, row[map.get("Key")].indexOf('-'));
		currentIssues.clear();
		
		epicMapping.clear();epicSeen.clear();
		storyMapping.clear();storySeen.clear();
		taskMapping.clear();taskSeen.clear();

		
		for(int i=1;i<projectsInfo.get(project_key);i++,row=jiraParser.readNext()){
			row[map.get("Key")]=row[map.get("Key")].substring(0,row[map.get("Key")].length()-1);
			currentIssues.add(row);
		}
			
		System.out.println(project_key);
		//System.out.println("IssuesNo:"+currentIssues.size());
		
		for(int i=0;i<currentIssues.size();i++){
			
			String issueType=currentIssues.get(i)[map.get("Issue Type")];
			//System.out.println(issueType);
			
			if(issueType.equalsIgnoreCase("Epic")){
			
				epicMapping.put(currentIssues.get(i)[map.get("Key")], i);
				epicSeen.put(currentIssues.get(i)[map.get("Key")], false);
				
			}else if(issueType.equalsIgnoreCase("Story")){

				storyMapping.put(currentIssues.get(i)[map.get("Key")], i);
				storySeen.put(currentIssues.get(i)[map.get("Key")], false);
				
			}else if(issueType.equalsIgnoreCase("Task")){
				
				taskMapping.put(currentIssues.get(i)[map.get("Key")], i);
				taskSeen.put(currentIssues.get(i)[map.get("Key")], false);
				
			}
		
		}
		
		
		
		issueToSee=1;
		indexCI=0;
		
		return true;
		
	}
	
	public MyQueerJIRAExtractor(String dbPath,String mapPath,String[] opFormat) throws IOException{
		
		this.dbPath=dbPath;
		this.mapPath=mapPath;
		
		jiraParser = new MyQueerJIRAParser(dbPath);
		mapParser = new MyMapParser(mapPath);
		this.opFormat=opFormat;
		
		jiraParser.readNext();
		jiraParser.readNext();
		jiraParser.readNext();
		
		String[] row=jiraParser.readNext();
		
		for(int i=0;i<row.length;i++)
			map.put(row[i], i);
		
		//System.out.println("Map Size:"+map.size());
		
		row=jiraParser.readNext();
		
		//Populating projectsInfo map
		while(row!=null){
			
			String project_key=row[map.get("Key")].substring(0, row[map.get("Key")].indexOf('-'));
			//System.out.println("Key:"+row[map.get("Key")]);
			
			if(!projectsInfo.containsKey(project_key))
				projectsInfo.put(project_key, 1);
			else
				projectsInfo.put(project_key, projectsInfo.get(project_key)+1);
			
			row=jiraParser.readNext();
			
		}
		
		//System.out.println("Projects:"+projectsInfo.size());
		
		reset();
		
		jiraParser.readNext();
		jiraParser.readNext();
		jiraParser.readNext();
		jiraParser.readNext();

		
		setNextProject();
		
	}
	
	private String constructThis(int index){
		
		String res="";

		res+="\""+currentIssues.get(index)[map.get("Summary")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Name
		res+="\""+currentIssues.get(index)[map.get("Description")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Description
		res+="\""+currentIssues.get(index)[map.get("Created")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Start_date,
		res+="\""+currentIssues.get(index)[map.get("Updated")]+"\",";// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
		res+="\""+currentIssues.get(index)[map.get("Resolution")]+"\",";// Function.Project.Epic.Story.Task.Subtask.Completion,
		res+="\""+currentIssues.get(index)[map.get("Creator")]+"\",";// Function.Project.Epic.Story.Task.Subtask.Owner,
		res+="\""+currentIssues.get(index)[map.get("Assignee")]+"\"";// Function.Project.Epic.Story.Task.Subtask.Assignee

		return res;
		
	}
	
	private String findEpic(String desc){

		String res="";
		int j=0;
		for(;j<currentIssues.size();j++){
			//System.out.println(currentIssues.get(j)[map.get("Epic Name")]+" : "+desc);
			if(desc.equalsIgnoreCase(currentIssues.get(j)[map.get("Epic Name")]) && epicMapping.containsKey(currentIssues.get(j)[map.get("Key")])){
				
				//System.out.println("filayelufblausbfulaiuaeblfiuawelfiubwelfb");
				res=currentIssues.get(j)[map.get("Key")];
				
			}
		}
		return res;
		
	}
	
	private String constructRecord(){
		
		System.out.println("Creating a Rec");
		String res="";
		String parent=currentIssues.get(indexCI)[map.get("Key")];
			
			if(currentIssues.get(indexCI)[map.get("Issue Type")].equalsIgnoreCase("Sub-task")){
				
				System.out.println("SubT:");
				res=constructThis(indexCI)+res;
				parent=currentIssues.get(indexCI)[map.get("Summary")].substring(0, currentIssues.get(indexCI)[map.get("Summary")].indexOf(" "));
				if(!parent.isEmpty())
					System.out.println("SubT:"+parent);
				
			}else{
				
				res="\"\",\"\",\"\",\"\",\"\",\"\",\"\""+res;
				
			}
			
			System.out.println(projectsInfo.get("PFR"));
			
			if(taskMapping.containsKey(parent)){
				
				System.out.println("Task:");
				taskSeen.put(parent, true);
				res=constructThis(taskMapping.get(parent))+","+res;
				parent=findEpic(currentIssues.get(taskMapping.get(parent))[map.get("Epic Link")]);
				if(!parent.isEmpty())
					System.out.println("Task:"+parent);
			}
			else{
				
				res="\"\",\"\",\"\",\"\",\"\",\"\",\"\","+res;
				
			} 
				
				
			if(storyMapping.containsKey(parent)){
			
				System.out.println("Story:");
				storySeen.put(parent, true);
				res=constructThis(storyMapping.get(parent))+","+res;
				parent=findEpic(currentIssues.get(storyMapping.get(parent))[map.get("Epic Link")]);
				if(!parent.isEmpty())
					System.out.println("Story:"+parent);				
			}
			else{
				
				res="\"\",\"\",\"\",\"\",\"\",\"\",\"\","+res;

				
			}
			
			
			if(epicMapping.containsKey(parent)){
				//Under Epic
				System.out.println("Epic:");
				epicSeen.put(parent, true); // epic Seen 
				res=constructThis(epicMapping.get(parent))+","+res;
				
				
			}else{
				
				res="\"\",\"\",\"\",\"\",\"\",\"\",\"\","+res;

				
			}
			
			System.out.println(" ");
			res="\"\",\"\",\"\",\"\",\"\","+res;
			res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
			res="\"Engineering and Product\","+res;
		
		return res;
	}
	
	public String nextRecord() throws IOException{
		
		String res="";
		
		if(issueToSee==1){
			
			for(;indexCI<currentIssues.size() && !currentIssues.get(indexCI)[map.get("Issue Type")].equalsIgnoreCase("Sub-task") ;indexCI++);
			//System.out.println("index:"+indexCI);
			
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=2;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==2){
		
			for(;indexCI<currentIssues.size() && (!currentIssues.get(indexCI)[map.get("Issue Type")].equalsIgnoreCase("Task") || taskSeen.get(currentIssues.get(indexCI)[map.get("Key")]));indexCI++);
			//System.out.println("index:"+indexCI);
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=3;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==3){
			
			for(;indexCI<currentIssues.size();indexCI++){
				
				if(currentIssues.get(indexCI)[map.get("Issue Type")].equalsIgnoreCase("Story") ){
					
//					System.out.println(currentIssues.get(indexCI)[map.get("Issue Type")]);
//					System.out.println(currentIssues.get(indexCI)[map.get("Key")]);
//					System.out.println(storyMapping.get(currentIssues.get(indexCI)[map.get("Key")]));
					
					if(storySeen.get(currentIssues.get(indexCI)[map.get("Key")]))
						break;
				
				}
				
			}
			
			
			//System.out.println("index:"+indexCI);
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=4;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==4){
			
			for(;indexCI<currentIssues.size() && (!currentIssues.get(indexCI)[map.get("Issue Type")].equalsIgnoreCase("Epic") || epicSeen.get(currentIssues.get(indexCI)[map.get("Key")]));indexCI++);
			//System.out.println("index:"+indexCI);
			if(indexCI>=currentIssues.size()){
				
				if(setNextProject())
					res=nextRecord();
				else
					res=null;
				
			}else{

				res=constructRecord();
				indexCI++;

			}
			
		}
		
		
		//System.out.println("Record To Send:"+res);
		return res;
		
	}
	
}
