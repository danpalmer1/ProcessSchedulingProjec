import java.util.List;

public class FCFS extends SchedulingAlgorithm {
      public FCFS(List<Process> queue) {
		super("FCFS", queue);
	}


      public Process pickNextProcess() {
		return readyQueue.get(0);
      }
}
