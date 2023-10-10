import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		// Start with the code to ask the user for the input scenario file and the scheduling parameters (algorithm, quantum time (only for RR), running mode, etc.).
		int fileSelection, algo, qtmTime, mode;
		String algoName;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.print("Please select a running mode: "+
			"\n(0) = auto" + 
			"\n(1) = manual");
        	mode = sc.nextInt();
		} while(mode < 0 || mode > 1);
		do {
			System.out.print("Please select a scenario 1-3: ");
        	fileSelection = sc.nextInt();
		} while(fileSelection < 1 || fileSelection > 3);
		String file = "src/Scenarios/scenario" + fileSelection + ".txt";
		do {
			System.out.print("Please select a scheduling algorithm: "+
			"\n(1) = PS" + 
			"\n(2) = PS w/ RR" + 
			"\n(3) = FCFS"
			);
        	algo = sc.nextInt();
			
		} while(algo < 1 || algo > 3);
		if(algo == 2){
		System.out.println("Please enter quantum time");
		qtmTime = sc.nextInt();
		}
		sc.close();
		try {
			Scanner scan = new Scanner(new File(file));
			List<Process> allProcs = new ArrayList<>();
			int id = 0;
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] arr = line.split("\\s++");
				String name = arr[0];
				int arrivalTime = Integer.parseInt(arr[1]);
				int priority = Integer.parseInt(arr[2]);
				List<Integer> cpuBursts = new ArrayList<>();
				List<Integer> ioBursts = new ArrayList<>();
				for(int i = 3; i < arr.length; i++) {
					if(i%2 == 1) cpuBursts.add(Integer.parseInt(arr[i]));
					else ioBursts.add(Integer.parseInt(arr[i]));
				}
				Process proc = new Process(id++, name, arrivalTime, priority, cpuBursts, ioBursts);
				allProcs.add(proc);
			}
			
			System.out.println("All processes have been successfully read");
			SchedulingAlgorithm scheduler = null;
			
			switch(algo) {
			case 1:
				scheduler = new PriorityScheduling(allProcs); break;
			case 2:
				scheduler = new PSWRR(allProcs); break;
			case 3:
				scheduler = new FCFS(allProcs); break;
			}
			scheduler.schedule();
			scan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
