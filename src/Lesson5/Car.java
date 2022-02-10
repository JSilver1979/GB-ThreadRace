package Lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


public class Car implements Runnable {
    private static int CARS_COUNT;
    private static volatile AtomicBoolean isWinner = new AtomicBoolean(false);
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;

    CountDownLatch prepRace;
    CountDownLatch finRace;

    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CountDownLatch prepRace, CountDownLatch finRace) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.prepRace = prepRace;
        this.finRace = finRace;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            prepRace.countDown();
            System.out.println(this.name + " готов");
            prepRace.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        finRace.countDown();
        if (!isWinner.getAndSet(true)) {
            System.out.println(this.name + " Победитель!!!");
        }
    }
}
