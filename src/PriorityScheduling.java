import java.util.Collections;
import java.util.List;

public class PriorityScheduling extends SchedulingAlgorithm {
      public PriorityScheduling (List<Process> queue) {
		super("Priority Scheduling", queue);
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
}
