package edu.teco.pavos.pim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.opencsv.CSVReader;

/**
 * Implementation of the FileReaderStrategy interface for CSV files.
 */
public class CSVReaderStrategy implements FileReaderStrategy {
	
	private int errorlines = 0;
	private String iotIDImport;
	private String url;
	private DataTable dataTable;
	private ArrayList<String> errors;
	
	private static String OBSERVED_PROPERTY = "observedProperty";
	private static String SENSOR = "sensor";
	private static String LOCATION = "location";
	private static String FEATURE_OF_INTEREST = "featureOfInterest";
	private static String THING = "thing";
	private static String DATASTREAM = "dataStream";
	private static String OBSERVATION = "observation";

    /**
     * Default constructor
     * @param url is the destination server for the data.
     */
    public CSVReaderStrategy(String url) {
    	
    	this.url = url;
    	this.iotIDImport = "";
    	this.errors = new ArrayList<String>();
    	
    }
    
    /**
     * Set the dataTable of the Import
     * @param dataTable of the Import
     */
    public void setDataTable(DataTable dataTable) {
    	
    	this.dataTable = dataTable;
    	
    }
    
    /**
     * Set the prefix for the iot id of imported data
     * @param prefix for imported data
     */
    public void setIotIdPrefix(String prefix) {
    	
    	this.iotIDImport = prefix;
    	
    }

    /**
     * Reads from a File as specified by the FilePath and sends the information in
     * it to the FROST-Server using the FrostSender that was provided.
     * @param file Is the File to Import.
     */
    public void sendFileData(File file) {
    	
		try {
			
			BufferedReader cbr = new BufferedReader(new FileReader(file));
			int total = 0;
			while (cbr.readLine() != null) {
				total++;
			}
			cbr.close();
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			CSVReader csvReader = new CSVReader(br);
			
			int number = 0;
			String[] line;
			
			while ((line = csvReader.readNext()) != null) {
				
				number++;
				int percent = number * 100 / total;
				this.dataTable.setProgress(file.getAbsolutePath(), percent);
				
				if (line.length >= 2) {
					
					if (line[0].equals(OBSERVED_PROPERTY) && line.length >= 5) {
						
						String json = this.getObservedProperty(line);
				        FrostSender.sendToFrostServer(this.url + "ObservedProperties", json, this.errors);
				        
					} else if (line[0].equals(SENSOR) && line.length >= 6) {
						
						String json = this.getSensor(line);
				        FrostSender.sendToFrostServer(this.url + "Sensors", json, this.errors);
				        
					} else if (line[0].equals(LOCATION) && line.length >= 6) {
						
						String json = this.getLocation(line);
				        FrostSender.sendToFrostServer(this.url + "Locations", json, this.errors);
				        
					} else if (line[0].equals(FEATURE_OF_INTEREST) && line.length >= 6) {
						
						String json = this.getFoI(line);
				        FrostSender.sendToFrostServer(this.url + "FeaturesOfInterest", json, this.errors);
				        
					} else if (line[0].equals(THING) && line.length >= 5) {
						
						String json = this.getThing(line);
				        FrostSender.sendToFrostServer(this.url + "Things", json, this.errors);
				        
					} else if (line[0].equals(DATASTREAM) && line.length >= 8) {
						
						String json = this.getDataStream(line);
				        FrostSender.sendToFrostServer(this.url + "Datastreams", json, this.errors);
				        
					} else if (line[0].equals(OBSERVATION) && line.length >= 7) {
						
						String json = this.getObservation(line);
				        FrostSender.sendToFrostServer(this.url + "Observations", json, this.errors);
				        
					} else {
						
						this.errorlines++;
						this.errors.add("A data line could not be used.\nType: " + line[0] + "\n@iot.id:" + line[1]);
						
					}
					
					System.out.println(line[0]);
					
				} else {
					
					this.errorlines++;
					this.errors.add("A data line could not be used.");
					
				}
				
			}
			
			csvReader.close();
			br.close();
			System.out.println(this.errorlines);
			
			if (!this.errors.isEmpty()) {
				String path = file.getParent() + File.separator + "Errors" + file.getName() + ".log";
				PrintWriter writer = new PrintWriter(new File(path), "UTF-8");
				for (String l : this.errors) {
					writer.println(l);
				}
				writer.close();
				JOptionPane.showMessageDialog(this.dataTable.getFrame(),
					    "Errors occured during the import. A logfile has been created on the files location.",
					    "Warning",
					    JOptionPane.WARNING_MESSAGE);
			}
			
			
		} catch (FileNotFoundException e) {
			
			System.out.println(e.getLocalizedMessage());
			
		} catch (IOException e) {
			
			System.out.println(e.getLocalizedMessage());
			
		}
		
    }
    
