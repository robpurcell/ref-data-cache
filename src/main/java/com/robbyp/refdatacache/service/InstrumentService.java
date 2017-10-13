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
package com.robbyp.refdatacache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.robbyp.refdatacache.domain.Instrument;

@Service
public class InstrumentService {

  private IMap<String, Instrument> instrumentMap;

  private final ObjectMapper mapper = new ObjectMapper();

  private final Logger log = LoggerFactory.getLogger(InstrumentService.class);

  @Autowired
  public InstrumentService(HazelcastInstance hazelcastInstance) {
    Assert.notNull(hazelcastInstance, "Parameter 'hazelcastInstance' must not be null.");
    this.instrumentMap = hazelcastInstance.getMap("instrument");
  }

  InstrumentService(IMap<String, Instrument> instrumentMap) {
    this.instrumentMap = instrumentMap;
  }

  public String findById(String id) {
    return createInstrumentJson(instrumentMap.get(id));
  }

  public String findByParams(Map<String, String> params) {
    log.info("Search parameters: {}", params.toString());
    Stream<Instrument> instruments = getInstrumentStream(params);
    return instruments.map(this::createInstrumentJson).reduce("", (a, b) -> a + b);
  }

  Stream<Instrument> getInstrumentStream(Map<String, String> params) {
    Stream<Instrument> instruments;
    if (params != null && !params.isEmpty()) {
      instruments = getInstrumentStreamForParams(params);
    } else {
      instruments = instrumentMap.values().stream();
    }
    return instruments;
  }

  private Stream<Instrument> getInstrumentStreamForParams(Map<String, String> params) {
    Predicate predicate;
    if (params.size() == 1) {
      predicate = PredicateBuilderHelper.getFirstPredicate(params);
    } else {
      predicate = PredicateBuilderHelper.getFirstPredicate(params);
      List<String> keys = new ArrayList<>(params.keySet());
      for (String k : keys.subList(1, keys.size())) {
        String value = params.get(k);
        if (value != null) {
          Predicate newPredicate = Predicates.equal(k, value);
          predicate = Predicates.and(predicate, newPredicate);
        }
      }
    }
    return instrumentMap.values(predicate).stream();
  }

  private String createInstrumentJson(Instrument instrument) {
    try {
      return mapper.writeValueAsString(instrument);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }

}
