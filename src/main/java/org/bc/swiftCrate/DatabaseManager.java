package org.bc.swiftCrate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bc.swiftCrate.api.SwiftCrateAPI;
import org.bc.swiftCrate.api.Warehouse;
import org.bc.swiftCrate.api.WarehouseType;
import org.bc.swiftCrate.dao.WarehouseDao;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * DatabaseManager 用于处理玩家物品数据的存储和加载，
 * 支持 MySQL 和 SQLite 两种数据库，具体使用哪种数据库由配置文件决定。
 */
public class DatabaseManager implements SwiftCrateAPI {
    private Connection connection;           // 数据库连接
    private final Logger logger;             // 日志记录器
    private final JavaPlugin plugin;         // 主插件实例，用于读取配置及调度任务
    private final String dbType;             // 数据库类型：mysql 或 sqlite
    private HikariDataSource dataSource;
    private Jdbi jdbi;

    /**
     * 构造函数，接收主插件实例以便读取配置文件和管理任务
     *
     * @param plugin 主插件实例
     */
    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        // 保存默认配置（如果不存在则创建config.yml）
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        // 读取配置文件中 database.type 的设置，默认为 sqlite
        this.dbType = config.getString("database.type", "sqlite").toLowerCase();

        // 根据配置初始化数据库连接
        initializeDatabase();
    }

    /**
     * 根据配置选择初始化 MySQL 或 SQLite 数据库连接
     */
    private void initializeDatabase() {
        if (dbType.equals("mysql")) {
            initializeMySQL();
        } else {
            dataSource = initializeSQLite();
            // 初始化 JDBI
            jdbi = Jdbi.create(dataSource);

            jdbi.installPlugin(new SqlObjectPlugin());
            jdbi.registerRowMapper(Warehouse.class, (rs, ctx) -> {
                UUID uuid = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                UUID owner = UUID.fromString(rs.getString("owner"));
                WarehouseType warehouseType = WarehouseType.valueOf(rs.getString("type"));
                int capacity = rs.getInt("capacity");
                return new Warehouse(uuid, name, owner, warehouseType, capacity);
            });
//            初始化表
            jdbi.useHandle(handle -> {
//                仓库表
                String createTableSQL = """
                        CREATE TABLE IF NOT EXISTS warehouses (
                            id       TEXT    NOT NULL PRIMARY KEY,
                            name     TEXT    NOT NULL,
                            owner    TEXT    NOT NULL,
                            type     TEXT    NOT NULL CHECK (type IN ('PRIVATE', 'GROUP', 'PUBLIC')),
                            capacity INTEGER NOT NULL
                        )
                        """;  // 注意：SQL 语句末尾无需分号（兼容性更好）
                handle.execute(createTableSQL);
//                log   todo 操作日志

            });

        }
    }

    /**
     * 初始化 MySQL 数据库连接
     */
    private void initializeMySQL() {

    }

    /**
     * 初始化 SQLite 数据库连接
     */
    HikariDataSource initializeSQLite() {
        HikariConfig config = new HikariConfig();
        File databaseFile = new File(plugin.getDataFolder(), "database.db");
        config.setJdbcUrl("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        config.setMaximumPoolSize(1); // SQLite 单连接
        return new HikariDataSource(config);
    }

    @Override
    public boolean createWarehouse(String name, UUID owner, WarehouseType warehouseType, int capacity) {
        try {
            Warehouse warehouse = new Warehouse(name, owner, warehouseType, capacity);
            jdbi.useExtension(WarehouseDao.class, dao -> dao.insert(warehouse.getId(), warehouse.getName(), warehouse.getOwner(), warehouse.getType(), warehouse.getCapacity()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteWarehouse(UUID warehouseId) {
        try {
//            TODO 清除物品
            jdbi.useExtension(WarehouseDao.class, dao -> dao.delete(warehouseId));
            return true;
        } catch (Exception e) {
            logger.info("删除仓库失败");
            return false;
        }
    }

    @Override
    public List<Warehouse> getPlayerWarehouses(UUID playerUUID) {
        return List.of();
    }

    @Override
    public int addMember(UUID warehouseId, UUID executor, UUID newMember) {
        return 0;
    }

    @Override
    public int removeMember(UUID warehouseId, UUID executor, UUID member) {
        return 0;
    }

    @Override
    public int depositItems(UUID warehouseId, UUID depositor, ItemStack[] items) {
//        TODO 将物品存入仓库
        return 0;
    }

    @Override
    public ItemStack[] withdrawItems(UUID warehouseId, UUID withdrawer, int maxItems) {
        return new ItemStack[0];
    }

    @Override
    public boolean setCapacity(UUID warehouseId, UUID executor, int newCapacity) {
        return false;
    }

    @Override
    public int transferOwnership(UUID warehouseId, UUID currentOwner, UUID newOwner) {
        return 0;
    }

    @Override
    public Warehouse getWarehouseById(UUID warehouseId, UUID requester) {
        return null;
    }

    @Override
    public int getAccessLevel(UUID warehouseId, UUID playerUUID) {
        return 0;
    }
}
