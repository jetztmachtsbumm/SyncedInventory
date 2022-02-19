package com.jetztmachtsbumm.syncedinv;

import com.jetztmachtsbumm.syncedinv.commands.SyncedInvCommand;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class SyncedInv extends JavaPlugin {

    private static SyncedInv instance;
    private Inventory inv;

    @Override
    public void onEnable() {
        instance = this;
        loadInventory();

        getCommand("syncedinv").setExecutor(new SyncedInvCommand());
    }

    @Override
    public void onDisable() {
        saveInventory();
    }

    private void loadInventory(){
        inv = Bukkit.createInventory(null, 9*5, "§6Ich trage Mantel.");
    }

    private void saveInventory(){

    }

    public static SyncedInv getInstance() {
        return instance;
    }

    public Inventory getInv() {
        return inv;
    }
}
