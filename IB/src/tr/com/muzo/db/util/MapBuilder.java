package tr.com.muzo.db.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
	
	/***
	 * Deðerlerden ilk gelen parametre kolonAdý , 2. arama tip , 3. arama yeri , 4. order tipi , 5 data tip , 6 label
	 * @param degerler
	 * @return
	 */
	
	public static Map<String, Map<String,String>> aramaMapOlustur(String... kriterler ) {
		
		int dataSayi = 6;
		
		if(kriterler.length % dataSayi != 0){
	        throw new IllegalArgumentException( "The array has to be of an even size - size is " + kriterler.length);
		}
		
		int donmeSayi = kriterler.length/dataSayi;
		
	    Map<String, Map<String,String>> values = new HashMap<String, Map<String,String>>();
	    for(int i=0; donmeSayi>i ; i++){
	    	Map<String,String> degerlerMap = new HashMap<String,String>();
	    	for(int j=1; dataSayi>j; j++){
	    		
	    		switch (j) {
				case 1:
					degerlerMap.put("aramaTip", kriterler[(i*dataSayi)+j]);
					break;
				case 2:
					degerlerMap.put("aramaYeri", kriterler[(i*dataSayi)+j]);
					break;
				case 3:
					degerlerMap.put("orderTip", kriterler[(i*dataSayi)+j]);
					break;
				case 4:
					degerlerMap.put("dataTip", kriterler[(i*dataSayi)+j]);
					break;
				case 5:
					degerlerMap.put("label", kriterler[(i*dataSayi)+j]);
					break;

				default:
					break;
				}
	    		
	    	}
	    	values.put(kriterler[((i*dataSayi) + 0)], degerlerMap);
	    }
	    return values;

	}
	
}
