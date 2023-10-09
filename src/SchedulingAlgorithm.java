import java.util.ArrayList;
import java.util.List;

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
      }
	
	public void schedule() {
		System.out.println("Scheduler: " + name);
		//while there are processes left
		while(!procs.isEmpty() || !readyQueue.isEmpty()) {
			System.out.println("System time: " + systemTime + " ");
			//iterate thru untouched processes
			for(Process proc : procs) {
				//if process arrives 
				if(proc.getArrivalTime() == systemTime) {
					readyQueue.add(proc); //add to ready for cpu queue
					proc.setState("READY"); //set state to ready 
				}
			}
			System.out.println("Ready Queue " + readyQueue); //ready queue returning null
			procs.removeAll(readyQueue);
			//iterate thru readyQueue to check for finished bursts
			for(Process proc : readyQueue) {
				if(proc.getCurrentBurstLeft() == 0) {
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
			//iterate thru io readyQueue to check for finished bursts
			for(Process proc : ioReadyQueue) 
				if(proc.getCurrentBurstLeft() == 0) {
					proc.setState("READY");
					readyQueue.add(proc); //add to cpu queue
					ioReadyQueue.remove(proc); //remove from io queue
				}
			
			/*
			 * TODO:
			 * - pick a processes depending on the algorithm
			 * - 
			 */

			systemTime++;
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
