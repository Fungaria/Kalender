package de.fungistudii.kalender.util;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import java.util.HashMap;

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
    
    public NinePatchDrawable createNinePatchDrawable(String name, int pad){
        return createNinePatchDrawable(name, pad, pad, pad, pad);
    }

    public SpriteDrawable createDrawable(String name) {
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");
        
        return new SpriteDrawable(new Sprite(region));
    }
}
