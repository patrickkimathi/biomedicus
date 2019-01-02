/*
 * Copyright (c) 2018 Regents of the University of Minnesota.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.umn.biomedicus.common.viterbi;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link SimpleCandidateProbability}.
 */
class SimpleCandidateProbabilityTest {
  SimpleCandidateProbability<String> simpleCandidateProbability;

  @BeforeEach
  void setUp() {
    simpleCandidateProbability = new SimpleCandidateProbability<>("state", -1.0);
  }

  @Test
  void testGetCandidate() {
    assertEquals(simpleCandidateProbability.getCandidate(), "state");
  }

  @Test
  void testGetEmissionLogProbability() {
    assertEquals(simpleCandidateProbability.getEmissionLogProbability(), -1.0d);
  }
}
