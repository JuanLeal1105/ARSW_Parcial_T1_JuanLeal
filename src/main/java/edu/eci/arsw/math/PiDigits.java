package edu.eci.arsw.math;

import java.util.LinkedList;
import java.util.List;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;
    private static List<PiDigitsThread> threads = new LinkedList<>();

    
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int N) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        }
        LinkedList<Byte> listDigits = new LinkedList<>();
        newThreads(start, count, N);
        for (PiDigitsThread t : threads){
            try{
                t.join();
                listDigits.addAll(t.getListDigits());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return linkedToArray(count, listDigits);
    }

    private static void newThreads(int start, int count, int N){
        int numDigits = count / N;
        for (int i = 0; i < N; i++){
            int startDigit = i * DigitsPerSum;
            int interval = i * DigitsPerSum;
            int endInterval = (i == N-1) ? count : start + numDigits;
            PiDigitsThread thread = new PiDigitsThread(startDigit, interval, endInterval, count);
            threads.add(thread);
            thread.start();
        }
    }
    public static byte[] linkedToArray (int count, LinkedList<Byte> list){
        byte[] bytes = new byte[count];
        int i = 0;
        for (Byte b : list){
            bytes[i++] = b;
        }
        return bytes;
    }


}
