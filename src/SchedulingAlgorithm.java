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
	protected List<Integer> tat;
	protected List<Integer> wat;
 
    public SchedulingAlgorithm(String name, List<Process> queue, int qtmTime) {
    	      this.name = name;
    	      this.procs = queue;
			  this.qtmTime = qtmTime;
    	      this.readyQueue = new ArrayList<>();
    	      this.finishedProcs = new ArrayList<>();
			  this.ioReadyQueue = new ArrayList<>();
			  this.tat = new ArrayList<>();
			  this.wat = new ArrayList<>();
     }

	public void schedule(int qtmTime) {
		String key = "";
		int input;
		boolean modeFlag;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("PLEASE SELECT MODE \n (0) AUTO \n (1) MANUAL ");
        	input = sc.nextInt();
		} while(input < 0 || input > 1);
		if(input== 0){
			modeFlag = true;
		} else {
		
			modeFlag = false;
		}
		System.out.println("SCHEDULER: " + name);
		System.out.println("PRESS ENTER TO PROCEDE");
		while(!procs.isEmpty() || !readyQueue.isEmpty() || !ioReadyQueue.isEmpty()) {
			if(!modeFlag){
        		key = sc.nextLine();
			}
			System.out.println("SYSTEM-TIME: " + systemTime + " ");
			checkForArrivedProcess(procs, systemTime);
			executeIOProcess(ioReadyQueue);
			executeProcess(readyQueue);
			systemTime++;
			System.out.println();
			} 
			sc.close();
		}
		
	private void executeProcess(List<Process> queue) {
		if(!queue.isEmpty()) {
			curProcess = pickNextProcess();
			if(curProcess.getStartTime() < 0) //first time process is executed
				curProcess.setStartTime(systemTime);
			CPU.execute(curProcess, 1); //subtract 1 from the burst
			curProcess.setQtmTimeLeft(curProcess.getQtmTimeLeft() - 1);
			print(readyQueue, true);
			increaseWaitTime(readyQueue);
			if(curProcess.getCPUBurstList().get(curProcess.getCurrentBurstIndex()) == 0) { //current burst is finished
				if(curProcess.getCurrentBurstIndex() > curProcess.getIOBurstList().size() - 1) { //this is the final cpu burst therefore the process is finished
					terminate(curProcess);
				} else {
					//switch process to execute io process
					curProcess.setState("WAITING");
					curProcess.setNextBurstFlag(false);
					readyQueue.remove(curProcess);
					ioReadyQueue.add(curProcess);
					System.out.println(curProcess.getName() + " ADDED TO THE IO QUEUE");
				}
				
			} 
		} 
	}

	private void terminate(Process process) {
		process.setState("TERMINATED");
		process.setFinishTime(systemTime + 1);
		readyQueue.remove(process);
		finishedProcs.add(process);
		System.out.println("PROCESS " + process.getName() + " FINISHED AT " + systemTime
			+ ", TAT = " + process.getTurnaroundTime() + ", AWT: " + process.getWaitTime()); 
		tat.add(process.getTurnaroundTime());
		wat.add(process.getWaitTime());

		int sumTat = 0, sumAwt= 0;
		if(readyQueue.isEmpty() && ioReadyQueue.isEmpty()){
			for(int i = 0; i < tat.size(); i++){
				sumTat += tat.get(i);
			}
			for(int i = 0; i < wat.size(); i++){
				sumAwt += wat.get(i);
			}
			double avgTat, avgAwt;
			avgTat = sumTat/tat.size();
			avgAwt = sumAwt/wat.size();
			System.out.println("AVG-TAT: --> " + avgTat +"\nAVG-AWT: --> " + avgAwt);
		}
	}

	private void executeIOProcess(List<Process> queue) {
		if(!ioReadyQueue.isEmpty()) {
				curProcess = pickNextIOProcess();
				IODevice.execute(curProcess, 1);
				print(ioReadyQueue, false);
				increaseWaitTime(ioReadyQueue);
				if(curProcess.getIOBurstList().get(curProcess.getCurrentBurstIndex()) == 0) { //current burst is finished
						//switch process to execute next cpu process
						curProcess.setState("READY");
						curProcess.setNextBurstFlag(true);
						curProcess.setCurrentBurstIndex(curProcess.getCurrentBurstIndex() + 1);
						ioReadyQueue.remove(curProcess);
						readyQueue.add(curProcess);
						System.out.println(curProcess.getName() + " ADED TO THE CPU QUEUE");
					}
			}
	}

	private void increaseWaitTime(List<Process> queue) {
		for(Process other : queue) 
					if(other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
	}

	private void checkForArrivedProcess(List<Process> list, int time) {
		for(Process proc : list) {
				//if process arrives 
				if(proc.getArrivalTime() == time) {
					readyQueue.add(proc); //add to ready for cpu queue
					proc.setState("READY"); //set state to ready 
					System.out.println("Process " + proc.getName() + " arrived at " + proc.getArrivalTime());
				}
			}
			procs.removeAll(readyQueue);
	}

    //print simulation step
    public void print(List<Process> queue, boolean flag) {
		System.out.println("CPU: " + curProcess == null ? " idle " : curProcess.toString()); 
		// System.out.println("CPU: " + (curProcess == null ? " idle " : curProcess.toString()));
		if(flag)
			System.out.print("READY QUEUE: [");
		else System.out.print("IO READY QUEUE: [");
		for(Process proc : queue)
			if(proc == queue.get(queue.size() - 1)) System.out.println(proc.getName() + "]");
			else 
				System.out.print(proc.getName() + ", ");
		System.out.println();
      }

	//Selects the next task using the appropriate scheduling algorithm
    public abstract Process pickNextProcess();

	//Selects the next io task using the appropriate scheduling algorithm
	public abstract Process pickNextIOProcess();
}
