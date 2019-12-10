package me.mohistzh.metrics.transactor.feed;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import me.mohistzh.metrics.model.pojo.MetricsInstance;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * A mysql feed stream manager
 *
 * @Author Jonathan
 * @Date 2019/12/10
 **/
@Component
@Slf4j
public class MySQLFeedManager extends AbstractFeedManager<NamedParameterJdbcTemplate> {

    private static final Class<com.mysql.jdbc.Driver> MYSQL_DRIVE_CLASS  = com.mysql.jdbc.Driver.class;
    private String urlPattern = "jdbc:mysql://${host}:${post}/${path}?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true";

    private LoadingCache<MetricsInstance, NamedParameterJdbcTemplate> templateLoadingCache = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .build(new CacheLoader<MetricsInstance, NamedParameterJdbcTemplate>() {
                @Override
                public NamedParameterJdbcTemplate load(MetricsInstance key) throws Exception {
                    SimpleDriverDataSource driverDataSource = new SimpleDriverDataSource();
                    driverDataSource.setDriverClass(MYSQL_DRIVE_CLASS);
                    String url  =  urlPattern.replace("${host}", key.getHost()).
                            replace("${post}", String.valueOf(key.getPort())).
                            replace("${path}", key.getPath());
                    driverDataSource.setUrl(url);
                    driverDataSource.setUsername(key.getUsername());
                    driverDataSource.setPassword(key.getPassword());
                    NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(driverDataSource);
                    return template;
                }
            });
    @Override
    protected LoadingCache<MetricsInstance, NamedParameterJdbcTemplate> getCache() {
        return templateLoadingCache;
    }

    @Override
    public void refresh() {
        templateLoadingCache.invalidateAll();
    }
}
