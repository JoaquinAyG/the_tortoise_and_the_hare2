import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {

        Semaphore hareSemaphore = new Semaphore(1);
        Semaphore tortoiseSemaphore = new Semaphore(1);
        Semaphore winnerSemaphore = new Semaphore(0);
        Hare hare = new Hare(hareSemaphore, winnerSemaphore, "Hare1");
        Tortoise tortoise = new Tortoise(tortoiseSemaphore, winnerSemaphore, "Tortoise1");
        Hare hare2 = new Hare(hareSemaphore, winnerSemaphore, "Hare2");
        Tortoise tortoise2 = new Tortoise(tortoiseSemaphore, winnerSemaphore, "Tortoise2");

        tortoise.start();
        hare.start();
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hare2.start();
        tortoise2.start();

        try {
            winnerSemaphore.acquire();
            winnerSemaphore.acquire();
            if(!tortoise2.getEndFlag() && !hare2.getEndFlag()) {
                winnerSemaphore.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(hare2.getEndFlag()) {
            tortoise.setEndFlag(true);
            tortoise2.setEndFlag(true);
            tortoiseSemaphore.release();
            System.out.println("Hare wins!");
        } else {
            hare.setEndFlag(true);
            hare2.setEndFlag(true);
            hareSemaphore.release();
            System.out.println("Tortoise wins!");
        }
    }
}