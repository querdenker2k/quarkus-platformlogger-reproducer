package quarkus.weakrefreproducer;

import sun.util.logging.PlatformLogger;

public class WeakRefReproducerPlainJava {
    public static void main(String[] args) {
        while (true) {
            PlatformLogger logger = PlatformLogger.getLogger(WeakRefReproducer.class.getName());
            logger.info("log with platform-logger");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
