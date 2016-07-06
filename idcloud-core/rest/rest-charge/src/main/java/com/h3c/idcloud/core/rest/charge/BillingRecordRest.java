package com.h3c.idcloud.core.rest.charge;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.h3c.idcloud.core.pojo.dto.charge.BillingRecord;
import org.springframework.context.annotation.Scope;



@Path("/billingRecord")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface BillingRecordRest {
	
	@WebMethod
	@POST
	@Path("/")
	public Response insertBillingRecord(BillingRecord record);
	
	@WebMethod
	@PUT
	@Path("/")
	public Response updateBillingRecord(BillingRecord record);
	
	@WebMethod
	@POST
	@Path("/billingRecordList")
	public Response getBillingRecordList(String params) throws IOException;
	
	@WebMethod
	@POST
	@Path("/billingAcountCrdList")
	public Response getBillingAcountCrdList(String params) throws IOException;
	
	/**
	 * 后台管理平台查询交易详情
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@WebMethod
	@POST
	@Path("/platform/billingRecordList")
	public Response getBillingPlatformRecordList(String params) throws IOException;
	
	/**
	 * Get pay status
	 * @param provider
	 * @param requestId
	 * @return
	 */
	@GET
	@Path("/payStatus/{provider}/{requestId}")
	public Response getPayStatus(@PathParam("provider") String provider, @PathParam("requestId") String requestId);
}
