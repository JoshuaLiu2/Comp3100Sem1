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


//Loop scheduling of jobs
while (!currentJob.equals("NONE")) {
	System.out.println("inside while loop");
	//System.out.println("Current job: " + currentJob);
	if(currentJob.equals("JOBN")) {
		System.out.println("inside jobn");
		
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
		System.out.println("-----");
		System.out.println(noRecords);
		System.out.println(recLength);
			
		inputString = "OK";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();
		for(int i = 0; i < noRecords; i++) {
		//first fit
			if (i = 0) {
			
			System.out.println(i);
			outputString=in.readLine();  
			System.out.println("Server says: "+outputString); 
		}
 		inputString = "OK";
		System.out.println("Job reply to server: " + inputString);
		dout.write((inputString+"\n").getBytes()); 
		dout.flush();

		strArr = outputString.split("\\s+");
		currentJob = strArr[0];
		currentJob = "NONE";
		
		//inputString = "OK";
		//System.out.println("Job reply to server: " + inputString);
		//dout.write((inputString+"\n").getBytes()); 
		//dout.flush();
		outputString=in.readLine();  
		System.out.println("Server says: "+outputString);  
		
		currentJob = "NONE";
	}
	System.out.println("outside if statements");
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
