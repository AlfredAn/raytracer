package net.alfredandersson.indaplusplus.raytracer;

public final class RaycastResult {
  
  public float hitDist;
  public float hitX, hitY, hitZ;
  public float normX, normY, normZ;
  public Shape shape;
  
  public void set(RaycastResult other) {
    shape = other.shape;
    
    hitDist = other.hitDist;
    
    hitX = other.hitX;
    hitY = other.hitY;
    hitZ = other.hitZ;
    
    normX = other.normX;
    normY = other.normY;
    normZ = other.normZ;
  }
  
  public void set(Shape shape, float hitDist, float hitX, float hitY, float hitZ, float normX, float normY, float normZ) {
    this.shape = shape;
    
    this.hitDist = hitDist;
    
    this.hitX = hitX;
    this.hitY = hitY;
    this.hitZ = hitZ;
    
    this.normX = normX;
    this.normY = normY;
    this.normZ = normZ;
  }
  
  public void setNoCollision() {
    shape = null;
    hitDist = Float.POSITIVE_INFINITY;
  }
  
  public boolean isHit() {
    return shape != null;
  }
}
