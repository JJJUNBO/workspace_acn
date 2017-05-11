package dvmrp;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class open_host implements Runnable{
    int host_id;
    long prev_len = 0;
    


	public open_host(int host_id) {
		super();
		this.host_id = host_id;
	}




	public void run() {
		
			try {
	        	  RandomAccessFile hout = new RandomAccessFile("hout"+host_id,"rw");
	        	  long now = (System.currentTimeMillis());
	        	  boolean flag = true;
                  while(flag){
                	  long len = hout.length();
                	  if(len==prev_len){
                		 // System.out.println("continue");
                		  continue;  
                	  }
                	  else{
                		  hout.seek(prev_len);
                		  
                		  while(hout.getFilePointer()!=len){
                			  
                			  String line = hout.readLine();
                			  if(line.contains("receiver")){
                				  int lan_id = Integer.parseInt(line.substring(line.indexOf(" ")+1));
                				  RandomAccessFile lanfile = new RandomAccessFile("lan"+lan_id,"rw");
                				  lanfile.seek(lanfile.length());
                				  lanfile.writeBytes(line+"\n");
                				  System.out.println(line);
                			  
                			  }
                			  else{
                				  int lan_id = Integer.parseInt(line.substring(line.indexOf(" ")+1,line.indexOf(" ")+2));
                				  RandomAccessFile lanfile = new RandomAccessFile("lan"+lan_id,"rw");
                				  lanfile.seek(lanfile.length());
                				  lanfile.writeBytes(line+"\n");
                				  System.out.println(line);
                			  
                		  }
                	  }
                  }
                  
                	  int ct = (int) ((System.currentTimeMillis()-now)/1000);
            		  System.out.println("current second: "+ct+"s");
            		  if(ct>=100)flag=false;
            		  prev_len=len;
			} 
			}
			    catch (Exception e) {
			    	System.out.println(e);
				
			}
			
		}
		
	}


