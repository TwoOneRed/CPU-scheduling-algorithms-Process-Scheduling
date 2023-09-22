public class Process {
    private String processName;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private int finishingTime;
    private int turnAroundTime;
    private int waitingTime;
    private Boolean status = true;
    private int remainingTime;

    public Process(String name, int burst, int arrival, int priority){
        this.processName = name;
        this.burstTime = burst;
        this.arrivalTime = arrival;
        this.priority = priority;
        this.remainingTime = burst;
    }

    public String getProcessName(){
        return processName;
    }

    public int getBurstTime(){
        return burstTime;
    }

    public int getArrivalTime(){
        return arrivalTime;
    }

    public int getPriority(){
        return priority;
    }

    public int getFinishingTime(){
        return finishingTime;
    }

    public int getTurnAroundTime(){
        return turnAroundTime;
    }
    
    public int getWaitingTime(){
        return waitingTime;
    }

    public Boolean getStatus(){
        return status;
    }

    public int getRemainingTime(){
        return remainingTime;
    }

    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }

    public void setArrivalTime(int arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setFinishingTime(int finishingTime){
        this.finishingTime = finishingTime;
    }

    public void setTurnAroundTime(int turnAroundTime){
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

    public void minusTime(){
        this.remainingTime--;
    }

    public void setStatusFalse(){
        this.status = false;
    }
    public void setCompletionTime(int time){
        this.finishingTime = time;
        this.turnAroundTime = finishingTime - arrivalTime;
        this.waitingTime = turnAroundTime-burstTime;
    }

    public String toString(){
        return processName + ", Burst time = " + burstTime + ", Arrival time = " + arrivalTime + ", Priority = " + priority + ", Remaining Time = " + remainingTime;
    }
    
}