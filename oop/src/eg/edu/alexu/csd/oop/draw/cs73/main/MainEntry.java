package eg.edu.alexu.csd.oop.draw.cs73.main;

import eg.edu.alexu.csd.oop.draw.cs73.controller.Controller;
import eg.edu.alexu.csd.oop.draw.cs73.model.DrawEngineImp;

public class MainEntry {
	public static void main(String[] args) {
		new Controller(DrawEngineImp.getUniqueInstance());
	}
}
