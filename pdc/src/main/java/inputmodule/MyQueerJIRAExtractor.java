package inputmodule;

import java.io.IOException;
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
	List<String[]> currentIssues=null; // All issues for a project
	
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

		
		for(int i=1;i<projectsInfo.get(project_key);i++,row=jiraParser.readNext())
			currentIssues.add(row);
		
		for(int i=0;i<currentIssues.size();i++){
			
			String issueType=currentIssues.get(i)[map.get("Issue Type")];
			
			if(issueType=="Epic"){
			
				epicMapping.put(currentIssues.get(i)[map.get("Key")], i);
				epicSeen.put(currentIssues.get(i)[map.get("Key")], false);
				
			}else if(issueType=="Story"){

				storyMapping.put(currentIssues.get(i)[map.get("Key")], i);
				storySeen.put(currentIssues.get(i)[map.get("Key")], false);
				
			}else if(issueType=="Task"){
				
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
		
		row=jiraParser.readNext();
		
		//Populating projectsInfo map
		while(row!=null){
			
			String project_key=row[map.get("Key")].substring(0, row[map.get("Key")].indexOf('-'));
			
			if(!projectsInfo.containsKey(project_key))
				projectsInfo.put(project_key, 1);
			else
				projectsInfo.put(project_key, projectsInfo.get(project_key)+1);
			
			row=jiraParser.readNext();
			
		}
		
		reset();
		
		jiraParser.readNext();
		jiraParser.readNext();
		jiraParser.readNext();
		jiraParser.readNext();
		
		setNextProject();
		
	}
	
	
	private String constructRecord(){
		
		String res="";
		
		if(issueToSee==1){
			
			res+="\""+currentIssues.get(indexCI)[map.get("Summary")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Name
			res+="\""+currentIssues.get(indexCI)[map.get("Description")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Description
			res+="\""+currentIssues.get(indexCI)[map.get("Created")]+"\","; // Function.Project.Epic.Story.Task.Subtask.Start_date,
			res+="\""+currentIssues.get(indexCI)[map.get("Updated")]+"\",";// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
			res+="\""+currentIssues.get(indexCI)[map.get("Resolution")]+"\",";// Function.Project.Epic.Story.Task.Subtask.Completion,
			res+="\""+currentIssues.get(indexCI)[map.get("Creator")]+"\",";// Function.Project.Epic.Story.Task.Subtask.Owner,
			res+="\""+currentIssues.get(indexCI)[map.get("Assignee")]+"\",";// Function.Project.Epic.Story.Task.Subtask.Assignee
			
			String parent=currentIssues.get(indexCI)[map.get("Summary")].substring(0, currentIssues.get(indexCI)[map.get("Summary")].indexOf(" "));
			
			if(epicMapping.containsKey(parent)){
				//Under Epic
				
				epicSeen.put(parent, true);
				
				res=",,,,,,,,,,,,,,,"+res;
																								
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
				res="\""+currentIssues.get(epicMapping.get(parent))[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name
				
				res=",,,,,,"+res;
				res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
				res="\"Engineering and Product\","+res;
				
			}
			else if(storyMapping.containsKey(parent)){

				storySeen.put(parent, true);
				
				res=",,,,,,,,"+res;
																								
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
				res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name
				
				String epicShit=currentIssues.get(storyMapping.get(parent))[map.get("Epic Link")];
				
				if(epicShit==""){
				
					res=",,,,,,"+res;
					res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
					res="\"Engineering and Product\","+res;
					
				}
				else{
					int j=0;
					for(;j<currentIssues.size();j++)
						if(currentIssues.get(j)[map.get("Summary")]==epicShit)
							break;
					
					epicSeen.put(currentIssues.get(j)[map.get("Key")], true);
					
					res="\""+currentIssues.get(j)[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
					res="\""+currentIssues.get(j)[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
					res="\""+currentIssues.get(j)[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
					res="\""+currentIssues.get(j)[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
					res="\""+currentIssues.get(j)[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
					res="\""+currentIssues.get(j)[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
					res="\""+currentIssues.get(j)[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name

					res=",,,,,,"+res;
					res="\""+currentIssues.get(j)[map.get("Project")]+"\","+res;
					res="\"Engineering and Product\","+res;
					
				}
				
			}
			else if(taskMapping.containsKey(parent)){

				taskSeen.put(parent, true);
																								
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
				res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name
				
				res=",,,,,,,,"+res;
				
				String epicShit=currentIssues.get(taskMapping.get(parent))[map.get("Epic Link")];
				
				if(epicShit==""){
				
					res=",,,,,,"+res;
					res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
					res="\"Engineering and Product\","+res;
					
				}
				else{
					int j=0;
					for(;j<currentIssues.size();j++)
						if(currentIssues.get(j)[map.get("Summary")]==epicShit)
							break;
					
					epicSeen.put(currentIssues.get(j)[map.get("Key")], true);
					
					res="\""+currentIssues.get(j)[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
					res="\""+currentIssues.get(j)[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
					res="\""+currentIssues.get(j)[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
					res="\""+currentIssues.get(j)[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
					res="\""+currentIssues.get(j)[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
					res="\""+currentIssues.get(j)[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
					res="\""+currentIssues.get(j)[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name

					res=",,,,,,"+res;
					res="\""+currentIssues.get(j)[map.get("Project")]+"\","+res;
					res="\"Engineering and Product\","+res;
					
				}
				
				//Under Task
			}
			
		}else if(issueToSee==2){
			
			String parent=currentIssues.get(indexCI)[map.get("Key")];
			taskSeen.put(parent, true);
			res=",,,,,,,";
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
			res="\""+currentIssues.get(taskMapping.get(parent))[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name
			
			res=",,,,,,,,"+res;
			
			String epicShit=currentIssues.get(taskMapping.get(parent))[map.get("Epic Link")];
			
			if(epicShit==""){
			
				res=",,,,,,"+res;
				res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
				res="\"Engineering and Product\","+res;
				
			}
			else{
				int j=0;
				for(;j<currentIssues.size();j++)
					if(currentIssues.get(j)[map.get("Summary")]==epicShit)
						break;
				
				epicSeen.put(currentIssues.get(j)[map.get("Key")], true);
				
				res="\""+currentIssues.get(j)[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
				res="\""+currentIssues.get(j)[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
				res="\""+currentIssues.get(j)[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
				res="\""+currentIssues.get(j)[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
				res="\""+currentIssues.get(j)[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
				res="\""+currentIssues.get(j)[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
				res="\""+currentIssues.get(j)[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name

				res=",,,,,,"+res;
				res="\""+currentIssues.get(j)[map.get("Project")]+"\","+res;
				res="\"Engineering and Product\","+res;
				
			}
			
			//Under Task			
			
			
		}else if (issueToSee==3){
			
			String parent=currentIssues.get(indexCI)[map.get("Key")];
			storySeen.put(parent, true);
			
			res=",,,,,,,,,,,,,,";
																							
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
			res="\""+currentIssues.get(storyMapping.get(parent))[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name
			
			String epicShit=currentIssues.get(storyMapping.get(parent))[map.get("Epic Link")];
			
			if(epicShit==""){
			
				res=",,,,,,"+res;
				res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
				res="\"Engineering and Product\","+res;
				
			}
			else{
				int j=0;
				for(;j<currentIssues.size();j++)
					if(currentIssues.get(j)[map.get("Summary")]==epicShit)
						break;
				
				epicSeen.put(currentIssues.get(j)[map.get("Key")], true);
				
				res="\""+currentIssues.get(j)[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
				res="\""+currentIssues.get(j)[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
				res="\""+currentIssues.get(j)[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
				res="\""+currentIssues.get(j)[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
				res="\""+currentIssues.get(j)[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
				res="\""+currentIssues.get(j)[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
				res="\""+currentIssues.get(j)[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name

				res=",,,,,,"+res;
				res="\""+currentIssues.get(j)[map.get("Project")]+"\","+res;
				res="\"Engineering and Product\","+res;
				
			}

			
			
			
		}else{
			
			res=",,,,,,,,,,,,,,,,,,,,,";
			
			epicSeen.put(currentIssues.get(indexCI)[map.get("Key")], true);
			
			res="\""+currentIssues.get(indexCI)[map.get("Assignee")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Assignee
			res="\""+currentIssues.get(indexCI)[map.get("Creator")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Owner,	
			res="\""+currentIssues.get(indexCI)[map.get("Resolution")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.Completion,
			res="\""+currentIssues.get(indexCI)[map.get("Updated")]+"\","+res;// Function.Project.Epic.Story.Task.Subtask.End/Update_date,
			res="\""+currentIssues.get(indexCI)[map.get("Created")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Start_date,
			res="\""+currentIssues.get(indexCI)[map.get("Description")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Description
			res="\""+currentIssues.get(indexCI)[map.get("Summary")]+"\","+res; // Function.Project.Epic.Story.Task.Subtask.Name

			res=",,,,,,"+res;
			res="\""+currentIssues.get(indexCI)[map.get("Project")]+"\","+res;
			res="\"Engineering and Product\","+res;
			
			
		}
		
		
		return res;
	}
	
	public String nextRecord() throws IOException{
		
		String res="";
		
		if(issueToSee==1){
			
			for(;indexCI<currentIssues.size() && currentIssues.get(indexCI)[map.get("Issue Type")]!="Sub-task";indexCI++);
			
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=2;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==2){
		
			for(;indexCI<currentIssues.size() && currentIssues.get(indexCI)[map.get("Issue Type")]!="Task";indexCI++);
			
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=3;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==3){
			
			for(;indexCI<currentIssues.size() && currentIssues.get(indexCI)[map.get("Issue Type")]!="Story";indexCI++);
			
			if(indexCI>=currentIssues.size()){
				
				indexCI=0;
				issueToSee=4;
				
			}else{
				
				res=constructRecord();
				indexCI++;
			}
			
		}
		if(issueToSee==4){
			
			for(;indexCI<currentIssues.size() && currentIssues.get(indexCI)[map.get("Issue Type")]!="Epic";indexCI++);
			
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
		
		return res;
		
	}
	
}
