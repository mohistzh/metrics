package me.mohistzh.metrics.transactor.fetcher;

import me.mohistzh.metrics.model.pojo.CommonMetricsData;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import me.mohistzh.metrics.transactor.feed.MySQLFeedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Fetching MySQL data stream
 * @Author Jonathan
 * @Date 2019/12/10
 **/
public class MySQLFetcher implements BasicFetcher<List<CommonMetricsData>> {

    @Autowired
    MySQLFeedManager mySQLFeedManager;

    private static final EmptySqlParameterSource EMPTY_SQL_PARAMETER_SOURCE = new EmptySqlParameterSource();

    private static final MySQLMetricsRowDataMapper MY_SQL_METRICS_ROW_DATA_MAPPER = new MySQLMetricsRowDataMapper();

    @Override
    public List<CommonMetricsData> fetch(MetricsInstance metricsInstance) {
        NamedParameterJdbcTemplate template = mySQLFeedManager.getMetricsConnector(metricsInstance);
        if (template == null) {
            return Collections.EMPTY_LIST;
        }
        List<CommonMetricsData> commonMetricsDataList = template.query(metricsInstance.getMetricsAPI(), EMPTY_SQL_PARAMETER_SOURCE, MY_SQL_METRICS_ROW_DATA_MAPPER);
        return commonMetricsDataList;
    }

    private static class MySQLMetricsRowDataMapper implements RowMapper<CommonMetricsData> {
        @Override
        public CommonMetricsData mapRow(ResultSet resultSet, int i) throws SQLException {
            CommonMetricsData result = new CommonMetricsData();
            result.setVariable(resultSet.getString("Variable_name"));
            result.setValue(resultSet.getString("Value"));
            return result;
        }
    }
}
