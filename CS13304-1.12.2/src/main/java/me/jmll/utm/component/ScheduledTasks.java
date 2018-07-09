package me.jmll.utm.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 2 (a) Agregar la anotación correcta para que sea
 *  cargada por el contexto raíz.
 * **/
// Escribe tu código aquí {
@Component
// }
public class ScheduledTasks {
	private static final Logger logger = LogManager.getLogger();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Ejecución por medio de intervalo
     * */
    //@Scheduled(fixedRate = 8000)
    public void reportCurrentTimeRate() {
        logger.info("fixedRate: The time is now {}", dateFormat.format(new Date()));
    }
    /**
     * Ejecución al completar la tarea anterior
     * */   
    //@Scheduled(fixedDelay = 5000)
    public void reportCurrentTimeDelay() {
        logger.info("fixedDelay: The time is now {}", dateFormat.format(new Date()));
    }
    /**
     * Ejecución por medio de intervalo con formato Cron
     * */
    //@Scheduled(cron = "*/10 * * * * ?")
    public void reportCurrentTimeCron() {
        logger.info("Cron Scheduled: The time is now {}", dateFormat.format(new Date()));
    }
}