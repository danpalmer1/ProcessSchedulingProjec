import java.util.ArrayList;
import java.util.List;

public class Process {
    private int pid; //the process id, which can be the process index in the scenario file
    private String name;
    private String state;
    /*
     * NEW: The process has just been created, not yet READY to be executed.
     * READY: The process is ready to be executed by the CPU but is currently
     *        in the ready queue
     * RUNNING: The process is currently executed by the CPU
     * WAITING: The process is in the I/O queue to wait for the I/O device
     * TERMINATED: The process has already finished its tasks
     */
    //time variables
    private int arrivalTime; //the system time that the process is created
    private int startTime;
    private int finishTime; //the system time that the process terminates
    private int turnaroundTime; //the total execution time of a process from start to finish
    private int waitTime; // the total time the process needs to wait in the ready queue
    private int ioWaitTime;  //the total time the process needs to wait in the I/O queue
    //info from file
    private int priority; //priority of the process as indicated in the file
    private List<Integer> cpuBurstTimes = new ArrayList<>();
    private List<Integer> ioBurstTimes = new ArrayList<>();

     public Process(int pid, String name, int arrivalTime, int priority, List<Integer> cpuBurstTimes, List<Integer> ioBurstTimes) {
        super();
        this.pid = pid;
        this.state = "NEW";
        this.priority = priority;
        this.cpuBurstTimes = cpuBurstTimes;
        this.ioBurstTimes = ioBurstTimes;
        this.arrivalTime = arrivalTime;
        this.startTime = -1;
        this.finishTime = -1;
        this.turnaroundTime = -1;
        this.ioWaitTime = -1;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
        this.turnaroundTime = finishTime - arrivalTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void increaseWaitTime(int burst) {
        this.waitTime += burst;
    }

    public int getIoWaitTime() {
        return ioWaitTime;
    }

    public void setIoWaitTime(int ioWaitTime) {
        this.ioWaitTime = ioWaitTime;
    }

    public void increaseIoWaitTime(int burst) {
        this.ioWaitTime += burst;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<Integer> getCpuBurstTimes() {
        return cpuBurstTimes;
    }

    public void setCpuBurstTimes(List<Integer> cpuBurstTimes) {
        this.cpuBurstTimes = cpuBurstTimes;
    }

    public List<Integer> getIoBurstTimes() {
        return ioBurstTimes;
    }

    public void setIoBurstTimes(List<Integer> ioBurstTimes) {
        this.ioBurstTimes = ioBurstTimes;
    }

    public String toString() {
		return  "Process [name=" + name + ", id=" + pid 
			    + ", arrivalTime=" + arrivalTime + ", cpuBurst=" + cpuBurstTimes 
                + ", ioBurst=" + ioBurstTimes 
			    + ", priority=" + priority + "]";
	}
}
