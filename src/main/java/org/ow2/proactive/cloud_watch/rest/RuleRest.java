//package org.ow2.proactive.cloud_watch.rest;
//
//import com.aol.micro.server.auto.discovery.Rest;
//import org.ow2.proactive.cloud_watch.service.RuleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//
//@Path("/rules")
//@Component
//@Rest(isSingleton = true)
//public class RuleRest {
//
//	@Autowired
//	private RuleService ruleService;
//
//	@GET
//	@Produces("application/json")
//	@Path("/all")
//	public Response getAllRules() {
//		return Response.ok(ruleService.getAllRules()).build();
//	}
//
//	@GET
//	@Produces("application/json")
//	@Path("/reload")
//	public Response reloadRules() {
//		ruleService.reloadRules();
//		return getAllRules();
//	}
//
//}
