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

@Schema(
    description = "Identifies the customer's preferences for marketing-related communications"
)
public class ContactPreferences {

    @Schema(required = true, description = "True if the customer has explicitly opted in to receive marketing emails")
    private boolean email;

    @Schema(required = true, description = "True if the customer has explicitly opted in to receive marketing telephone calls")
    private boolean telephone;

    @Schema(required = true, description = "True if the customer has explicitly opted in to receive marketing material by post")
    private boolean post;


    public ContactPreferences(boolean emailAllowed, boolean telephoneAllowed, boolean postAllowed) {
        email = emailAllowed;
        telephone = telephoneAllowed;
        post = postAllowed;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isTelephone() {
        return telephone;
    }

    public void setTelephone(boolean telephone) {
        this.telephone = telephone;
    }

    public boolean isPost() {
        return post;
    }

    public void setPost(boolean post) {
        this.post = post;
    }
}
