import java.util.ArrayList;
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
		Collections.sort(ioReadyQueue, (o1, o2) -> o1.getPriority() - o2.getPriority());

		return ioReadyQueue.get(0);

	}

	@Override
	public void schedule() {
	System.out.println("OVERDIDEEE");
    System.out.println("Scheduler: " + name);
    while (!procs.isEmpty() || !readyQueue.isEmpty() || !ioReadyQueue.isEmpty()) {
        System.out.println("System time: " + systemTime + " ");
        // Iterate through untouched processes
        for (Process proc : procs) {
            // If process arrives
            if (proc.getArrivalTime() == systemTime) {
                readyQueue.add(proc); // Add to ready for CPU queue
                proc.setState("READY"); // Set state to ready
            }
        }
        procs.removeAll(readyQueue);

        // Execute I/O device
        if (!ioReadyQueue.isEmpty()) {
            curProcess = pickNextIOProcess();
            printIO();
            IODevice.execute(curProcess, 1);
            for (Process other : ioReadyQueue)
                if (other != curProcess) other.setWaitTime(other.getWaitTime() + 1);
            int index = curProcess.getCurrentBurstIndex();
            if (curProcess.getIOBurstList().get(index) == 0) { // Current I/O burst is finished
                // Switch process to execute the next CPU process
                curProcess.setState("READY");
                curProcess.setCurrentBurstIndex(curProcess.getCurrentBurstIndex() + 1);
                ioReadyQueue.remove(curProcess);
                readyQueue.add(curProcess);
            }
        }

        // Execute next CPU process using Round Robin
        if (!readyQueue.isEmpty()) {
            curProcess = pickNextProcess();
            print();
            if (curProcess.getStartTime() < 0) // First time process is executed
                curProcess.setStartTime(systemTime);

            // Execute for a time quantum (1 time unit)
            int quantum = 1;
            CPU.execute(curProcess, quantum);

            for (Process other : readyQueue)
                if (other != curProcess) other.setWaitTime(other.getWaitTime() + quantum);

            int index = curProcess.getCurrentBurstIndex();
            if (curProcess.getCPUBurstList().get(index) == 0) { // Current CPU burst is finished
                if (curProcess.getCurrentBurstIndex() >= curProcess.getCPUBurstList().size() - 1) {
                    // This is the final CPU burst; the process is finished
                    curProcess.setState("TERMINATED");
                    curProcess.setFinishTime(systemTime + 1);
                    readyQueue.remove(curProcess);
                    finishedProcs.add(curProcess);
                    System.out.println("PROCESS " + curProcess.getName() + " FINISHED AT " + systemTime
                            + ", TAT = " + curProcess.getTurnaroundTime() + ", WAT: " + curProcess.getWaitTime());
                } else {
                    // Switch process to execute I/O process
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

}