package org.bc.swiftCrate.api;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public record WarehouseItem(UUID id, UUID warehouseId, UUID depositor) {
}
