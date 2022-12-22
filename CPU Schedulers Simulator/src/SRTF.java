
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SRTF {
	private ArrayList<Process>processes;
	private int ContextSwitching;
	private int procsNum;
	private int CurrentTime;
	private int Min;
	private int CurrentProc;
	private ArrayList<Integer>result=new ArrayList<Integer>();
	private ArrayList<Integer>BTList=new ArrayList<Integer>();
	private ArrayList<Integer>Time=new ArrayList<Integer>();
	
	
	public SRTF(ArrayList<Process> proc,int contextswitching) {
		this.processes=proc;
		this.ContextSwitching=contextswitching;
		this.CurrentTime=0;
		this.CurrentProc=0;
		this.Min=Integer.MAX_VALUE;
		SortProcess(processes);//Sort all the Processes Based upon the Arrival Time
		this.procsNum=processes.size();
		SetBurstTimeList(processes);
		Time.add(0);
		}
	
	
   private void SortProcess(ArrayList<Process> proc){
		Collections.sort(proc,Comparator.comparing(Process::getArrivalTime));
		}
	
	
	private void SetBurstTimeList(ArrayList<Process> proc) {
		int i=0;
		while(i<proc.size()) {
			BTList.add(proc.get(i).getBurstTime());
			i++;
		}
		}
	
	private int CalculateWaitingTime(int CurrentTime,ArrayList<Process> proc) {
		return CurrentTime + 1 - proc.get(CurrentProc).getBurstTime() - proc.get(CurrentProc).getArrivalTime();
		
	}
	private int CalculateTurnAroundTime(int CurrentTime,ArrayList<Process> proc) {
		return processes.get(CurrentProc).getWaitingTime() + processes.get(CurrentProc).getBurstTime();
		
	}
	 
	
	public void Run() {
		 boolean bool = false;
	     int loop = 0;
	     int previousProc = 0;
	     while (procsNum > 0) {
	            for (int i = 0; i < processes.size(); i++) {
	                if (processes.get(i).getArrivalTime() <= CurrentTime && BTList.get(i) > 0 && BTList.get(i) < Min) {
	                    Min = BTList.get(i);
	                    CurrentProc = i;
	                    bool = true;
	                }
	            }
	            if (!bool) {
	            	CurrentTime ++;
	                continue;
	            }
	            loop++;
	            if (loop > 1 && previousProc != CurrentProc) {
	            	CurrentTime += ContextSwitching;
	                result.add(previousProc);
	                Time.add(CurrentTime);
	               
	            }
	            BTList.set(CurrentProc, (BTList.get(CurrentProc)) - 1);
	            Min = BTList.get(CurrentProc);
	            
	            if (Min == 0)
	            	Min = Integer.MAX_VALUE;
	            
	            previousProc = CurrentProc;
	            if (BTList.get(CurrentProc) == 0) {
	                procsNum--;
	                bool = false;
	                int newWaitingTime = CalculateWaitingTime(CurrentTime,processes);
	                if (newWaitingTime > 0) {
	                	processes.get(CurrentProc).setWaitingTime(newWaitingTime);}
	                else {
	                	processes.get(CurrentProc).setWaitingTime(0);
	                	}
	                int TAtime= CalculateTurnAroundTime(CurrentTime,processes);
	                processes.get(CurrentProc).setTurnAroundTime(TAtime);
	            }
	            CurrentTime++;
	        }
	        result.add(previousProc);
	        Time.add(CurrentTime);
	        print(result);
		
	}
	
	private void print(ArrayList<Integer> Result) {
		double AvgWaitTime = 0;
        double AvgTurnTime = 0;
        for (int i = 0; i < Result.size(); i++) {
        	System.out.print(" ");
            System.out.print(processes.get(Result.get(i)).getName());
            System.out.print(" |");
        }
        System.out.println();
        
        for (int i = 0; i < Result.size()+1; i++) {
        	if(i>0) {
        	System.out.print("  ");}
        	System.out.print(Time.get(i)); 
            System.out.print("  ");
        }
        System.out.println();
        System.out.println("Process Name   Waiting Time   Turnaround Time");
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            AvgTurnTime+=p.TurnAroundTime;
            AvgWaitTime+=p.WaitingTime;
            System.out.println(" "+p.ProcName+"\t\t\t"+p.WaitingTime+"\t\t"+p.TurnAroundTime);
        }
        System.out.println("Average waiting time: " +  AvgWaitTime/ processes.size());
        System.out.println("Average turnaround time: " + AvgTurnTime / processes.size());
    }
	

	
	
	

}
