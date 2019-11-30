package lesson6;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();
        if(firstLength == 0 || secondLength == 0) return "";
        int[][] LCS = new int[firstLength + 1][secondLength + 1];

        for (int i = 0; i < firstLength; i++) {
            for (int j = 0; j < secondLength; j++) {
                if (first.charAt(i) == second.charAt(j)) {
                    LCS[i+1][j+1] = LCS[i][j] + 1;
                } else {
                    LCS[i+1][j+1] = Math.max(LCS[i][j+1], LCS[i+1][j]);
                }
            }
        }

        StringBuffer result = new StringBuffer();
        int bufI = firstLength;
        int bufJ = secondLength;
        while( bufI != 0 && bufJ != 0) {
            if(LCS[bufI][bufJ] == LCS[bufI-1][bufJ]) {
                bufI--;
            }
            else if(LCS[bufI][bufJ] == LCS[bufI][bufJ-1]) {
                bufJ--;
            }
            else {
                result.append(first.charAt(bufI - 1));
                bufI--;
                bufJ--;
            }
        }
        return result.reverse().toString();
    }
    //Time O(length(first) * length(second))
    //Memory O(length(first) * length(second))

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        int n = list.size();

        if(n == 1 || n == 0) {
            return list;
        }

        int subSequenceLength = -1;

        int[] subsequence = new int[n];
        int[] indexes = new int[n];

        for (int i = 0; i < list.size(); ++i) {
            subsequence[i] = Integer.MAX_VALUE;
        }

        subsequence[0] = list.get(0);
        indexes[0] = 0;

        // алгоритм поиска подпоследовательности
        // Time Complexity(n*logn)
        // Memory O(n)
        for (int i = 1; i < list.size(); ++i) {
            indexes[i] = customBinarySearch(subsequence, 0, i, list.get(i));
            if (subSequenceLength < indexes[i]) {
                subSequenceLength = indexes[i];
            }
        }

        //выбор подпоследовательности удовлетворяющей условию задачи
        //Time complexity O(subSequenceLength * indexes.length)
        //Memory O(list.size())
        ArrayList<Integer> resultList = new ArrayList<>();
        int currentInd = subSequenceLength;
        int currentNum = 0;
        int previousNum = Integer.MAX_VALUE;
        for (int i = subSequenceLength; i >= 0; i--) {
            for (int j = indexes.length - 1; j >= 0; j--) {
                if (currentInd == indexes[j] && list.get(j) < previousNum) {
                    currentNum = list.get(j);
                }
            }
            previousNum = currentNum;
            resultList.add(currentNum);
            currentInd--;
        }
        Collections.reverse(resultList);
        return (resultList);
    }

    static int customBinarySearch(int subsequence[],
                                  int startLeft,
                                  int startRight,
                                  int key){

        int mid = 0;
        int left = startLeft;
        int right = startRight;
        int ceilIndex = 0;
        boolean ceilIndexFound = false;

        for (mid = (left + right) / 2; left <= right && !ceilIndexFound; mid = (left + right) / 2) {
            if (subsequence[mid] > key) {
                right = mid - 1;
            }
            else if (subsequence[mid] == key) {
                ceilIndex = mid;
                ceilIndexFound = true;
            }
            else if (mid + 1 <= right && subsequence[mid + 1] >= key) {
                subsequence[mid + 1] = key;
                ceilIndex = mid + 1;
                ceilIndexFound = true;
            } else {
                left = mid + 1;
            }
        }

        if (!ceilIndexFound) {
            if (mid == left) {
                subsequence[mid] = key;
                ceilIndex = mid;
            }
            else {
                subsequence[mid + 1] = key;
                ceilIndex = mid + 1;
            }
        }

        return ceilIndex;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) throws IOException {
        FileReader reader = new FileReader(inputName);
        BufferedReader scan = new BufferedReader(reader);
        String keeper;
        ArrayList<String> inputs = new ArrayList<>();
        while ((keeper = scan.readLine()) != null) {
           inputs.add(keeper);
        }
        int lines = inputs.size();
        int columns = inputs.get(0).split(" ").length;
        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            String[] bufferArray = inputs.get(i).split(" ");
            for (int j = 0; j < bufferArray.length; j++) {
            matrix[i][j] = Integer.parseInt(bufferArray[j]);
            }
        }

        for(int i=0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if(i>0 && j>0){
                    matrix[i][j]+=Math.min(Math.min(matrix[i-1][j], matrix[i][j-1]), matrix[i-1][j-1]);
                }else{
                    if(i>0){
                        matrix[i][j]+=matrix[i-1][j];
                    }else if(j>0){
                        matrix[i][j]+=matrix[i][j-1];
                    }
                }
            }
        }
        return matrix[lines-1][columns-1];
    }
    //Time O(lines*columns)
    //Memory O(lines*columns)

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
