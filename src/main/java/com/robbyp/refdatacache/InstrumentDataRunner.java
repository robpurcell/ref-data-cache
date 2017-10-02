// Copyright 2017 Purcell Informatics Limited
//
// See the LICENCE file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.robbyp.refdatacache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentinterface.ReflectionBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.robbyp.refdatacache.domain.Instrument;
import com.robbyp.refdatacache.domain.InstrumentBuilder;

@Component
public class InstrumentDataRunner implements CommandLineRunner {

  private final ApplicationContext ctx;

  private HazelcastInstance hazelcastInstance;

  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public InstrumentDataRunner(ApplicationContext context, HazelcastInstance hazelcastInstance) {
    this.ctx = context;
    this.hazelcastInstance = hazelcastInstance;
  }

  public void run(String... args) throws Exception {
    IMap<String, Instrument> m1 = this.hazelcastInstance.getMap("instrument");
    m1.put("1", new Instrument());
    m1.put("2", anInstrument().withIsin("1234").withSedol("4321").build());
  }

  private String createInstrumentJson() {
    try {
      return mapper.writeValueAsString(new Instrument());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }

  private static InstrumentBuilder anInstrument() {
    return ReflectionBuilder.implementationFor(InstrumentBuilder.class).create();
  }
}
