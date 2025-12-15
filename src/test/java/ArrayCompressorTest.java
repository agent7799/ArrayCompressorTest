import org.example.ArrayCompressor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ArrayCompressorTest {

    @Test
    @DisplayName("Базовый тест: удаление подряд идущих дубликатов")
    void testBasicCompression() {
        int[] input = {1, 1, 2, 2, 3};
        int[] expected = {1, 2, 3};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив [1, 1, 2, 2, 3] должен сжаться в [1, 2, 3]");
    }

    @Test
    @DisplayName("Тест с чередующимися значениями")
    void testAlternatingValues() {
        int[] input = {0, 0, 1, 1, 0};
        int[] expected = {0, 1, 0};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив [0, 0, 1, 1, 0] должен сжаться в [0, 1, 0]");
    }

    @Test
    @DisplayName("Пустой массив")
    void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Пустой массив должен остаться пустым");
    }

    @Test
    @DisplayName("Один элемент")
    void testSingleElement() {
        int[] input = {5};
        int[] expected = {5};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив с одним элементом должен остаться без изменений");
    }

    @Test
    @DisplayName("Все элементы одинаковые")
    void testAllSameElements() {
        int[] input = {3, 3, 3, 3, 3};
        int[] expected = {3};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив [3, 3, 3, 3, 3] должен сжаться в [3]");
    }

    @Test
    @DisplayName("Без дубликатов")
    void testNoDuplicates() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив без дубликатов должен остаться без изменений");
    }

    @Test
    @DisplayName("Длинный массив с разными паттернами")
    void testLongArray() {
        int[] input = {1, 1, 2, 3, 3, 3, 4, 4, 5, 6, 6, 7, 7, 7, 7};
        int[] expected = {1, 2, 3, 4, 5, 6, 7};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Длинный массив с разными паттернами должен корректно сжаться");
    }

    @Test
    @DisplayName("Отрицательные числа")
    void testNegativeNumbers() {
        int[] input = {-5, -5, -3, -3, -3, 0, 0, 2, 2};
        int[] expected = {-5, -3, 0, 2};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив с отрицательными числами должен корректно сжаться");
    }

    @Test
    @DisplayName("Большие числа")
    void testLargeNumbers() {
        int[] input = {1000000, 1000000, 2000000, 2000000, 3000000};
        int[] expected = {1000000, 2000000, 3000000};
        int[] result = ArrayCompressor.compressNumbers(input);
        assertArrayEquals(expected, result,
                "Массив с большими числами должен корректно сжаться");
    }

    @Test
    @DisplayName("Null на входе")
    void testNullInput() {
        int[] result = ArrayCompressor.compressNumbers(null);
        assertNotNull(result, "При null должен возвращаться пустой массив, а не null");
        assertEquals(0, result.length, "При null должен возвращаться пустой массив");
    }

    @Test
    @DisplayName("Сравнение двух реализаций")
    void testBothImplementations() {
        int[][] testCases = {
                {1, 1, 2, 2, 3},
                {0, 0, 1, 1, 0},
                {},
                {5},
                {3, 3, 3, 3, 3},
                {1, 2, 3, 4, 5},
                {-5, -5, -3, -3, -3, 0, 0, 2, 2}
        };

        for (int[] testCase : testCases) {
            int[] result1 = ArrayCompressor.compressNumbers(testCase);
            int[] result2 = ArrayCompressor.compressNumbersOptimized(testCase);
            assertArrayEquals(result1, result2,
                    "Обе реализации должны давать одинаковый результат для: " +
                            Arrays.toString(testCase));
        }
    }

    @Test
    @DisplayName("Производительность: массив из 1 млн элементов")
    void testPerformance() {
        // Генерируем большой массив для теста производительности
        int size = 1000000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = i / 100;  // Каждые 100 элементов одинаковые
        }

        long startTime = System.currentTimeMillis();
        int[] result = ArrayCompressor.compressNumbers(largeArray);
        long endTime = System.currentTimeMillis();

        long startTimeOptimezed = System.currentTimeMillis();
        int[] resultOptimized = ArrayCompressor.compressNumbersOptimized(largeArray);
        long endTimeOptimized = System.currentTimeMillis();

        long startTimeStream = System.currentTimeMillis();
        int[] resultStream = ArrayCompressor.compressNumbersStream(largeArray);
        long endTimeStream = System.currentTimeMillis();

        // Проверяем, что результат правильный
        assertEquals(10000, result.length,
                "Массив из 1 млн элементов должен сжаться до 10000 элементов");
        assertEquals(10000, resultOptimized.length,
                "Массив из 1 млн элементов должен сжаться до 10000 элементов");
        assertEquals(10000, resultStream.length,
                "Массив из 1 млн элементов должен сжаться до 10000 элементов");

        System.out.println("Время выполнения для 1 млн элементов: " +
                (endTime - startTime) + " мс");
        assertTrue((endTime - startTime) < 1000,
                "Оптимизированная реализация должна обрабатывать 1 млн элементов менее чем за 1 секунду");

        System.out.println("Время выполнения для 1 млн элементов (Optimized): " +
                (endTimeStream - startTimeStream) + " мс");
        assertTrue((endTimeStream - startTimeStream) < 1000,
                "Оптимизированная реализация должна обрабатывать 1 млн элементов менее чем за 1 секунду");

        System.out.println("Время выполнения для 1 млн элементов (Stream): " +
                (endTimeOptimized - startTimeOptimezed) + " мс");
        assertTrue((endTimeOptimized - startTimeOptimezed) < 1000,
                "Оптимизированная реализация должна обрабатывать 1 млн элементов менее чем за 1 секунду");
    }
}