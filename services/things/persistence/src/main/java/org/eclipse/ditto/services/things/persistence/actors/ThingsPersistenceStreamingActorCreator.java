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
package org.eclipse.ditto.services.things.persistence.actors;

import org.eclipse.ditto.services.models.things.ThingTag;
import org.eclipse.ditto.services.utils.persistence.mongo.DefaultPersistenceStreamingActor;
import org.eclipse.ditto.services.utils.persistence.mongo.streaming.PidWithSeqNr;

import com.typesafe.config.Config;

import akka.actor.Props;


/**
 * Creates an actor which streams information about persisted things.
 */
public final class ThingsPersistenceStreamingActorCreator {

    /**
     * The name of the created Actor in the ActorSystem.
     */
    public static final String ACTOR_NAME = "persistenceStreamingActor";

    private ThingsPersistenceStreamingActorCreator() {
        throw new AssertionError();
    }

    /**
     * Creates Akka configuration object Props for this PersistenceQueriesActor.
     *
     * @param config the actor system configuration.
     * @param streamingCacheSize the size of the streaming cache.
     * @return the Akka configuration Props object.
     */
    public static Props props(final Config config, final int streamingCacheSize) {
        return DefaultPersistenceStreamingActor.props(ThingTag.class, config, streamingCacheSize,
                ThingsPersistenceStreamingActorCreator::createElement);
    }

    private static ThingTag createElement(final PidWithSeqNr pidWithSeqNr) {
        final String id = pidWithSeqNr.getPersistenceId()
                .replaceFirst(ThingPersistenceActor.PERSISTENCE_ID_PREFIX, "");
        return ThingTag.of(id, pidWithSeqNr.getSequenceNr());
    }
}
