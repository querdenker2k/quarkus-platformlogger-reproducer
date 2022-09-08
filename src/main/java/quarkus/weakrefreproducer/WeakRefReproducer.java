package quarkus.weakrefreproducer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import javax.inject.Singleton;
import sun.util.logging.PlatformLogger;

@QuarkusMain
public class WeakRefReproducer {
    public static void main(String[] args) {
        Quarkus.run(Inner.class, args);
    }

    @Singleton
    public static class Inner implements QuarkusApplication {
        void doIt() {
            for (int i = 0; i < 100; i++) {
                PlatformLogger.getLogger(WeakRefReproducer.class.getName());
                if (i % 20 == 0) {
                    System.gc();
                    System.gc();
                    System.gc();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // there are a few LoggerWeakRef instances inside which do not have a logger anymore. this causes a memory leak.
            System.out.println("evaluate this expression: ((RootLogger)((Logger)((JULWrapper)((PlatformLogger)PlatformLogger.getLogger(WeakRefReproducer.class.getName())).loggerProxy).julLogger).parent).kids.get(1).get()");
            // TODO: evaluate this expression
//            ((RootLogger)((Logger)((JULWrapper)((PlatformLogger)PlatformLogger.getLogger(WeakRefReproducer.class.getName())).loggerProxy).julLogger).parent).kids.get(1).get()
        }

        @Override
        public int run(String... args) {
            doIt();
            return 0;
        }
    }
}
