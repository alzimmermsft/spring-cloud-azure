/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.messaging.container;

import com.microsoft.azure.spring.integration.core.api.SubscribeByGroupOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Warren Zhu
 */
@Slf4j
@Getter
@AllArgsConstructor
class DefaultMessageListenerContainer extends AbstractListenerContainer {

    private final SubscribeByGroupOperation subscribeOperation;

    @Override
    protected void doStart() {
        synchronized (this.getLifecycleMonitor()) {
            subscribeOperation.subscribe(getDestination(), getGroup(), getMessageHandler()::handleMessage,
                    getMessageHandler().getMessagePayloadType());
        }
    }

    @Override
    protected void doStop() {
        synchronized (this.getLifecycleMonitor()) {
            subscribeOperation.unsubscribe(getDestination(), getGroup());
        }
    }
}