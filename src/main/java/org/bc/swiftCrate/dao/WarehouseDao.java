package org.bc.swiftCrate.dao;

import org.bc.swiftCrate.api.WarehouseItem;
import org.bc.swiftCrate.api.WarehouseType;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

public interface WarehouseDao {

    //    插入仓库
    @SqlUpdate("INSERT INTO warehouses (uuid, name, owner, type,capacity) VALUES (:uuid, :name, :owner,:type,:capacity)")
    void insert(
            @Bind("uuid") UUID uuid,
            @Bind("name") String name,
            @Bind("owner") UUID owner,
            @Bind("type") WarehouseType type,
            @Bind("capacity") int capacity
    );

    //    删除仓库
    @SqlUpdate("DELETE FROM warehouses WHERE uuid = :uuid")
    void delete(@Bind("uuid") UUID uuid);

    //    存放物品
    @SqlUpdate("INSERT into warehouse_items(id,warehouseId,depositor) values (:id,:warehouseId,:depositor)")
    void depositItems( @Bind("id") UUID id, @Bind("warehouseId") UUID warehouseId, @Bind("depositor") UUID depositor);

    //    取出物品
    @SqlUpdate("DELETE FROM warehouse_items WHERE warehouseId= :warehouseId and id = :id")
    void deleteItems( @Bind("warehouseId") UUID warehouseId,@Bind("id") UUID id);

//    获取符合仓库id的物品id
    @SqlUpdate("SELECT id FROM warehouse_items WHERE warehouseId = :warehouseId")
    List<UUID> getWarehouseItems(@Bind("warehouseId") UUID warehouseId);
}
