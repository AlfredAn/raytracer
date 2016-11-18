package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.BufferUtils;
import java.nio.ByteBuffer;

public class CustomTextureData implements TextureData {
  
  private final ByteBuffer data;
  private final int width, height;
  
  public CustomTextureData(float[] data, int width, int height) {
    this.data = BufferUtils.newByteBuffer(data.length);
    
    for (int i = 0; i < data.length; i++) {
      this.data.put((byte)Math.min(Math.max(Math.round(data[i] * 255), 0), 255));
    }
    
    this.data.flip();
    
    this.width = width;
    this.height = height;
  }
  
  @Override
  public TextureDataType getType() {
    return TextureDataType.Custom;
  }
  
  @Override
  public boolean isPrepared() {
    return true;
  }
  
  @Override
  public void prepare() {}
  
  @Override
  public Pixmap consumePixmap() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public boolean disposePixmap() {
    return false;
  }
  
  @Override
  public void consumeCustomData(int target) {
    Gdx.gl.glTexImage2D(target, 0, GL30.GL_RGBA, width, height, 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, data);
  }
  
  @Override
  public int getWidth() {
    return width;
  }
  
  @Override
  public int getHeight() {
    return height;
  }
  
  @Override
  public Pixmap.Format getFormat() {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public boolean useMipMaps() {
    return false;
  }
  
  @Override
  public boolean isManaged() {
    return false;
  }
}
