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
		while(!procs.isEmpty() || !readyQueue.isEmpty()) {
			System.out.print("System time: " + systemTime + " ");
			for(Process proc: procs) 
				if(proc.getArrivalTime() == systemTime) 
					readyQueue.add(proc);
			procs.removeAll(readyQueue);
			if(!readyQueue.isEmpty()){
			curProcess = pickNextProcess();
			print();
			if(curProcess.getStartTime() < 0)
				curProcess.setStartTime(systemTime);
			CPU.execute(curProcess, 1);
			for(Process other: readyQueue)
				if(other != curProcess) other.increaseWaitingTime(1);
			//systemTime++;
			int index = curProcess.getCurrentBurstIndex();
			if(curProcess.getCPUBurstList().get(index) == 0) {
				curProcess.setFinishTime(systemTime);
				readyQueue.remove(curProcess);
				finishedProcs.add(curProcess);
				System.out.println("Process " + curProcess.getName() + " finished at " + systemTime
						+ ", TAT = " + curProcess.getTurnaroundTime() + ", WAT: " + curProcess.getWaitTime()); 
			
			} 
			systemTime++;
			
			}
			System.out.println();
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
