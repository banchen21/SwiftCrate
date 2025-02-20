package org.bc.swiftCrate.dao;

import org.bc.swiftCrate.api.WarehouseType;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

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

}
