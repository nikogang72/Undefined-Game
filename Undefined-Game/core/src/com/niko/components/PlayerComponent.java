package com.niko.components;

import com.badlogic.ashley.core.Component;
import com.niko.cfg.cfg;

public class PlayerComponent implements Component{

	public float health;
	public float energy;
	
	public PlayerComponent()
	{
		energy = cfg.plyEnergy;
		health = cfg.plyHealth;
	}
}
