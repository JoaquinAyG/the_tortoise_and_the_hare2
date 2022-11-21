import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Tortoise extends Thread{

    private int position = 0;
    private final Semaphore tortoiseSemaphore;
    private final Semaphore winnerSemaphore;
    private boolean endFlag = false;
    private String name;

    public Tortoise(Semaphore tortoiseSemaphore, Semaphore winnerSemaphore, String name) {
        this.tortoiseSemaphore = tortoiseSemaphore;
        this.winnerSemaphore = winnerSemaphore;
        this.name = name;
    }

    public void run() {
        try {
            tortoiseSemaphore.acquire();
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
                tortoiseSemaphore.release();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int step() {
        return switch (ThreadLocalRandom.current().nextInt(10)) {
            case 0, 1, 2, 3, 4 -> 3;
            case 5, 6 -> -6;
            case 7, 8, 9 -> 1;
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
