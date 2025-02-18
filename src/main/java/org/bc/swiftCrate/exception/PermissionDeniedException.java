package org.bc.swiftCrate.exception;

import java.util.UUID; /**
 * 通用权限不足
 */
public class PermissionDeniedException extends WarehouseException {
    private final String requiredPermission;

    public PermissionDeniedException(UUID playerId, String permission) {
        super(ErrorCode.PERMISSION_DENIED, playerId,
                "Permission required: " + permission);
        this.requiredPermission = permission;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }
}
