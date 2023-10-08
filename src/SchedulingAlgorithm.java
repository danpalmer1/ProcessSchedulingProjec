import java.util.ArrayList;
import java.util.List;

public abstract class SchedulingAlgorithm {
    
	protected String name;		      //scheduling algorithm name
	protected List<Process> allProcs;		//the initial list of processes
	protected List<Process> readyQueue;	//ready queue of ready processes
	protected List<Process> finishedProcs;	//list of terminated processes
	protected Process curProcess; //current selected process by the scheduler
	protected int systemTime; //system time or simulation time steps
 
      public SchedulingAlgorithm(String name, List<Process> queue) {
    	      this.name = name;
    	      this.allProcs = queue;
    	      this.readyQueue = new ArrayList<>();
    	      this.finishedProcs = new ArrayList<>();
      }
	
	public void schedule() {
		System.out.println("Scheduler: " + name);
		while(!allProcs.isEmpty() || !readyQueue.isEmpty()) {
			
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
