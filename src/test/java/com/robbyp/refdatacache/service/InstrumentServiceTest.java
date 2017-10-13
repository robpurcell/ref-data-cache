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

import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.robbyp.refdatacache.domain.Instrument;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentServiceTest {

  @Mock private IMap<String, Instrument> mockInstrumentMap;

  @Test
  public void testGetInstrumentStreamNoParams() {
    // Given
    InstrumentService is = new InstrumentService(mockInstrumentMap);
    when(mockInstrumentMap.values()).thenReturn(Collections.emptyList());

    // When
    is.getInstrumentStream(new HashMap<>());

    // Then
    verify(mockInstrumentMap).values();
  }

  @Test
  public void testGetInstrumentStreamOneParam() {
    // Given
    InstrumentService is = new InstrumentService(mockInstrumentMap);
    Map<String, String> params = new HashMap<>();
    String key = "test";
    String value = "1234";
    params.put(key, value);

    // When
    is.getInstrumentStream(params);

    // Then
    ArgumentCaptor<Predicate> argument = ArgumentCaptor.forClass(Predicate.class);
    Predicate expected = Predicates.equal(key, value);

    verify(mockInstrumentMap).values(argument.capture());

    assertThat(argument.getValue().toString(), is(expected.toString()));
  }

  @Test
  public void testGetInstrumentStreamTwoParams() {
    // Given
    InstrumentService is = new InstrumentService(mockInstrumentMap);
    Map<String, String> params = new HashMap<>();
    String key1 = "test";
    String value1 = "1234";
    String key2 = "anotherTest";
    String value2 = "4321";
    params.put(key1, value1);
    params.put(key2, value2);

    // When
    is.getInstrumentStream(params);

    // Then
    ArgumentCaptor<Predicate> argument = ArgumentCaptor.forClass(Predicate.class);
    Predicate expected = Predicates.and(
      Predicates.equal(key1, value1),
      Predicates.equal(key2, value2)
    );

    verify(mockInstrumentMap).values(argument.capture());

    assertThat(argument.getValue().toString(), is(expected.toString()));
  }

}
