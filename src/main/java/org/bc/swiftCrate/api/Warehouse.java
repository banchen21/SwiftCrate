package org.bc.swiftCrate.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter

public class Warehouse {
    //    仓库编号
    private UUID id;
    //    仓库名称
    private final String name;
    //    仓库所有者
    private final UUID owner;
    //    仓库类型
    private final WarehouseType type;
    //    仓库容量
    private final int capacity;
//    仓库内物品
    private List<WarehouseItem> items;

    public Warehouse(String name, UUID owner, WarehouseType type, int capacity) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.capacity = capacity;
    }

    public Warehouse(UUID id, String name, UUID owner, WarehouseType type, int capacity) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.capacity = capacity;
    }
}
