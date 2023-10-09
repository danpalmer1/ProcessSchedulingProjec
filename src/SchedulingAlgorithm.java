import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class SchedulingAlgorithm {
    
	protected String name;		      //scheduling algorithm name
	protected List<Process> procs;	//the initial list of processes
	protected List<Process> readyQueue;	//queue of processes ready for cpu
	protected List<Process> ioReadyQueue; //queue of processes ready for io device
	protected List<Process> finishedProcs;	//list of terminated processes
	protected Process curProcess; //current selected process by the scheduler
	protected int systemTime; //system time or simulation time steps
 
      public SchedulingAlgorithm(String name, List<Process> queue) {
    	      this.name = name;
    	      this.procs = queue;
    	      this.readyQueue = new ArrayList<>();
    	      this.finishedProcs = new ArrayList<>();
			  this.ioReadyQueue = new ArrayList<>();
      }
	
	public void schedule() {
		System.out.println("Scheduler: " + name);
		//while there are processes left
		while(systemTime < 20) {
			System.out.println("System time: " + systemTime + " ");
			//iterate thru untouched processes
			for(Process proc : procs) {
				//if process arrives 
				if(proc.getArrivalTime() == systemTime) {
					readyQueue.add(proc); //add to ready for cpu queue
					proc.setState("READY"); //set state to ready 
				}
			}
			procs.removeAll(readyQueue);
			//iterate thru readyQueue to check for finished bursts
			for(Process proc : readyQueue) {
				if(proc.getCPUBurstList().get(proc.getCurrentBurstIndex()) == 0) {
					//if current index > io burst list size, we've finished the last cpu burst
					if(proc.getCurrentBurstIndex() > proc.getIOBurstList().size()-1) {
						proc.setFinishTime(systemTime);
						proc.setState("TERMINATED");
						finishedProcs.add(proc); //add to finished processes
					} else {
						proc.setState("WAITING");
						ioReadyQueue.add(proc); //add to io ready queue
					}
					readyQueue.remove(proc); //remove process from ready queue
				}
			}
			//iterate thru io readyQueue to check for finished io bursts
			for(Process proc : ioReadyQueue) 
				if(proc.getIOBurstList().get(proc.getCurrentBurstIndex()) == 0) {
					proc.setState("READY");
					readyQueue.add(proc); //add to cpu queue
					ioReadyQueue.remove(proc); //remove from io queue
				}
			System.out.println(readyQueue);
			/*
			 * TODO:
			 * - pick a processes depending on the algorithm
			 */

			 if(!readyQueue.isEmpty()) {
				curProcess = pickNextProcess();
	//		    - call print() to print simulation events: CPU, ready queue, ..
				print();
	//		    - update the start time of the selected process (curProcess)
				if(curProcess.getStartTime() < 0) curProcess.setStartTime(systemTime);
	//		    - Call CPU.execute() to let the CPU execute 1 CPU burst unit time of curProcess
				CPU.execute(curProcess, 1);
	//		    - Increase 1 to the waiting time of other processes in the ready queue
				for(Process other : readyQueue)
					if(other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
	//		    - Increase systemTime by 1
				systemTime++;
	//		    - Check if the remaining CPU burst of curProcess = 0
				if(curProcess.getState() == "TERMINATED") { //curProcess finished 
					
	//		        - Update finishTime of curProcess
					curProcess.setFinishTime(systemTime);
	//		        - remove curProcess from readyQueue
					readyQueue.remove(curProcess);
	//		        - add curProcess to the finished queue (finishedProcs)
					finishedProcs.add(curProcess);
	//		        - Print to console a message displaying process name, terminated time, 
	//		                         startTime, turnaroundTime, waitingTime
					System.out.println("Process " + curProcess.getName() + " finished at " + systemTime
							+ ", TAT = " + curProcess.getTurnaroundTime() 
							+ ", WAT: " + curProcess.getWaitTime());
				}
			} else {
			systemTime++;
			}
		}
	}
	
	//Selects the next task using the appropriate scheduling algorithm
      public abstract Process pickNextProcess();

      //print simulation step
      public void print() {
		System.out.println("CPU: " + curProcess == null ? " idle " : curProcess.toString()); 
		System.out.println("Ready queue: [");
		for(Process proc : readyQueue)
			System.out.println(proc.getName() + " ");
		System.out.println("]");
      }
}
