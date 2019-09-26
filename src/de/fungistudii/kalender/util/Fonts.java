package de.fungistudii.kalender.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sreis
 */
public class Fonts implements Disposable{

    private FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    
    public static final int MEDIUM = 0;
    public static final int BOLD = 1;
    public static final int LIGHT = 2;
    public static final int THIN = 3;
    
    private final HashMap<String, FreeTypeFontGenerator> generators = new HashMap<>();
    private final HashMap<String, BitmapFont> fonts = new HashMap<>();
    
    public Fonts() {
        generators.put("roboto0", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto/Roboto-Medium.ttf")));
        generators.put("roboto1", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto/Roboto-Bold.ttf")));
        generators.put("roboto2", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto/Roboto-Light.ttf")));
        generators.put("roboto3", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto/Roboto-Thin.ttf")));
        generators.put("robotoCondensed0", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto_Condensed/RobotoCondensed-Regular.ttf")));
        generators.put("robotoCondensed1", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto_Condensed/RobotoCondensed-Bold.ttf")));
        generators.put("robotoCondensed2", new FreeTypeFontGenerator(Gdx.files.internal("ttf/Roboto_Condensed/RobotoCondensed-Light.ttf")));
    }
    
    public BitmapFont createFont(String font, int size){
        return createFont(font, size, MEDIUM);
    }
    
    public BitmapFont createFont(String font, int size, int style){
        parameter.size = size;
        String key = font+""+style+"_"+size;
        if(fonts.containsKey(key)){
            return fonts.get(key);
        }else{
            BitmapFont fnt = createFont(font, parameter, style);
            fonts.put(key, fnt);
            return fnt;
        }
    }
    
    public BitmapFont createFont(String font, FreeTypeFontParameter parameter, int style){
        return generators.get(font+""+style).generateFont(parameter);
    }
    
    @Override
    public void dispose() {
        for (Map.Entry<String, FreeTypeFontGenerator> entry : generators.entrySet()) {
            entry.getValue().dispose();
        }
    }
}
