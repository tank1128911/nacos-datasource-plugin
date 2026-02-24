package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.ArrayUtils;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.NamespaceUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.ContextConstant;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * config_info表dml postgresql实现
 *
 * @author ken.yu
 **/
public class ConfigInfoMapperByPostgresql extends AbstractMapperByPostgresql implements ConfigInfoMapper {
    @Override
    public MapperResult findConfigInfoByAppFetchRows(MapperContext context) {
        String tenantId = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,app_name,content FROM config_info"
                + " WHERE tenant_id LIKE ? AND app_name= ?"
                + " ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(tenantId, appName, pageSize, startRow));
    }

    @Override
    public MapperResult getTenantIdList(MapperContext context) {
        String nameSpaceDefaultId = NamespaceUtil.getNamespaceDefaultId();
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT tenant_id FROM config_info WHERE tenant_id != ? GROUP BY tenant_id ORDER BY tenant_id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(nameSpaceDefaultId, pageSize, startRow));
    }

    @Override
    public MapperResult getGroupIdList(MapperContext context) {
        String nameSpaceDefaultId = NamespaceUtil.getNamespaceDefaultId();
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT group_id FROM config_info WHERE tenant_id =? GROUP BY group_id ORDER BY group_id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(nameSpaceDefaultId, pageSize, startRow));
    }

    @Override
    public MapperResult findAllConfigKey(MapperContext context) {
        String tenantId = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT data_id,group_id,app_name FROM config_info WHERE tenant_id LIKE ? ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(tenantId, pageSize, startRow));
    }
    
    @Override
    public MapperResult findAllConfigInfoBaseFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,content,md5 FROM config_info ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(pageSize, startRow));
    }
    
    @Override
    public MapperResult findAllConfigInfoFragment(MapperContext context) {
        long id = (long) context.getWhereParameter(FieldConstant.ID);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,app_name," + (Boolean.parseBoolean(context.getContextParameter(ContextConstant.NEED_CONTENT)) ? "content," : "")
                + "md5,gmt_modified,type,encrypted_data_key FROM config_info WHERE id > ? ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(id, pageSize, startRow));
    }
    
    @Override
    public MapperResult findChangeConfigFetchRows(MapperContext context) {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String tenantTmp = StringUtils.isBlank(tenant) ? StringUtils.EMPTY : tenant;
        final Timestamp startTime = (Timestamp) context.getWhereParameter(FieldConstant.START_TIME);
        final Timestamp endTime = (Timestamp) context.getWhereParameter(FieldConstant.END_TIME);
        
        List<Object> paramList = new ArrayList<>();
        
        final String sqlFetchRows = "SELECT id,data_id,group_id,tenant_id,app_name,type,md5,gmt_modified FROM config_info WHERE ";
        String where = " id > ? ";
        paramList.add(context.getWhereParameter(FieldConstant.LAST_MAX_ID));

        if (!StringUtils.isBlank(dataId)) {
            where += " AND data_id LIKE ? ";
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group)) {
            where += " AND group_id LIKE ? ";
            paramList.add(group);
        }
        
        if (!StringUtils.isBlank(tenantTmp)) {
            where += " AND tenant_id = ? ";
            paramList.add(tenantTmp);
        }
        
        if (!StringUtils.isBlank(appName)) {
            where += " AND app_name = ? ";
            paramList.add(appName);
        }
        if (startTime != null) {
            where += " AND gmt_modified >=? ";
            paramList.add(startTime);
        }
        if (endTime != null) {
            where += " AND gmt_modified <=? ";
            paramList.add(endTime);
        }

        final String paging = " ORDER BY id LIMIT ? OFFSET ?";
        paramList.add(context.getPageSize());
        paramList.add(context.getStartRow());

        return new MapperResult(sqlFetchRows + where + paging, paramList);
    }
    
    @Override
    public MapperResult listGroupKeyMd5ByPageFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,app_name,md5,type,gmt_modified,encrypted_data_key FROM config_info ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(pageSize, startRow));
    }
    
    @Override
    public MapperResult findConfigInfoBaseLikeFetchRows(MapperContext context) {
        List<Object> paramList = new ArrayList<>();
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();
        
        final String sqlFetchRows = "SELECT id,data_id,group_id,tenant_id,content FROM config_info WHERE ";
        String where = " tenant_id= ? ";
        paramList.add(NamespaceUtil.getNamespaceDefaultId());
        
        if (!StringUtils.isBlank(dataId)) {
            where += " AND data_id LIKE ? ";
            paramList.add(dataId);
        }
        if (!StringUtils.isBlank(group)) {
            where += " AND group_id LIKE ";
            paramList.add(group);
        }
        if (!StringUtils.isBlank(content)) {
            where += " AND content LIKE ? ";
            paramList.add(content);
        }

        final String paging = " ORDER BY id Limit ? OFFSET ?";
        paramList.add(pageSize);
        paramList.add(startRow);

        return new MapperResult(sqlFetchRows + where + paging, paramList);
    }
    
    @Override
    public MapperResult findConfigInfo4PageFetchRows(MapperContext context) {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();
        
        List<Object> paramList = new ArrayList<>();
        
        // 性能优化：先 LIMIT 再 JOIN，减少 JOIN 和 GROUP BY 的数据量
        StringBuilder innerSql = new StringBuilder("SELECT id,data_id,group_id,tenant_id,app_name,"
                + "content,md5,type,encrypted_data_key,c_desc FROM config_info WHERE tenant_id=?");
        paramList.add(tenant);
        
        if (StringUtils.isNotBlank(dataId)) {
            innerSql.append(" AND data_id=?");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group)) {
            innerSql.append(" AND group_id=?");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName)) {
            innerSql.append(" AND app_name=?");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content)) {
            innerSql.append(" AND content LIKE ?");
            paramList.add(content);
        }
        
        // 先分页，减少后续 JOIN 的数据量
        innerSql.append(" ORDER BY id LIMIT ? OFFSET ?");
        paramList.add(pageSize);
        paramList.add(startRow);
        
        // 外层查询：对分页后的结果进行标签关联
        final String sql = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.type,a.encrypted_data_key,a.c_desc,"
                          + "string_agg(b.tag_name, ',') as config_tags "
                          + "FROM (" + innerSql + ") a LEFT JOIN config_tags_relation b ON a.id=b.id "
                          + "GROUP BY a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.type,a.encrypted_data_key,a.c_desc";
        
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult findConfigInfoBaseByGroupFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,content FROM config_info WHERE group_id=? AND tenant_id=? ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.GROUP_ID),
                context.getWhereParameter(FieldConstant.TENANT_ID), pageSize, startRow));
    }
    
    @Override
    public MapperResult findConfigInfoLike4PageFetchRows(MapperContext context) {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] types = (String[]) context.getWhereParameter(FieldConstant.TYPE);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();
        
        List<Object> paramList = new ArrayList<>();
        
        // 性能优化：先 LIMIT 再 JOIN，减少 JOIN 和 GROUP BY 的数据量
        StringBuilder innerSql = new StringBuilder("SELECT id,data_id,group_id,tenant_id,app_name,content,md5,"
                + "encrypted_data_key,type,c_desc FROM config_info WHERE tenant_id LIKE ?");
        paramList.add(tenant);
        
        if (StringUtils.isNotBlank(dataId)) {
            innerSql.append(" AND data_id LIKE ?");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group)) {
            innerSql.append(" AND group_id LIKE ?");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName)) {
            innerSql.append(" AND app_name = ?");
            paramList.add(appName);
        }
        if (StringUtils.isNotBlank(content)) {
            innerSql.append(" AND content LIKE ?");
            paramList.add(content);
        }
        if (!ArrayUtils.isEmpty(types)) {
            innerSql.append(" AND type IN (");
            for (int i = 0; i < types.length; i++) {
                if (i != 0) {
                    innerSql.append(", ");
                }
                innerSql.append("?");
                paramList.add(types[i]);
            }
            innerSql.append(")");
        }
        
        // 先分页，减少后续 JOIN 的数据量
        innerSql.append(" ORDER BY id LIMIT ? OFFSET ?");
        paramList.add(pageSize);
        paramList.add(startRow);
        
        // 外层查询：对分页后的结果进行标签关联
        final String sql = "SELECT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.encrypted_data_key,a.type,a.c_desc,"
                          + "string_agg(b.tag_name, ',') as config_tags "
                          + "FROM (" + innerSql + ") a LEFT JOIN config_tags_relation b ON a.id=b.id "
                          + "GROUP BY a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.encrypted_data_key,a.type,a.c_desc";
        
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult findAllConfigInfoFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,app_name,content,md5 FROM config_info  WHERE tenant_id LIKE ? ORDER BY id LIMIT ? OFFSET ?";

        return new MapperResult(sql,
                CollectionUtils.list(context.getWhereParameter(FieldConstant.TENANT_ID), context.getPageSize(),
                        context.getStartRow()));
    }
}
