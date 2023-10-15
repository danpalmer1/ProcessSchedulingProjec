import java.util.Collections;
import java.util.List;


public class PSWRR extends SchedulingAlgorithm {
      public PSWRR (List<Process> queue, int qtmTime) {
		super("Priority Scheduling", queue, qtmTime);
	}

     public Process pickNextProcess() {
            Collections.sort(readyQueue, (o1, o2) -> o1.getPriority() - o2.getPriority());
            return readyQueue.get(0);
      }

    @Override
	public Process pickNextIOProcess() {
		return ioReadyQueue.get(0);
	}

    public boolean priorityTie() {
        Collections.sort(readyQueue, (o1, o2) -> o1.getPriority() - o2.getPriority());
        if(readyQueue.size() > 1) {
            for(int i = 0; i < readyQueue.size()-1; i++) {
                if(readyQueue.get(i).getPriority() == readyQueue.get(i+1).getPriority())
                    return true;
            }
        }
        return false;
    }
   
    private void executeProcess(List<Process> queue) {
        // if there are items in the queue
        // if currentProcess.quantumTimeLeft == 0 and there are none with similar priority
        //     pick a new process from the queue based on priority
        // else if currentProcess.quantumTimeLeft == 0 and there are processes with similar priority in the queue
        //     save the current process
        //     pick a new process that isn't the currentProcess
        //     set that process to the currentProcess`
        // else if currentProcess.status == finished OR WAITING (for io))
        //     pick a new process
        // else 
        //     execute the process
        //     subtract from the quantumTimeLeft
        //     check if the processs is finished or ready for the IO queue
    }

	// @Override
	// public void schedule() {
    // System.out.println("Scheduler: " + name);
    // while (!procs.isEmpty() || !readyQueue.isEmpty() || !ioReadyQueue.isEmpty()) {
    //     System.out.println("System time: " + systemTime + " ");
    //     // Iterate through untouched processes
    //     for (Process proc : procs) {
    //         // If process arrives
    //         if (proc.getArrivalTime() == systemTime) {
    //             readyQueue.add(proc); // Add to ready for CPU queue
    //             proc.setState("READY"); // Set state to ready
    //         }
    //     }
    //     procs.removeAll(readyQueue);

    //     // Execute I/O device
    //     if (!ioReadyQueue.isEmpty()) {
    //         curProcess = pickNextIOProcess();
    //         print(ioReadyQueue, false);
    //         IODevice.execute(curProcess, 1);
    //         for (Process other : ioReadyQueue)
    //             if (other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
    //         int index = curProcess.getCurrentBurstIndex();
    //         if (curProcess.getIOBurstList().get(index) == 0) { // Current I/O burst is finished
    //             // Switch process to execute the next CPU process
    //             curProcess.setState("READY");
    //             curProcess.setCurrentBurstIndex(curProcess.getCurrentBurstIndex() + 1);
    //             ioReadyQueue.remove(curProcess);
    //             readyQueue.add(curProcess);
    //         }
    //     }

    //     // Execute next CPU process using Round Robin
    //     if (!readyQueue.isEmpty()) {
    //         curProcess = pickNextProcess();
    //         print(readyQueue, true);
    //         if (curProcess.getStartTime() < 0) // First time process is executed
    //             curProcess.setStartTime(systemTime);

    //         // Execute for a time quantum (1 time unit)
    //         int quantum = 1;
    //         CPU.execute(curProcess, quantum);

    //         for (Process other : readyQueue)
    //             if (other != curProcess) other.setWaitTime(other.getWaitTime() + quantum);

    //         int index = curProcess.getCurrentBurstIndex();
    //         if (curProcess.getCPUBurstList().get(index) == 0) { // Current CPU burst is finished
    //             if (curProcess.getCurrentBurstIndex() >= curProcess.getCPUBurstList().size() - 1) {
    //                 // This is the final CPU burst; the process is finished
    //                 curProcess.setState("TERMINATED");
    //                 curProcess.setFinishTime(systemTime + 1);
    //                 readyQueue.remove(curProcess);
    //                 finishedProcs.add(curProcess);
    //                 System.out.println("PROCESS " + curProcess.getName() + " FINISHED AT " + systemTime
    //                         + ", TAT = " + curProcess.getTurnaroundTime() + ", WAT: " + curProcess.getWaitTime());
    //             } else {
    //                 // Switch process to execute I/O process
    //                 curProcess.setState("WAITING");
    //                 readyQueue.remove(curProcess);
    //                 ioReadyQueue.add(curProcess);
    //             }
    //         }
    //     }
    //     systemTime++;
    //     System.out.println();
    // }
}