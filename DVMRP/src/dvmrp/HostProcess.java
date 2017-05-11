package dvmrp;
import java.io.File;
import java.io.RandomAccessFile;
import java.sql.Date;

public class HostProcess implements Runnable{
	
	int hostId;
	int lan_id;
	String type;
	int start_time;
	int period;
	long prev_len=0;


	public HostProcess(int host_id, int lan_id, String type, int start_time, int period) {
		super();
		this.hostId = host_id;
		this.lan_id = lan_id;
		this.type = type;
		this.start_time = start_time;
		this.period = period;
	}

	public void run() {
		
          try{
        	  long now = (System.currentTimeMillis());
        	  boolean flag = true;
        	  RandomAccessFile hout = new RandomAccessFile("hout"+hostId,"rw");
        	  RandomAccessFile hin = new RandomAccessFile("hin"+hostId,"rw");
        	  RandomAccessFile lanfile = new RandomAccessFile("lan"+lan_id,"rw");
        	 if(type.equals("receiver")){
       		  hout.writeBytes("receiver "+hostId+"\n");
       		  int k=0;
        		 while(flag){
        		  Thread.sleep(1000);k++;
        		  if(k%10==0){
        		  hout.writeBytes("receiver "+hostId+"\n");
        		  }
        		  long len = lanfile.length();
  				
  				if(prev_len!=len){
  					lanfile.seek(prev_len);
  					while(lanfile.getFilePointer()!=len){
  						String line = lanfile.readLine();
  						if(line.contains("data")){
  							//System.out.println(line);
  							hin.writeBytes(line+"\n");
  						}
  					}
  				}
  				
  				  prev_len=len;
        		  int ct = (int) ((System.currentTimeMillis()-now)/1000);
        		  System.out.println("current second: "+ct+"s");
        		  if(ct>=100)flag=false;
        		     		  
        	  }

        	 }
        	 if(type.equals("sender")){
        	  
       		  Thread.sleep(start_time*1000);
        	  hout.writeBytes("data "+lan_id+" "+hostId+"\n"); 
        	  
        	  while(flag){
                  hout.seek(hout.length());
            	  Thread.sleep(period*1000);
        		  hout.writeBytes("data "+lan_id+" "+hostId+"\n");        	  
            	  if(System.currentTimeMillis()-now>=100000)flag=false;
            	  
        	  }
        	 }
          
          }
         catch(Exception e){
        	 
        	 System.out.println(e);
         }
		
		
	}

}
