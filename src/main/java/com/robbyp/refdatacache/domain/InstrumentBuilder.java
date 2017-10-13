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
package com.robbyp.refdatacache.domain;

import com.fluentinterface.builder.Builder;

public interface InstrumentBuilder extends Builder<Instrument> {
  InstrumentBuilder withIsin(String isin);

  InstrumentBuilder withCusip(String cusip);

  InstrumentBuilder withSedol(String sedol);

  InstrumentBuilder withIssuer(String issuer);

  InstrumentBuilder withIssue(String issue);

  InstrumentBuilder withCountryOfIssue(String countryOfIssue);

  InstrumentBuilder withCountryOfIncorporation(String countryOfIncorporation);
}
