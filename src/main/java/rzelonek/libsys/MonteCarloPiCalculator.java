package rzelonek.libsys;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MonteCarloPiCalculator {

    public static double calculatePi(int totalPoints) {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Number of available processor cores
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        int pointsPerThread = totalPoints / numThreads;
        int pointsInCircle = 0;

        try {
            List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < numThreads; i++) {
                Callable<Integer> task = new PiCalculatorTask(pointsPerThread);
                futures.add(executor.submit(task));
            }

            for (Future<Integer> future : futures) {
                pointsInCircle += future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        double pi = 4.0 * pointsInCircle / totalPoints;
        return pi;
    }

    private static class PiCalculatorTask implements Callable<Integer> {
        private final int pointsToGenerate;

        public PiCalculatorTask(int pointsToGenerate) {
            this.pointsToGenerate = pointsToGenerate;
        }

        @Override
        public Integer call() {
            int pointsInCircle = 0;
            for (int i = 0; i < pointsToGenerate; i++) {
                double x = Math.random() * 2 - 1; // Random number between -1 and 1
                double y = Math.random() * 2 - 1; // Random number between -1 and 1
                if (x * x + y * y <= 1) {
                    pointsInCircle++;
                }
            }
            return pointsInCircle;
        }
    }
}
