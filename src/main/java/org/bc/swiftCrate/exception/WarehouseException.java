package org.bc.swiftCrate.exception;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * 仓库系统异常基类（非受检异常）
 *
 * <p>所有仓库相关异常的父类，建议使用全局异常处理器捕获</p>
 */
public class WarehouseException extends RuntimeException {
    private final long timestamp = System.currentTimeMillis();
    private final ErrorCode errorCode;
    private final UUID contextId; // 相关ID（仓库/玩家）

    public WarehouseException(ErrorCode errorCode, UUID contextId, String message) {
        super(message);
        this.errorCode = errorCode;
        this.contextId = contextId;
    }

    public WarehouseException(ErrorCode errorCode, UUID contextId, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.contextId = contextId;
    }

    // region 公共访问方法
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public UUID getContextId() {
        return contextId;
    }

    public long getTimestamp() {
        return timestamp;
    }
    // endregion

    /**
     * 错误代码枚举（包含国际化的消息key）
     */
    public enum ErrorCode {
        // 验证错误 (1xxx)
        INVALID_NAME(1001, "error.validation.name"),
        DUPLICATE_NAME(1002, "error.validation.duplicate"),
        CAPACITY_LIMIT(1003, "error.validation.capacity"),

        // 权限错误 (2xxx)
        PERMISSION_DENIED(2001, "error.permission.general"),
        OWNER_REQUIRED(2002, "error.permission.owner"),
        ADMIN_REQUIRED(2003, "error.permission.admin"),

        // 存储错误 (3xxx)
        STORAGE_FULL(3001, "error.storage.full"),
        ITEM_INVALID(3002, "error.storage.item"),

        // 系统错误 (4xxx)
        DATABASE(4001, "error.system.database"),
        CONFIG(4002, "error.system.config");

        private final int code;
        private final String messageKey;

        ErrorCode(int code, String messageKey) {
            this.code = code;
            this.messageKey = messageKey;
        }

        public int getCode() {
            return code;
        }

        public String getMessageKey() {
            return messageKey;
        }
    }
}

// region 具体异常类型
//--------------------------------------------------
// 验证异常
//--------------------------------------------------

//--------------------------------------------------
// 权限异常
//--------------------------------------------------

//--------------------------------------------------
// 存储操作异常
//--------------------------------------------------

//--------------------------------------------------
// 系统级异常
//--------------------------------------------------

// endregion