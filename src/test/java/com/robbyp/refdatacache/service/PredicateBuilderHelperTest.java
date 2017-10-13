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

import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PredicateBuilderHelperTest {

  @Test
  public void singleParameter() {
    Map<String, String> params = new HashMap<>();
    params.put("sedol", "1234");
    Predicate expected = Predicates.equal("sedol", "1234");

    Predicate actual = PredicateBuilderHelper.getFirstPredicate(params);

    assertThat(actual.toString(), is(expected.toString()));
  }

  @Test
  public void multipleParameters() {
    Map<String, String> params = new HashMap<>();
    params.put("sedol", "1234");
    params.put("isin", "4321");
    Predicate expected = Predicates.equal("sedol", "1234");

    Predicate actual = PredicateBuilderHelper.getFirstPredicate(params);

    assertThat(actual.toString(), is(expected.toString()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullParameters() {
    PredicateBuilderHelper.getFirstPredicate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void emptyParameters() {
    PredicateBuilderHelper.getFirstPredicate(new HashMap<>());
  }

}
