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

@Schema(
    description = "Records the most recent customer loyalty communication with this customer"
)
public class LoyaltyContact {

    private LocalDateTime datetime;

    public LoyaltyContact(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
