
import java.util.*;


public class RoundRobin {
    int timeQuantum;
    int remProcesses;
    int contextSwitchTime;
    ArrayList<Process> processesList = new ArrayList<Process>();
    ArrayList<Process> ganttChart = new ArrayList<Process>();
    Queue<Process> RRqueue = new LinkedList<Process>();
    boolean isItSwitchingQuestionMark;
    double averageWaitingTime =0.0;
    double averageTurnAroundTime=0.0;
    int timer=0;
    
    
    public RoundRobin(ArrayList<Process> proc,int RRTimeQuantum,int ContextSwitching,int numofproc) {
		this.processesList=proc;
		timeQuantum=RRTimeQuantum;
		contextSwitchTime=ContextSwitching;
		remProcesses=numofproc;
		}

    public static class sortByArrivalTime implements Comparator<Process> {

        @Override
        public int compare(Process o1, Process o2) {
            return o1.getArrivalTime() - o2.getArrivalTime();

        }
    }
    //Take processes, arranges them in queue according to their arrival time.
    void SortProcess(ArrayList<Process> processesList){
    	this.processesList=processesList;
        Collections.sort(processesList, new RoundRobin.sortByArrivalTime());

    }
    void execute(){
        ///start process, look at processTable w get
        int contextSwitchTimer=0;
        int quantumTimer=0;
        ///A process to placed to the back of the queue if its quantum ended,
        Process prevProcess = null;
        // it will not be directly added, so we can check if new process arrived at that same moment
        remProcesses =processesList.size();
        for (timer = 0; remProcesses >0 ; timer++) {
            ///to handle the case of contextSwitching time
            if(isItSwitchingQuestionMark==true){
                contextSwitchTimer++;
                if(contextSwitchTimer>=contextSwitchTime){
                    isItSwitchingQuestionMark=false;
                    contextSwitchTimer=0;
                }
                else{
                    continue;
                }
            }
//if process arrived, add it to queue
            for (int i = 0; i < processesList.size(); i++) {
                Process p = processesList.get(i);
                if(p.arrivalTime==timer){
                    RRqueue.add(p);
                }
            }
            if(prevProcess!=null){
                RRqueue.add(prevProcess);
                prevProcess=null;
            }
            if(RRqueue.size()>0) {
                ///fetch process
                Process p = RRqueue.peek();
                //check if the same process is being executed
                if(ganttChart.size()==0 || ganttChart.get(ganttChart.size()-1)!=p){
                    ganttChart.add(p);
                }
                p.curBurstTime--;
                quantumTimer++;
                if(p.curBurstTime==0){  //process ended
                    p.TurnAroundTime=(timer+1) - p.arrivalTime;
                    p.WaitingTime=p.TurnAroundTime - p.burstTime;
                    ///check if there is a process waiting in any queue
                    if(RRqueue.size()>0){
                        if(contextSwitchTime>0){
                            isItSwitchingQuestionMark=true;
                        }
                    }
                    quantumTimer=0;
                    RRqueue.poll();
                    prevProcess = null;
                    remProcesses--;
                    continue;
                }
                //quantum time ended and process didn't end
                else if (timer > 0 && quantumTimer % timeQuantum == 0) {
                    //It is not the only process in the queue
                    if(RRqueue.size()>1){
                        prevProcess = p;
                        RRqueue.poll();
                        isItSwitchingQuestionMark=true;
                        continue;
                    }
                }
            }
            continue;
        }
    }


    void print(){
        ///process Execution order
        System.out.print("Gantt Chart: ");
        for (int i = 0; i < ganttChart.size(); i++) {
            System.out.print(ganttChart.get(i).ProcName + " ");
        }
        System.out.println();

        ///Waiting Time for each Process
        System.out.println("Process Name   Waiting Time   Turnaround Time");
        for (int i = 0; i < processesList.size(); i++) {
            Process p = processesList.get(i);
            averageTurnAroundTime+=p.TurnAroundTime;
            averageWaitingTime+=p.WaitingTime;
            System.out.println(" "+p.ProcName+"\t\t\t\t\t"+p.WaitingTime+"\t\t\t\t"+p.TurnAroundTime);
        }

        ///calculate Average turn around time and average waiting time
        averageTurnAroundTime = averageTurnAroundTime / processesList.size();
        averageWaitingTime = averageWaitingTime / processesList.size();

        System.out.println("Average Turnaround Time is: " + averageTurnAroundTime);
        System.out.println("Average WaitingTime is: " + averageWaitingTime);
    }


void run(){
	SortProcess(processesList);
    //take process and run it, all processes came at same time
    execute();
    print();
}}
