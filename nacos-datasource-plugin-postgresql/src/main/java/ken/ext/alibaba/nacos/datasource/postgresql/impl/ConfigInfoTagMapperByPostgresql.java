package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.mapper.ConfigInfoTagMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

import java.util.Collections;

/**
 * config_info_tag表dml postgresql实现
 *
 * @author ken.yu
 **/
public class ConfigInfoTagMapperByPostgresql extends AbstractMapperByPostgresql implements ConfigInfoTagMapper {
    @Override
    public MapperResult findAllConfigInfoTagForDumpAllFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        String sql = "SELECT id,data_id,group_id,tenant_id,tag_id,app_name,content,md5,gmt_modified " +
                "FROM config_info_tag " +
                "ORDER BY id LIMIT " + pageSize + " OFFSET " + startRow;
        return new MapperResult(sql, Collections.emptyList());
    }
}
