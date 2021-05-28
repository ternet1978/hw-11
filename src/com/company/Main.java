package com.company;

public class Main {
    volatile static int time = 0;
    volatile static String threadNumber;
    volatile static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        System.out.println("Task 1\n");
        Thread secondThread = new Thread(() -> {
            int oneSecondCopy = time;
            while (!Thread.interrupted()) {
                if (time <= 30)
                    synchronized (Thread.currentThread()) {
                        try {
                            Thread.currentThread().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                if (oneSecondCopy != time) {
                    oneSecondCopy = time;
                    if (time % 5 == 0) System.out.println("Прошло 5 секунд");
                }
            }
        });
        secondThread.start();
        for (int i = 0; i <= 30; i++) {
            Thread.sleep(1000);
            time = time + 1;
            System.out.println(time + " second");
            synchronized (secondThread) {
                secondThread.notify();
            }
        }
        secondThread.interrupt();

        System.out.println("\nTask 2\n");
        Thread threadA = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (threadNumber.equals("A"))
                    synchronized (Thread.currentThread()) {
                        fizz();
                    }
            }
        });
        Thread threadB = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (threadNumber.equals("B"))
                    synchronized (Thread.currentThread()) {
                        buzz();
                    }
            }
        });
        Thread threadC = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (threadNumber.equals("C"))
                    synchronized (Thread.currentThread()) {
                        fizzbuzz();
                    }
            }
        });
        Thread threadD = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (threadNumber.equals("D"))
                    synchronized (Thread.currentThread()) {
                        number();
                    }
            }
        });
        int number = 15;
        count = 1;
        threadNumber = "C";
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        while (threadNumber != "Z") {
        }
        threadA.interrupt();
        threadB.interrupt();
        threadC.interrupt();
        threadD.interrupt();
    }

    static private void fizz() {
        if (count % 3 == 0) {
            print("fizz");

            if (count < 15) {
                count++;
                threadNumber = "C";
            } else {
                threadNumber = "Z";
            }
        } else {
            threadNumber = "B";
        }
    }

    static private void buzz() {

        if (count % 5 == 0) {
            print("buzz");
            if (count < 15) {
                count++;
                threadNumber = "C";
            } else {
                threadNumber = "Z";
            }
        } else {
            threadNumber = "D";
        }


    }

    static private void fizzbuzz() {
        if ((count % 5 == 0) & (count % 3 == 0)) {
            print("fizzbuzz");

            if (count < 15) {
                count++;
            } else {
                threadNumber = "Z";
            }

        } else {
            threadNumber = "A";
        }
    }

    static private void number() {
        print(count);
        if (count < 15) {
            count++;
            threadNumber = "C";
        } else {
            threadNumber = "Z";
        }
    }

    private static void print(Object string) {
        System.out.print(string);
        if (count < 15) System.out.print(", ");
    }

}