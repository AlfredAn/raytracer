package net.alfredandersson.indaplusplus.raytracer.shapes;

import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.materials.Material;

public final class Plane extends Shape {
  
  public final Vector3 point, normal;
  
  public Plane(Material material, Vector3 point, Vector3 normal) {
    super(material);
    
    this.point = new Vector3(point);
    this.normal = new Vector3(normal);
  }
  
  @Override
  public boolean raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir) {
    float tNumerator = Vector3.dot(point.x - xStart, point.y - yStart, point.z - zStart, normal.x, normal.y, normal.z);
    float tDenominator = Vector3.dot(xDir, yDir, zDir, normal.x, normal.y, normal.z);
    
    if (Math.abs(tDenominator) < 0.0001) {
      result.setNoCollision();
      return false;
    }
    
    float t = tNumerator / tDenominator;
    if (t < 0.0001) {
      result.setNoCollision();
      return false;
    }
    
    result.shape = this;
    result.hitDist = t;
    
    result.hit.set(xStart + xDir * t, yStart + yDir * t, zStart + zDir * t);
    result.hitDir.set(xDir, yDir, zDir);
    result.norm.set(normal);
    
    return true;
  }
}
