package com.hedroed.bellum.vehicule;

import com.hedroed.bellum.joueur.*;
import com.hedroed.bellum.main.FDamier;

import javax.swing.JPanel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.io.*;

public class VehiculeFactory {
	
	
	public Vehicule getVehiculeByType(TypeVec type,int angle, FDamier damier, Joueur joueur) {
		Vehicule ret = null;
		switch (type) {
			case mangouste:
				ret = new Mangouste(angle, damier, joueur);
				break;
			case warthog: 
				ret = new Warthog(angle, damier, joueur);
				break;
			case scorpion:        
				ret = new Scorpion(angle, damier, joueur);
				break;
			case walker:        
				ret = new Walker(angle, damier, joueur);
				break;
			case bridger:
				ret = new Bridger(angle, damier, joueur);
				break;
			case turret:        
				ret = new Turret(angle, damier, joueur);
				break;
			case activeTurret:        
				ret = new ActiveTurret(angle, damier, joueur);
				break;
			case helicopter:        
				ret = new Helicopter(angle, damier, joueur);
				break;
			default:
				System.out.println("Erreur :: nom inconnu");
				break;
		}
		return ret;
		
	}
	
	public Vehicule getVehiculeByName(String name, int angle, FDamier damier, Joueur joueur) {
		Class toBuild = null;
		
		try {
			toBuild = Class.forName("com.hedroed.bellum.vehicule."+name);
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}

		Constructor[] builders = toBuild.getConstructors();

		Object[] param = {
			angle, damier, joueur
		};
		
		Vehicule v = null;
		try {
			v = (Vehicule) builders[0].newInstance(param);
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
}