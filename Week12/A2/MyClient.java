import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("127.0.0.1",50000);  
//DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));  
 
//Set strings
String userName = System.getProperty("user.name");
String inputString="",outputString=""; 
 
 
 			//System.out.println("~~~~~");
 
//Create greeting message to Server
inputString="HELO";
//Send to server
dout.write((inputString+"\n").getBytes());  
dout.flush();

//Acknowledgement from Server
outputString=in.readLine();  
//Print message received from server
//System.out.println("Server says: "+outputString);  



//Create Authentication message to Server
inputString="AUTH " + userName;
//Send to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();

//Receive message from server 
outputString=in.readLine();  
//Print message received from server
//System.out.println("Server says: "+outputString);    



//Client is 'Ready' for simulation
inputString="REDY";
//Send to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();

//Receive message from server 
outputString=in.readLine();  
//Print message received from server
//System.out.println("Server says: "+outputString);  
  
  
  
//Splitting of given JOB

String[] strArr = outputString.split("\\s+");

//System.out.println("JOB = " + outputString);
//JOBN 32 0 728 1 700 600
/* 
System.out.println(strArr[0]);
System.out.println(strArr[1]);
System.out.println(strArr[2]);
System.out.println(strArr[3]);
System.out.println(strArr[4]);
System.out.println(strArr[5]);
System.out.println(strArr[6]);
*/
//System.out.println("-------------");
//Set command to current Job
String currentJob = strArr[0];

//System.out.println(currentJob);

//variables used for scheduling jobs
int jobID = -1;
int jobCore = 0;
int serverCore = 0;
String serverID = "";
String serverName = "";
boolean scheduleJob = false;

//Loop scheduling of jobs
while (!currentJob.equals("NONE")) {
	//Current Job
	//System.out.println("Current Job: " + currentJob);
	//System.out.println("-----");

	//Command 1 JobN
	if(currentJob.equals("JOBN")) {
		//System.out.println();
		//System.out.println("Command 1: JOBN");
	
		scheduleJob = true;
	
		//Find cores required for job
		jobCore = Integer.parseInt(strArr[4]);
		jobID++;
	
		//System.out.println("JobCore = " + jobCore);
		//System.out.println("JobID = " + jobID);
	
		//Find capable servers
		inputString = "GETS Capable " + strArr[4] +" " + strArr[5] +" " + strArr[6];
	
		//Reply to job
		//System.out.println("Job reply to server: " + inputString);
	
		//Send message to server
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
	
		//Receive message from server 
		outputString=in.readLine();  
	
		//Print message received from server
		//System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
	
		//Assign next job
		currentJob = strArr[0];
	}
	if(currentJob.equals("DATA")) {
		//System.out.println();
		//System.out.println("Command 2: DATA");
		
		int noRecords = Integer.parseInt(strArr[1]);
		int recLength = Integer.parseInt(strArr[2]);
		
		

		for(int i = 0; i < noRecords; i++) {
		//write out all available servers
		inputString = "OK";
		//System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		//System.out.println("-----Loop: " + i + "-----");
		outputString=in.readLine();  
		//System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
		
		//serverName, serverID, serverStatus, serverStatusID, serverCore, serverSpace
		//tiny 0 inactive -1 1 4000 32000 0 0
		//Determining individual cores and server id and names
		serverCore = Integer.parseInt(strArr[4]);
		//System.out.println("This server '" + strArr[0] + "' has: " + serverCore + "cores");
		
		//Determining best fit
		int bestFitValue = 0;
		int comparisonValue = 0;
		//initialise bf to first server
		if (i == 0) {
			bestFitValue = serverCore - jobCore;
			serverName = strArr[0];
			serverID = strArr[1];
		}
		 
		int compare = serverCore - jobCore;
		comparisonValue = Math.abs(compare);
		//System.out.println("Current Best Fit Value: " + comparisonValue);
		//System.out.println("Best Fit Value to beat: " + bestFitValue);


		//check if next server is better fit
		if (comparisonValue < bestFitValue) {
			serverName = strArr[0];
			serverID = strArr[1];
			bestFitValue = comparisonValue;
			}
		} //end of printing all servers loop
		
		//finish listing server information, proceed to next step
		inputString = "OK";
		//System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		//System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
	}
	if(currentJob.equals(".")) {
		//System.out.println();
		//System.out.println("Command 3: .");
	//Check if there is a job to schedule, if so then schedule job
		if(scheduleJob) {
			inputString = "SCHD " + jobID + " " + serverName + " " + serverID;
			//System.out.println("Job reply to server: " + inputString);
			//System.out.println(jobID);
			//System.out.println(serverName);
			//System.out.println(serverID);
			
			dout.write((inputString+"\n").getBytes()); 
			dout.flush();
						//System.out.println("!");
			outputString=in.readLine();  
						//System.out.println("!2");
			//System.out.println("server says: " + outputString);
			
			while(!outputString.equals("OK")) {
			//System.out.println("null?");
			outputString=in.readLine();
			//System.out.println("null?778");
			//System.out.println("server says: " + outputString);
			}
			//System.out.println("server says: " + outputString);
			inputString = "REDY";
			dout.write((inputString+"\n").getBytes()); 
			//System.out.println("Job reply to server: " + inputString);
			dout.flush();
			//System.out.println("!");
			outputString=in.readLine();
			//System.out.println("2");			
			



			
			inputString = "REDY";
			//System.out.println("Job reply to server: " + inputString);
			dout.write((inputString+"\n").getBytes()); 
			dout.flush();
			outputString=in.readLine();  
			//System.out.println("Server says: "+outputString); 
			strArr = outputString.split("\\s+");
			currentJob = strArr[0];
			scheduleJob = false;
			//currentJob = "NONE";
		}
	
	}
	if(currentJob.equals("JCPL")) {
		inputString = "REDY";
		//System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		//System.out.println("Server says: "+outputString); 
		
		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
	
	}
}
	
	
	

//System.out.println("outside while loop");



//Quit/Exit simulation
inputString="QUIT";
//Send message to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();
//Receive message from server 
outputString=in.readLine();  
//Print message received from server
//System.out.println("Server says: "+outputString);  
dout.close();  
s.close();  
}
} 
