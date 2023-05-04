import java.net.*;  
import java.io.*;  
class JavaServer{  
public static void main(String args[])throws Exception{  
ServerSocket ss=new ServerSocket(3333);  
Socket s=ss.accept();  
//DataInputStream din=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));  

String str="",str2="";  

//Receive message from Client
str=in.readLine(); 
//Print message from Client 
System.out.println("client says: "+str);  
//Create message
str2="g'DAY";
//Send message to Client
dout.write((str2+"\n").getBytes());  
dout.flush();  

//Receive message from Client
str=in.readLine(); 
//Print message from Client 
System.out.println("client says: "+str);  
//Create message
str2="BYE";
//Send message to Client
dout.write((str2+"\n").getBytes());  
dout.flush();  


in.close();  
s.close();  
ss.close();  
}}  
