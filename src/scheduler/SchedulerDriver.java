package scheduler;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Simple scheduler to print the current time. This code can be modified to perform
 * different tasks. This program uses Quartz scheduler.
 * @author Andrea 
 */
public class SchedulerDriver {

    public static void main(String[] args) {
        try {
          
            URL url = SchedulerDriver.class.getResource("../log4j.xml");
            String file = url.getFile();
            DOMConfigurator.configure(file);
            
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            
            // define the job and tie it to our PrintingJob class
            JobBuilder jobBuilder = JobBuilder.newJob(PrintingJob.class);
            jobBuilder.withIdentity("job1", "group1");
            jobBuilder.storeDurably();
            JobDetail job = jobBuilder.build();
            scheduler.addJob(job, false);
            
            
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity("printingJobTrigger", "group1");

            // TODO: possibly build cron job based on config file?
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("* 0/1 * * * ?"));
            triggerBuilder.forJob(job);
            
            scheduler.scheduleJob(triggerBuilder.build());

            triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity("printingJobTriggerStartup", "group1");
            triggerBuilder.forJob(job);
            scheduler.scheduleJob(triggerBuilder.build());
            
            // TODO: create appropriate method for shutting down the scheduler. 
//            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

}
