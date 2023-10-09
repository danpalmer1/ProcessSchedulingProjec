import java.util.Collections;
import java.util.List;

public class SJF extends SchedulingAlgorithm {
      public SJF (List<Process> queue) {
		super("SJF", queue);
	}


      public Process pickNextProcess() {
		Collections.sort(readyQueue, (o1, o2) -> o1.getCPUBurstList().get(o1.getCurrentBurstIndex()) - o2.getCPUBurstList().get(o2.getCurrentBurstIndex()));
		return readyQueue.get(0);
      }
}
