package me.syrrox.lynx;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.mineacademy.fo.plugin.SimplePlugin;

/**
 * PluginTemplate is a simple template you can use every time you make
 * a new plugin. This will save you time because you no longer have to
 * recreate the same skeleton and features each time.
 * It uses Foundation for fast and efficient development process.
 */
public final class LynxPlugin extends SimplePlugin {



	/**
	* Automatically perform login ONCE when the plugin starts.
	*/
	@Override
	protected void onPluginStart() {
		System.out.println("Successfully started Lynx!");
	}



	/**
	 * Automatically perform login when the plugin starts and each time it is reloaded.
	 */
	@Override
	protected void onReloadablesStart() {

		// You can check for necessary plugins and disable loading if they are missing
		//Valid.checkBoolean(HookManager.isVaultLoaded(), "You need to install Vault so that we can work with packets, offline player data, prefixes and groups.");

		// Uncomment to load variables
		// Variable.loadVariables();

		//
		// Add your own plugin parts to load automatically here
		// Please see @AutoRegister for parts you do not have to register manually
		//
	}

	@Override
	protected void onPluginPreReload() {

		// Close your database here if you use one
		//YourDatabase.getInstance().close();
	}

	/* ------------------------------------------------------------------------------- */
	/* Events */
	/* ------------------------------------------------------------------------------- */

	/**
	 * An example event that checks if the right-clicked entity is a cow, and makes an explosion.
	 * You can write your events to your main class without having to register a listener.
	 * @param event
	 */



	@EventHandler
	public void onRightClick(final PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getType() == EntityType.COW) {
			event.getRightClicked().getWorld().createExplosion(event.getRightClicked().getLocation(), 5);
			System.out.println("BOOM!");
			org.mineacademy.fo.Common.broadcast("&fBOOM!");
		}

		if (event.getRightClicked().getType() == EntityType.PIG) {
			event.getRightClicked().setFireTicks(200);
			System.out.println("Pig set on fire for 2 seconds.");
		}

		if (event.getRightClicked().getType() == EntityType.CHICKEN) {
			event.getRightClicked().setPassenger(event.getRightClicked().getWorld().spawn(event.getRightClicked().getLocation(), Zombie.class));

		}

		if (event.getRightClicked().getType() == EntityType.ENDERMAN){

			((org.bukkit.entity.LivingEntity) event.getRightClicked()).getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED).setBaseValue(0);

			System.out.println("Froze enderman for exactly 10 second!");
		}
		if (event.getRightClicked().getType() == org.bukkit.entity.EntityType.SHEEP && event.getPlayer().isSneaking()){
			org.bukkit.entity.Sheep sheep = (org.bukkit.entity.Sheep) event.getRightClicked();

			// 1. Changes the wool color permanently to black
			sheep.setColor(org.bukkit.DyeColor.BLACK);

			// 2. Changes the name string
			sheep.setCustomName("FLASHH!");


			((org.bukkit.entity.LivingEntity) event.getRightClicked()).getAttribute(org.bukkit.attribute.Attribute.MOVEMENT_SPEED).setBaseValue(0.5);


		}
	}


	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event){
		giveDiamondOnJoin(event);
	}

	public void giveDiamondOnJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();

		ItemStack[] contents = inventory.getStorageContents();
		boolean diamondGiven = false;
		int firstEmptySlot = -1;

		int diamondSlot = -1;
		for (int i = 0; i < contents.length; i++){

			ItemStack item = contents[i];
			if ((item == null || item.getType() == Material.AIR) && firstEmptySlot == -1){
				firstEmptySlot = i;
			}

			boolean isDiamond = item != null && item.getType() == Material.DIAMOND;

			if (isDiamond && item.getAmount() < item.getMaxStackSize()){
				diamondSlot = i;
				break;
			}


		}

		if (diamondSlot != -1){
			int diamondAmount = contents[diamondSlot].getAmount();
			contents[diamondSlot].setAmount(contents[diamondSlot].getAmount() + 1);
			player.sendMessage("You've been given 1 diamond at slot " + (diamondSlot + 1) + " !");
			diamondGiven = true;

		} else {
			if (firstEmptySlot != -1){
				contents[firstEmptySlot] = new ItemStack(Material.DIAMOND);
				player.sendMessage("You've been given 1 diamond at slot " + (firstEmptySlot + 1) + " !");
				diamondGiven = true;
			}
		}
		if (!diamondGiven) {
			player.sendMessage("Cannot give you a diamond as your inventory is full!");
		}

		inventory.setStorageContents(contents);
	}



	/* ------------------------------------------------------------------------------- */
	/* Static */
	/* ------------------------------------------------------------------------------- */

	/**
	 * Return the instance of this plugin, which simply refers to a static
	 * field already created for you in SimplePlugin but casts it to your
	 * specific plugin instance for your convenience.
	 *
	 */
	public static me.syrrox.lynx.LynxPlugin getInstance() {
		return (me.syrrox.lynx.LynxPlugin) SimplePlugin.getInstance();
	}
}
