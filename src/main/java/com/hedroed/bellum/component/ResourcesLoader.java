package com.hedroed.bellum.component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourcesLoader {
	
	private boolean error;
	
	private HashMap<String,ArrayList<TransferVec>> vecList;
	
	public ResourcesLoader(String fileName) throws IOException{
		
		BufferedReader in = null;
		
		vecList = new HashMap<String,ArrayList<TransferVec>>();
		

		in = new BufferedReader(new FileReader(fileName));
		
		String line = in.readLine();
		while(line != null) {
			int i = line.indexOf(';');
			if(i != -1) {
				String key = line.substring(0,i);
				System.out.println("Key : "+key);
				//entre dans une liste
				ArrayList<TransferVec> tvs = new ArrayList<TransferVec>();
				line = in.readLine();
				while(line != null && line.indexOf(';') == -1) {
					int nb = extractInt(line);
					String vecName = extractName(line);
					if(nb > 0 && vecName != null) {
						TransferVec t = new TransferVec(vecName,nb);
						tvs.add(t);
					}
					else {
						System.out.println("Error :: Nom du vehicule ou nombre incorrect");
					}
					line = in.readLine();
				}
				vecList.put(key,tvs);
			}
			else {
				line = in.readLine();
			}
		}
	}
	
	private String extractName(String s) {
		String ret = null;
		if(s != null && !"".equals(s)) {
			int i = s.indexOf(':');
			if(i != -1) {
				ret = s.substring(0,i);
			}
		}
		return ret;
	}
	
	private int extractInt(String s) {
		int ret = -1;
		if(s != null && !"".equals(s)) {
			int i = s.indexOf(':');
			if(i != -1) {
				s = s.substring(i+1);
				try {
					ret = Integer.parseInt(s);
				}
				catch(NumberFormatException e) {
					System.out.println("impossible d'extract int");
				}
			}
		}
		return ret;
	}
	
	public HashMap<String,ArrayList<TransferVec>> getResourcesVehicules() {
		return this.vecList;
	}
	
}