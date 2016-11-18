package net.alfredandersson.indaplusplus.raytracer.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.alfredandersson.indaplusplus.raytracer.Main;

public class DesktopLauncher {
  
  public static void main(String[] arg) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    
    DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
    config.setFullscreenMode(dm);
    
    config.useOpenGL3(true, 3, 2);
    
    new Lwjgl3Application(new Main(), config);
  }
}
