package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.ArrayUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigTagsRelationMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

import java.util.ArrayList;
import java.util.List;

/**
 * config_tags_relation表dml postgresql实现
 *
 * @author ken.yu
 **/
public class ConfigTagsRelationMapperByPostgresql extends AbstractMapperByPostgresql implements ConfigTagsRelationMapper {
    @Override
    public MapperResult findConfigInfo4PageFetchRows(MapperContext context) {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();
        
        List<Object> paramList = new ArrayList<>();
        
        // 构建内层查询：根据标签条件筛选配置
        StringBuilder innerWhere = new StringBuilder(" WHERE ");
        innerWhere.append(" a.tenant_id=? ");
        paramList.add(tenant);
        
        if (StringUtils.isNotBlank(dataId)) {
            innerWhere.append(" AND a.data_id=? ");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group)) {
            innerWhere.append(" AND a.group_id=? ");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName)) {
            innerWhere.append(" AND a.app_name=? ");
            paramList.add(appName);
        }
        if (!StringUtils.isBlank(content)) {
            innerWhere.append(" AND a.content LIKE ? ");
            paramList.add(content);
        }
        innerWhere.append(" AND b.tag_name IN (");
        for (int i = 0; i < tagArr.length; i++) {
            if (i != 0) {
                innerWhere.append(", ");
            }
            innerWhere.append('?');
            paramList.add(tagArr[i]);
        }
        innerWhere.append(") ");
        
        // 使用子查询分离筛选逻辑和标签聚合逻辑
        final String sql = "SELECT c.id,c.data_id,c.group_id,c.tenant_id,c.app_name,c.content,c.md5,c.type,c.encrypted_data_key,c.c_desc,"
                + "string_agg(DISTINCT d.tag_name, ',') as config_tags "
                + "FROM ("
                + "SELECT DISTINCT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.type,a.encrypted_data_key,a.c_desc "
                + "FROM config_info a LEFT JOIN config_tags_relation b ON a.id=b.id"
                + innerWhere
                + " ORDER BY b.id LIMIT ? OFFSET ?) c LEFT JOIN config_tags_relation d ON c.id=d.id "
                + "GROUP BY c.id,c.data_id,c.group_id,c.tenant_id,c.app_name,c.content,c.md5,c.type,c.encrypted_data_key,c.c_desc";
        paramList.add(pageSize);
        paramList.add(startRow);
        
        return new MapperResult(sql, paramList);
    }
    
    @Override
    public MapperResult findConfigInfoLike4PageFetchRows(MapperContext context) {
        final String tenant = (String) context.getWhereParameter(FieldConstant.TENANT_ID);
        final String dataId = (String) context.getWhereParameter(FieldConstant.DATA_ID);
        final String group = (String) context.getWhereParameter(FieldConstant.GROUP_ID);
        final String appName = (String) context.getWhereParameter(FieldConstant.APP_NAME);
        final String content = (String) context.getWhereParameter(FieldConstant.CONTENT);
        final String[] tagArr = (String[]) context.getWhereParameter(FieldConstant.TAG_ARR);
        final String[] types = (String[]) context.getWhereParameter(FieldConstant.TYPE);
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        List<Object> paramList = new ArrayList<>();
        
        // 构建内层查询：根据标签条件筛选配置
        StringBuffer subQuery = new StringBuffer("SELECT DISTINCT a.id,a.data_id,a.group_id,a.tenant_id,a.app_name,a.content,a.md5,a.encrypted_data_key,a.type,a.c_desc " +
                "FROM config_info a LEFT JOIN config_tags_relation b ON a.id=b.id WHERE a.tenant_id LIKE ? ");
        paramList.add(tenant);

        if (StringUtils.isNotBlank(dataId)) {
            subQuery.append(" AND a.data_id LIKE ? ");
            paramList.add(dataId);
        }
        if (StringUtils.isNotBlank(group)) {
            subQuery.append(" AND a.group_id LIKE ? ");
            paramList.add(group);
        }
        if (StringUtils.isNotBlank(appName)) {
            subQuery.append(" AND a.app_name = ? ");
            paramList.add(appName);
        }
        if (StringUtils.isNotBlank(content)) {
            subQuery.append(" AND a.content LIKE ? ");
            paramList.add(content);
        }
        if (!ArrayUtils.isEmpty(tagArr)) {
            subQuery.append(" AND (");

            subQuery.append(" b.tag_name LIKE ? ");
            paramList.add(tagArr[0]);

            for (int i = 1; i < tagArr.length; i++) {
                subQuery.append(" OR b.tag_name LIKE ? ");
                paramList.add(tagArr[i]);
            }
            subQuery.append(" )");
        }
        if (!ArrayUtils.isEmpty(types)) {
            subQuery.append(" AND a.type IN (");

            subQuery.append("?");
            paramList.add(types[0]);

            for (int i = 1; i < types.length; i++) {
                subQuery.append(", ?");
                paramList.add(types[i]);
            }

            subQuery.append(") ");
        }

        //排序和分页
        subQuery.append(" ORDER BY b.id LIMIT ? OFFSET ?");
        paramList.add(pageSize);
        paramList.add(startRow);

        
        // 构建外层查询：获取筛选出的配置的完整标签信息
        final String sql = "SELECT c.id,c.data_id,c.group_id,c.tenant_id,c.app_name,c.content,c.md5,c.encrypted_data_key,c.type,c.c_desc,"
                + "string_agg(DISTINCT d.tag_name, ',') as config_tags "
                + "FROM (" + subQuery + ") c "
                + "LEFT JOIN config_tags_relation d ON c.id=d.id "
                + "GROUP BY c.id,c.data_id,c.group_id,c.tenant_id,c.app_name,c.content,c.md5,c.encrypted_data_key,c.type,c.c_desc";
        
        return new MapperResult(sql, paramList);
    }
}
