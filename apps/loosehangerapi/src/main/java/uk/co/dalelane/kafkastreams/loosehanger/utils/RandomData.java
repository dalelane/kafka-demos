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
package uk.co.dalelane.kafkastreams.loosehanger.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.random.RandomGenerator;

import uk.co.dalelane.kafkastreams.loosehanger.data.Tier;

public class RandomData {

    private static final RandomGenerator RNG = RandomGenerator.getDefault();

    public static boolean getBoolean() {
        return RNG.nextBoolean();
    }

    public static Tier getTier() {
        return Tier.values()[RNG.nextInt(Tier.values().length)];
    }

    public static LocalDateTime getPreviousDatetime(int rangeDaysStart, int rangeDaysEnd) {
        LocalDateTime now = LocalDateTime.now();

        long epochRangeStart = now.minusDays(rangeDaysStart).toEpochSecond(ZoneOffset.UTC);
        long epochRangeEnd = now.minusDays(rangeDaysEnd).toEpochSecond(ZoneOffset.UTC);

        long epochRandom = RNG.nextLong(epochRangeStart, epochRangeEnd);

        return LocalDateTime.ofEpochSecond(epochRandom, 0, ZoneOffset.UTC);
    }
}
