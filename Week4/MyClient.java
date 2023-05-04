import java.net.*;  
import java.io.*;  
class MyClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("127.0.0.1",50000);  
//DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));  
 
String userName = System.getProperty("user.name");
String str="",str2="";  
//Greet Server
str="HELO";
//Send message to server
dout.write((str+"\n").getBytes());  
dout.flush();
//Acknowledgement from Server
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);  
  
//Send Authentication to Server
str="AUTH " + userName;
//Send message to server
dout.write((str+"\n").getBytes()); 
dout.flush();
//Receive message from server 
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);    

//Client is 'Ready' for simulation
str="REDY";
//Send message to server
dout.write((str+"\n").getBytes()); 
dout.flush();
//Receive message from server 
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);  
  
//Loop to do Job(s) here!
System.out.println("JOB = " + str2);
String[] strArr = str2.split("\\s+");
/* Splitting of given JOB
System.out.println(strArr[0]);
System.out.println(strArr[1]);
System.out.println(strArr[2]);
System.out.println(strArr[3]);
System.out.println(strArr[4]);
System.out.println(strArr[5]);
System.out.println(strArr[6]);
*/

//Reply to job
str = "GETS All " + strArr[4] +" " + strArr[5] +" " + strArr[6];
System.out.println("Job reply to server: " + str);
//Send message to server
dout.write((str+"\n").getBytes()); 
dout.flush();
//Receive message from server 
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);  


//Quit/Exit simulation
str="OK";
//Send message to server
dout.write((str+"\n").getBytes()); 
dout.flush();
//Receive message from server 
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);  
dout.close();  
s.close();  
}
} 
