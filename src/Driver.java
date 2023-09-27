import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(new File("src/proc.txt"));
			String algo = scan.nextLine().toUpperCase(); //read the scheduling algorithm
			List<PCB> allProcs = new ArrayList<>();
			int id = 0;
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arr = line.split(",\\s*");
				String name = arr[0];
				int arrivalTime = Integer.parseInt(arr[1]);
				int burst = Integer.parseInt(arr[2]);
				int priority = Integer.parseInt(arr[3]);
				PCB proc = new PCB(name, id++, arrivalTime, burst, priority);
				allProcs.add(proc);
			}
			
			
			SchedulingAlgorithm scheduler = null;
			
			switch(algo) {
			case "FCFS":
				scheduler = new FCFS(allProcs); break;
			}
			scheduler.schedule();
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
