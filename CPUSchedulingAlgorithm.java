/**
 * @import java.util.* means import all the classes and interfaces within java.util package and make them availabe to use within the current class or interface
 */
import java.util.*;

public class CPUSchedulingAlgorithm{
    public static void main(String[] args){
        /**
         * Some simplified explanation about the Scanner class
         * It is a standard Oracle class which you can use by calling the import java.util.Scanner
         * Creates a new object of type Scanner from the standard input of the program
         */
        Scanner input = new Scanner(System.in);

        int option, number_of_process, w;
        w = 0;

        System.out.println("Choose a Scheduling algorithm you want to apply : \n" +
                "   1. FCFS\n" +
                "   2. SJF\n" +
                "   3. Priority\n"        
        );
        System.out.print("Enter an option: ");
        option = input.nextInt();
        System.out.print("Enter number of process: ");
        number_of_process = input.nextInt();
        System.out.println();

        //Creates an array to store burstTimes and ganntChart of each process
        int[] burstTimes = new int[number_of_process];
        int[] ganttChart = new int[number_of_process];

        //Loops till number of process
        for(int i = 0; i < number_of_process; i++){
            ganttChart[i] = i + 1;
            System.out.print("Enter the burst time for process"+(i+1)+" : ");

            // Accepts burst time and stores it in array.
            burstTimes[i] = input.nextInt();
            
        }
        System.out.println();
        /**
         * Choose a scheduling algorithm by condition
         * when option equal to 1 then compiler excute the FCFS Algorithm(First Come First Serve)
         * And option equal to 2 then compiler excute the SJF Algorithm(Shortest Job First)
         * and last on option equal to 3 then compiler excute the Priority Algorithm
         */
        if(option == 1){
            System.out.println("\nGantt Chart for FCFS Algorithm: ");
            
            //Call the function
            printGanttChartAndProcessTable(ganttChart, burstTimes);
        }
        else if(option == 2){
            int[] duplicateBT = Arrays.copyOfRange(burstTimes, 0, burstTimes.length);

            for(int i = 0; i < number_of_process - 1; i++)
            {
                int minIndex = i;
                for(int j = i+1; j < number_of_process; j++)
                {
                    if(duplicateBT[minIndex] > duplicateBT[j]){
                        minIndex = j;
                    }
                }
                int swap = duplicateBT[i];
                duplicateBT[i] = duplicateBT[minIndex];
                duplicateBT[minIndex] = swap;


                swap = ganttChart[i];
                ganttChart[i] = ganttChart[minIndex];
                ganttChart[minIndex] = swap;
            }
            System.out.println("\nGantt Chart for SJF Algorithm: ");
            printGanttChartAndProcessTable(ganttChart, burstTimes);
        }
        else if(option == 3){
            int[] priority = new int[number_of_process];
            for(int i = 0; i < number_of_process; i++)
            {
                System.out.print("Enter priority for the process " + (i+1) + " : ");
                priority[i] = input.nextInt();
            }
            System.out.println();
            for(int i = 0; i < number_of_process - 1; i++)
            {
                //Finding the smallest number
                int minIndex = i;
                for(int j = i + 1; j < number_of_process; j++)
                {
                    if(priority[i] > priority[j]){
                        minIndex = j;
                    }
                }
                //swaping
                int swap = priority[i];
                priority[i] = priority[minIndex];
                priority[minIndex] = swap;

                swap = ganttChart[i];
                ganttChart[i] = ganttChart[minIndex];
                ganttChart[minIndex] = swap;
            }
            System.out.println("\nGantt Chart for Priority Algorithm: ");
            printGanttChartAndProcessTableForPriority(ganttChart, burstTimes, priority);
        }
        else {
            System.out.println("Please try again.");
        }
    }

    /**
     * @function_name printGanttChartAndProcessTable
     * @return_type void
     * @param ganttChart
     * @param bTime
     */
    static void printGanttChartAndProcessTable(int[] ganttChart, int[] bTime){

        //declare some variable
        int waiting, len, total_waiting_time, total_turn_around_time;

        //initialize 0 for waiting, total_turn_around_time and total_waiting_time
        waiting = total_turn_around_time = total_waiting_time = 0;

        //Find the ganttChart length
        len = ganttChart.length;

        //Accepts waitingTime and stores it in array.
        int[] waitingTime = new int[len+1];

        //System.out.println("Gantt Chart for FCFS Algorithm: ");
        System.out.print(waiting);

        for(int i,j = 0; j < len;  j++){
            i = ganttChart[j];
            waitingTime[i] = waiting;
            waiting += bTime[i-1];
            System.out.print("___P" + ganttChart[j] + "____" + waiting);
        }
        //Displays the heading
        System.out.println("\n\nProcess\t\tBurst Time\tWaiting Time\tTurn Around Time");

        for(int i = 0; i < len; i++)
        {
            int b = bTime[i];
            waiting = waitingTime[i+1];
            int t_a_t = b + waiting;

            //Displays each process information
            System.out.println("P" + (i+1) + "\t\t" + b + "\t\t" + waiting + "\t\t" + t_a_t);

            total_waiting_time += waiting;
            total_turn_around_time += t_a_t;
        }
        //Calculates and displays average waiting time and turn around time
        System.out.println("\nAverage Waiting Time: " +(total_waiting_time / (len + .0)));
        System.out.println("Average Turn Around Time: " + (total_turn_around_time / (len + .0)));
    }
    /**
     * @function_name printGanttChartAndProcessTableForPriority
     * @return_type void 
     * @param ganttChart
     * @param bTime
     * @param priority
     */
    static void printGanttChartAndProcessTableForPriority(int[] ganttChart, int[] bTime, int[] priority){
        int waiting, len, total_waiting_time, total_turn_around_time;
        waiting = total_turn_around_time = total_waiting_time = 0;
        len = ganttChart.length;

        //
        int[] waitingTime = new int[len+1];
        System.out.print(waiting);

        for(int i,j = 0; j < len;  j++){
            i = ganttChart[j];
            waitingTime[i] = waiting;
            waiting += bTime[i-1];
            System.out.print("____P" + ganttChart[j] + "____" + waiting);
        }
        //Displays the heading
        System.out.println("\n\nProcess\t\tBurst Time\tPriority\tWaiting Time\tTurn Around Time");

        for(int i = 0; i < len; i++)
        {
            int b = bTime[i];
            waiting = waitingTime[i+1];
            int t_a_t = b + waiting;
            int priority1 = priority[i];
    
            //Here print the process id, Burst Time, Priority, Waiting time and Turn Around Tiem         
            System.out.println("P" + (i+1) + "\t\t" + b + "\t\t" + priority1 + "\t\t" + waiting + "\t\t\t" + t_a_t);
            
            //Here finding the total_waiting_time and total_turn_around_time 
            total_waiting_time += waiting;
            total_turn_around_time += t_a_t;
        }
        System.out.println("\nAverage Waiting Time: " +(total_waiting_time / (len + .0)));
        System.out.println("Average Turn Around Time: " + (total_turn_around_time / (len + .0)));
    }
}