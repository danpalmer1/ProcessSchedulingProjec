public class IODevice {	
	public static void execute(Process process, int ioBurst) {
		//  add code to complete the method	
		process.setIOBurst(process.getIOBurstList().get(process.getCurrentBurstIndex()) - ioBurst);
	}
}
