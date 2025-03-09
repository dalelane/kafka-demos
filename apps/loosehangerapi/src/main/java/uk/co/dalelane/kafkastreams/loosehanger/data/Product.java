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
package uk.co.dalelane.kafkastreams.loosehanger.data;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import uk.co.dalelane.kafkastreams.loosehanger.utils.RandomData;

/**
 * An item manufactured and sold by Loosehanger.
 */
public class Product {

    @Schema(required = false, description = "Unique identifier for the product", format = "uuid")
    private String id = null;

    @Schema(required = true, description = "Garment description")
    private String description = null;

    @Schema(required = true, description = "Garment style")
    private String style = null;

    @Schema(required = true, description = "List of the materials used in manufacturing of this garment")
    private Material[] materials = null;


    public Product() {
    }


    public Product(String description, String style) {
        this.id = RandomData.getUUID();
        this.description = description;
        this.style = style;
        this.materials = RandomData.getMaterials();
    }



    public String getId() {
        return id;
    }
    public void setId(String productid) {
        this.id = productid;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }

    public Material[] getMaterials() {
        return materials;
    }
    public void setMaterials(Material[] materials) {
        this.materials = materials;
    }
}
