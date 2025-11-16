public class Task2 {
    public static void main(String[] args) {

        FizzBuzzMultithreaded fb = new FizzBuzzMultithreaded(15);

        Thread threadA = new Thread(fb::fizz);
        Thread threadB = new Thread(fb::buzz);
        Thread threadC = new Thread(fb::fizzbuzz);
        Thread threadD = new Thread(fb::number);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}


class FizzBuzzMultithreaded {
    private final int n;
    private int current = 1;
    private final Object lock = new Object();

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
    }

    public void fizz() {
        while (true) {
            synchronized (lock) {
                if (current > n) return;

                if (!(current % 3 == 0 && current % 5 != 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    continue;
                }

                System.out.println("fizz");

                current++;
                lock.notifyAll();
            }
        }
    }

    public void buzz() {
        while (true) {
            synchronized (lock) {
                if (current > n) return;

                if (!(current % 5 == 0 && current % 3 != 0)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    continue;
                }

                System.out.println("buzz");

                current++;
                lock.notifyAll();
            }
        }
    }

    public void fizzbuzz() {
        while (true) {
            synchronized (lock) {
                if (current > n) return;

                if (!(current % 15 == 0)) {   // делится на 3 и 5
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    continue;
                }

                System.out.println("fizzbuzz");

                current++;
                lock.notifyAll();
            }
        }
    }

    public void number() {
        while (true) {
            synchronized (lock) {
                if (current > n) return;

                if (current % 3 == 0 || current % 5 == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    continue;
                }

                System.out.println(current);

                current++;
                lock.notifyAll();
            }
        }
    }
}