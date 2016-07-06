package com.h3c.idcloud.infrastructure.common.ticket;

import com.h3c.idcloud.infrastructure.common.constants.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * 日常维护工单
 */
@Component
public class TickectDailyMaintenanceService extends TicketBaseService {

    private static final Logger logger = LoggerFactory.getLogger(TickectDailyMaintenanceService.class);

    public TickectDailyMaintenanceService () {
        this.ticketType = WebConstants.ticketType.DAILY_MAINTENANCE_TICKET;
    }

    public void createTicket(Map<String, Object> params) {
        logger.debug("params=" + params);
//        Ticket ticket = new Ticket();
//        ticket.setTitle((String)params.get("title"));
//        ticket.setContent((String) params.get("content"));
//        ticket.setAutoHandlerFlag(0);
//        saveTicket(ticket);
    }

    @Override
    public void manualHandlerTicketUpdateServiceData(Map<String, Object> params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void autoHandlerTicket(Map<String, Object> params) {
        // TODO Auto-generated method stub

    }
}
