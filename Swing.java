import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Swing extends JFrame{
    ArrayList<Process> processDetails = new ArrayList<Process>();
    static int process = 0;
    ArrayList<JTextArea>textbox = new ArrayList<>();
    int start;


    public Swing(int Start){
        super("Details Of Process");
        this.start = Start;
        JPanel panel = new JPanel(new GridLayout(process+1,4));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        String panelName[] = {"Process Name","Burst Time","Arrival Time","Priority"};
        //Label
        for(int i = 0 ; i < 4 ; i++){
            JLabel btn = new JLabel(panelName[i]);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(btn);
        }

        for(int i = 0 ; i < process ; i++){
            JLabel btn = new JLabel(String.valueOf("P"+ (Start++)));
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JTextArea textArea = new JTextArea();
            textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textArea.setName("a"+String.valueOf(i));

            JTextArea textArea1 = new JTextArea();
            textArea1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textArea1.setName("b"+String.valueOf(i));

            JTextArea textArea2 = new JTextArea();
            textArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            textArea2.setName("c"+String.valueOf(i));
            
            textbox.add(textArea);
            textbox.add(textArea1);
            textbox.add(textArea2);

            panel.add(btn);
            panel.add(textArea);
            panel.add(textArea1);
            panel.add(textArea2);
        }

        //Bottom Panel
        JButton rr = new JButton("Round Robin with Quantum");
        rr.addActionListener(new rrActionListener());

        JButton psjf = new JButton("Preemptive SJF");
        psjf.addActionListener(new psjfActionListener());
        
        JButton pp = new JButton("Preemptive Priority");
        pp.addActionListener(new ppActionListener());
        
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
                processCount();
            }
        });
        bottomPanel.add(reset);
        bottomPanel.add(rr);
        bottomPanel.add(psjf);
        bottomPanel.add(pp);
        
        //label.setText(line);
        //System.out.println(line);
        this.add(panel,BorderLayout.CENTER);
        this.add(bottomPanel,BorderLayout.SOUTH);
        //setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setSize(700,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void processCount(){
        JFrame processNumber = new JFrame("Process");
        JPanel panel =  new JPanel(new GridLayout(2,1));
        JLabel label = new JLabel("Please Enter Number Of Processes");
        JTextField input = new JTextField("");

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Ok");
        JButton cancel= new JButton("Cancel");
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                process = Integer.parseInt(input.getText());
                if(process >= 3 && process <= 10){
                    processNumber.dispose();
                    startWith();
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(),"Processes must range from 3 to 10","Alert",JOptionPane.WARNING_MESSAGE);     
                }
            }
        });
        cancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                processNumber.dispose();
            }
        });
        //Top Panel
        panel.add(label);   
        panel.add(input);
        //Bottom Panel
        bottom.add(ok);         
        bottom.add(cancel);

        processNumber.add(panel,BorderLayout.CENTER);
        processNumber.add(bottom,BorderLayout.SOUTH);
        processNumber.setSize(300,150);
        processNumber.setVisible(true);
        processNumber.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[]args){
        processCount();
    }

    public static void startWith(){
        JFrame startWith = new JFrame("Start With");
        JPanel panel =  new JPanel(new GridLayout(2,1));
        JLabel label = new JLabel("Start With Process (0 - XXX)");
        JTextField input = new JTextField("");

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("Ok");
        JButton cancel= new JButton("Cancel");
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int start = Integer.parseInt(input.getText());
                if(start >= 0){
                    startWith.dispose();
                    new Swing(start);
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(),"Processes must more than 0","Alert",JOptionPane.WARNING_MESSAGE);     
                }
            }
        });
        panel.add(label);   
        panel.add(input);
        //Bottom Panel
        bottom.add(ok);         
        bottom.add(cancel);

        startWith.add(panel,BorderLayout.CENTER);
        startWith.add(bottom,BorderLayout.SOUTH);
        startWith.setSize(300,150);
        startWith.setVisible(true);
        startWith.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    //Round Robin
    public class rrActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                input();
                JFrame timeFrame = new JFrame();
                JPanel panel = new JPanel(new GridLayout(2,1));
                JLabel label = new JLabel("Please Enter Time Quantum->");
                JTextField time = new JTextField();
                panel.add(label);
                panel.add(time);
                JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JButton ok = new JButton("Ok");
                JButton cancel= new JButton("Cancel");
                ok.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        int timeQuantum = Integer.parseInt(time.getText());        
                        if(timeQuantum > 0){
                            timeFrame.dispose();
                            roundRobinProcess(timeQuantum);
                        }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(),"Quantum Must More Than 0.","Alert",JOptionPane.WARNING_MESSAGE);     
                        }
                        
                    }
                });
                cancel.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        timeFrame.dispose();
                    }
                });

                bottom.add(ok);
                bottom.add(cancel);
                timeFrame.add(panel,BorderLayout.CENTER);
                timeFrame.add(bottom,BorderLayout.SOUTH);
                timeFrame.setSize(300,150);
                timeFrame.setVisible(true);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(new JFrame(),"Some input were missing.","Alert",JOptionPane.WARNING_MESSAGE);     
            }
        }
    }

    public void roundRobinProcess(int timeQuantum){
        ArrayList<Process> tempProcessDetails = new ArrayList<>();
        ArrayList<Process> readyQueue = new ArrayList<>();
        ArrayList<Graph> ganttChartsDetails = new ArrayList<>(); 
        int finishedTime = 0;
        int totalTime = 0;

        for (int i = 0; i < processDetails.size(); i++)
            tempProcessDetails.add(new Process(processDetails.get(i).getProcessName(), processDetails.get(i).getBurstTime(), processDetails.get(i).getArrivalTime(), processDetails.get(i).getPriority()));
        
        int minArrivalTime = tempProcessDetails.get(0).getArrivalTime();
        for(Process p: processDetails){
            totalTime = totalTime + p.getBurstTime();
            if (p.getArrivalTime() < minArrivalTime)
                minArrivalTime = p.getArrivalTime();
        }
        for (int i = 0; i < tempProcessDetails.size(); i++){
            for(int j = 1; j < (tempProcessDetails.size()-i); j++){
                if(tempProcessDetails.get(j-1).getArrivalTime() > tempProcessDetails.get(j).getArrivalTime()){
                    Process temp = tempProcessDetails.get(j-1);
                    tempProcessDetails.set(j-1, tempProcessDetails.get(j));
                    tempProcessDetails.set(j, temp);

                }
            }
        }
        for(int i = 0; i < totalTime; i++){
            for(int j = 0; j < tempProcessDetails.size(); j++){
                if (tempProcessDetails.get(j).getBurstTime() > 0){
                    if (readyQueue.isEmpty()){
                        if (tempProcessDetails.get(j).getBurstTime() <= timeQuantum){
                            if (ganttChartsDetails.isEmpty())
                                finishedTime = finishedTime + tempProcessDetails.get(0).getBurstTime() + minArrivalTime; 
                            else
                                finishedTime = finishedTime + readyQueue.get(0).getBurstTime();
                            tempProcessDetails.get(j).setBurstTime(-1);

                            for (int z = 0; z < tempProcessDetails.size(); z++){
                                if (tempProcessDetails.get(z).getArrivalTime() <= finishedTime && tempProcessDetails.get(z).getBurstTime() > 0 )
                                    readyQueue.add(tempProcessDetails.get(z));
                                if (processDetails.get(z).getProcessName() == tempProcessDetails.get(j).getProcessName())
                                    processDetails.get(z).setCompletionTime(finishedTime);
                            }
                            ganttChartsDetails.add(new Graph(tempProcessDetails.get(j).getProcessName(), finishedTime));
                        }
                        else{
                            if (ganttChartsDetails.isEmpty()) 
                                finishedTime = finishedTime + timeQuantum + minArrivalTime; 
                            else
                                finishedTime = finishedTime + timeQuantum;
                            tempProcessDetails.get(j).setBurstTime(tempProcessDetails.get(j).getBurstTime()-timeQuantum);
                            for (int z = 0; z < tempProcessDetails.size(); z++){
                                if (tempProcessDetails.get(z).getArrivalTime() <= finishedTime && tempProcessDetails.get(z).getBurstTime() > 0){
                                    if(tempProcessDetails.get(z).getProcessName() != tempProcessDetails.get(j).getProcessName())
                                        readyQueue.add(tempProcessDetails.get(z));
                                }
                            }
                            readyQueue.add(tempProcessDetails.get(j));
                            ganttChartsDetails.add(new Graph(tempProcessDetails.get(j).getProcessName(), finishedTime));
                        }
                    }
                    else{
                        if (readyQueue.get(0).getBurstTime() <= timeQuantum){
                            finishedTime = finishedTime + readyQueue.get(0).getBurstTime();
                            for (int z = 0; z < tempProcessDetails.size(); z++){
                                if (readyQueue.get(0).getProcessName() == processDetails.get(z).getProcessName())
                                    processDetails.get(z).setCompletionTime(finishedTime);          
                                if(readyQueue.get(0).getProcessName() == tempProcessDetails.get(z).getProcessName())
                                    tempProcessDetails.get(z).setBurstTime(-1);                           
                                if (tempProcessDetails.get(z).getArrivalTime() <= finishedTime && tempProcessDetails.get(z).getBurstTime() > 0){
                                    if (!readyQueue.contains(tempProcessDetails.get(z)))
                                        readyQueue.add(tempProcessDetails.get(z));
                                }
                            }
                            ganttChartsDetails.add(new Graph(readyQueue.get(0).getProcessName(), finishedTime));
                            readyQueue.remove(0);
                        }
                        else{
                            int index = 0;
                            finishedTime = finishedTime + timeQuantum;
                            for (int z = 0; z < tempProcessDetails.size(); z++){
                                if (readyQueue.get(0).getProcessName() == tempProcessDetails.get(z).getProcessName()){
                                    tempProcessDetails.get(z).setBurstTime(tempProcessDetails.get(z).getBurstTime()-timeQuantum);
                                    index = z;
                                }
                                if (tempProcessDetails.get(z).getArrivalTime() <= finishedTime && tempProcessDetails.get(z).getBurstTime() > 0 ){
                                    if (!readyQueue.contains(tempProcessDetails.get(z)))
                                        readyQueue.add(tempProcessDetails.get(z));
                                }
                            }
                            readyQueue.add(tempProcessDetails.get(index));
                            ganttChartsDetails.add(new Graph(readyQueue.get(0).getProcessName(), finishedTime));
                            readyQueue.remove(0);
                        }
                    }
                }
            }
        }
        report("Round Robin (Quantum ="+timeQuantum+")", 0, ganttChartsDetails);
    }
    
    public static String ganttChartDash(int num){
        String dash = "";
        for (int i = 0; i < num; i++){
            dash = dash + "------+";
        }
        return dash;
    }

    //Preemptive Short Job First
    public class psjfActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                input();
                ArrayList<Process>waitingProcess = new ArrayList<>();
                int totalTime = 0;
                ArrayList<Graph>processGraphing = new ArrayList<>();

                for(int i = 0; i < processDetails.size(); i++)
                    totalTime = totalTime + processDetails.get(i).getBurstTime();

                int minArrivalTime = processDetails.get(0).getArrivalTime();
                for(Process p: processDetails){
                    totalTime = totalTime + p.getBurstTime();
                    if (p.getArrivalTime() < minArrivalTime)
                        minArrivalTime = p.getArrivalTime();
                }
                int arrivalTime = minArrivalTime;

                for(int j = minArrivalTime; j <= totalTime ; j++){
                    for(int i = 0; i < processDetails.size();i++){ //detect arrival time
                        if(processDetails.get(i).getArrivalTime() == arrivalTime){ // start with 0, if find dao 0 then do..
                            waitingProcess.add(processDetails.get(i));
                        }
                    }

                    int lowremainTime = 1000; 
                    for(int i = 0 ; i < waitingProcess.size();i++){ 
                        if(lowremainTime > waitingProcess.get(i).getRemainingTime() && (waitingProcess.get(i).getStatus())){ //get Process Time
                            lowremainTime = waitingProcess.get(i).getRemainingTime();
                        }
                    }

                    //Sort By Priority
                    for (int i = 0; i < waitingProcess.size(); i++){
                        for(int h = 1; h < (waitingProcess.size()-i); h++){
                            if(waitingProcess.get(h-1).getPriority() > waitingProcess.get(h).getPriority()){
                                Process temp = waitingProcess.get(h-1);
                                waitingProcess.add(h-1, waitingProcess.get(h));
                                waitingProcess.remove(h);
                                waitingProcess.add(h, temp);
                                waitingProcess.remove(h+1);
                            }
                        }
                    }

                    for(int i = 0 ; i <waitingProcess.size() ; i++){
                        if(waitingProcess.get(i).getRemainingTime() == lowremainTime && (waitingProcess.get(i).getStatus())){ //get Process with lowest remaining time and active.
                            waitingProcess.get(i).minusTime(); //minus time
                            processGraphing.add(new Graph(waitingProcess.get(i).getProcessName(), j+1));
                            if(waitingProcess.get(i).getRemainingTime() == 0) //if the process end set status to false
                            {
                                waitingProcess.get(i).setStatusFalse(); 
                                for(int z = 0; z < processDetails.size(); z++){
                                    if (processDetails.get(z).getProcessName() == waitingProcess.get(i).getProcessName())
                                        processDetails.get(z).setCompletionTime(j+1);
                                }
                            }
                            break; //run once if same prio
                        }
                    }
                    arrivalTime++;
                }

                for (int i = 0; i < processGraphing.size(); i++){
                    for(int h = 1; h < (processGraphing.size()-i); h++){
                        if(processGraphing.get(h-1).getTime() > processGraphing.get(h).getTime()){
                            Graph temp = processGraphing.get(h-1);
                            processGraphing.add(h-1, processGraphing.get(h));
                            processGraphing.remove(h);
                            processGraphing.add(h, temp);
                            processGraphing.remove(h+1);
                        }
                    }
                }
                for(int i = processGraphing.size()-1; i > 0 ; i--){
                    if(processGraphing.get(i).getProcess().equals(processGraphing.get(i-1).getProcess())){
                        processGraphing.remove(i-1);
                    }
                }
                report("Preemptive Short Job First",minArrivalTime, processGraphing);
                
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(new JFrame(),"Some input were missing.","Alert",JOptionPane.WARNING_MESSAGE);     
            }
        }
    }

    //Preemptive Priority
    public class ppActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                input();
                ArrayList<Process>waitingProcess = new ArrayList<>();
                int totalTime = 0;
                ArrayList<Graph>enterGraphing = new ArrayList<>();
                ArrayList<Graph>processGraphing = new ArrayList<>();

                for(int i = 0; i < processDetails.size(); i++)
                    totalTime += processDetails.get(i).getBurstTime();

                int minArrivalTime = processDetails.get(0).getArrivalTime();
                for(Process p: processDetails){
                    totalTime = totalTime + p.getBurstTime();
                    if (p.getArrivalTime() < minArrivalTime)
                        minArrivalTime = p.getArrivalTime();
                }
                int arrivalTime = minArrivalTime;

                for(int j = minArrivalTime; j <= totalTime ; j++){ //time 1sec by 1sec
                    for(int i = 0; i < processDetails.size();i++){ //detect arrival time
                        if(processDetails.get(i).getArrivalTime() == arrivalTime){ // start with 0, if find dao 0 then do..
                            waitingProcess.add(processDetails.get(i));
                            enterGraphing.add(new Graph(processDetails.get(i).getProcessName(),j));
                        }
                    }

                    int lowPrio = 1000; //default priority set high
                    for(int i = 0 ; i < waitingProcess.size();i++){ 
                        if(lowPrio > waitingProcess.get(i).getPriority() && (waitingProcess.get(i).getStatus())){ //get Lowest Priority
                            lowPrio = waitingProcess.get(i).getPriority();
                        }
                    }

                    for(int i = 0 ; i <waitingProcess.size() ; i++){
                        if(waitingProcess.get(i).getPriority() == lowPrio && (waitingProcess.get(i).getStatus())){ //get Process with lowest priority and active.
                            waitingProcess.get(i).minusTime(); //minus 1
                            processGraphing.add(new Graph(waitingProcess.get(i).getProcessName(), j+1));
                            if(waitingProcess.get(i).getRemainingTime() == 0) //if the process end set status to false
                            {
                                waitingProcess.get(i).setStatusFalse(); 
                                for(int z = 0; z < processDetails.size(); z++){
                                    if (processDetails.get(z).getProcessName() == waitingProcess.get(i).getProcessName())
                                        processDetails.get(z).setCompletionTime(j+1);
                                }
                            }
                            break; //run once if same prio
                        }
                    }
                    arrivalTime++;
                }
                //remove both duplicate from all the process.
                for(int i = processGraphing.size()-1; i > 0 ; i--){
                    if(processGraphing.get(i).getProcess().equals(processGraphing.get(i-1).getProcess())){
                        processGraphing.remove(i-1);
                    }
                }

                for (int i = 0; i < processGraphing.size(); i++){
                    for(int h = 1; h < (processGraphing.size()-i); h++){
                        //STOP HERE
                        if(processGraphing.get(h-1).getTime() > processGraphing.get(h).getTime()){
                            Graph temp = processGraphing.get(h-1);
                            processGraphing.add(h-1, processGraphing.get(h));
                            processGraphing.remove(h);
                            processGraphing.add(h, temp);
                            processGraphing.remove(h+1);
                        }
                    }
                }
                report("Preemptive Priority", minArrivalTime, processGraphing);
                
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(new JFrame(),"Some input were missing.","Alert",JOptionPane.WARNING_MESSAGE);     
            }
        }
    }

    public void report(String name, int minArrivalTime ,ArrayList<Graph>processGraphing){
        JFrame result = new JFrame(name);
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel(new GridLayout(process + 1,6));

        //Graph
        String line ="<html><pre>";
        line += "   +";
        for(int i = 0; i < processGraphing.size();i++){
            line += "------+";
        }
        line+="   <br />";
        line += "   |";
        for(int i = 0; i < processGraphing.size();i++){
            line  = line + processGraphing.get(i).getProcess() + "    |";
        }

        line += "   <br />";

        line += "   +";
        for(int i = 0; i < processGraphing.size();i++){
            line += "------+";
        }
        line+="   <br />";
        line += "   " + minArrivalTime;
        for(int i = 0; i < processGraphing.size();i++){
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format("     %2d",processGraphing.get(i).getTime());
            line += (sbuf.toString());
            fmt.close();
        }
        line += "   ";

        JLabel graph = new JLabel(line);
        graph.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        String top[] = {"Process","Arrival Time","Burst Time","Finishing Time","Turn Around Time","Waiting Time"};
        for(int i = 0 ; i < 6 ; i++){
            JLabel label = new JLabel(top[i]);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            middlePanel.add(label);
        }
        
        for(int i = 0 ; i < processDetails.size(); i++){
            JLabel label = new JLabel(processDetails.get(i).getProcessName());
            JLabel label1 = new JLabel(String.valueOf(processDetails.get(i).getArrivalTime()));
            JLabel label2 = new JLabel(String.valueOf(processDetails.get(i).getBurstTime()));
            JLabel label3 = new JLabel(String.valueOf(processDetails.get(i).getFinishingTime()));
            JLabel label4 = new JLabel(String.valueOf(processDetails.get(i).getTurnAroundTime()));
            JLabel label5 = new JLabel(String.valueOf(processDetails.get(i).getWaitingTime()));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            middlePanel.add(label);
            middlePanel.add(label1);
            middlePanel.add(label2);
            middlePanel.add(label3);
            middlePanel.add(label4);
            middlePanel.add(label5);
        }

        JPanel bottomPanel = new JPanel();
        JLabel line1 = new JLabel();
        String calculation = "<html>";

        //b) Total and Average Turnaround time for the entire processes
        float totalTurnAroundTime = 0;
        for(int i = 0 ; i < processDetails.size(); i++)
            totalTurnAroundTime += processDetails.get(i).getTurnAroundTime();
        //d) Total and Average Waiting time for the entire processes
        float totalWaitingTime = 0; 
        for(int i = 0 ; i < processDetails.size(); i++)
            totalWaitingTime += processDetails.get(i).getWaitingTime();

        calculation += "Total Turnaround time => "+ totalTurnAroundTime;
        calculation+="<br />";
        calculation += "Average Turnaround time => "+ String.format("%.2f", totalTurnAroundTime/processDetails.size());
        calculation+="<br />";
        calculation += "Total Waiting time => "+ totalWaitingTime;
        calculation+="<br />";
        calculation += "Average Waiting time => "+ String.format("%.2f", totalWaitingTime/processDetails.size());

        topPanel.add(graph);
        result.add(topPanel);
        line1.setText(calculation);
        bottomPanel.add(line1);
        result.add(topPanel,BorderLayout.NORTH);
        result.add(middlePanel,BorderLayout.CENTER);
        result.add(bottomPanel,BorderLayout.SOUTH);
        result.setSize(900,350);
        result.setVisible(true);
    }

    public void input() throws NumberFormatException{
        processDetails.clear();
        int j = start;
        for(int i = 0 ; i < process*3 ; i+=3){
            String burstTime = textbox.get(i).getText();
            String arrivalTime = textbox.get(i+1).getText();
            String priority = textbox.get(i+2).getText();
            processDetails.add(new Process("P"+(j), Integer.parseInt(burstTime),Integer.parseInt(arrivalTime),Integer.parseInt(priority)));
            j++;
        }
        for(int i = 0 ; i< processDetails.size();i++)
            System.out.println(processDetails.get(i));
    }
}
