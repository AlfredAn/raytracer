package net.alfredandersson.indaplusplus.raytracer.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public interface Light {
  
  float calc(Color lightCol, Vector3 lightNorm, float xPos, float yPos, float zPos);
}
