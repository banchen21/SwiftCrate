package org.bc.swiftCrate.exception;

import java.util.UUID; /**
 * 数据库操作异常
 */
public class DatabaseException extends WarehouseException {
    private final String sqlState;

    public DatabaseException(UUID contextId, String sqlState, Throwable cause) {
        super(ErrorCode.DATABASE, contextId,
                "Database error: " + sqlState, cause);
        this.sqlState = sqlState;
    }

    public String getSqlState() {
        return sqlState;
    }
}
