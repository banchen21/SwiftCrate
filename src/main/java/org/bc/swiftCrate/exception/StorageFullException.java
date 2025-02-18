package org.bc.swiftCrate.exception;

import java.util.UUID;

/**
 * 仓库存储已满
 */
public class StorageFullException extends WarehouseException {
    private final int attemptedAmount;

    public StorageFullException(UUID warehouseId, int attempted) {
        super(ErrorCode.STORAGE_FULL, warehouseId,
                "Storage full, attempted: " + attempted);
        this.attemptedAmount = attempted;
    }

    public int getAttemptedAmount() {
        return attemptedAmount;
    }
}
