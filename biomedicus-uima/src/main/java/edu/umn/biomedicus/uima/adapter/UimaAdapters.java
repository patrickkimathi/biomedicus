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

package edu.umn.biomedicus.uima.adapter;

import edu.umn.biomedicus.uima.labels.LabelAdapters;
import edu.umn.nlpengine.Artifact;
import javax.annotation.Nullable;
import org.apache.uima.cas.CAS;

/**
 * Utility class for adapting the UIMA backend to the Biomedicus type system.
 *
 * @author Ben Knoll
 * @since 1.3.0
 */
public final class UimaAdapters {

  private UimaAdapters() {
    throw new UnsupportedOperationException("Instantiation of utility class");
  }

  /**
   * Initializes a BioMedICUS document on a UIMA CAS view.
   */
  public static Artifact createArtifact(
      CAS initialView,
      @Nullable LabelAdapters labelAdapters,
      String documentId
  ) {
    return new CASArtifact(labelAdapters, initialView, documentId);
  }

  public static Artifact getArtifact(CAS initialView, @Nullable LabelAdapters labelAdapters) {
    return new CASArtifact(labelAdapters, initialView);
  }
}
