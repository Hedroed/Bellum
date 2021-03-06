package com.hedroed.bellum.main;

import java.io.InputStream;
import java.io.BufferedInputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class PlaySound {
	
	private Clip clip;
	private FloatControl gainControl;
	private int discount;
	
	public PlaySound(String s) {
		
		
		try {
		    InputStream in = getClass().getResourceAsStream(s);
            InputStream bufferedIn = new BufferedInputStream(in);
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(bufferedIn));
		} catch(Exception e) {
			System.out.println("Error :: Clip (file: "+s+")");
			e.printStackTrace();
		}
		
		try {
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
			gainControl.setValue(-20f);
		} catch (java.lang.IllegalArgumentException e) {
			//TODO: handle exception
		}
	}
	
	public PlaySound(String s, int volume) {
		this(s);
		if(gainControl != null) {
			gainControl.setValue((float)volume);
		}
	}
	
	public PlaySound(String s, int volume, int discount) {
		this(s,volume);
		this.discount = discount;
	}
	
	public void setVolume(int volume) {
		// System.out.println("vol :"+volume);
		if(volume > -60 && volume <= 5) {
			if(gainControl != null) {
				gainControl.setValue((float)volume-discount);
			}
			this.playContinuously();
		}
		else if(volume == -60) {
			if(clip != null) {
				clip.stop();
			}
		}
		else {
			System.out.println("Erreur :: volume incorrect");
		}
	}
	
	public void stop() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
		}
	}
	
	public void play() {
		if(gainControl != null) {		
			if(gainControl.getValue() != (float)OptionState.soundVolume-discount) {
				gainControl.setValue((float)OptionState.soundVolume-discount);
				// System.out.println("change vol");
			}
		}
		if(clip != null && !clip.isRunning() && OptionState.soundVolume != -40) {
			stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void playContinuously() {
		if(clip != null && !clip.isRunning() && OptionState.musicVolume != -60) {
			stop();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}
	}
	
	public void close() {
		if(clip != null) {
			clip.close();
		}
	}
	
	public boolean isRunning() {
		if(clip != null) {
			return clip.isRunning();
		} else {
			return false;
		}
	}
}