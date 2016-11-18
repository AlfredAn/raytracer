package net.alfredandersson.indaplusplus.raytracer.materials;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;

public interface Material {
  
  Color shade(Pool<Color> resultPool, Color rayColor, RaycastResult raycast, int depth);
}
