package net.alfredandersson.indaplusplus.raytracer.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public final class PointLight implements Light {
  
  private final Color color;
  public final float x, y, z, intMul;
  
  public PointLight(Color color, float intMul, float x, float y, float z) {
    this.color = new Color(color);
    this.intMul = intMul;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  @Override
  public float calc(Color lightCol, Vector3 lightNorm, float xPos, float yPos, float zPos) {
    float dx = xPos - x;
    float dy = yPos - y;
    float dz = zPos - z;
    
    float intensity = intMul / (dx * dx + dy * dy + dz * dz);
    
    lightCol.r = color.r * intensity;
    lightCol.g = color.g * intensity;
    lightCol.b = color.b * intensity;
    
    lightNorm.set(dx, dy, dz);
    float len = lightNorm.len();
    lightNorm.scl(-1.0f / len);
    
    return len;
  }
}
