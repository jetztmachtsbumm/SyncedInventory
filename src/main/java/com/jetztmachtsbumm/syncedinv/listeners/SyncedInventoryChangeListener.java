package com.jetztmachtsbumm.syncedinv.listeners;

import com.jetztmachtsbumm.syncedinv.SyncedInv;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SyncedInventoryChangeListener implements Listener {

    @EventHandler
    public void onSyncedInvChanged(InventoryClickEvent e){
        if(e.getInventory() == SyncedInv.getInstance().getInv()){
            Bukkit.getScheduler().runTaskAsynchronously(SyncedInv.getInstance(), () -> {
                SyncedInv.getInstance().saveInventory();
            });
        }
    }

}
