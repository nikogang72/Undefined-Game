package com.niko.components;

import com.badlogic.ashley.core.Component;

public class StatusComponent implements Component {
	
	public boolean alive;
	
	public StatusComponent() {
		alive = true;
	}

}
