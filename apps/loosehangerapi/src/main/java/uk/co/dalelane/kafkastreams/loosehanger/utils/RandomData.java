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
import java.util.List;
import java.util.UUID;
import java.util.random.RandomGenerator;

import uk.co.dalelane.kafkastreams.loosehanger.data.Material;
import uk.co.dalelane.kafkastreams.loosehanger.data.Tier;

public class RandomData {

    private static final RandomGenerator RNG = RandomGenerator.getDefault();

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean getBoolean() {
        return RNG.nextBoolean();
    }

    public static int getInt(int max) {
        return RNG.nextInt(max + 1);
    }

    public static int getInt(int min, int max) {
        return RNG.nextInt(min, max);
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

    public static <T> T getListItem(List<T> list) {
        return list.get(RNG.nextInt(list.size()));
    }

    private static Material tweakPercentage(Material material, int max) {
        return new Material(material.getName(),
            Math.min(max,
                     getInt(material.getPercentage() - 4, material.getPercentage() + 4)),
            material.getOrigin(),
            material.getCertification());
    }

    public static Material[] getMaterials() {
        int totalPercentage = 99;
        Material[] templateMaterials = getListItem(MATERIAL_COMBINATIONS);
        Material[] materials = new Material[templateMaterials.length];
        for (int i = 0; i < templateMaterials.length; i++) {
            if (i == templateMaterials.length - 1) {
                materials[i] = new Material(templateMaterials[i].getName(),
                                            totalPercentage + 1,
                                            templateMaterials[i].getOrigin(),
                                            templateMaterials[i].getCertification());
            }
            else {
                materials[i] = tweakPercentage(templateMaterials[i], totalPercentage);
            }
            totalPercentage -= materials[i].getPercentage();
        }
        return materials;
    }

    private static List<Material[]> MATERIAL_COMBINATIONS = List.of(
        new Material[] {
            new Material("Recycled Nylon", 80, "Taiwan", "Global Recycled Standard"),
            new Material("Elastene", 10, "South Korea", "OEKO-TEX"),
            new Material("Polyurethane Coating", 10, "Germany", "Bluesign")
        },
        new Material[] {
            new Material("Cotton Canvas", 85, "USA", "Better Cotton Initiative"),
            new Material("Paraffin Wax Coating", 15, "United Kingdom", "Eco-Wax")
        },
        new Material[] {
            new Material("Linen", 100, "Belgium", "European Flax")
        },
        new Material[] {
            new Material("Cotton", 100, "Japan", "GOTS")
        },
        new Material[] {
            new Material("Cotton", 75, "Bangladesh", "Better Cotton Initiative"),
            new Material("Polyester", 20, "China", "Recycled"),
            new Material("Spandex", 5, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Organic Cotton", 98, "Egypt", "GOTS"),
            new Material("Elastene", 2, "Italy", "OEKO-TEX")
        },
        new Material[] {
            new Material("Cotton", 95, "Turkey", "Better Cotton Initiative"),
            new Material("Spandex", 5, "Japan", "OEKO-TEX")
        },
        new Material[] {
            new Material("Denim (Cotton)", 88, "Brazil", "GOTS"),
            new Material("Polyester", 10, "Vietnam", "Recycled"),
            new Material("Spandex", 2, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Cotton Twill", 90, "Pakistan", "Better Cotton Initiative"),
            new Material("Nylon", 10, "China", "OEKO-TEX")
        },
        new Material[] {
            new Material("Cotton", 85, "India", "Better Cotton Initiative"),
            new Material("Polyester", 12, "Vietnam", "Recycled"),
            new Material("Elastene", 3, "Turkey", "OKEO-TEX")
        },
        new Material[] {
            new Material("Recycled Nylon", 85, "Italy", "Global Recycled Standard"),
            new Material("Elastane", 15, "South Korea", "OKEO-TEX")
        },
        new Material[] {
            new Material("Hemp", 80, "France", "GOTS"),
            new Material("Organic Cotton", 20, "India", "GOTS")
        },
        new Material[] {
            new Material("Bamboo Viscose", 85, "Vietnam", "FSC Certified"),
            new Material("Nylon", 10, "Japan", "OKEO-TEX"),
            new Material("Spandex", 5, "South Korea", "OEKO-TEX")
        },
        new Material[] {
            new Material("Recycled Polyester", 92, "USA", "Global Recycled Standard"),
            new Material("Spandex", 8, "Taiwan", "Bluesign")
        },
        new Material[] {
            new Material("Organic Cotton", 95, "Peru", "GOTS"),
            new Material("Elastene", 5, "Germany", "OEKO-TEX")
        },
        new Material[] {
            new Material("Cotton", 80, "India", "GOTS"),
            new Material("Polyester", 18, "China", "Recycled"),
            new Material("Elastene", 2, "Turkey", "OKEO-TEX")
        },
        new Material[] {
            new Material("Raw Denim (Cotton)", 100, "Japan", "Selvedge Certified")
        },
        new Material[] {
            new Material("Cotton", 85, "Brazil", "Better Cotton Initiative"),
            new Material("Polyester", 10, "Vietnam", "Recycled"),
            new Material("Spandex", 5, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Ripstop Cotton", 90, "Pakistan", "Better Cotton Initiative"),
            new Material("Nylon Reinforcement", 10, "China", "OEKO-TEX")
        },
        new Material[] {
            new Material("Corduroy (Cotton)", 95, "Turkey", "Better Cotton Initiative"),
            new Material("Elastane", 5, "Japan", "OEKO-TEX")
        },
        new Material[] {
            new Material("Organic Hemp", 70, "France", "GOTS"),
            new Material("Organic Cotton", 30, "India", "GOTS")
        },
        new Material[] {
            new Material("Polyester", 80, "China", "Recycled"),
            new Material("Cotton", 18, "India", "Better Cotton Initiative"),
            new Material("Elastane", 2, "Turkey", "OEKO-TEX")
        },
        new Material[] {
            new Material("Tencel Lyocell", 100, "Austria", "Lenzing Certified")
        },
        new Material[] {
            new Material("Wool Blend", 60, "New Zealand", "RWS"),
            new Material("Polyester", 30, "China", "Recycled"),
            new Material("Elastane", 10, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Bamboo Fiber", 85, "Vietnam", "FSC Certified"),
            new Material("Nylon", 10, "Japan", "OEKO-TEX"),
            new Material("Spandex", 5, "South Korea", "OEKO-TEX")
        },
        new Material[] {
            new Material("Waxed Cotton", 90, "United Kingdom", "Eco-Wax"),
            new Material("Polyester Lining", 10, "China", "Recycled")
        },
        new Material[] {
            new Material("Cotton Twill", 92, "USA", "Better Cotton Initiative"),
            new Material("Elastane", 8, "Taiwan", "Bluesign")
        },
        new Material[] {
            new Material("Recycled Cotton", 70, "India", "Global Recycled Standard"),
            new Material("Recycled Polyester", 20, "China", "Recycled"),
            new Material("Spandex", 10, "South Korea", "OEKO-TEX")
        },
        new Material[] {
            new Material("Vegan Leather (PU)", 75, "Germany", "PETA Approved Vegan"),
            new Material("Cotton Lining", 25, "India", "GOTS")
        },
        new Material[] {
            new Material("Organic Cotton", 98, "Egypt", "GOTS"),
            new Material("Elastane", 2, "Italy", "OEKO-TEX")
        },
        new Material[] {
            new Material("Denim (Cotton)", 87, "Brazil", "GOTS"),
            new Material("Polyester", 10, "Vietnam", "Recycled"),
            new Material("Spandex", 3, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Hemp", 85, "France", "GOTS"),
            new Material("Recycled Cotton", 15, "USA", "Global Recycled Standard")
        },
        new Material[] {
            new Material("Cotton Canvas", 80, "USA", "Better Cotton Initiative"),
            new Material("Paraffin Wax Coating", 20, "United Kingdom", "Eco-Wax")
        },
        new Material[] {
            new Material("Stretch Denim", 92, "India", "Better Cotton Initiative"),
            new Material("Spandex", 8, "South Korea", "Bluesign")
        },
        new Material[] {
            new Material("Recycled Nylon", 88, "Taiwan", "Global Recycled Standard"),
            new Material("Elastane", 12, "South Korea", "OEKO-TEX")
        },
        new Material[] {
            new Material("Cotton Twill", 96, "Pakistan", "Better Cotton Initiative"),
            new Material("Elastane", 4, "Germany", "OEKO-TEX")
        }
    );
}
