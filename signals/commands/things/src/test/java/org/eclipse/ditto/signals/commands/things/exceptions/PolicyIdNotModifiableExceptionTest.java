/*
 * Copyright (c) 2017-2018 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.signals.commands.things.exceptions;

import static org.eclipse.ditto.signals.commands.things.assertions.ThingCommandAssertions.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.net.URI;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.model.base.common.HttpStatusCode;
import org.eclipse.ditto.model.base.exceptions.DittoRuntimeException;
import org.eclipse.ditto.signals.commands.things.TestConstants;
import org.junit.Test;

/**
 * Unit test for {@link PolicyIdNotModifiableException}.
 */
public class PolicyIdNotModifiableExceptionTest {

    private static final JsonObject KNOWN_JSON = JsonFactory.newObjectBuilder()
            .set(DittoRuntimeException.JsonFields.STATUS, HttpStatusCode.NOT_FOUND.toInt())
            .set(DittoRuntimeException.JsonFields.ERROR_CODE, PolicyIdNotModifiableException.ERROR_CODE)
            .set(DittoRuntimeException.JsonFields.MESSAGE,
                    TestConstants.Thing.POLICY_ID_NOT_MODIFIABLE_EXCEPTION.getMessage())
            .set(DittoRuntimeException.JsonFields.DESCRIPTION,
                    TestConstants.Thing.POLICY_ID_NOT_MODIFIABLE_EXCEPTION.getDescription().get())
            .set(DittoRuntimeException.JsonFields.HREF,
                    TestConstants.Thing.POLICY_ID_NOT_MODIFIABLE_EXCEPTION.getHref()
                            .map(URI::toString).orElse(null))
            .build();


    @Test
    public void assertImmutability() {
        assertInstancesOf(PolicyIdNotModifiableException.class, areImmutable());
    }


    @Test
    public void checkThingErrorCodeWorks() {
        final DittoRuntimeException actual =
                ThingErrorRegistry.newInstance().parse(KNOWN_JSON, TestConstants.EMPTY_DITTO_HEADERS);

        assertThat(actual).isEqualTo(TestConstants.Thing.POLICY_ID_NOT_MODIFIABLE_EXCEPTION);
    }

}
