public class Task1 {


    public static void main(String[] args) {
        Object monitor = new Object();

        Thread timerThread = new Thread(new TimerRunnable(monitor));
        Thread notifierThread = new Thread(new NotifierRunnable(monitor));

        timerThread.start();
        notifierThread.start();
    }
}

class TimerRunnable implements Runnable {
    private Object monitor;

    public TimerRunnable(Object monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        int seconds = 0;
        while (true) {
            try {
                Thread.sleep(1000);  // ждем 1 секунду
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds++;
            System.out.println("Прошло " + seconds + " секунд");

            if (seconds % 5 == 0) {
                synchronized (monitor) {
                    monitor.notify();
                }
            }
        }
    }
}

class NotifierRunnable implements Runnable {
    private Object monitor;

    public NotifierRunnable(Object monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                synchronized (monitor) {
                    monitor.wait();
                    System.out.println("Прошло 5 секунд");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}