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
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Stream;

import com.robbyp.refdatacache.domain.Instrument;

@Service
public class InstrumentService {

  @Autowired
  private HazelcastInstance hazelcastInstance;

  private ObjectMapper mapper = new ObjectMapper();

  public String findById(String id) {
    IMap<String, Instrument> instrumentMap = hazelcastInstance.getMap("instrument");
    return createInstrumentJson(instrumentMap.get(id));
  }

  public String findByParams(Map<String, String> params) {
    System.out.println(params);
    IMap<String, Instrument> instrumentMap = hazelcastInstance.getMap("instrument");
    Stream<Instrument> instruments;
    if (params != null && !params.isEmpty()) {
      EntryObject e = new PredicateBuilder().getEntryObject();
      Predicate predicate =
        e.get("isin").equal(params.get("isin"))
         .and(e.get("sedol").equal(params.get("sedol")));
      instruments = instrumentMap.values(predicate).stream();
    } else {
      instruments = instrumentMap.values().stream();
    }
    return instruments.map(this::createInstrumentJson).reduce("", (a, b) -> a + b);
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
