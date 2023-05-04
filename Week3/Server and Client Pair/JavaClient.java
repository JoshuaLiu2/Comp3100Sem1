import java.net.*;  
import java.io.*;  
class JavaClient{  
public static void main(String args[])throws Exception{  
Socket s=new Socket("localhost",3333);  
//DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));  
  
String str="",str2="";  
//Create message to send to server
str="HELO";
//Send message to server
dout.write((str+"\n").getBytes());  
dout.flush();
//Receive message from server 
str2=in.readLine();  
//Print message received from server
System.out.println("Server says: "+str2);  
  
//Create message to send to server
str="BYE";
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
