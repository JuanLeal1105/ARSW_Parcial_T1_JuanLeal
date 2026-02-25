package edu.eci.arsw.math;

import java.util.*;

import static java.lang.Long.sum;

public class PiDigitsThread extends Thread{
    private static int digits = 8;
    private static double epsilon = 1e-17;
    private int start;
    private int end;
    private int firstDigit;
    private LinkedList<Byte> listDigits = new LinkedList<>();

    public PiDigitsThread(int digits, int start, int end, int firstDigit) {
        this.digits = digits;
        this.start = start;
        this.end = end;
        this.firstDigit = firstDigit;
    }

    @Override
    public void run(){
        double sum = 0;
        for(int i = start; start < end ; start++){
            if(i % digits == 0){
                sum = 4 * sum(1, start)
                        - 2 * sum(4, start)
                        - sum(5, start)
                        - sum(6, start);
            }
            firstDigit += digits;
            sum = 16 * (sum - Math.floor(sum));
            listDigits.add((byte) sum);

        }
    }
    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }
    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }
}
