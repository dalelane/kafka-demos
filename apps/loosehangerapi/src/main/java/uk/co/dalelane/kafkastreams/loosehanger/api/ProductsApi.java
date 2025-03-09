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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import uk.co.dalelane.kafkastreams.loosehanger.data.Product;
import uk.co.dalelane.kafkastreams.loosehanger.store.ProductsStore;
import uk.co.dalelane.kafkastreams.loosehanger.utils.RandomData;

/**
 * REST API for retrieving information about products.
 */
@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsApi {

    private static final ProductsStore store = new ProductsStore();

    @GET()
    @Path("/")
    @Operation(description = "Looks up detailed information from description and style")
    @APIResponseSchema(
        responseCode = "200",
        value = Product.class
    )
    public Product lookupProductInformation(
        @Parameter(
            description = "description of the garment to retrieve details for",
            schema = @Schema(type = SchemaType.STRING),
            required = true
        )
        @QueryParam("description") String description,
        @Parameter(
            description = "style of the garment to retrieve details for",
            schema = @Schema(type = SchemaType.STRING),
            required = true
        )
        @QueryParam("style") String style)
    {
        Product product = store.search(description, style);
        if (product == null) {
            product = new Product(description, style);
            product = store.insert(product);
        }
        return product;
    }


    @GET()
    @Path("/{productid}")
    @Operation(description = "Retrieves detailed information by ID")
    @APIResponseSchema(
        responseCode = "200",
        value = Product.class
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public Product getById(
        @Parameter(
            description = "ID of the garment to retrieve details for",
            schema = @Schema(type = SchemaType.STRING, format = "uuid"),
            required = true
        )
        @PathParam("productid") String productid)
    {
        Product product = store.getById(productid);
        if (product == null) {
            throw new NotFoundException();
        }
        return product;
    }


    @DELETE()
    @Path("/{productid}")
    @Operation(description = "Deletes a product from the store")
    @APIResponse(
        responseCode = "204",
        description = "Successfully deleted"
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public void deleteProduct(
        @Parameter(
            description = "ID of the garment to retrieve details for",
            schema = @Schema(type = SchemaType.STRING, format = "uuid"),
            required = true
        )
        @PathParam("productid") String productid)
    {
        boolean deleted = store.delete(productid);
        if (!deleted) {
            throw new NotFoundException();
        }
    }


    @PUT
    @Path("/{productid}")
    @Operation(description = "Updates an existing product")
    @APIResponseSchema(
        responseCode = "200",
        value = Product.class
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid product definition"
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public Product updateProduct(
        @Parameter(
            description = "ID of the product to update",
            schema = @Schema(type = SchemaType.STRING, format = "uuid"),
            required = true
        )
        @PathParam("productid") String productid,

        @Parameter(
            description = "Updated product information",
            required = true
        )
        Product product)
    {
        if (!productid.equals(product.getId())) {
            throw new BadRequestException();
        }

        Product updatedProduct = store.update(product);
        if (updatedProduct == null) {
            throw new NotFoundException();
        }
        return updatedProduct;
    }


    @POST
    @Path("/")
    @Operation(description = "Stores a new product definition")
    @APIResponseSchema(
        responseCode = "200",
        value = Product.class
    )
    @APIResponse(
        responseCode = "409",
        description = "Existing product exists"
    )
    public Product storeProduct(
        @Parameter(
            description = "Product information to store",
            required = true
        )
        Product product)
    {
        if (product.getId() != null) {
            throw new BadRequestException();
        }
        Product existingProduct = store.search(product.getDescription(), product.getStyle());
        if (existingProduct != null) {
            throw new ClientErrorException(Status.CONFLICT);
        }

        product.setId(RandomData.getUUID());
        return store.insert(product);
    }
}
