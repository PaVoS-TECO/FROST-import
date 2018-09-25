package edu.teco.pavos.pim;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.opencsv.CSVWriter;

/**
 * Test of the CSVReaderStrategy
 * @author Jean Baumgarten
 */
public class CSVReaderStrategyTest {
	
	@Test
	public void buildUp() {
		
		File file = new File(System.getProperty("user.home") + File.separator + "Desktop/froststealer.csv");
		File file2 = new File(System.getProperty("user.home") + File.separator + "Desktop/observations.csv");
		BufferedReader cbr;
		BufferedReader br;
		PrintWriter writer;
		
		try {
			cbr = new BufferedReader(new FileReader(file));
			
			writer = new PrintWriter(System.getProperty("user.home") + File.separator + "Desktop/sensorup.csv", "UTF-8");
			CSVWriter csvWriter = new CSVWriter(writer,
	                CSVWriter.DEFAULT_SEPARATOR,
	                CSVWriter.DEFAULT_QUOTE_CHARACTER,
	                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	                CSVWriter.DEFAULT_LINE_END);
			String line;
			while ((line = cbr.readLine()) != null) {
				
				String[] elements = line.split("Ϣ");
				csvWriter.writeNext(elements);
				
			}
			cbr.close();
			
			br = new BufferedReader(new FileReader(file2));
			while ((line = br.readLine()) != null) {
				
				String[] elements = line.split("Ϣ");
				elements[6] = "";
				csvWriter.writeNext(elements);
				
			}
			br.close();
			
			csvWriter.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void getObservedPropertyTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "definition"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getObservedProperty", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((String) obj.get("definition")).equals("definition"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getSensorTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "encodingType", "metadata"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getSensor", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((String) obj.get("encodingType")).equals("encodingType"));
			assertTrue(((String) obj.get("metadata")).equals("metadata"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getLocationTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "encodingType", "{\"a\": \"1\"}"
		};
		String[] data2 = {
				"", "1", "name", "description", "encodingType", "\"a\": \"1\"}"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getLocation", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			String json2 = (String) method.invoke(reader, new Object[] { data2 });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			
			assertTrue(json2.equals(""));
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((String) obj.get("encodingType")).equals("encodingType"));
			assertTrue(((JSONObject) obj.get("location")).toJSONString().equals("{\"a\":\"1\"}"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getFoITest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "encodingType", "{\"a\": \"1\"}"
		};
		String[] data2 = {
				"", "1", "name", "description", "encodingType", "\"a\": \"1\"}"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getFoI", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			String json2 = (String) method.invoke(reader, new Object[] { data2 });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			
			assertTrue(json2.equals(""));
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((String) obj.get("encodingType")).equals("encodingType"));
			assertTrue(((JSONObject) obj.get("feature")).toJSONString().equals("{\"a\":\"1\"}"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getThingTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "{\"a\":\"1\"}", "1;2"
		};
		String[] data2 = {
				"", "1", "name", "description", "\"a\":\"1\"}", "1;2"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getThing", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			String json2 = (String) method.invoke(reader, new Object[] { data2 });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);

			assertTrue(json2.equals(""));
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((JSONObject) obj.get("properties")).toJSONString().equals("{\"a\":\"1\"}"));
			JSONArray a = (JSONArray) obj.get("Locations");
			for (int i = 0; i < a.size(); i++) {
				String o = "" + a.get(i);
				assertTrue(o.equals("{\"@iot.id\":\"1\"}") || o.equals("{\"@iot.id\":\"2\"}"));
			}
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getDataStreamTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "name", "description", "observationType", "{\"a\":\"1\"}", "2", "3", "4"
		};
		String[] data2 = {
				"", "1", "name", "description", "observationType", "\"a\":\"1\"}", "2", "3", "4"
		};
		String[] data3 = {
				"", "1", "name", "description", "observationType", "{\"a\":\"1\"}", "2", "3", "4",
				"observedArea", "phenomenonTime", "resultTime"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getDataStream", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			String json2 = (String) method.invoke(reader, new Object[] { data2 });
			String json3 = (String) method.invoke(reader, new Object[] { data3 });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			JSONObject obj2 = (JSONObject) parser.parse(json3);

			assertTrue(json2.equals(""));
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("name")).equals("name"));
			assertTrue(((String) obj.get("description")).equals("description"));
			assertTrue(((String) obj.get("observationType")).equals("observationType"));
			assertTrue(((JSONObject) obj.get("unitOfMeasurement")).toJSONString().equals("{\"a\":\"1\"}"));
			assertTrue(((JSONObject) obj.get("Thing")).toJSONString().equals("{\"@iot.id\":\"2\"}"));
			assertTrue(((JSONObject) obj.get("ObservedProperty")).toJSONString().equals("{\"@iot.id\":\"3\"}"));
			assertTrue(((JSONObject) obj.get("Sensor")).toJSONString().equals("{\"@iot.id\":\"4\"}"));
			assertTrue(((String) obj2.get("observedArea")).equals("observedArea"));
			assertTrue(((String) obj2.get("phenomenonTime")).equals("phenomenonTime"));
			assertTrue(((String) obj2.get("resultTime")).equals("resultTime"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

	@Test
	public void getObservationTest() {
		
		CSVReaderStrategy reader = new CSVReaderStrategy("");
		String[] data = {
				"", "1", "phenomenonTime", "result", "resultTime", "2", "3",
				"resultQuality", "validTime", "{\"a\":\"1\"}"
		};
		String[] data2 = {
				"", "1", "phenomenonTime", "result", "null", "2", "3",
				"resultQuality", "validTime", "{\"a\":\"1\"}"
		};
		
		Method method;
		try {
			
			method = CSVReaderStrategy.class.getDeclaredMethod("getObservation", String[].class);
			method.setAccessible(true);
			String json = (String) method.invoke(reader, new Object[] { data });
			String json2 = (String) method.invoke(reader, new Object[] { data2 });
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(json);
			JSONObject obj2 = (JSONObject) parser.parse(json2);
			
			assertTrue(((String) obj.get("@iot.id")).equals("1"));
			assertTrue(((String) obj.get("phenomenonTime")).equals("phenomenonTime"));
			assertTrue(((String) obj.get("result")).equals("result"));
			assertTrue(((String) obj.get("resultTime")).equals("resultTime"));
			assertTrue(((String) obj2.get("resultTime")).equals("phenomenonTime"));
			assertTrue(((JSONObject) obj.get("Datastream")).toJSONString().equals("{\"@iot.id\":\"2\"}"));
			//assertTrue(((JSONObject) obj.get("FeatureOfInterest")).toJSONString().equals("{\"@iot.id\":\"3\"}"));
			assertTrue(((String) obj.get("resultQuality")).equals("resultQuality"));
			assertTrue(((String) obj.get("validTime")).equals("validTime"));
			assertTrue(((JSONObject) obj.get("parameters")).toJSONString().equals("{\"a\":\"1\"}"));
			
		} catch (NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (SecurityException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalAccessException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (InvocationTargetException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
			assertTrue(false);
		}
		
	}

}
