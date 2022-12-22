
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class Priority {
   public ArrayList<Process> all=new ArrayList<>(),ready=new ArrayList<>(),tempAll=new ArrayList<>();
   public ArrayList<String> printingOrder=new ArrayList<>();
   public int curTime,maxTime=100;
   public Priority(ArrayList<Process>in){
        curTime=0;
        for(int i=0;i<in.size();i++) {
            all.add(in.get(i));
            tempAll.add(in.get(i));
            tempAll.get(i).setWaitingTime(-1*in.get(i).getBurstTime());
            tempAll.get(i).setTurnAroundTime(in.get(i).getBurstTime());
        }
        Collections.sort(all, Comparator.comparing(Process::getArrivalTime));
    }
    public void excute(){
        while(curTime<maxTime){
            while(all.size()>0&&all.get(0).getArrivalTime()<=curTime){
                ready.add(all.get(0));
                all.remove(0);
            }
            Collections.sort(ready, Comparator.comparing(Process::getPriority));
            if(ready.size()==0)
                break;
            int remTime=ready.get(0).getBurstTime();
            printingOrder.add(ready.get(0).getName());
            if(remTime>1)
                ready.get(0).setBurstTime(remTime-1);
            else{
                ready.get(0).setCompletionTime(curTime+1);
                ready.get(0).setWaitingTime(
                ready.get(0).getCompletionTime()-ready.get(0).getArrivalTime()+ready.get(0).getWaitingTime());
                ready.get(0).setBurstTime(ready.get(0).getTurnAroundTime());
                ready.get(0).setTurnAroundTime(ready.get(0).getWaitingTime()+ready.get(0).getTurnAroundTime());
                ready.remove(0);
            }
            curTime++;
        }
        print();
    }
    public void print(){
        System.out.println("Process execution order: ");
        for(int i=0;i<printingOrder.size();i++)
            System.out.println("from "+i+" to "+(i+1)+" executing "+printingOrder.get(i));
        System.out.println("------------------------------------------------------------" +
                "------------------------------------------------------------");
        System.out.println("Process\t  "+"CompletionTime\t\t"+"TurnAroundTime\t\t"+"Waiting time");
        int avgWaiting = 0, avgTurn = 0;
        for(int i=0;i<tempAll.size();i++) {
            System.out.println(tempAll.get(i).getName() + "\t\t" 
                    +  tempAll.get(i).getCompletionTime() + "\t\t\t      " +
                    tempAll.get(i).getTurnAroundTime() + "\t\t\t    " + tempAll.get(i).getWaitingTime());
            avgWaiting += tempAll.get(i).getWaitingTime();
            avgTurn += tempAll.get(i).getTurnAroundTime();
        }
        System.out.println("Average waiting time:    " + (double) avgWaiting / tempAll.size());
        System.out.println("Average turnaround time: " + (double) avgTurn / tempAll.size());
    }
}
