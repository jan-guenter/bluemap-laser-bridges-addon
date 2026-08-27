/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.laserbridges.profile;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `laserbridges-5.3-mc1.21.1`. */
public final class LaserBridges53Profile {

    public static final String PROFILE_ID = "laserbridges-5.3-mc1.21.1";
    public static final List<ArtifactPin> ARTIFACTS = List.of(
            new ArtifactPin(
                    "laserBridges",
                    "laserbridges",
                    "5.3",
                    "laserbridges-1.21.1-neoforge-5.3.jar",
                    199_979L,
                    "51fbc91a5d5b28ff8664da3d60c1e13066f61d959ff7fabc15a7e3f55d8c9a72"
            ),
            new ArtifactPin(
                    "deimos",
                    "deimos",
                    "2.7",
                    "deimos-1.21.1-neoforge-2.7.jar",
                    49_576L,
                    "ee35d4e8967ccb23dee3c1a05b55c8d4ac0e0045bf8d324e215b397869af0573"
            )
    );

    private LaserBridges53Profile() {
    }
}
