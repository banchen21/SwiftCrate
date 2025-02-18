package org.bc.swiftCrate.exception;

import java.util.UUID; /**
 * 仓库名称重复
 */
public class DuplicateWarehouseException extends WarehouseException {
    private final String duplicateName;

    public DuplicateWarehouseException(UUID playerId, String name) {
        super(ErrorCode.DUPLICATE_NAME, playerId,
                "Duplicate warehouse name: " + name);
        this.duplicateName = name;
    }

    public String getDuplicateName() {
        return duplicateName;
    }
}
