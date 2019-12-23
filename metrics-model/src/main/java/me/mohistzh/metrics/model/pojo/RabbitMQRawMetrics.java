package me.mohistzh.metrics.model.pojo;

import lombok.Data;

/**
 * Checkout from here <a>https://www.rabbitmq.com/monitoring.html#node-metrics</a>
 * @Author Jonathan
 * @Date 2019/12/23
 **/
@Data
public class RabbitMQRawMetrics {

        private int fd_total;
        private int sockets_total;
        private long mem_limit;

        private long uptime;
        private long disk_free_limit;
        private int proc_total;
        private int run_queue;
        private int processors;

        private int net_ticktime;

        private long mem_used;
        private int fd_used;
        private int sockets_used;
        private int proc_used;
        private long disk_free;
        private long gc_num;
        private long gc_bytes_reclaimed;
        private double context_switches;
        private long io_read_count;
        private long io_read_bytes;
        private double io_read_avg_time;
        private long io_write_count;
        private long io_write_bytes;
        private double io_write_avg_time;
        private long io_sync_count;
        private double io_sync_avg_time;
        private long io_seek_count;
        private double io_seek_avg_time;
        private long io_reopen_count;
        private long mnesia_ram_tx_count;
        private long mnesia_disk_tx_count;
        private long msg_store_read_count;
        private long msg_store_write_count;
        private long queue_index_journal_write_count;
        private long queue_index_write_count;
        private long queue_index_read_count;
        private double io_file_handle_open_attempt_count;
        private double io_file_handle_open_attempt_avg_time;
}
