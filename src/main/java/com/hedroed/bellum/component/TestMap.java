package com.hedroed.bellum.component;

import com.hedroed.bellum.joueur.*;

public class TestMap {
	public static void main(String[] args) {
		Joueur[] js = new Joueur[2];
		js[0] = new Joueur("1",null,null);
		js[1] = new Joueur("2",null,null);
		new MapLoader("/ressources/map2.map",null,js);
	}
}