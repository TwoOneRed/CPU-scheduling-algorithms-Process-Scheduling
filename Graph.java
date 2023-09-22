public class Graph{
    private String processName;
    private int time;

    public Graph(String processName, int time){
        this.processName = processName;
        this.time = time;
    }
    public String getProcess(){
        return processName;
    }
    public int getTime(){
        return time;
    }

    public String toString(){
        return processName +" , time => " + time;
    }
}