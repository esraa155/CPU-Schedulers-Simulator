

public class Process {
	public  int arrivalTime;
	public int burstTime;
	public String ProcName;
	public int TurnAroundTime;
	public int WaitingTime;
	public int curBurstTime;
	public int p_perioryty;
	public int QuantumTime;
	int completionTime;
	

	public Process() {}
	
	public Process(String name,int arrvtime,int bursttime,int p,int q) {
		ProcName=name;
		arrivalTime=arrvtime;
		burstTime=bursttime;
		p_perioryty=p;
		QuantumTime=q;
	}
	public int compareTo(Process o) {
        {
            if(this.arrivalTime < o.arrivalTime){
                return  1;
            }
            else if(this.arrivalTime == o.arrivalTime)
            {
                return  0;
            }
            else{
                return -1;
            }
        }
    }
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getBurstTime() {
		return burstTime;
	}
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	public String getProcName() {
		return ProcName;
	}
	public void setProcName(String procName) {
		ProcName = procName;
	}
	public void setTurnAroundTime(int turnAroundTime) {
		this.TurnAroundTime = turnAroundTime;
	}
	public int getTurnAroundTime() {
		return TurnAroundTime;
	}

	public int getWaitingTime() {
		return WaitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		WaitingTime = waitingTime;
	}
	
	public void setName(String Name) {
		this.ProcName = Name;
	}
	public String getName() {
		return ProcName;
	}
	public int getPriority() {
	    return p_perioryty;
	}
	public void setPriority(int priority) {
	    this.p_perioryty = priority;
	}
	public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }
	 public String toString() {
	        return ProcName + " :  " + " WaitingTime=" + WaitingTime + ",  turnaroundTime=" + TurnAroundTime;
	    }
	

}