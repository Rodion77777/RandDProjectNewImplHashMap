
/* Генератор значений, способствующий заполнению колекции значениями пар <key, value> в "n" количестве,
* для проверки некоторых функций самой коллекции
* */
public class GenerateData {

    public OlympHashMap dataKey_Values(OlympHashMap map, int nData){

        int defaultKey = 100;
        long defaultValue = 1000;

        for (int i=0; i<nData; i++){ map.put(defaultKey+i, defaultValue+i); }

        return map;
    }

}
