package net.alfredandersson.indaplusplus.raytracer;

public final class Sphere implements Shape {
  
  public final float x, y, z, radius;
  
  public Sphere(float x, float y, float z, float radius) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.radius = radius;
  }
  
  @Override
  public void raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir) {
    // project (start -> (x, y, z)) onto (dir)
    float dx = x - xStart;
    float dy = y - yStart;
    float dz = z - zStart;
    
    // dir is assumed to be normalized
    float projLength = dx * xDir + dy * yDir + dz * zDir;
    
    float startDistSqr = dx * dx + dy * dy + dz * dz;
    float minDist = (float)Math.sqrt(startDistSqr - projLength * projLength);
    
    if (minDist >= 0 && minDist <= radius) {
      // TODO: find point of intersection
      result.set(this, projLength, 0, 0, 0, 0, 0, 0);
    } else {
      result.setNoCollision();
    }
  }
}
