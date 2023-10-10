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
    private int startTime;
    private int arrivalTime; //the system time that the process is created
    private int finishTime; //the system time that the process terminates
    private int turnaroundTime; //the total execution time of a process from start to finish
    private int waitTime; // the total time the process needs to wait in the ready queue
    private int ioWaitTime;  //the total time the process needs to wait in the I/O queue
    //info from file
    private int priority; //priority of the process as indicated in the file
    private List<Integer> cpuBurstList = new ArrayList<>();
    private List<Integer> ioBurstList = new ArrayList<>();
    private boolean flag; //false = cpu, true = io
    private int currentBurst; //index in burst list
    private int currentBurstLeft; //burst left

     public Process(int pid, String name, int arrivalTime, int priority, List<Integer> cpuBurstTimes, List<Integer> ioBurstTimes) {
        super();
        this.pid = pid;
        this.name = name;
        this.state = "NEW";
        this.priority = priority;
        this.cpuBurstList = cpuBurstTimes;
        this.ioBurstList = ioBurstTimes;
        this.arrivalTime = arrivalTime;
        this.flag = false;
        this.currentBurst = 0;
        this.currentBurstLeft = cpuBurstList.get(0);
        this.startTime = -1;
        this.finishTime = -1;
        this.turnaroundTime = -1;
        this.ioWaitTime = -1;
    }

    public int getStartTime(){
        return startTime;

    }

    public void setStartTime(int startTime){
    this.startTime = startTime;
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

    //getter and setter for cpu burst list
    public List<Integer> getCPUBurstList() {
        return cpuBurstList;
    }

    public void setCPUBurstList(List<Integer> cpuBurstTimes) {
        this.cpuBurstList = cpuBurstTimes;
    }

    //getter and setter for IO burst list
    public List<Integer> getIOBurstList() {
        return ioBurstList;
    }

    public void setIOBurstList(List<Integer> ioBurstTimes) {
        this.ioBurstList = ioBurstTimes;
    }

    //tells whether to look in cpu or io list
    public boolean getBurstFlag() {
        return flag;
    }

    public void setNextBurstFlag(boolean flag) {
        this.flag = flag;
    }

    //tracks index in cpu or io list
    public int getCurrentBurstIndex() {
        return currentBurst;
    }

    public void setCurrentBurstIndex(int newIndex) {
        this.currentBurst = newIndex;
    }

    public int getCPUBurst() {
        return cpuBurstList.get(currentBurst);
    }

    //set CPU burst
    public void setCPUBurst(int newBurst) {
        cpuBurstList.set(currentBurst, newBurst);
    }

    public int getIOBurst() {
        return ioBurstList.get(currentBurst);
    }
    //set io burst
    public void setIOBurst(int newBurst) {
        ioBurstList.set(currentBurst, newBurst);
    }

    public String toString() {
		return  "Process [name=" + name + ", id=" + pid 
			    + ", arrivalTime=" + arrivalTime + ", cpuBurst=" + cpuBurstList 
                + ", ioBurst=" + ioBurstList
			    + ", priority=" + priority + "]";
	}
}
