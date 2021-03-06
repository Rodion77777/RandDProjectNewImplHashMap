import java.util.Arrays;

import static java.lang.System.out;


/* Данная реализация наиболее полно соответствует требованиям ТЗ
* По этому класс MyHashMap реализован более творчески, где я добавил функций по желанию
* */
public class OlympHashMap {
    int FREE = Integer.MIN_VALUE;
    int size;
    int[] keys; // хранит ключи
    long[] values; // хранит значения
    int addElement;

    //Конструктор
    OlympHashMap(int size) {
        this.size = Math.max(3*size/2, size) +1;
        keys = new int [this.size];
        values = new long[this.size];
        Arrays.fill(keys, FREE);
    }

    //Добавляет пару в множество
    // Присутствуют недостатки: при отсутствии возможности совершить запись, цикл становится бесконечным.
    void put(int x, long y) {
        addElement++;
        for (int i = index(hash(x)); ; i++) {
            if (i == size) i = 0; // если индекс X равен размеру массива, возвращаем итератор к началу массива потому что такой ячейки нет
            if (keys[i] == FREE) // если вычесленная ячейка в keys[] свободна, осуществляем заипись
                keys[i] = x;
            if (keys[i] == x) { // если запись ключа была произведена успешно, производим запись значения. Без записи "keys", не будет записи "values"
                values[i] = y;
                return; // выход из цикла/метода по завершению записи
            }
        }
    }

    //Извлекает значение
    long get(int x) {
        for (int i = index(hash(x)); ; i++) { // в первую очередь ссылаемся на нужную ячейку по индексу. Так она определялась для записи, таким же способом ищем для извлечения
            if (i == size) i = 0;
            if (keys[i] == FREE) throw new RuntimeException("No such key!");
            if (keys[i] == x) return values[i]; // если ячейка не пустая, а равная ключу (а без записи ключа, не будет и записи значения) - значит вернем значение из ячейки values[] с соответствующим index(i)
        }
    }

    //Проверяет наличие пары с ключем x
    // аналогичен методу get(), но вместо значения возвращает true/false
    boolean containsKey(int x) {
        for (int i = index(hash(x)); ; i++) {
            if (i == size) i = 0;
            if (keys[i] == FREE) return false;
            if (keys[i] == x) return true;
        }
    }

    //хэш-функция
    /*
    "^" - операция "XOR", используемая в качестве простого шифрования
        (логическое исключающее ИЛИ).
        В данном случае результатом будет исходное число "Х", которое и используется
        в качестве ключа шифрования.
    ">>" - операция знакового сдвига, в данном случае X смещаем на 15 разрядов вправо.
        Менее дорогостоящая операция на аппаратном уровне чем деление, если исходное число
        кратное двум, так как фактически получается умножение/деление на 2.
    */
    int hash(int x) {
        return (x >> 15) ^ x;
    }

    /* возвращает номер головы по значению хэш-функции
       Получение индекса, в виде остатка деления, от полученного абсолютного значения хэш */
    int index(int hash) {
        return Math.abs(hash) % size;
    }

    // данный метод возвращает количество добавленных элементов, а не полный размер коллекции, так же как и в обычной HashMap
    int getSize(){return addElement;}

    void showAll(OlympHashMap map){
        for (int i=0; i<map.getSize(); i++){
            out.printf("Key: %d, Value: %d\n", map.keys[i], map.values[i]);
        }
    }
}

