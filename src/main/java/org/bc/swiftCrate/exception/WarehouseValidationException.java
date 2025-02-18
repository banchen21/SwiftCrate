package org.bc.swiftCrate.exception;

import java.util.UUID; /**
 * 仓库名称验证失败
 */
public class WarehouseValidationException extends WarehouseException {
    private final String invalidName;

    public WarehouseValidationException(UUID contextId, String invalidName) {
        super(ErrorCode.INVALID_NAME, contextId,
                "Invalid warehouse name: " + invalidName);
        this.invalidName = invalidName;
    }

    public String getInvalidName() {
        return invalidName;
    }
}
