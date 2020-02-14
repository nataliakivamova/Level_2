package Lesson5;

import java.util.Arrays;

public class Main {

    private static final int size = 1000000;
    private static final int h = size / 2;

    public static void main(String[] args) {

        FirstVersion(MyArray());
        SecondVersion(MyArray());
    }

    private static float[] MyArray() {
        float[] arr = new float[size];
        Arrays.fill(arr, 1);
        return arr;
    }

    private static void FirstVersion(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++){
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
        }
        System.out.print("Время выполнения расчета для первого варианта составило: ");
        System.out.println(System.currentTimeMillis() - a);
    }

    private static void SecondVersion(float[] arr) {
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];
        Thread calculationOne = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++){
                arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
            }
        });

        Thread calculationTwo = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++){
                arr2[i] = (float)(arr2[i] * Math.sin(0.2f + i / 5.0) * Math.cos(0.2f + i / 5.0) * Math.cos(0.4f + i / 2.0));
            }
        });

        long b = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);
        calculationOne.start();
        calculationTwo.start();

        try {
            calculationOne.join();
            calculationTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
        System.out.print("Время выполнения расчета для второго варианта составило: ");
        System.out.println(System.currentTimeMillis() - b);
    }
}
