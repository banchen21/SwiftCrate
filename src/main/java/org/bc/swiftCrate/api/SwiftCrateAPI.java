package org.bc.swiftCrate.api;

import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.UUID;

/**
 * SwiftCrate 仓库系统API
 * 权限节点说明：
 * | 权限节点                  | 功能说明               |
 * |--------------------------|----------------------|
 * | swiftcrate.create         | 创建新仓库            |
 * | swiftcrate.delete         | 删除自有仓库          |
 * | swiftcrate.delete.others  | 删除他人仓库(需admin) |
 * | swiftcrate.rename         | 重命名仓库            |
 * | swiftcrate.view.self      | 查看自己的仓库列表    |
 * | swiftcrate.view.others    | 查看他人仓库(需admin) |
 * | swiftcrate.member.add     | 添加仓库成员          |
 * | swiftcrate.member.remove  | 移除仓库成员          |
 * | swiftcrate.capacity.set   | 设置仓库容量          |
 * | swiftcrate.item.deposit   | 存入物品              |
 * | swiftcrate.item.withdraw  | 取出物品              |
 * | swiftcrate.admin          | 管理员权限            |
 */
public interface SwiftCrateAPI {

    //region 基础操作
    /**
     * 创建仓库（需要 swiftcrate.create 权限）
     * @param name 仓库名称(3-16字符)
     * @param owner 所有者UUID
     * @return 操作是否成功
     */
    boolean createWarehouse(String name, UUID owner);

    /**
     * 删除仓库（需要 swiftcrate.delete 或管理员权限）
     * @param warehouseId 仓库唯一ID
     * @param requester 请求者UUID（用于权限验证）
     * @return 操作状态码：0=成功, 1=无权限, 2=仓库不存在
     */
    int deleteWarehouse(UUID warehouseId, UUID requester);

    /**
     * 获取玩家拥有的所有仓库（需要 swiftcrate.view.self）
     * @param playerUUID 玩家UUID
     * @return 仓库列表（无权限返回空列表）
     */
    List<Warehouse> getPlayerWarehouses(UUID playerUUID);
    //endregion

    //region 成员管理
    /**
     * 添加仓库成员（需要仓库所有者或 swiftcrate.member.add 权限）
     * @param warehouseId 仓库ID
     * @param executor 操作者UUID
     * @param newMember 新成员UUID
     * @return 操作状态码：0=成功, 1=无权限, 2=成员已存在
     */
    int addMember(UUID warehouseId, UUID executor, UUID newMember);

    /**
     * 移除仓库成员（需要所有者或 swiftcrate.member.remove 权限）
     * @param warehouseId 仓库ID
     * @param executor 操作者UUID
     * @param member 被移除成员UUID
     * @return 操作状态码：0=成功, 1=无权限, 2=成员不存在
     */
    int removeMember(UUID warehouseId, UUID executor, UUID member);
    //endregion

    //region 物品管理
    /**
     * 存入物品到仓库（需要 swiftcrate.item.deposit 权限）
     * @param warehouseId 仓库ID
     * @param depositor 存入者UUID
     * @param items 要存入的物品数组
     * @return 实际成功存入的物品数量
     */
    int depositItems(UUID warehouseId, UUID depositor, ItemStack[] items);

    /**
     * 从仓库取出物品（需要 swiftcrate.item.withdraw 权限）
     * @param warehouseId 仓库ID
     * @param withdrawer 取出者UUID
     * @param maxItems 最大取出数量
     * @return 取出的物品数组（可能小于请求数量）
     */
    ItemStack[] withdrawItems(UUID warehouseId, UUID withdrawer, int maxItems);
    //endregion

    //region 高级功能
    /**
     * 设置仓库容量（需要 swiftcrate.capacity.set 权限）
     * @param warehouseId 仓库ID
     * @param executor 操作者UUID
     * @param newCapacity 新容量(1-54)
     * @return 操作是否成功
     */
    boolean setCapacity(UUID warehouseId, UUID executor, int newCapacity);

    /**
     * 转移仓库所有权（需要仓库当前所有者权限）
     * @param warehouseId 仓库ID
     * @param currentOwner 当前所有者UUID
     * @param newOwner 新所有者UUID
     * @return 操作状态码：0=成功, 1=无权限, 2=新所有者无效
     */
    int transferOwnership(UUID warehouseId, UUID currentOwner, UUID newOwner);
    //endregion

    //region 查询接口
    /**
     * 根据ID获取仓库详情（需要访问权限）
     * @param warehouseId 仓库ID
     * @param requester 请求者UUID
     * @return Warehouse对象或null（无权限时返回null）
     */
    Warehouse getWarehouseById(UUID warehouseId, UUID requester);

    /**
     * 检查玩家对仓库的权限
     * @param warehouseId 仓库ID
     * @param playerUUID 玩家UUID
     * @return 权限级别：0=无权限, 1=成员, 2=所有者, 3=管理员
     */
    int getAccessLevel(UUID warehouseId, UUID playerUUID);
}