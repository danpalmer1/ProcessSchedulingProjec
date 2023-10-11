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
	protected int qtmTime;
 
    public SchedulingAlgorithm(String name, List<Process> queue, int qtmTime) {
    	      this.name = name;
    	      this.procs = queue;
			  this.qtmTime = qtmTime;
    	      this.readyQueue = new ArrayList<>();
    	      this.finishedProcs = new ArrayList<>();
			  this.ioReadyQueue = new ArrayList<>();
     }

	public void schedule() {
			String key;
			int mode;
			boolean flag;
			Scanner sc = new Scanner(System.in);
			do {
			System.out.print("Please select a scenario \n (0) AUTO \n (1) MANUAL ");
        	mode = sc.nextInt();
			} while(mode < 0 || mode > 1);
		if(mode == 0){
			flag = true;
		} else {
			flag = false;
		}
		System.out.println("Scheduler: " + name);
		while(!procs.isEmpty() || !readyQueue.isEmpty() || !ioReadyQueue.isEmpty()) {
			if(!flag){
				System.out.print("Enter key to continue");
        		key = sc.nextLine();
			}
			
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
			//execute IO device
			if(!ioReadyQueue.isEmpty()) {
				curProcess = pickNextIOProcess();
				printIO();
				IODevice.execute(curProcess, 1);
				for(Process other : ioReadyQueue) 
					if(other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
				int index = curProcess.getCurrentBurstIndex();
				if(curProcess.getIOBurstList().get(index) == 0) { //current burst is finished
						//switch process to execute next cpu process
						curProcess.setState("READY");
						curProcess.setCurrentBurstIndex(curProcess.getCurrentBurstIndex() + 1);
						ioReadyQueue.remove(curProcess);
						readyQueue.add(curProcess);
					}
				}
			//execute next cpu process
			if(!readyQueue.isEmpty()){
				curProcess = pickNextProcess();
				print();
				if(curProcess.getStartTime() < 0) //first time process is executed
					curProcess.setStartTime(systemTime);

				CPU.execute(curProcess, 1); //subtract 1 from the burst
				for(Process other : readyQueue) 
					if(other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
				int index = curProcess.getCurrentBurstIndex();
				if(curProcess.getCPUBurstList().get(index) == 0) { //current burst is finished
					if(curProcess.getCurrentBurstIndex() > curProcess.getIOBurstList().size() - 1) { //this is the final cpu burst therefore the process is finished
						curProcess.setState("TERMINATED");
						curProcess.setFinishTime(systemTime + 1);
						readyQueue.remove(curProcess);
						finishedProcs.add(curProcess);
					System.out.println("PROCESS " + curProcess.getName() + " FINISHED AT " + systemTime
							+ ", TAT = " + curProcess.getTurnaroundTime() + ", WAT: " + curProcess.getWaitTime()); 
					} else {
						//switch process to execute io process
						curProcess.setState("WAITING");
						readyQueue.remove(curProcess);
						ioReadyQueue.add(curProcess);
					}
				} 
			} 
			systemTime++;
			System.out.println();
			} 
		}
			
			
		

	
	
	//Selects the next task using the appropriate scheduling algorithm
    public abstract Process pickNextProcess();

	//Selects the next io task using the appropriate scheduling algorithm
	public abstract Process pickNextIOProcess();

      //print simulation step
    public void print() {
		System.out.println("CPU: " + curProcess == null ? " idle " : curProcess.toString()); 
		// System.out.println("CPU: " + (curProcess == null ? " idle " : curProcess.toString()));
		System.out.print("READY QUEUE: [");
		for(Process proc : readyQueue)
			System.out.print(proc.getName() + ", ");
		System.out.print("]");
		System.out.println();
      }

	    //print simulation step
    public void printIO() {
		System.out.println("IO: " + curProcess == null ? " idle " : curProcess.toString()); 
		// System.out.println("IO: " + (curProcess == null ? " idle " : curProcess.toString()));
		System.out.print("IO READY QUEUE: [");
		// int len = ioReadyQueue.size()-1;
		// int count = 0;
		for(Process proc : ioReadyQueue){
			// if(len == count){
			// 	System.out.print(proc.getName());
			// }
			// count++;
			System.out.print(proc.getName() + ", ");
		}
		System.out.print("]");
		System.out.println();
      }
}
