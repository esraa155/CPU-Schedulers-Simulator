import java.util.ArrayList;
import java.util.Collections;

public class AG {

	    int arrivaltime;
	    int processnumber;
	    int bursttime;
	    int priority;
	    int quantumtime;
	    int numberofprocesses;
	    ArrayList<Integer> turnaroundtime;
		double averagewaitingtime;
		double averageturnaroundtime;

	  ArrayList<Integer> ArrivalTime;
	  ArrayList<Integer> BurstTime;
	  ArrayList<Integer> Priority;
	  ArrayList<Integer> QuantumTime;
	  ArrayList<ArrayList<Integer>> QuantumHistory;
	  ArrayList<Integer> WaitingTime;
	  ArrayList<String> ProcessName;
	  ArrayList<String> ProcessesOrder=new ArrayList<String>();
	  
	 
	  AG(ArrayList<Integer> ArrivalTime, ArrayList<Integer> BurstTime, ArrayList<Integer> Priority, ArrayList<Integer> QuantumTime,  ArrayList<String> ProcessName,int numberofprocesses)
	  {
	    this.ArrivalTime=ArrivalTime;
	    this.BurstTime=BurstTime;
	    this.Priority=Priority;
	    this.QuantumTime=QuantumTime;
	    this.ProcessName=ProcessName;
	    this.numberofprocesses=numberofprocesses;
	    WaitingTime = new ArrayList<Integer>(Collections.nCopies(numberofprocesses, 0));
	    turnaroundtime = new ArrayList<Integer>(Collections.nCopies(numberofprocesses, 0));

	    QuantumHistory=new ArrayList<ArrayList<Integer>>(numberofprocesses);
	    for(int i=0;i<numberofprocesses;i++)
	    {
	        QuantumHistory.add(new ArrayList<Integer>());
	    }

	  }

