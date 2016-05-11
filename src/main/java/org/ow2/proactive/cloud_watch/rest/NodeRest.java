//package org.ow2.proactive.cloud_watch.rest;
//
//import com.aol.micro.server.auto.discovery.Rest;
//import org.ow2.proactive.cloud_watch.exceptions.NodeIdNotFoundException;
//import org.ow2.proactive.cloud_watch.service.NodeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Response;
//
//@Path("/nodes/ids")
//@Component
//@Rest(isSingleton = true)
//public class NodeRest {
//
//	@Autowired
//	private NodeService nodeService;
//
//	@GET
//	@Produces("application/json")
//	@Path("/all")
//	public Response getAllNodesIds() {
//		return Response.ok(nodeService.getAllNodesIds()).build();
//	}
//
//	@GET
//	@Produces("application/json")
//	@Path("convert/aliennodeid/{alienNodeId}")
//	public String convertAlienNodeId(@PathParam("alienNodeId") String alienNodeId) {
//		return nodeService.getProactiveNodeId(alienNodeId).map(nodeInformation -> nodeInformation.getNodeId()).orElseThrow(
//				() -> new NodeIdNotFoundException("Alien Node Id : " + alienNodeId + " not found in the system"));
//	}
//
//}
