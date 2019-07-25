import org.junit.*;

import java.security.PrivateKey;

import static java.lang.System.out;
import static org.junit.Assert.*;

public class GenerateDataTest {

    private GenerateData gd;
    private OlympHashMap map;

    @BeforeClass public static void beforeClass(){out.println("\nTest \"GenerateData\" class: start; ");}
    @AfterClass public static void afterClass(){out.println("\nTest \"GenerateData\" class: finished! ");}
    @Before public void initTest(){
        gd = new GenerateData();
        map = new OlympHashMap(16);
    }
    @After public void afterTest(){gd = null;}

    @Test
    public void dataKey_Values() {
        int n = 10;

        out.println("Проверка работоспособности метода \"dataKey_Values\": ");
        out.println(" - Заполняем коллекцию количеством \"n\" ("+n+") пар <key, values>; ");
            gd.dataKey_Values(map, n);
                out.println(" - Получаем значение \"size\" из коллекции, и сравниваем со значением \"n\";");
                assertEquals(n, map.getSize());
                    out.println("Количество найденных элементов соответствует количеству положенных!("+n+")");
                    out.println("\nПросмотр всех записанных элементов: \n");
                        map.showAll(map);
    }
}