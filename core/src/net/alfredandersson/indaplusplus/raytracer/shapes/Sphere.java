package net.alfredandersson.indaplusplus.raytracer.shapes;

import com.badlogic.gdx.math.Vector3;
import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.materials.Material;

public final class Sphere extends Shape {
  
  public final float radius, invradius;
  public final Vector3 pos;
  
  public Sphere(Material material, float x, float y, float z, float radius) {
    super(material);
    pos = new Vector3(x, y, z);
    this.radius = radius;
    this.invradius = 1.0f / radius;
  }
  
  @Override
  public boolean raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir) {
    float dx = pos.x - xStart;
    float dy = pos.y - yStart;
    float dz = pos.z - zStart;
    
    // quadratic formula
    float a = xDir * xDir + yDir * yDir + zDir * zDir;
    float b = -2 * (xDir * dx + yDir * dy + zDir * dz);
    float c = dx * dx + dy * dy + dz * dz - radius * radius;
    
    float insideSqrt = b * b - 4 * a * c;
    
    if (insideSqrt < 0) {
      if (result != null) result.setNoCollision();
      return false;
    }
    
    float t = (-b - (float)Math.sqrt(insideSqrt)) * 0.5f / a;
    //float t2 = (-b + (float)Math.sqrt(insideSqrt)) * 0.5f / a; // this will only be useful if the camera is inside the sphere --- ignore it for performance
    
    if (t < 0) {
      if (result != null) result.setNoCollision();
      return false;
    }
    
    if (result == null) {
      return true;
    }
    
    result.shape = this;
    result.hitDist = t;
    
    float hitX = xStart + xDir * t;
    float hitY = yStart + yDir * t;
    float hitZ = zStart + zDir * t;
    
    result.hit.set(hitX, hitY, hitZ);
    
    float normX = (hitX - pos.x) * invradius;
    float normY = (hitY - pos.y) * invradius;
    float normZ = (hitZ - pos.z) * invradius;
    
    result.norm.set(normX, normY, normZ);
    
    return true;
  }
}
