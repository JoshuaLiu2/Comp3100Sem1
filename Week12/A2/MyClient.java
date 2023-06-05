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
 
//Create greeting message to Server
inputString="HELO";
//Send to server
dout.write((inputString+"\n").getBytes());  
dout.flush();

//Acknowledgement from Server
outputString=in.readLine();  
//Print message received from server
System.out.println("Server says: "+outputString);  



//Create Authentication message to Server
inputString="AUTH " + userName;
//Send to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();

//Receive message from server 
outputString=in.readLine();  
//Print message received from server
System.out.println("Server says: "+outputString);    



//Client is 'Ready' for simulation
inputString="REDY";
//Send to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();

//Receive message from server 
outputString=in.readLine();  
//Print message received from server
System.out.println("Server says: "+outputString);  
  
  
  
//Splitting of given JOB
//System.out.println("JOB = " + outputString);
String[] strArr = outputString.split("\\s+");
/* 
System.out.println(strArr[0]);
System.out.println(strArr[1]);
System.out.println(strArr[2]);
System.out.println(strArr[3]);
System.out.println(strArr[4]);
System.out.println(strArr[5]);
System.out.println(strArr[6]);
*/
System.out.println("-------------");
//Check if NONE jobs available
String currentJob = strArr[0];
//System.out.println(currentJob);

//variables used for scheduling jobs
int jobID = -1;
int jobCore = 0;
String serverID = "";
int serverCore = 0;
String serverName = "";

//Loop scheduling of jobs
while (!currentJob.equals("NONE")) {
	//System.out.println("inside while loop");
	//System.out.println("Current job: " + currentJob);
	if(currentJob.equals("JOBN")) {
		System.out.println("inside jobn");
		//set cores required for job 
		jobCore = Integer.parseInt(strArr[4]);
		jobID++;
		System.out.println("JobCores = " + jobCore);
		System.out.println("JobID = " + jobID);
		inputString = "GETS Capable " + strArr[4] +" " + strArr[5] +" " + strArr[6];
		//Reply to job
		System.out.println("Job reply to server: " + inputString);
		//Send message to server
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		//Receive message from server 
		outputString=in.readLine();  
		//Print message received from server
		System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
	}
	if(currentJob.equals("DATA")) {
		System.out.println("inside data");
		
		int noRecords = Integer.parseInt(strArr[1]);
		int recLength = Integer.parseInt(strArr[2]);
		

		//write out all available servers
		for(int i = 0; i < noRecords; i++) {
		//Print each seperate line for available servers
		inputString = "OK";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		System.out.println("-----Loop: " + i + "-----");
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
		
		//Determining individual cores and server id and names
		serverCore = Integer.parseInt(strArr[4]);
		//String cores = strArr[4];
		//System.out.println("This server is: " + strArr[1] + "." + strArr[0]);
		//System.out.println("This server has: " + cores + " cores.");
		System.out.println("This server has: " + serverCore + "cores");
		
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
		System.out.println("Best Fit Value to beat: " + bestFitValue);
		System.out.println("Current Best Fit Value: " + comparisonValue);

		//check if next server is better fit
		if (comparisonValue < bestFitValue) {
			serverName = strArr[0];
			serverID = strArr[1];
			bestFitValue = comparisonValue;
			}
		}
 		inputString = "OK";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString); 
		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
		
	}
	if(currentJob.equals("JCPL")) {
		inputString = "REDY";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString); 
		
		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
	}
	if(currentJob.equals(".")) {
		inputString = "SCHD " + jobID + " " + serverName + " " + serverID;
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString);

		inputString = "OK";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString); 
		
		strArr = outputString.split("\\s+");
		//currentJob = strArr[0];
		currentJob = "NONE";
	}
	
	
	
	//System.out.println("outside if statements");
}
System.out.println("outside while loop");



//Quit/Exit simulation
inputString="QUIT";
//Send message to server
dout.write((inputString+"\n").getBytes()); 
dout.flush();
//Receive message from server 
outputString=in.readLine();  
//Print message received from server
System.out.println("Server says: "+outputString);  
dout.close();  
s.close();  
}
} 