    private String getObservedProperty(String[] data) {
        
        String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\","
        		+ "\"definition\":\"" + data[4] + "\""
        		+ "}";
        
        return json;
        
    }
    
    private String getSensor(String[] data) {
        
        String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\","
        		+ "\"encodingType\":\"" + data[4] + "\","
                + "\"metadata\":\"" + data[5] + "\""
        		+ "}";
        
        return json;
        
    }
    
    private String getLocation(String[] data) {
		
		String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\","
        		+ "\"encodingType\":\"" + data[4] + "\","
                + "\"location\":" + data[5]
        		+ "}";
        
        return json;
		
    }
    
    private String getFoI(String[] data) {
		
		String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\","
        		+ "\"encodingType\":\"" + data[4] + "\","
                + "\"feature\":" + data[5]
        		+ "}";
        
        return json;
		
    }
    
    private String getThing(String[] data) {
		
		String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\"";
		
		if (!data[4].equals("")) {
	        json += ",\"properties\":" + data[4];
		}
		
		if (data.length >= 6 && !data[5].equals("")) {
			String[] ids = data[5].split(";");
			json += ",\"Locations\":[";
			for (int i = 0; i < ids.length; i++) {
				json += "{\"@iot.id\":\"" + this.iotIDImport + ids[i] + "\"}";
				if ((i + 1) < ids.length) {
					json += ",";
				}
			}
			json += "]";
		}
        		
        json += "}";
        
        return json;
        
    }
    
    private String getDataStream(String[] data) {
		
		String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"name\":\"" + data[2] + "\","
        		+ "\"description\":\"" + data[3] + "\","
				+ "\"observationType\":\"" + data[4] + "\","
				+ "\"unitOfMeasurement\":" + data[5] + ","
				+ "\"Thing\":{\"@iot.id\":\"" + this.iotIDImport + data[6] + "\"},"
				+ "\"ObservedProperty\":{\"@iot.id\":\"" + this.iotIDImport + data[7] + "\"},"
				+ "\"Sensor\":{\"@iot.id\":\"" + this.iotIDImport + data[8] + "\"}";
		
		if (data.length >= 10 && !data[9].equals("")) {
        	
        	json += ",\"observedArea\":" + data[9];
	        if (data.length >= 11 && !data[10].equals("")) {
	        	
	        	json += ",\"phenomenonTime\":\"" + data[10] + "\"";
		        if (data.length >= 12 && !data[11].equals("")) {
		        	
		        	json += ",\"resultTime\":\"" + data[11] + "\"";
		        	
		        }
		        
	        }
	        
        }
        		
        json += "}";
        
        return json;
		
    }
    
    private String getObservation(String[] data) {
		
		String json = "{"
        		+ "\"@iot.id\":\"" + this.iotIDImport + data[1] + "\","
        		+ "\"phenomenonTime\":\"" + data[2] + "\","
        		+ "\"result\":" + data[3] + ",";
		
		if (data[4].equals("null")) {
			json += "\"resultTime\":\"" + data[2] + "\",";
        } else {
        	json += "\"resultTime\":\"" + data[4] + "\",";
        }
		
		json += "\"Datastream\":{\"@iot.id\":\"" + this.iotIDImport + data[5] + "\"}";
		if (!data[6].equals("")) {
			json += ",\"FeatureOfInterest\":{\"@iot.id\":\"" + this.iotIDImport + data[6] + "\"}";
		}
		
		if (data.length >= 8 && !data[7].equals("")) {
        	
        	json += ",\"resultQuality\": \"" + data[7] + "\"";
            if (data.length >= 9 && !data[8].equals("")) {
            	
            	json += ",\"validTime\": \"" + data[8] + "\"";
            	if (data.length >= 10 && !data[9].equals("")) {
    	        	
    		        json += ",\"parameters\": " + data[8];
    		        
    	        }
            }
            
        }
        		
        json += "}";
        
        return json;
        
    }

}
