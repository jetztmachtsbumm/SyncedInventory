package com.jetztmachtsbumm.syncedinv;

import com.jetztmachtsbumm.syncedinv.commands.SyncedInvCommand;
import com.jetztmachtsbumm.syncedinv.listeners.SyncedInventoryChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class SyncedInv extends JavaPlugin {

    private static SyncedInv instance;
    private Inventory inv;

    @Override
    public void onEnable() {
        instance = this;
        loadInventory();

        Bukkit.getPluginManager().registerEvents(new SyncedInventoryChangeListener(), this);

        getCommand("syncedinv").setExecutor(new SyncedInvCommand());
    }

    public void saveInventory(){
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) != null){
                String rootPath = "Inventory." + i + ".";
                ItemStack item = inv.getItem(i);
                getConfig().set(rootPath + "Material",item.getType().toString());
                getConfig().set(rootPath + "Amount", item.getAmount());
                if(item.getItemMeta().hasDisplayName()) {
                    getConfig().set(rootPath + "DisplayName", item.getItemMeta().getDisplayName());
                }
                if(item.getItemMeta().hasEnchants()){
                    for(Map.Entry<Enchantment, Integer> entry : item.getItemMeta().getEnchants().entrySet()){
                        getConfig().set(rootPath + "Enchantments." + entry.getKey().getKey().getKey(), entry.getValue());
                    }
                }
            }else{
                getConfig().set("Inventory." + i, "");
            }
        }
        saveConfig();
    }

    private void loadInventory(){
        inv = Bukkit.createInventory(null, 9*5, "§6Ich trage Mantel.");
        if(getConfig().contains("Inventory")){
            for(int i = 0; i < inv.getSize(); i++){
                String rootPath = "Inventory." + i + ".";
                if(getConfig().contains(rootPath + "Material")) {
                    ItemStack item = new ItemStack(Material.valueOf(getConfig().getString(rootPath + "Material")), getConfig().getInt(rootPath + "Amount"));
                    ItemMeta meta = item.getItemMeta();
                    if (getConfig().contains(rootPath + "DisplayName")) {
                        meta.setDisplayName(getConfig().getString(rootPath + "DisplayName"));
                    }
                    if (getConfig().contains(rootPath + "Enchantments")) {
                        for (String enchantment : getConfig().getConfigurationSection(rootPath + "Enchantments").getKeys(false)) {
                            meta.addEnchant(EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchantment)), getConfig().getInt(rootPath + "Enchantments." + enchantment), false);
                        }
                    }
                    item.setItemMeta(meta);
                    inv.setItem(i, item);
                }
            }
        }
    }

    public static SyncedInv getInstance() {
        return instance;
    }

    public Inventory getInv() {
        return inv;
    }
}
