import org.junit.*;

import static java.lang.System.out;
import static org.junit.Assert.*;

public class OlympHashMapTest {

    private OlympHashMap map;
    private GenerateData gd;
    int key = 11;
    int falseKey = 00;
    long value = 222;
    long falseValue = 000;

    @BeforeClass public static void beforeClass(){out.println("\nTest \"OlympHashMap\" class: start; ");}
    @AfterClass public static void afterClass(){out.println("Test \"OlympHashMap\" class: finished! ");}
    @Before public void initTest(){
        map = new OlympHashMap(16);
        gd = new GenerateData();
    }
    @After public void afterTest(){map = null;}

    @Test
    public void put() {
        map.put(key, value);
        for (int i=0; i<map.keys.length; i++){
            if (map.keys[i] != map.FREE){

                out.print("\nПроверка записи \"key\"("+key+"): ");
                    assertEquals(key, map.keys[i]);
                        out.println("\"key\"("+map.keys[i]+") - записан, найден, соответствует!\n");

                out.print("Проверка записи \"value\"("+value+"): ");
                    assertEquals(value, map.values[i]);
                        out.println("\"value\"("+map.values[i]+") - записан, найден, соответствует!\n");
            }
        }
    }

    @Test
    public void get() {
        map.put(key, value);

        out.println("Проверка получения записи \"value\"("+value+") по ключу \"key\"("+key+"): ");
            assertEquals(value, map.get(key));
                out.println("\t\"value\"("+map.get(key)+") - найден, получен, соответствует!\n");
    }

    @Test
    public void getSize() {
        int n = 10;
        out.println("Проверка определения размера коллекции: ");
        out.println(" - Заполняем коллекцию количеством \"n\" ("+n+") пар <key, values>; ");
            gd.dataKey_Values(map, n);
                out.println(" - Получаем значение \"size\" из коллекции, и сравниваем со значением \"n\";");
                assertEquals(n, map.getSize());
                    out.println("Количество найденных элементов соответствует количеству положенных!("+n+")");
                        out.println("\nПросмотр всех записанных элементов: \n");
                    map.showAll(map);
    }
}