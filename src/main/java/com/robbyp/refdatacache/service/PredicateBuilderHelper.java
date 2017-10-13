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
// limitations under the License
package com.robbyp.refdatacache.service;

import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class PredicateBuilderHelper {

  static Predicate getFirstPredicate(Map<String, String> params) {
    Assert.notNull(params, "Parameter 'params' must not be null.");
    Assert.notEmpty(params, "Parameter 'params' must not be empty.");

    List<String> keys = new ArrayList<>(params.keySet());
    String key = keys.get(0);
    return Predicates.equal(key, params.get(key));
  }

}


