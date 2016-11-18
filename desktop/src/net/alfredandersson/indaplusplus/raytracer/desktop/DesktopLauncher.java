package net.alfredandersson.indaplusplus.raytracer.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.alfredandersson.indaplusplus.raytracer.Main;

public class DesktopLauncher {
  
  public static void main(String[] arg) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    
    new Lwjgl3Application(new Main(), config);
  }
}
