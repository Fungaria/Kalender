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
    public final BitmapFont comic;
    public final BitmapFont openSans;
    public final BitmapFont openSansSmall;
    public final BitmapFont openSansBold;
    public final BitmapFont openSansBoldSmall;
    public final BitmapFont robotoCondensedBold;
    public final BitmapFont robotoCondensedRegular;
    public final BitmapFont robotoCondensedRegular18;
    public final BitmapFont robotoCondensedRegular10;
    public final BitmapFont roboto20;

    public final Color grey1 = new Color(0.949f, 0.949f, 0.949f, 1);
    public final Color grey2 = new Color(200f/255f, 200f/255f, 200f/255f, 1);
    public final Color grey3 = new Color(130f/255f, 130f/255f, 130f/255f, 1);
    public final Color grey4 = new Color(160f/255f, 160f/255f, 160f/255f, 1);
    public final Color grey5 = new Color(53f/255f, 53f/255f, 53f/255f, 1);

    public final Fonts fonts;
    
    private final TextureAtlas atlas;
    
    public Assets() {
        
        fonts = new Fonts();
        
        comic = new BitmapFont(Gdx.files.internal("fonts\\opensans_regular.fnt"));
        openSans = new BitmapFont(Gdx.files.internal("fonts\\opensans_regular.fnt"));
        openSansBold = new BitmapFont(Gdx.files.internal("fonts\\opensans_bold.fnt"));
        openSansSmall = new BitmapFont(Gdx.files.internal("fonts\\opensans_regular_small.fnt"));
        openSansBoldSmall = new BitmapFont(Gdx.files.internal("fonts\\opensans_bold_small.fnt"));
        robotoCondensedRegular = new BitmapFont(Gdx.files.internal("fonts\\roboto_condensed_regular_33.fnt"));
        robotoCondensedRegular18 = new BitmapFont(Gdx.files.internal("fonts\\roboto_condensed_regular_17.fnt"));
        robotoCondensedRegular10 = new BitmapFont(Gdx.files.internal("fonts\\roboto_condensed_regular_10.fnt"));
        robotoCondensedBold = new BitmapFont(Gdx.files.internal("fonts\\roboto_condensed_bold_33.fnt"));
        roboto20 = new BitmapFont(Gdx.files.internal("fonts\\roboto_20.fnt"));
        
        atlas = new TextureAtlas(Gdx.files.internal("img/sprites.pack"));
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int l, int r, int t, int b){
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");

        return new NinePatchDrawable(new NinePatch(region, l, r, t, b));
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
