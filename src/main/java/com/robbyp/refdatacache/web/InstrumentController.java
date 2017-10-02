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
package com.robbyp.refdatacache.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.robbyp.refdatacache.service.InstrumentService;

@RestController
@RequestMapping("instruments")
public class InstrumentController {

  @Autowired
  private InstrumentService instrumentService;

  private ObjectMapper mapper = new ObjectMapper();

  @GetMapping("/{id}")
  public String findById(@PathVariable String id) {
    return instrumentService.findById(id);
  }

  @GetMapping("/")
  public String findByParams(@RequestParam(required = false) Map<String, String> params) {
    return instrumentService.findByParams(params);
  }

}