package dvmrp;
import java.io.RandomAccessFile;

public class router_open implements Runnable{
int router_id;
long prev_len=0;


	public void run() {
		try{
      	  RandomAccessFile rout = new RandomAccessFile("rout"+router_id,"rw");
      	  long now = (System.currentTimeMillis());
      	  boolean flag = true;
      	  while(flag){
      		long len = rout.length();
      	  if(len==prev_len){
      		 // System.out.println("continue");
      		  continue;  
      	  }
      	  else{
      		  rout.seek(prev_len);
      		  prev_len=len;
      		  while(rout.getFilePointer()!=len){
      			String line = rout.readLine();
      			if(line.contains("DV")){
  				  int lan_id = Integer.parseInt(line.substring(line.indexOf(" ")+1,line.indexOf(" ")+2));;
  				  
  				  RandomAccessFile lanfile = new RandomAccessFile("lan"+lan_id,"rw");
  				  lanfile.seek(lanfile.length());
  				  lanfile.writeBytes(line+"\n");
				 
  			  
  			  }
      			else{
      				
      			}
      		  }
      	  }
    	  int ct = (int) ((System.currentTimeMillis()-now)/1000);
		  System.out.println("current second: "+ct+"s");
		  if(ct>=100)flag=false;
      	  }
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}


	public router_open(int router_id) {
		super();
		this.router_id = router_id;
	}

}
