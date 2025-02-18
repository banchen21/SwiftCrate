package org.bc.swiftCrate.exception;

import org.bukkit.inventory.ItemStack;

import java.util.UUID; /**
 * 无效物品操作
 */
public class InvalidItemException extends WarehouseException {
    private final ItemStack invalidItem;

    public InvalidItemException(UUID contextId, ItemStack item) {
        super(ErrorCode.ITEM_INVALID, contextId,
                "Invalid item operation: " + item.getType());
        this.invalidItem = item.clone();
    }

    public ItemStack getInvalidItem() {
        return invalidItem.clone();
    }
}
