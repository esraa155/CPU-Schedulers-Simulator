
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int ProcNum,RRQuantumtime,ContextSwitching;
		Scanner input=new Scanner(System.in);
		System.out.println("Enter number of processes:");
		ProcNum=input.nextInt();
		System.out.println("Enter Round robin Time Quantum: ");
		RRQuantumtime=input.nextInt();
		System.out.println("Enter Context Switching:");
		ContextSwitching=input.nextInt();
		ArrayList<Process> processes = new ArrayList<>();
		
		 for(int i=0;i<ProcNum;i++) {
			    Process p;
			    String Name;
				int ArrivalTime;
				int BurstTime;
				int priority1;
			    System.out.println("Enter Process "+(i+1)+ " Name:");
			    Name=input.next();
				System.out.println("Enter Process "+(i+1)+" ArrivalTime: ");
				ArrivalTime=input.nextInt();
				System.out.println("Enter Process "+(i+1)+ " BurstTime:");
				BurstTime=input.nextInt();
				System.out.println("Enter Process "+(i+1)+ " priority :");
				priority1=input.nextInt();
				System.out.println("quantum Process "+(i+1)+ " time : ");
				int Qstr = input.nextInt();
			    p=new Process(Name,ArrivalTime,BurstTime,priority1, Qstr); 
			    processes.add(p);
			    
		   }

		while(true) {
		     System.out.println("Choose a Schedule");
		     System.out.println("1.	preemptive Shortest- Job  First (SJF) Scheduling  with context switching ");
		     System.out.println("2.	Round Robin (RR) with context switching");
		     System.out.println("3.	preemptive  Priority Scheduling with starvation solution");
		     System.out.println("4.	AG  Scheduling ");
		     System.out.println("5.	Exit ");
		 	int schedulenum = input.nextInt(); 
		
		   switch(schedulenum) {
		   case 1:
			   SRTF srtf=new SRTF(processes, ContextSwitching);
			   srtf.Run();
			   continue;
		   case 2:
			   RoundRobin RR=new RoundRobin(processes,RRQuantumtime,ContextSwitching,ProcNum);
			   RR.run();
			   continue;
			   
		   case 3:
			   Priority prr = new Priority(processes);
			   prr.excute();
			   System.out.println();
			   continue;
		   case 4:
			  

				
				ArrayList<String> PN = new ArrayList<String>();
				ArrayList<Integer> AT = new ArrayList<Integer>();
				ArrayList<Integer> BT = new ArrayList<Integer>();
				ArrayList<Integer> P = new ArrayList<Integer>();
				ArrayList<Integer> Q = new ArrayList<Integer>();
				for(int i=0;i<ProcNum;i++) {
				    PN.add(processes.get(i).getName());
				    AT.add(processes.get(i).getArrivalTime());
				    BT.add(processes.get(i).getBurstTime());
				    P.add(processes.get(i).p_perioryty);
				    Q.add(processes.get(i).QuantumTime);
				    
			   }
				
				

				  AG ag = new AG(AT,BT,P,Q,PN,ProcNum);
				   ag.AGSchdeuling();
				   System.out.println("Order  : ");ag.printarrstr(ag.ProcessesOrder);
				   System.out.println();
				   System.out.println("WaitingTime  : ");ag.printarrint(ag.WaitingTime);
				   System.out.println();
				   System.out.println("turnaroundtime  : ");ag.printarrint(ag.turnaroundtime);
				   System.out.println();
				   System.out.println("averageWaitingTime  : ");System.out.println(ag.averagewaitingtime);
				   System.out.println();
				   System.out.println("averageturnaroundtime  : ");System.out.println(ag.averageturnaroundtime);
				   System.out.println();
				   ag.printtable(ag.QuantumHistory);
				   continue;
	    
		   case 5:
			   break;
		

	}

}}}