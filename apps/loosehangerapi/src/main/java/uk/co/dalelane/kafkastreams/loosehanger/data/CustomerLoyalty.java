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

import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import uk.co.dalelane.kafkastreams.loosehanger.utils.RandomData;

/**
 * Represents a record for a customer in the customer loyalty system.
 */
public class CustomerLoyalty {

    @Schema(required = true, description = "True if the customer has explicitly opted in to receive marketing emails", format = "uuid")
    private String customerid = null;

    @Schema(required = true, description = "Identifies what marketing communications the customer has opted in to receive")
    private ContactPreferences contactPreferences = null;

    @Schema(required = true, description = "The customer's current loyalty tier, which is determined by how long they have been a customer for and their amount of purchases")
    private Tier tier = null;

    @Schema(required = true, description = "The most recent loyalty/marketing-related communication with this customer")
    private LoyaltyContact lastContact = null;

    @Schema(required = true, description = "Date that this customer registered with Loosehanger")
    private LocalDateTime customerSince = null;

    public LocalDateTime getCustomerSince() {
        return customerSince;
    }


    public void setCustomerSince(LocalDateTime customerSince) {
        this.customerSince = customerSince;
    }


    public CustomerLoyalty(String customerid) {
        this.customerid = customerid;
        this.contactPreferences = new ContactPreferences(
            RandomData.getBoolean(),
            RandomData.getBoolean(),
            RandomData.getBoolean());
        this.tier = RandomData.getTier();
        this.lastContact = new LoyaltyContact(RandomData.getPreviousDatetime(210, 30));

        switch (this.tier) {
            case PLATINUM:
                this.customerSince = RandomData.getPreviousDatetime(1200, 600);
                break;
            case GOLD:
                this.customerSince = RandomData.getPreviousDatetime(1000, 400);
                break;
            case BRONZE:
                this.customerSince = RandomData.getPreviousDatetime(450, 60);
                break;
        }
    }


    public String getCustomerId() {
        return customerid;
    }
    public void setCustomerId(String id) {
        this.customerid = id;
    }

    public ContactPreferences getContactPreferences() {
        return contactPreferences;
    }
    public void setContactPreferences(ContactPreferences contactPreferences) {
        this.contactPreferences = contactPreferences;
    }

    public Tier getTier() {
        return tier;
    }
    public void setTier(Tier tier) {
        this.tier = tier;
    }


    public LoyaltyContact getLastContact() {
        return lastContact;
    }
    public void setLastContact(LoyaltyContact lastContact) {
        this.lastContact = lastContact;
    }
}
