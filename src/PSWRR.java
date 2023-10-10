import java.util.List;


public class PSWRR extends SchedulingAlgorithm {
      public PSWRR (List<Process> queue, int qtmTime) {
		super("Priority Scheduling", queue, qtmTime);
	}

     public Process pickNextProcess() {
	 	System.out.println("qtmTime: " + qtmTime);
    	  return readyQueue.get(0);
      }

    @Override
	public Process pickNextIOProcess() {
		return ioReadyQueue.get(0);
	}
}