import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Hare extends Thread {
    private int position = 0;
    private final Semaphore hareSemaphore;
    private final Semaphore winnerSemaphore;
    private boolean endFlag = false;
    private String name;
    public Hare(Semaphore hareSemaphore, Semaphore winnerSemaphore, String name) {
        this.hareSemaphore = hareSemaphore;
        this.winnerSemaphore = winnerSemaphore;
        this.name = name;
    }

    public void run() {
        try {
            hareSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!endFlag) {
            position += step();
            position = Math.max(0, position);
            System.out.println(name+ " position: " + position);
            if (position >= 100) {
                endFlag = true;
                winnerSemaphore.release();
                hareSemaphore.release();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private int step() {
        return switch(ThreadLocalRandom.current().nextInt(10)) {
            case 5, 6, 7 ->  9;
            case 8 -> -12;
            case 9 -> 1;
            default -> 0;
        };
    }

    public void setEndFlag(boolean endFlag) {
        this.endFlag = endFlag;
    }

    public boolean getEndFlag() {
        return endFlag;
    }
}
