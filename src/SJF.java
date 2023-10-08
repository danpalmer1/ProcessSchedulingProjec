import java.util.Collections;
import java.util.List;

public class SJF extends SchedulingAlgorithm {
      public SJF (List<Process> queue) {
		super("SJF", queue);
	}


      public Process pickNextProcess() {
		Collections.sort(readyQueue, (o1, o2) -> o1.getCurrentBurstLeft() - o2.getCurrentBurstLeft());
		return readyQueue.get(0);
      }
}
