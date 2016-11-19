package net.alfredandersson.indaplusplus.raytracer.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public final class DirectionalLight implements Light {
  
  private final Color col;
  private final Vector3 norm;
  
  public DirectionalLight(float xDir, float yDir, float zDir, float r, float g, float b) {
    norm = new Vector3(xDir, yDir, zDir).nor();
    col = new Color(r, g, b, 1.0f);
  }
  
  @Override
  public float calc(Color lightCol, Vector3 lightNorm, float xPos, float yPos, float zPos) {
    lightCol.set(col);
    lightNorm.set(norm);
    
    return Float.POSITIVE_INFINITY;
  }
}
