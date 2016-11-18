package net.alfredandersson.indaplusplus.raytracer;

import net.alfredandersson.indaplusplus.raytracer.shapes.Shape;
import com.badlogic.gdx.math.Vector3;

public final class RaycastResult {
  
  public Shape shape;
  public float hitDist;
  public final Vector3 hit = new Vector3();
  public final Vector3 norm = new Vector3();
  
  public void set(RaycastResult other) {
    shape = other.shape;
    
    hitDist = other.hitDist;
    
    hit.set(other.hit);
    norm.set(other.norm);
  }
  
  public void setNoCollision() {
    shape = null;
    hitDist = Float.POSITIVE_INFINITY;
  }
  
  public boolean isHit() {
    return shape != null;
  }
}
