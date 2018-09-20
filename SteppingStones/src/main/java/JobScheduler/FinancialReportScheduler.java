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
 * @author Riana
 */
public class FinancialReportScheduler implements ServletContextListener{
    private ScheduledExecutorService scheduler;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        String a = event.getServletContext().getRealPath("/temp");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new FinancialReportJob(event.getServletContext().getRealPath("/temp")), 0, 1, TimeUnit.DAYS);
        //scheduler.scheduleAtFixedRate(new FinancialReportJob(event.getServletContext().getRealPath("/temp")), 0, 1, TimeUnit.HOURS);
        
        //use this when debugging
        //scheduler.scheduleAtFixedRate(new FinancialReportJob(event.getServletContext().getRealPath("/temp")), 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    
}
