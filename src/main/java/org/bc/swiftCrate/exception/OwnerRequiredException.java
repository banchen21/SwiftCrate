package org.bc.swiftCrate.exception;

import java.util.UUID;

/**
 * 需要仓库所有者权限
 */
public class OwnerRequiredException extends WarehouseException {
    private final UUID warehouseId;

    public OwnerRequiredException(UUID playerId, UUID warehouseId) {
        super(ErrorCode.OWNER_REQUIRED, playerId,
                "Warehouse ownership required: " + warehouseId);
        this.warehouseId = warehouseId;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }
}
