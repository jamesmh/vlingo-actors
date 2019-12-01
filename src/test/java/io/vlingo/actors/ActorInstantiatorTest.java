// Copyright © 2012-2018 Vaughn Vernon. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.actors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ActorInstantiatorTest extends ActorsTest {

  @Test
  public void testThatActorInstantiates() {
    final AtomicInteger holder = new AtomicInteger(0);
    final ProtocolInstantiator instantiator = new ProtocolInstantiator(100, holder);

    final Protocol protocol = world.actorFor(Protocol.class, Definition.has(ProtocolActor.class, instantiator));

    assertNotNull(protocol);
    assertEquals(100, holder.get());
  }

  public static class ProtocolInstantiator implements ActorInstantiator<ProtocolActor> {
    private final AtomicInteger holder;
    private final int value;

    public ProtocolInstantiator(final int value, final AtomicInteger holder) {
      this.value = value;
      this.holder = holder;
    }

    @Override
    public ProtocolActor instantiate() {
      return new ProtocolActor(value, holder);
    }
  }

  public static interface Protocol { }

  public static class ProtocolActor extends Actor implements Protocol {
    private final int value;

    public ProtocolActor(final int value, final AtomicInteger holder) {
      this.value = value;
      System.out.println("VALUE: " + this.value);
      holder.set(this.value);
    }
  }
}
