package de.fungistudii.kalender.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import static de.fungistudii.kalender.Main.ERE;
import de.fungistudii.kalender.client.NetworkData;
import de.fungistudii.kalender.database.Blockierung;

/**
 *
 * @author sreis
 */
public class BlockListener extends Listener{

    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof Blockierung){
            addBlock((Blockierung)object);
            updateGUI();
        }else if(object instanceof NetworkData.RemoveBlockRequest){
            removeBlock(((NetworkData.RemoveBlockRequest) object).id);
            updateGUI();
        }
    }
    
    public void updateGUI(){
        Gdx.app.postRunnable(() -> {
            ERE.mainScreen.kalender.updateCurrentTable();
        });
    }
    
    public void addBlock(Blockierung block){
        ERE.data.root.blockierungen.put(block.id, block);
        ERE.data.writeFile();
    }
    
    public void removeBlock(int id){
        ERE.data.root.blockierungen.remove(id);
        ERE.data.writeFile();
    }
}
