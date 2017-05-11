package dvmrp;
import java.util.Scanner;

public class Command {
	
	public static void main(String[] args) {		
		Scanner scanner = new Scanner(System.in).useDelimiter("&");
		String userCommand;
		String commandtype;
		do{
			System.out.print("command:");		    
			userCommand = scanner.next();
			{
			 if(userCommand.contains("host")&&!userCommand.contains("controller"))createhost(userCommand);
			 else if(userCommand.contains("router")&&!userCommand.contains("controller"))createrouter(userCommand);
			 else if(userCommand.contains("controller"))createcontroller(userCommand);
			 else System.out.println("I didn't understand the command: \"" + userCommand + "\"");
			}
			
		}while(!userCommand.contains("controller"));
	}

	private static void createcontroller(String userCommand) {
		int start = userCommand.indexOf(" ");
		int start_2 = userCommand.indexOf("router");
		String[] host_s = userCommand.substring(start+6,start_2).split(" ");		
		int host[] =  new int[host_s.length];
		int i=0;
		for(String x: host_s){
			host[i] = Integer.parseInt(x);
			i++;
		}
		int start_3 = userCommand.indexOf("lan");
		String[] router_s = userCommand.substring(start_2+7,start_3).split(" ");
		int router[] =  new int[router_s.length];
		i=0;
		for(String x: router_s){
			router[i] = Integer.parseInt(x);
			i++;
		}
        String lan_s[] = userCommand.substring(start_3+4).split(" ");
        int lan[] =  new int[lan_s.length];
		i=0;
		for(String x: lan_s){
			lan[i] = Integer.parseInt(x);
			i++;
		}
		controller controller = new controller(host,router,lan);
		new Thread(controller).start();
	}

	private static void createrouter(String userCommand) {
		int start = userCommand.indexOf(" ");
		int router_id = Integer.parseInt(userCommand.substring(start+1,start+2));
		String[] lan_s = userCommand.substring(start+3).split(" ");
		int lan[] =  new int[lan_s.length];
		int i=0;
		for(String x: lan_s){
			lan[i] = Integer.parseInt(x);
			i++;
		}
		router_process routerX = new router_process(router_id,lan);
		new Thread(routerX).start();
		
	}

	private static void createhost(String userCommand) {
	    int start = userCommand.indexOf(" ");
		int host_id = Integer.parseInt(userCommand.substring(start+1,start+2));
		int lan_id = Integer.parseInt(userCommand.substring(start+1,start+2));
		int start_time = 0;
		int period = 0;
		String type;
		if(userCommand.contains("sender")){
			type="sender";
			start_time= Integer.parseInt(userCommand.substring(userCommand.indexOf("sender")+7, userCommand.indexOf("sender")+9));
			period= Integer.parseInt(userCommand.substring(userCommand.indexOf("sender")+10, userCommand.indexOf("sender")+12));
		}
		else type = "receiver";
	    HostProcess hostX =  new HostProcess(host_id,lan_id,type,start_time,period);
		new Thread(hostX).start();
		
	}

}
