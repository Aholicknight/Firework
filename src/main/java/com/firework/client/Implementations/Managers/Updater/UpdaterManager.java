package com.firework.client.Implementations.Managers.Updater;

import com.firework.client.Implementations.Managers.Updater.Updater;
import java.util.ArrayList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class UpdaterManager {
    public ArrayList<Updater> updaters = new ArrayList();
    public int index = 0;

    public boolean containsIndex(int index) {
        for (Updater updater : this.updaters) {
            if (updater.index != index) continue;
            return true;
        }
        return false;
    }

    public void registerUpdater(Updater updater) {
        this.updaters.add(updater);
    }

    public void removeUpdater(Updater updater) {
        this.updaters.remove(updater);
        System.out.println("d");
    }

    public void removeUpdater(int index) {
        Updater up = null;
        for (Updater updater : this.updaters) {
            if (updater.index != index) continue;
            up = updater;
        }
        this.updaters.remove(up);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Updater updater : this.updaters) {
            updater.run();
        }
    }
}
