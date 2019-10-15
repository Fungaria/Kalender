package de.fungistudii.kalender.util;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 *
 * @author sreis
 */
public class Assets {

    public final Color grey1 = new Color(0.94f, 0.94f, 0.94f, 1);
    public final Color grey2 = new Color(200f/255f, 200f/255f, 200f/255f, 1);
    public final Color grey3 = new Color(130f/255f, 130f/255f, 130f/255f, 1);
    public final Color grey4 = new Color(160f/255f, 160f/255f, 160f/255f, 1);
    public final Color grey5 = new Color(53f/255f, 53f/255f, 53f/255f, 1);
    
    public final Color darkRed = new Color(204/255f, 43/255f, 43/255f, 1);
    public final Color lightRed = new Color(238/255f, 31/255f, 32/255f, 1);
    
    public final Color lightGreen = new Color(218/255f, 241/255f, 184/255f, 1);
    public final Color mediumGreen = new Color(115/255f, 215/255f, 0/255f, 1);
    public final Color darkGreen = new Color(104/255f, 193/255f, 0/255f, 1);

    public final Fonts fonts;
    
    private final TextureAtlas atlas;
    
    
    public Assets() {
        fonts = new Fonts();
        atlas = new TextureAtlas(Gdx.files.internal("img/sprites.pack"));
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int l, int r, int t, int b){
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");

        NinePatchDrawable res = new NinePatchDrawable(new NinePatch(region, l, r, t, b));
        res.getPatch().scale(0.5f, 0.5f);
        return res;
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int l, int r, int t, int b, Color color){
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");

        NinePatch ninePatch = new NinePatch(region, l, r, t, b);
        ninePatch.setColor(color);
        NinePatchDrawable res = new NinePatchDrawable(ninePatch);
        res.getPatch().scale(0.5f, 0.5f);
        return res;
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int pad, Color color){
        return createNinePatchDrawable(name, pad, pad, pad, pad, color);
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int pad){
        return createNinePatchDrawable(name, pad, pad, pad, pad);
    }
    
    public SpriteDrawable createDrawable(String name, Color color) {
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");
        
        Sprite sprite = new Sprite(region);
        sprite.setColor(color);
        return new SpriteDrawable(sprite);
    }
    
    public SpriteDrawable createDrawable(String name) {
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");
        
        return new SpriteDrawable(new Sprite(region));
    }
}
