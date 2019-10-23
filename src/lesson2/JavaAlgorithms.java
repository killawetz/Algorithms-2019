package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static java.lang.StrictMath.max;


@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws FileNotFoundException {
        FileReader reader = new FileReader(inputName);
        Scanner scan = new Scanner(reader);
        ArrayList<Integer> inputs = new ArrayList<>();
        while (scan.hasNext()) {
            inputs.add(Integer.parseInt(scan.nextLine()));
        }
        int best = 0;
        int current = inputs.get(0);
        int dayBuy = 0;
        int daySell = 0;
        int daySellInd = 0;
        for (int i = 0; i < inputs.size() - 1; i++) {
            int difference = inputs.get(i+1) - current;
            if(difference < 0) {
            current = inputs.get(i+1);
            continue;
            }
            if(best < difference) {
                best = difference;
                dayBuy = current;
                daySell = inputs.get(i+1);
                daySellInd = i+1;
            }
        }
        Pair pair = new Pair(inputs.indexOf(dayBuy) + 1, daySellInd + 1);
        return pair;
    }
    // временные затраты O(n)
    // ресерсные затраты O(n)

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) { throw new NotImplementedError(); }
       /* String str1 = String.join("", first.split(" "));
        String[] charArrayFirst = String.join("", str1).split("");
        String str2 = String.join("", second.split(" "));
        String[] charArraySecond = String.join("", str2).split("");
        ArrayList<Character> charList = new ArrayList<>();
        String bufferSecond = "";
        String result = "";
        ArrayList<String> resultList = new ArrayList<>();



        *//*for (int i = 0; i < charArrayFirst.length; i++) {
            String bufferFirst = charArrayFirst[i];
            for (int j = 0; j < charArrayFirst.length; j++) {
                if(str2.contains(bufferFirst + bufferSecond)) {
                    bufferSecond = bufferFirst;
                    bufferSecond += charArrayFirst[j];
                }

            }
            resultList.add(bufferFirst);*//*

        Collections.sort(resultList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
        return resultList.get(resultList.size() - 1);
    }*/


    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        if(limit < 0) return 0;
        boolean[] digit = new boolean[limit + 1];
        int count = 0;

        Arrays.fill(digit, true);
        digit[0] = false;
        digit[1] = false;
        for (int i = 2; i < digit.length; ++i) {
            if (digit[i]) {
                for (int j = 2; i * j < digit.length; ++j) {
                    digit[i * j] = false;
                }
            }
        }
        for (int i = 0; i < digit.length; i++) {
            if(digit[i]) count++;
        }
        return count;
    }

    // время O(nlog(logn))
    // память O(n)

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
