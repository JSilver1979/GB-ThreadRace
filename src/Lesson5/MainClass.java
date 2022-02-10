package Lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class MainClass {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        CountDownLatch prepRace = new CountDownLatch(CARS_COUNT);
        CountDownLatch startRace = new CountDownLatch(1);
        CountDownLatch finRace = new CountDownLatch(CARS_COUNT);
        Semaphore tunnelCapacity = new Semaphore(CARS_COUNT/2);
        Semaphore finisher = new Semaphore(1);
        Race race = new Race(new Road(60), new FinishLine(finisher));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), prepRace, startRace, finRace);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            prepRace.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            startRace.countDown();
            finRace.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
