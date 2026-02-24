package ken.ext.alibaba.nacos.datasource.postgresql.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.plugin.datasource.constants.FieldConstant;
import com.alibaba.nacos.plugin.datasource.mapper.HistoryConfigInfoMapper;
import com.alibaba.nacos.plugin.datasource.model.MapperContext;
import com.alibaba.nacos.plugin.datasource.model.MapperResult;

import java.util.ArrayList;
import java.util.List;

/**
 * his_config_info表dml postgresql实现
 *
 * @author ken.yu
 **/
public class HistoryConfigInfoMapperByPostgresql extends AbstractMapperByPostgresql implements HistoryConfigInfoMapper {

    @Override
    public MapperResult removeConfigHistory(MapperContext context) {
        String sql = "DELETE FROM his_config_info id IN (SELECT id FROM his_config_info WHERE gmt_modified < ? LIMIT ?)";
        return new MapperResult(sql, CollectionUtils.list(context.getWhereParameter(FieldConstant.START_TIME),
                context.getWhereParameter(FieldConstant.LIMIT_SIZE)));
    }

    @Override
    public MapperResult pageFindConfigHistoryFetchRows(MapperContext context) {
        int pageSize = context.getPageSize();
        int startRow = context.getStartRow();

        List<Object> paramList = new ArrayList<>();

        String sql =
                "SELECT nid,data_id,group_id,tenant_id,app_name,src_ip,src_user,op_type,ext_info,publish_type,gray_name,gmt_create,gmt_modified "
                        + "FROM his_config_info " + "WHERE data_id = ? AND group_id = ? AND tenant_id = ? ORDER BY nid DESC  LIMIT ? OFFSET ?";

        paramList.add(context.getWhereParameter(FieldConstant.DATA_ID));
        paramList.add(context.getWhereParameter(FieldConstant.GROUP_ID));
        paramList.add(context.getWhereParameter(FieldConstant.TENANT_ID));
        paramList.add(pageSize);
        paramList.add(startRow);

        return new MapperResult(sql, paramList);
    }
}
