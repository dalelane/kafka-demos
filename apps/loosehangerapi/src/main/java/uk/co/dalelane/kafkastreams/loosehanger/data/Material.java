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


/**
 * Represents a material used in manufacturing.
 */
public class Material {

    @Schema(required = true, description = "Name of the material used in manufacturing")
    private String name = null;

    @Schema(required = true, description = "The proportion of the garment that uses this material")
    private int percentage = 0;

    @Schema(required = true, description = "Country of origin of the material")
    private String origin = null;

    @Schema(required = true, description = "Certification scheme that has approved the material from this supplier")
    private String certification = null;


    public Material() {
    }

    public Material(String name, int percentage, String origin, String certification) {
        this.name = name;
        this.percentage = percentage;
        this.origin = origin;
        this.certification = certification;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPercentage() {
        return percentage;
    }
    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCertification() {
        return certification;
    }
    public void setCertification(String certification) {
        this.certification = certification;
    }


    @Override
    public String toString() {
        return "Material [name=" + name + ", percentage=" + percentage + ", origin=" + origin + ", certification="
                + certification + "]";
    }
}
