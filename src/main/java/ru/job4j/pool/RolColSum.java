package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * CompletableFuture. Асинхронное программирование
 * Необходимо подсчитать суммы по строкам и столбцам квадратной матрицы, реализовав последовательную и асинхронную версии
 *
 * @author Ilya Kaltygin
 */
public class RolColSum {

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    /**
     * Последовательное выполнение программы
     * @param matrix Квадратная матрица
     * @return       Массив объектов Sums, ге каждый объект содержит суммы i строки и i столбца
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = getRowAndColSum(matrix, i);
        }
        return sums;
    }

    /**
     * Асинхронное выполнение программы, где i - я задача считает сумму по i столбцу и i строке
     * @param matrix Квадратная матрица
     * @return       Массив объектов Sums, ге каждый объект содержит суммы одной строки и одного столбца
     */
    public static Sums[] asyncSum(int[][] matrix) throws InterruptedException, ExecutionException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            sums[i] = CompletableFuture.supplyAsync(() -> getRowAndColSum(matrix, finalI)).get();
        }
        return sums;
    }

    /**
     * Метод считает сумму по строкам и столбцам
     * @param matrix   Квадратная матрица
     * @param position Индекс, который пермещается по элементам строки и столбца
     * @return         Объект типа Sums, в конструкторе которого будут значения i строки и i столбца
     */
    private static Sums getRowAndColSum(int[][] matrix, int position) {
        int rSum = 0;
        int cSum = 0;
        for (int x = 0; x < matrix.length; x++) {
            rSum += matrix[position][x];
            cSum += matrix[x][position];
        }
        return new Sums(rSum, cSum);
    }
}