	   public void AGSchdeuling()
	   {
	        int Time = ArrivalTime.get(0);
			int activeprocessindex = -1;
			int nextarrivaltime = ArrivalTime.get(1);
			boolean availiable1 = true;
			boolean availiable2  = true;
			boolean availiable3 = true;
			int responsetime = ArrivalTime.get(0);
			int nextprocessidx = 1;
			int remainingtime = BurstTime.get(0);
			int token = 1;
			ArrayList<Integer> remainingtimelist = new ArrayList<Integer>(BurstTime);
			ArrayList<Integer> waitingqueueindex = new ArrayList<Integer>(); 
			ArrayList<Integer> waitingqueueremainingtime= new ArrayList<Integer>(); 
			ArrayList<Integer> waitingqueuepriority = new ArrayList<Integer>(); 
			ArrayList<Integer> remainingquantumtime =  new ArrayList<Integer>(QuantumTime);

			ArrayList<Integer> q1remainingtime = new ArrayList<Integer>();
			ArrayList<Integer> q1waitingqueueindex = new ArrayList<Integer>(); 
			ArrayList<Integer> q1waitingqueuepriority = new ArrayList<Integer>(); 
			ArrayList<Integer> q1remainingquantumtime =  new ArrayList<Integer>();
			
			ArrayList<Integer> q2remainingtime = new ArrayList<Integer>();
			ArrayList<Integer> q2waitingqueueindex = new ArrayList<Integer>(); 
			ArrayList<Integer> q2waitingqueuepriority = new ArrayList<Integer>(); 
			ArrayList<Integer> q2remainingquantumtime =  new ArrayList<Integer>();
			
			ArrayList<Integer> q3remainingtime = new ArrayList<Integer>();
			ArrayList<Integer> q3waitingqueueindex = new ArrayList<Integer>(); 
			ArrayList<Integer> q3waitingqueuepriority = new ArrayList<Integer>(); 
			ArrayList<Integer> q3remainingquantumtime =  new ArrayList<Integer>();
			
			int remainintime=0;
			int prior=0;
			int remainingquantum=0;
			
			int activeidxq1 = 0;
			int activeidxq2 = 0;
			int activeidxq3 = 0;



	        while(true) {
			
				if(checkzeros(remainingtimelist)){
					break;
				}
				
				//check if this process at second queue
	    		if(activeprocessindex!=-1) {
	    		if(0.5*QuantumTime.get(activeprocessindex)>=QuantumTime.get(activeprocessindex)-remainingquantum && QuantumTime.get(activeprocessindex)-remainingquantum > 0.25*QuantumTime.get(activeprocessindex) && (availiable1  == false)) {
	     			    q2remainingtime.add(remainingtime);
	    				q2waitingqueueindex.add(activeprocessindex);
	    				q2waitingqueuepriority.add(prior);
	    				//update quantum 
	    				QuantumTime.set(activeprocessindex,QuantumTime.get(activeprocessindex)+2);
	    				//save it in history 
	    				QuantumHistory.get(activeprocessindex).add(QuantumTime.get(activeprocessindex));
	    				
	    				q2remainingquantumtime.add(QuantumTime.get(activeprocessindex));
	    				activeprocessindex = -1;
	    				availiable1 = true;
	    		}
	    		}

	    		
	    		//check if this process at third queue
	    		if(activeprocessindex!=-1) {
	    		if(0.5*QuantumTime.get(activeprocessindex)<QuantumTime.get(activeprocessindex)-remainingquantum &&(availiable2) == false) {
	 			    
	    			q3remainingtime.add(remainingtime);
					q3waitingqueueindex.add(activeprocessindex);
					q3waitingqueuepriority.add(prior);
					//update quantum
					QuantumTime.set(activeprocessindex,QuantumTime.get(activeprocessindex)+(QuantumTime.get(activeprocessindex)/2) );
					//save it in history 
					QuantumHistory.get(activeprocessindex).add(QuantumTime.get(activeprocessindex));
					
					q3remainingquantumtime.add(QuantumTime.get(activeprocessindex));
					
					availiable2 = true;
					
					activeprocessindex = -1;
					
					
	    		}
	    		}
				
				
				// if a process arrived
	    		for(int i=0;i<numberofprocesses;i++) {
	    			if(activeprocessindex == i) {
	    				continue;
	    			}
	    			if(ArrivalTime.get(i) == Time) {
	    				q1remainingtime.add(BurstTime.get(i));
	    				q1waitingqueueindex.add(i);
	    				q1waitingqueuepriority.add(Priority.get(i));
	    				q1remainingquantumtime.add(QuantumTime.get(i));	
	    			}
	    		}
	    		
	    		//check in everyqueue
	    		//q1
	    		if(!q1waitingqueueindex.isEmpty() || availiable1 == false) {
	    			token = 1;
	    			//check if there is any process working on another queue and save it 
	    			//check q2 
	    			if(availiable2 == false) {
	    				q2remainingtime.add(remainingtime);
	    				q2waitingqueueindex.add(activeprocessindex);
	    				q2waitingqueuepriority.add(prior);
	    				q2remainingquantumtime.add(remainingquantum);	
	    				
	    				availiable2 = true;
	    			}
	    			
	    			//check q3 
	    			if(availiable3 == false){
	        			q3remainingtime.add(remainingtime);
	    				q3waitingqueueindex.add(activeprocessindex);
	    				q3waitingqueuepriority.add(prior);
	    				//update quantum 
	    				QuantumTime.set(activeprocessindex, QuantumTime.get(activeprocessindex)+remainingquantum);
	    				//save update history 
	    				QuantumHistory.get(activeprocessindex).add(QuantumTime.get(activeprocessindex));
	    				//
	    				q3remainingquantumtime.add(QuantumTime.get(activeprocessindex));
	    				
	    				availiable3 = true;
	    			}
	    			
	    			
	    			
	    		}
	    		else {
	    			if(!q2waitingqueueindex.isEmpty() || availiable2 == false) {
	    				token = 2;
	        			//check if there is any process working on another queue and save it 
	        			//check q3 
	        			if(availiable3 == false){
	            			q3remainingtime.add(remainingtime);
	        				q3waitingqueueindex.add(activeprocessindex);
	        				q3waitingqueuepriority.add(prior);
	        				QuantumTime.set(activeprocessindex, QuantumTime.get(activeprocessindex)+remainingquantum);
	        				//save update history 
	        				QuantumHistory.get(activeprocessindex).add(QuantumTime.get(activeprocessindex));
	        				
	        				q3remainingquantumtime.add(QuantumTime.get(activeprocessindex));
	        				
	        				availiable3 = true;
	        			}
	        			
	    			}
	    			else {
	    				if(!q3waitingqueueindex.isEmpty() || availiable3 == false) {
	    					token = 3;
	    				}
	    				else {
	    					token = 0;   
	    				}
	    			}
	    		}	
	    		
	    		    		
	    		if(token == 1) {
	    			//if the process has finished 
	    			if(activeprocessindex!=-1) {
	    	    		if(remainingtime==0) {
	    	    			availiable1 = true;
	    	    			activeprocessindex = -1;
	    	    		}
	    	    		}
	    			
	    			
	                //get one from q1
	        		//if there is no process ongoing 
	        		if(availiable1){
	        			if(!q1waitingqueueindex.isEmpty()) {
	        				remainingtime = q1remainingtime.get(0);
	        				activeprocessindex = q1waitingqueueindex.get(0);
	        				prior = q1waitingqueuepriority.get(0);
	        				remainingquantum = q1remainingquantumtime.get(0);	
	        				
	        				q1remainingtime.remove(0);
	        				q1waitingqueueindex.remove(0);
	        				q1waitingqueuepriority.remove(0);
	        				q1remainingquantumtime.remove(0);		
	        				
	        				availiable1 = false;
	        			}
	        		
	        		}
	        		
	    			
	    		}
	    		
	    		if(token == 2) {
	    			
	    			//if the process has finished 
	        		if(activeprocessindex!=-1){
	        		if(remainingtime==0) {
	        			availiable2 = true;
	        			activeprocessindex = -1;
	        		}
	        		}
	        		
	        		//if there is no process ongoing 
	        		if(availiable2){
	        			if(!q2waitingqueueindex.isEmpty()) {
	        				int minindexpriority = getminidx(q2waitingqueuepriority);
	            			
	        				activeprocessindex = q2waitingqueueindex.get(minindexpriority);
	            			remainingtime = q2remainingtime.get(minindexpriority);
	            			prior = q2waitingqueuepriority.get(minindexpriority);
	        				remainingquantum = q2remainingquantumtime.get(minindexpriority);	


	            			
	        				q2remainingtime.remove(minindexpriority);
	        				q2waitingqueueindex.remove(minindexpriority);
	        				q2waitingqueuepriority.remove(minindexpriority);
	        				q2remainingquantumtime.remove(minindexpriority);	
	        				
	        				availiable2 = false;
	        			}
	        		
	        		}
	        		
	        	
	    		}
	    		
	    		if(token == 3) {
			
					//if process has finshed //or it's ready to get sth
	    		
			    	if(remainingtime == 0 || availiable3 == true) {
			    		if(!q3waitingqueueindex.isEmpty()) {
			    			
			    			//get one from waiting queue
			    			int idxhpro = getminidx(q3remainingtime);
			    			activeprocessindex = q3waitingqueueindex.get(idxhpro);
			    			remainingtime = q3remainingtime.get(idxhpro);
			    			prior = q3waitingqueuepriority.get(idxhpro);
							remainingquantum = q3remainingquantumtime.get(idxhpro);	
							
							q3remainingtime.remove(idxhpro);
							q3waitingqueueindex.remove(idxhpro);
							q3waitingqueuepriority.remove(idxhpro);
							q3remainingquantumtime.remove(idxhpro);	
							
							availiable3 = false;
			    		    
			    		}
			    		// if it is empty here i am just gonna wait till a process came 
			    		else {
			    			activeprocessindex = -1;
			    			availiable3 = true;
			    		}
			    	}
			    	//if there's still remaining time
			    	else {
			    		availiable3 = false;
			    	}
			    	
			    	/////////////
			    	
			    	//there's a process has a higher priority 
		    		if(activeprocessindex!=-1) {
			    	if(!q3waitingqueueindex.isEmpty()) {
		    			
		    			//get one from waiting queue
		    			int idxhpro1 = getminidx(q3remainingtime);
		    			//current vs waitingqueue
		    			if(Priority.get(activeprocessindex)>q3waitingqueuepriority.get(idxhpro1)) {
		    			int minidx = q3waitingqueueindex.get(idxhpro1);
		    				    			
		    			//add the curr to waiting queue 
	    				q3remainingtime.add(remainingtime);
	    				q3waitingqueueindex.add(activeprocessindex);
	    				q3waitingqueuepriority.add(prior);
	    				q3remainingquantumtime.add(remainingquantum);	
		    			
		    			activeprocessindex = minidx;
		    				    			
		    			//delete it from waiting queue
						q3remainingtime.remove(idxhpro1);
						q3waitingqueueindex.remove(idxhpro1);
						q3waitingqueuepriority.remove(idxhpro1);
						q3remainingquantumtime.remove(idxhpro1);	
						
						availiable3 = false;
		    			}
		    		    
		    		}
					
	    		}
			    	
		    		}

		    		
			        if(activeprocessindex!=-1) { 	 
		   	            remainingtime--;
		   	            remainingquantum--;
		   	            remainingtimelist.set(activeprocessindex, remainingtimelist.get(activeprocessindex)-1);
			    	}
			       
					
		
					////get data 
				if(activeprocessindex!=-1) {
			    	if(ProcessesOrder.isEmpty()) {
	                    ProcessesOrder.add(ProcessName.get(activeprocessindex));
			        	 }
			        	 else {
			        		 if(!ProcessesOrder.get(ProcessesOrder.size()-1).equals(ProcessName.get(activeprocessindex))) {
	                            ProcessesOrder.add(ProcessName.get(activeprocessindex));
			        		 }
			        	 }
			    	}
			        	 for(int i=0;i<numberofprocesses;i++) {
			        		 if((ArrivalTime.get(i) <= Time) && (i!=activeprocessindex) && (remainingtimelist.get(i)!=0)) {
			        			 WaitingTime.set(i, WaitingTime.get(i)+1);
			        		 }
			        	 }
			        	 
			    	Time++;
	    		}
	    		
	    	
	    	        if(activeprocessindex!=-1) { 	 
	   	            remainingtime--;
	   	            remainingquantum--;
	   	            remainingtimelist.set(activeprocessindex, remainingtimelist.get(activeprocessindex)-1);
		    	}
		        
		        
		        
		        
		        
		        //increment time
				Time++;
				
				////get data 
			if(activeprocessindex!=-1) {
		    	if(ProcessesOrder.isEmpty()) {
	                ProcessesOrder.add(ProcessName.get(activeprocessindex));
		        	 }
		        	 else {
		        		 if(!ProcessesOrder.get(ProcessesOrder.size()-1).equals(ProcessName.get(activeprocessindex))) {
	                        ProcessesOrder.add(ProcessName.get(activeprocessindex));
		        		 }
		        	 }
		    	}
		        	 for(int i=0;i<numberofprocesses;i++) {
		        		 if((ArrivalTime.get(i) <= Time) && (i!=activeprocessindex) && (remainingtimelist.get(i)!=0)) {
		        			 WaitingTime.set(i, WaitingTime.get(i)+1);
		        		 }
		        	 }
		        	 
		        	 
		 	    if(activeprocessindex!=-1) { 	 
		    	remainingtimelist.set(activeprocessindex, remainingtimelist.get(activeprocessindex)-1);
		 	    	}
		    	Time++;
		
		
		for(int i=0;i<numberofprocesses;i++) {
			turnaroundtime.set(i, WaitingTime.get(i)+BurstTime.get(i));
		}
		int waitingsum=0;
		int turnaroundtimesum = 0;
		for(int i=0;i<numberofprocesses;i++) {
		      waitingsum+=WaitingTime.get(i);
		      turnaroundtimesum+=turnaroundtime.get(i);
		}
		averagewaitingtime = (double)waitingsum/(double)numberofprocesses;
		averageturnaroundtime = (double)turnaroundtimesum/(double)numberofprocesses;
		
		//////get quantum history 

			}
		
		
		
			boolean checkzeros(ArrayList<Integer> a) {
				if(a.isEmpty()) {
					return false;
				}
				for(int i:a) {
					if(i!=0) {
						return false;
					}
				}
				return true;
			}
			public void printarrstr(ArrayList<String> a) {
				for(int i=0;i<a.size();i++) {
					System.out.print(a.get(i) + "   ");
				}
				System.out.println();
			}
			public void printarrint(ArrayList<Integer> a) {
				for(int i=0;i<a.size();i++) {
					System.out.print(a.get(i) + "   ");
				}
				System.out.println();
			}
			
			public int getminidx(ArrayList<Integer> a){
				int min = 0;
				for(int i=0;i<a.size();i++) {
					if(a.get(i)<a.get(min)){
						min = i;
					}
				}
				return min;
			}
			
			public void printtable(ArrayList<ArrayList<Integer>> mat) {
				for(int i=0;i<mat.size();i++) {
					System.out.print(ProcessName.get(i) + " : ");
					for(int j:mat.get(i)) {
						System.out.print(j + "   ");
					}
					System.out.println();
				}


	}
			}