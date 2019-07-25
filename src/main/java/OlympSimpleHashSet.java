
/* Простая реализация HashSet'а на цепочках для интов */
public class OlympSimpleHashSet {

    int[] head; // массив голов
    int[] next; // массив ссылок на следующий элемент
    int[] keys; // массив с ключами
    int headNum; // количество голов
    int cnt = 1; // ссылка на место для вставки нового эдемента

    /* Конструктор */
    OlympSimpleHashSet(int headNum, int maxSize){
        this.headNum = headNum;

        head = new int[headNum];
        next = new int[maxSize+1];
        keys = new int[maxSize+1];
    }

    /* Добавляет элемент в множество */
    boolean add(int x){

        if (this.contains(x)) return false;

        int h = index(hash(x));

        next[cnt] = head[h];
        keys[cnt] = x;
        head[h] = cnt++;

        return true;
    }

    /* Проверка содержания элемента во множестве */
    boolean contains(int x){
        int h = index(hash(x)); // получение индекса из hash значения, для поиска
        for (int i = head[h]; i != 0; i = next[i])
            if (keys[i] == x)
                return true;
        return false;
    }

    /*
    "^" - операция "XOR", используемая в качестве простого шифрования
        (логическое исключающее ИЛИ).
        В данном случае результатом будет исходное число "Х", которое и используется
        в качестве ключа шифрования.
    ">>" - операция знакового сдвига, в данном случае X смещаем на 15 разрядов вправо.
        Менее дорогостоящая операция на аппаратном уровне чем деление, если исходное число
        кратное двум, так как фактически получается умножение/деление на 2.
    */
    int hash(int x){ return (x >> 15) ^ x;}

    /* Получение индекса, в виде остатка деления, от полученного абсолютного значения хэш */
    int index(int hash){ return Math.abs(hash) % headNum; }

}
