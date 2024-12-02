/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package uk.co.dalelane.kafkastreams.loosehanger.api;

import jakarta.ws.rs.PathParam;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import uk.co.dalelane.kafkastreams.loosehanger.data.CustomerLoyalty;

/**
 * REST API for retrieving information about customers.
 */
@Path("/loyalty")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerLoyaltyApi {

    private static final Map<String, CustomerLoyalty> previousResponses = new HashMap<>();

    @GET()
    @Path("/customers/{customerid}")
    @Operation(description = "Retrieves the loyalty record for a Loosehanger customer")
    @APIResponseSchema(
        responseCode = "200",
        value = CustomerLoyalty.class
    )
    public CustomerLoyalty getCustomerLoyaltyRecord(
        @Parameter(
            description = "ID of the customer to retrieve the customer loyalty record for",
            schema = @Schema(type = SchemaType.STRING, format = "uuid"),
            required = true
        )
        @PathParam("customerid") String customerid)
    {
        if (!previousResponses.containsKey(customerid)) {
            previousResponses.put(customerid, new CustomerLoyalty(customerid));
        }
        return previousResponses.get(customerid);
    }
}
