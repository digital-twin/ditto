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
package org.eclipse.ditto.signals.commands.live.modify;

import java.time.Instant;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.signals.commands.base.CommandResponse;
import org.eclipse.ditto.signals.commands.live.base.LiveCommandAnswer;
import org.eclipse.ditto.signals.commands.things.ThingErrorResponse;
import org.eclipse.ditto.signals.commands.things.exceptions.AttributeNotAccessibleException;
import org.eclipse.ditto.signals.commands.things.exceptions.AttributeNotModifiableException;
import org.eclipse.ditto.signals.commands.things.modify.ModifyAttributeResponse;
import org.eclipse.ditto.signals.events.base.Event;
import org.eclipse.ditto.signals.events.things.AttributeCreated;
import org.eclipse.ditto.signals.events.things.AttributeModified;

/**
 * A mutable builder with a fluent API for creating a {@link LiveCommandAnswer} for a {@link
 * ModifyAttributeLiveCommand}.
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
final class ModifyAttributeLiveCommandAnswerBuilderImpl
        extends
        AbstractLiveCommandAnswerBuilder<ModifyAttributeLiveCommand, ModifyAttributeLiveCommandAnswerBuilder.ResponseFactory, ModifyAttributeLiveCommandAnswerBuilder.EventFactory>
        implements ModifyAttributeLiveCommandAnswerBuilder {

    private ModifyAttributeLiveCommandAnswerBuilderImpl(final ModifyAttributeLiveCommand command) {
        super(command);
    }

    /**
     * Returns a new instance of {@code ModifyAttributeLiveCommandAnswerBuilderImpl}.
     *
     * @param command the command to build an answer for.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     */
    public static ModifyAttributeLiveCommandAnswerBuilderImpl newInstance(final ModifyAttributeLiveCommand command) {
        return new ModifyAttributeLiveCommandAnswerBuilderImpl(command);
    }

    @Override
    protected CommandResponse doCreateResponse(
            final Function<ResponseFactory, CommandResponse<?>> createResponseFunction) {
        return createResponseFunction.apply(new ResponseFactoryImpl());
    }

    @Override
    protected Event doCreateEvent(final Function<EventFactory, Event<?>> createEventFunction) {
        return createEventFunction.apply(new EventFactoryImpl());
    }

    @Immutable
    private final class ResponseFactoryImpl implements ResponseFactory {

        @Nonnull
        @Override
        public ModifyAttributeResponse created() {
            return ModifyAttributeResponse.created(command.getThingId(), command.getAttributePointer(),
                    command.getAttributeValue(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ModifyAttributeResponse modified() {
            return ModifyAttributeResponse.modified(command.getThingId(), command.getAttributePointer(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ThingErrorResponse attributeNotAccessibleError() {
            return errorResponse(command.getThingId(), AttributeNotAccessibleException.newBuilder(command.getThingId(),
                    command.getAttributePointer())
                    .dittoHeaders(command.getDittoHeaders())
                    .build());
        }

        @Nonnull
        @Override
        public ThingErrorResponse attributeNotModifiableError() {
            return errorResponse(command.getThingId(), AttributeNotModifiableException.newBuilder(command.getThingId(),
                    command.getAttributePointer())
                    .dittoHeaders(command.getDittoHeaders())
                    .build());
        }
    }

    @Immutable
    private final class EventFactoryImpl implements EventFactory {

        @Nonnull
        @Override
        public AttributeCreated created() {
            return AttributeCreated.of(command.getThingId(), command.getAttributePointer(),
                    command.getAttributeValue(), -1, Instant.now(), command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public AttributeModified modified() {
            return AttributeModified.of(command.getThingId(), command.getAttributePointer(),
                    command.getAttributeValue(), -1, Instant.now(), command.getDittoHeaders());
        }
    }

}
