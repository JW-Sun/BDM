import com.jw.bigwhalemonitor.util.SchedulerUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

public class QuartzTest implements Job {

    public QuartzTest() {}

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("caonima");
    }

    public static void main(String[] args) throws SchedulerException {
        SchedulerUtils.schedulerCronJob(QuartzTest.class, "0/2 * * * * ?");
    }
}