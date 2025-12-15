package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ArrayCompressor {

        /**
         * Удаляет подряд идущие дубликаты из массива чисел
         * @param numbers исходный массив чисел
         * @return новый массив без подряд идущих дубликатов
         */
        public static int[] compressNumbers(int[] numbers) {
            if (numbers == null || numbers.length == 0) {
                return new int[0];
            }

            List<Integer> result = new ArrayList<>();
            result.add(numbers[0]);

            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] != numbers[i - 1]) {
                    result.add(numbers[i]);
                }
            }

            // Конвертируем List<Integer> в int[]
            return result.stream().mapToInt(Integer::intValue).toArray();
        }

        /**
         * Альтернативная реализация с использованием массива фиксированного размера
         */
        public static int[] compressNumbersOptimized(int[] numbers) {
            if (numbers == null || numbers.length == 0) {
                return new int[0];
            }

            int uniqueCount = 1;
            // Сначала считаем количество уникальных подряд идущих элементов
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] != numbers[i - 1]) {
                    uniqueCount++;
                }
            }

            // Создаем массив нужного размера
            int[] result = new int[uniqueCount];
            result[0] = numbers[0];

            int index = 1;
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] != numbers[i - 1]) {
                    result[index++] = numbers[i];
                }
            }

            return result;
        }

    public static int[] compressNumbersStream(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return new int[0];
        }

        return IntStream.range(0, numbers.length)
                .filter(i -> i == 0 || numbers[i] != numbers[i - 1])
                .map(i -> numbers[i])
                .toArray();
    }
    }
