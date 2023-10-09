public class CPU {	
	public static void execute(Process process, int cpuBurst) {
		//  add code to complete the method	
		process.setCPUBurst(process.getCPUBurstList().get(process.getCurrentBurstIndex()) - cpuBurst);
	}
}
