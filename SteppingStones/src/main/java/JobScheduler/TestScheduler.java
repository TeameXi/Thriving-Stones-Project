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
public class TestScheduler implements ServletContextListener {

    private static ScheduledExecutorService scheduler;

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        scheduler = Executors.newScheduledThreadPool(1);
        //scheduler.scheduleAtFixedRate(new TestJob(event.getServletContext().getRealPath("/temp")), 0, 30, TimeUnit.MINUTES);
    }
}