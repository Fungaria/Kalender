package de.fungistudii.kalender.util;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import de.fungistudii.kalender.Cons;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.main.generic.TwoColorLabel;

/**
 *
 * @author sreis
 */
public class Assets {
    public final Color kalSide = new Color(buildColor(229, 229, 231));
    public final Color kalBG = new Color(buildColor(245, 245, 247));
    
    public final Color grey1 = new Color(0.94f, 0.94f, 0.94f, 1);
    public final Color grey2 = new Color(225f/255f, 225f/255f, 225f/255f, 1);
    public final Color grey2p5 = new Color(215f/255f, 215f/255f, 215f/255f, 1);
    public final Color grey3 = new Color(200f/255f, 200f/255f, 200f/255f, 1);
    public final Color grey4 = new Color(130f/255f, 130f/255f, 130f/255f, 1);
    public final Color grey5 = new Color(160f/255f, 160f/255f, 160f/255f, 1);
    public final Color grey6 = new Color(100f/255f, 100f/255f, 100f/255f, 1);
    public final Color grey7 = new Color(53f/255f, 53f/255f, 53f/255f, 1);
    
    public final Color darkRed = new Color(204/255f, 43/255f, 43/255f, 1);
    public final Color lightRed = new Color(238/255f, 31/255f, 32/255f, 1);
    
    public final Color lightGreen = new Color(218/255f, 241/255f, 184/255f, 1);
    public final Color mediumGreen = new Color(115/255f, 215/255f, 0/255f, 1);
    public final Color darkGreen = new Color(104/255f, 193/255f, 0/255f, 1);
    public final Color tabGrey = new Color(0.3f, 0.3f, 0.3f, 1);
    public final Color tabGreyer = new Color(0.25f, 0.25f, 0.25f, 1);

    public Label.LabelStyle titleStyle1;
    public Label.LabelStyle titleStyle2;
    public Label.LabelStyle bodyStyle1;
    public Label.LabelStyle bodyStyle2;
    public CheckBox.CheckBoxStyle checkBoxStyle;
    public List.ListStyle listStyle;
    
    public TwoColorLabel.TwoColorLabelStyle twoColorStyle;
    
    public Fonts fonts;
    
    private TextureAtlas atlas;
    
    private final boolean debug;
    
    public SpriteDrawable horizontal_separator;
    public SpriteDrawable verticalSeparator;
    
    public Assets() {
        debug = !Gdx.files.internal("").file().getAbsolutePath().endsWith("assets");
    }
    
    public void load(){
        atlas = new TextureAtlas(internal("img/sprites.pack"));
        
        for (Texture texture : atlas.getTextures()) {
            System.out.println(texture.getMinFilter());
        }
        
        horizontal_separator = createDrawable("generic/horizontal_separator");
        verticalSeparator = createDrawable("generic/vertical_separator");
        
        fonts = new Fonts();
        
        initStyles();
    }
    
    public void initStyles(){
        titleStyle1 = new Label.LabelStyle(fonts.createFont("roboto",24), grey7);
        titleStyle2 = new Label.LabelStyle(fonts.createFont("roboto", 22), grey7);
        bodyStyle1 = new Label.LabelStyle(fonts.createFont("roboto", 18), grey7);
        bodyStyle2 = new Label.LabelStyle(fonts.createFont("roboto", 16), grey6);
        checkBoxStyle = new CheckBox.CheckBoxStyle(createDrawable("generic/square"), createDrawable("generic/square"), fonts.createFont("roboto", 16), grey6);
        twoColorStyle = new TwoColorLabel.TwoColorLabelStyle(ERE.assets.fonts.createFont("roboto", 18), ERE.assets.fonts.createFont("roboto", 18), ERE.assets.grey5, ERE.assets.grey7);
        
        listStyle = new List.ListStyle(fonts.createFont("roboto", 15, Fonts.LIGHT), Color.BLACK, grey6, new NinePatchSolid(grey1, 5));
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int l, int r, int t, int b){
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");

        NinePatchDrawable res = new NinePatchDrawable(new NinePatch(region, l, r, t, b));
        res.getPatch().scale(Cons.downscale, Cons.downscale);
        return res;
    }
    
    public NinePatchDrawable createNinePatchDrawable(String name, int l, int r, int t, int b, Color color){
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");

        NinePatch ninePatch = new NinePatch(region, l, r, t, b);
        ninePatch.setColor(color);
        NinePatchDrawable res = new NinePatchDrawable(ninePatch);
        res.getPatch().scale(Cons.downscale, Cons.downscale);
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
        SpriteDrawable drawable = new SpriteDrawable(sprite);
        return drawable;
    }
    
    public SpriteDrawable createDrawable(String name) {
        TextureRegion region = atlas.findRegion(name);
        
        if(region == null)
            throw new RuntimeException("Texture " + name +" not found!");
        
        return new SpriteDrawable(new Sprite(region));
    }
    
    public NinePatchOffsetDrawable createRounded(String stuff){
        NinePatch res = new NinePatch(atlas.findRegion("rounded/"+stuff), 15, 15, 15, 15);
        res.scale(Cons.downscale, Cons.downscale);
        NinePatchOffsetDrawable rounded = new NinePatchOffsetDrawable(res);
//        rounded.setOffsetX(Cons.roundedOffset);
//        rounded.setPadding(4.5f, 0, 4.5f, 4.5f);
        return rounded;
    }
    
    public NinePatchOffsetDrawable createRounded(String stuff, Color color){
        NinePatch res = new NinePatch(atlas.findRegion("rounded/"+stuff), 20, 20, 20, 20);
        res.setColor(color);
        res.scale(Cons.downscale, Cons.downscale);
        NinePatchOffsetDrawable rounded = new NinePatchOffsetDrawable(res);
//        rounded.setOffsetX(Cons.roundedOffset);
//        rounded.setPadding(4.5f, 0, 4.5f, 4.5f);
        return rounded;
    }
    
    public static int buildColor(int r, int g, int b){
        return (r << 24) | (g << 16) | (b << 8);
    }
    
    public FileHandle internal(String path){
        if(debug){
            return Gdx.files.internal("..\\core\\assets\\"+path);
        }else{
            return Gdx.files.internal(path);
        }
    }
    
}
