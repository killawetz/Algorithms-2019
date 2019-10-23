package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */

    public static int timeStrToMinute(String time) {  // Время представленное строкой представляю в виде минут прошедших с 00:00
        String[] digitParts = time.split(":|\\s");
        String[] charParts = time.split(" ");
        int result = 0;
        if(charParts[1].matches("AM")) {
            if(digitParts[0].matches("12")) {
                for (int i = 1; i < digitParts.length - 1; i++) {
                        int number = Integer.parseInt(digitParts[i]);
                        result = result * 60 + number;
                    }
                }
                else {
                        for (int n = 1; n < digitParts.length - 1; n++) {
                            int number = Integer.parseInt(digitParts[n]);
                            result = result * 60 + number;
                        }
                result = result + Integer.parseInt(digitParts[0]) * 3600;
            }
        }
        else {
            if (digitParts[0].matches("12")) {
                for (int j = 1; j < digitParts.length - 1; j++) {
                    int number = Integer.parseInt(digitParts[j]);
                    result = result * 60 + number;
                }
            result += 43200;
        }
            else {
                for (int j = 0; j < digitParts.length - 1; j++) {
                    int number = Integer.parseInt(digitParts[j]);
                    result = result * 60 + number;
                }
                result += 43200;
            }
        }
        return result;
    }


    static public void sortTimes(String inputName, String outputName) throws IOException {
        ArrayList<String> inputStrings = new ArrayList<>();
        FileReader reader = new FileReader(inputName);
        Scanner scan = new Scanner(reader);
        while (scan.hasNextLine()) {
            String buffer = scan.nextLine();
            if (buffer.matches("((1[0-2]|0?[1-9]):([0-5][0-9]):([0-5][0-9]) ([AaPp][Mm]))")) {
                inputStrings.add(buffer);
            } else {
                throw new NotImplementedError();
            }
        }
            Collections.sort(inputStrings, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return Integer.compare(timeStrToMinute(o1),timeStrToMinute(o2));
                }
            });

            FileWriter writer = new FileWriter(outputName);
        for (int i = 0; i < inputStrings.size() ; i++) {
            writer.write(inputStrings.get(i) + System.getProperty("line.separator"));
        }
        writer.close();
    }

  // Сложность алгоритма O(logN*N)
    // память O(n)

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws FileNotFoundException {
/*
        problema s kirilicey
*/
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        FileReader reader = new FileReader(inputName);
        BufferedReader scan = new BufferedReader(reader);
        ArrayList<Double> inputs = new ArrayList<>();
        String keeper;
        while((keeper = scan.readLine()) != null) {
            double bufferStr = Double.parseDouble(keeper);
            inputs.add(bufferStr);
        }

        Collections.sort(inputs, new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1.compareTo(o2);
            }
        });
        File file = new File(outputName);
        FileWriter fileReader = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileReader);
        for (int i = 0; i < inputs.size() ; i++) {
            writer.write(String.valueOf(inputs.get(i)) + System.getProperty("line.separator"));
        }
        writer.close();
    }

    // время O(n*logn)
    // память O(n)

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        FileReader reader = new FileReader(inputName);
        BufferedReader scan = new BufferedReader(reader);
        ArrayList<String> inputs = new ArrayList<>();
        String keeper;
        while ((keeper = scan.readLine()) != null) {
            inputs.add(keeper);
        }

        ArrayList<Integer> inputsSorted = new ArrayList<>();
        for (int i = 0; i < inputs.size() ; i++) {
            inputsSorted.add(Integer.parseInt(inputs.get(i)));
        }
        inputsSorted.sort(Integer::compareTo);

        HashMap<Integer, Integer> bufferMap = new HashMap<>(); // подсчет количества одинаковых элементов
        for (int element : inputsSorted) {
            if (bufferMap.containsKey(element)) {
                bufferMap.put(element, bufferMap.get(element) + 1);
            } else {
                bufferMap.put(element, 1);
            }
        }
        int maxValue = Collections.max(bufferMap.values());
        int maxKey = 0;

        Iterator it = bufferMap.entrySet().iterator();
        ArrayList<Integer> pairKeeper = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().equals(maxValue)) {
                pairKeeper.add((Integer)pair.getKey());
            }
        }

        maxKey = Collections.min(pairKeeper);

        ArrayList<String> preResult = new ArrayList<>();
        for (int i = 0; i < inputs.size() ; i++) {
            if(Integer.parseInt(inputs.get(i)) != maxKey) {
                preResult.add(inputs.get(i));
            }
        }

        for (int i = 0; i < maxValue ; i++) {
            preResult.add(String.valueOf(maxKey));
        }
        File file = new File(outputName);
        FileWriter fileReader = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileReader);
        for (int i = 0; i < inputs.size() ; i++) {
            writer.write(preResult.get(i) + System.getProperty("line.separator"));
        }
        writer.close();
    }

    // время O(n*logn)
    // память O(n)

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int li = 0, ri = second.length/2;
        for (int i = 0; i < second.length; i++) {
            if (li < first.length && (ri == second.length || first[li].compareTo(second[ri]) < 0)) {
                second[i] = first[li++];
            }
            else {
                second[i] = second[ri++];
            }
        }
    }

    // Временные затраты O(N)

}
