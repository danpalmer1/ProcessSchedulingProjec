import java.util.List;

public class FCFS extends SchedulingAlgorithm {
      public FCFS(List<Process> queue, int qtmTime) {
		super("FCFS", queue, qtmTime);
	}


      public Process pickNextProcess() {
		return readyQueue.get(0);
      }


      @Override
      public Process pickNextIOProcess() {
            return ioReadyQueue.get(0);

      }
}
