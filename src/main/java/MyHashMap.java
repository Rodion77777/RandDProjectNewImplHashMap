import static java.lang.System.out;
import java.io.Serializable;
import java.util.*;

/*
* В данной реализации "MyHashMap" решил добавить несколько элементов от обычного HashMap,
* такие как:
* - реализация Cloneable, Serializable;
* - внедрил понятие DEFAULT_LOAD_FACTOR;
* - использовал перегрузку конструкторов;
*
* Так же добавил метод получения размера коллекции, соответствующий количеству возможных хранимых элементов
* Добавлена возможность расширения коллекции путем пересоздания и записи данных из oldCollection в newCollection
* */

public class MyHashMap implements Cloneable, Serializable {

    int FREE = Integer.MIN_VALUE;
    int size = 16;
    int valuesSize;
    int[] keys;
    long[] values;
    static final float DEFAULT_LOAD_FACTOR = 0.80f;
    float loadFactor = DEFAULT_LOAD_FACTOR;

    /*
    * Для разнообразия добавил перегрузку конструкторов
    * */
    MyHashMap(){ generateHashMap(this.size); }
    MyHashMap(int size){ generateHashMap(size); }
    MyHashMap(float loadFactor){ this.loadFactor = loadFactor; generateHashMap(this.size, loadFactor); }
    MyHashMap(int size, float loadFactor){ this.loadFactor = loadFactor; generateHashMap(size, loadFactor); }

    // generateHashMap отвечает за все нужные процессы по созданию коллекции
    private void generateHashMap(int size){
        keys = new int[generateNewSize(size)];
        values = new long[generateNewSize(size)];
        Arrays.fill(keys, FREE);
    }

    private void generateHashMap(int size, float loadFactor){
        keys = new int[generateNewSize(size, loadFactor)];
        values = new long[generateNewSize(size, loadFactor)];
        Arrays.fill(keys, FREE);
    }

    // generateNewSize определяет размер коллекции, необходимый для текущих или возросших потребностей
    private int generateNewSize(int size){
        if (size < 0) throw new IllegalArgumentException();
        if (size > this.size && size <= 64) do this.size *= 2; while (this.size < size);
        /* Делить значение на DEFAULT_LOAD_FACTOR предполагает меньше действий чем умножение на коэффициент запаса:
            this.size = (int)(size*(reserveVolume+loadFactor));
                где:
                reserveVolume = 0.25(указывается клиентом приблизительно в таком виде)
                loadFactor = 1.0(указана единица так как нагрузочный фактор сам по себе не может заранее знать об интенсивности использования коллекции,
                по этому коэффициент запаса учитывается отдельно)
        */
        if (size > 64) this.size = (int)(size/DEFAULT_LOAD_FACTOR);
        return this.size;
    }

    private int generateNewSize(int size, float loadFactor){
        if (size < 0) throw new IllegalArgumentException();
        if (size > this.size && size <= 64) do this.size *= 2; while (this.size < size);
        if (size > 64) this.size = (int)(size/loadFactor);
        return this.size;
    }

    //Добавляет пару в множество
    void put(int x, long y) {
        try {
            if (valuesSize == keys.length){
                throw new RuntimeException();
            }
            valuesSize++;
            int iteration=0;
            for (int i = index(hash(x)); ; i++) {
                if (i == size) {
                    i = 0; // если индекс X равен размеру массива, возвращаем итератор к началу массива
                    iteration++;
                    if (iteration == 2) return;
                }
                if (keys[i] == FREE) // если вычесленная ячейка в keys[] свободна, осуществляем заипись
                    keys[i] = x;
                if (keys[i] == x) { // если ключ в соответствующей ячейке записан, производим запись значения. Без записи ключа, не будет записи значения
                    values[i] = y;
                    return; // выход из цикла/метода по завершению записи
                }
            }
        } catch (RuntimeException re){out.println("Коллекция заполнена");
            return;}


    }

    // Метод, расширяющий коллекцию
    MyHashMap getNewExpandMyHashMap(MyHashMap oldMap){
        MyHashMap newExpandMyHashMap = new MyHashMap(oldMap.getTotalValuesSize());
        // перезапись данных из oldMap в newExpandMyHashMap
        for (int i=0; i<oldMap.getTotalValuesSize(); i++){
            newExpandMyHashMap.put(oldMap.keys[i], oldMap.values[i]);
        }
        return newExpandMyHashMap;
    }

    long getValue(int k) {
        for (int i = index(hash(k)); ; i++) { // в первую очередь ссылаемся на нужную ячейку по индексу. Так она определялась для записи, таким же способом ищем для извлечения
            if (i == size) i = 0;
            if (keys[i] == FREE) throw new RuntimeException("No such key!");
            if (keys[i] == k) return values[i]; // если ячейка не пустая, а равная ключу (а без записи ключа, не будет и записи значения) - значит вернем значение из ячейки values[] с соответствующим index(i)
        }
    }

    // к сожалению не эффективный способ поиска по всему каталогу, но на данном этапе проекта это единственно возможная реализация
    int getKey(int v) {
        for (int i = values.length; ; i++) {
            if (i == size) i = 0;
            if (values[i] == FREE) throw new RuntimeException("No such key!");
            if (values[i] == v) return keys[i]; // если ячейка не пустая, а равная ключу (а без записи ключа, не будет и записи значения) - значит вернем значение из ячейки values[] с соответствующим index(i)
        }
    }

    int hash(int x) { return (x >> 15) ^ x; }
    int index(int hash) { return Math.abs(hash) % size; }

    // возвращает размер коллекции, соответствующий количеству возможных хранимых элементов
    int getTotalValuesSize() { return this.size; }

    // возвращает реальное количество содержащихся элементов
    int getSize() { return valuesSize; }

    // метод который будет полезен для каких нибудь тестов
    void showAll(MyHashMap map){
        for (int i=0; i<map.getTotalValuesSize(); i++){
            out.printf("Key: %d, Value: %d\n", map.keys[i], map.values[i]);
        }
    }
}
