import java.util.List;


public class PSWRR extends SchedulingAlgorithm {
      public PSWRR (List<Process> queue) {
		super("Priority Scheduling", queue);
	}

     public Process pickNextProcess() {
    	  return readyQueue.get(0);
      }
}