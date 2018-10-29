/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JobScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 *
 * @author Shawn
 */
public class PaymentReminderScheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler.scheduleAtFixedRate(new PaymentReminderJob(event.getServletContext().getRealPath("/temp")), 0, 1, TimeUnit.MINUTES);
    }
}
