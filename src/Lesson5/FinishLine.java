package Lesson5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class FinishLine extends Stage{
    Semaphore finisher;
    private AtomicInteger placeNum = new AtomicInteger(0);

    public FinishLine(Semaphore finisher) {
        this.finisher = finisher;
    }

    @Override
    public void go(Car c) {
        try {
            finisher.acquire();
            System.out.println(c.getName() + " пересек финишную черту");
            placeNum.addAndGet(1);
            if (placeNum.get() == 1) {
                System.out.println(c.getName() + " выиграл гонку!");
            } else {
                System.out.println(c.getName() + " финишировал на " + placeNum.get() + " месте");
            }
            finisher.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
