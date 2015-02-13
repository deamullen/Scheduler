package scheduler;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@PersistJobDataAfterExecution
public class PrintingJob implements Job {

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        
        //The code for the method to be performed goes here
        Date date = new Date();
        System.out.println("Current time: " + date);
    }
}